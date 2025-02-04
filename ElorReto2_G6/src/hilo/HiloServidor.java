package hilo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import modelo.Ciclos;
import modelo.Users;

public class HiloServidor extends Thread {

	Socket conexionCli;

	public HiloServidor(Socket conexionCli) {
	
		this.conexionCli = conexionCli;
	}

	public void run() {
		int opcion= 0;
		boolean conectado = false;
		 

		try {
			
			//Para mandar variables primitivas
			DataOutputStream salida = new DataOutputStream(conexionCli.getOutputStream());
			DataInputStream entrada = new DataInputStream(conexionCli.getInputStream());
			
			//Para mandar objetos
			ObjectOutputStream salidaObj= new ObjectOutputStream(conexionCli.getOutputStream());
			
			
			while (!conectado) {
				
				opcion = entrada.readInt();
				
				switch (opcion) {
					case 1:
						iniciarSesion(entrada, salida);
				      break;
					case 2:
						visualizarHorarioProfesor(entrada, salida, salidaObj);
						break;
					case 3:
						visualizarProfesores(entrada, salida, salidaObj);
						break;
					case 4:
						recogerIdProfesor(entrada, salida, salidaObj);
						break;
					case 5:
						visualizarReunionesProfesor(entrada, salida, salidaObj);
						break;
					case 6:
						visualizarReunionesPendientes(entrada, salida, salidaObj);
						break;
					case 7:
						visualizarOtrasReuniones(entrada, salida, salidaObj);
						break;
					case 8:
				        modificarEstadoReunion(entrada, salida);
				        break;
	
				default:
					break;
				}
				
				

			}
		} catch (IOException e) {

			System.out.println(e);
		}
	}


	private void iniciarSesion(DataInputStream entrada, DataOutputStream salida) {
		try {
			// Recibimos los datos cifrados
	        String usuarioCifrado = entrada.readUTF();
	        String contraCifrada = entrada.readUTF();
			int usuarioComprobado = new Users().insertarLogin(usuarioCifrado, contraCifrada);
			
			salida.writeInt(usuarioComprobado);
			salida.flush();
			//Inserto el ciclo nuevo al iniciar sesion de forma correcta.
			insertarCicloEnBD();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	private void insertarCicloEnBD() {
		//Creo en el modelo de Ciclos un ciclo nuevo
		new Ciclos().insertarCicloEnBD();
		
	}
	
	private void visualizarHorarioProfesor(DataInputStream entrada, DataOutputStream salida, ObjectOutputStream salidaObj) {
		try {
			int idProfesor=entrada.readInt();
			
			String[][] horarioProfesor = new Users().getHorarioById(idProfesor);
			
			salidaObj.writeObject(horarioProfesor);
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private void visualizarProfesores(DataInputStream entrada, DataOutputStream salida, ObjectOutputStream salidaObj) {
		try {
			
			int idProfesor=entrada.readInt();
			
			String[] profesores = new Users().getProfesores(idProfesor);
			
			salidaObj.writeObject(profesores);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

	private void recogerIdProfesor(DataInputStream entrada, DataOutputStream salida, ObjectOutputStream salidaObj) {
		
		try {
			
			String nombreProfesor = entrada.readUTF();
			
			int idProfesor = new Users().obtenerIdProfesor(nombreProfesor);
			
			salida.writeInt(idProfesor);
			salida.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	private void visualizarReunionesProfesor(DataInputStream entrada, DataOutputStream salida,
			ObjectOutputStream salidaObj) {
		
		try {
			
			int idProfesor=entrada.readInt();
			String[][] reunionProfesor = new Users().getReuniones(idProfesor);
			salidaObj.writeObject(reunionProfesor);
			salidaObj.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	
	private void visualizarReunionesPendientes(DataInputStream entrada, DataOutputStream salida,
			ObjectOutputStream salidaObj) {

		try {

			int idProfesor = entrada.readInt();

			// Obtener reuniones, los IDs de centros y el estado de cada reunion
			ArrayList<String[]> reunionesPendientes = new Users().getReunionesPendientes(idProfesor);
			ArrayList<String> idsCentros = new Users().getIdsCentros(idProfesor);
			ArrayList<String> estadoReuniones = new Users().getEstados(idProfesor);
			
			// Obtener los municipios de los centros educativos
			ArrayList<String> municipioCentros = obtenerMunicipios(idsCentros);
			// Obtener los nombres de los centros educativos
			ArrayList<String> nombresCentros = obtenerCentros(idsCentros);

			for (int i = 0; i < reunionesPendientes.size(); i++) {
				String[] reunion = reunionesPendientes.get(i);

				// Verificamos si el municipio existe
				String municipioCentro;

				if (i < municipioCentros.size()) {
					municipioCentro = municipioCentros.get(i);
				} else {
					municipioCentro = "Desconocido"; 
				}
				// Verificamos si el nombre del centro existe
				String nombreCentro;

				if (i < nombresCentros.size()) {

					nombreCentro = nombresCentros.get(i); 
				} else {

					nombreCentro = "Desconocido"; 
				}

				// Verificamos el estado de la reunión
				String estadoReunion;

				if (i < estadoReuniones.size()) {

					estadoReunion = estadoReuniones.get(i);
					
					
				} else {
					estadoReunion = "Desconocido"; 
				}

				// Actualizamos la reunión con el nombre del centro y el estado
				reunionesPendientes.set(i,
						new String[] { reunion[0], reunion[1], reunion[2], municipioCentro, nombreCentro, estadoReunion });
			}
			// Enviar las reuniones completas al cliente
			salidaObj.writeObject(reunionesPendientes);
			salidaObj.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	private void visualizarOtrasReuniones(DataInputStream entrada, DataOutputStream salida,
			ObjectOutputStream salidaObj) {
		try {

			int idProfesor = entrada.readInt();

			// Obtener reuniones, los IDs de centros y el estado de cada reunion
			ArrayList<String[]> otrasReuniones = new Users().getOtrasReuniones(idProfesor);
			ArrayList<String> idsOtrosCentros = new Users().getIdsOtrosCentros(idProfesor);
			ArrayList<String> estadoReuniones = new Users().getOtrosEstados(idProfesor);

			// Obtener los municipios de los centros educativos
			ArrayList<String> municipioCentros = obtenerMunicipios(idsOtrosCentros);
			// Obtener los nombres de los centros educativos
			ArrayList<String> nombresCentros = obtenerCentros(idsOtrosCentros);

			for (int i = 0; i < otrasReuniones.size(); i++) {
				String[] reunion = otrasReuniones.get(i);
				
				// Verificamos si el municipio existe
				String municipioCentro;

				if (i < municipioCentros.size()) {
					municipioCentro = municipioCentros.get(i); 
				} else {
					municipioCentro = "Desconocido"; 
				}

				// Verificamos si el nombre del centro existe
				String nombreCentro;

				if (i < nombresCentros.size()) {

					nombreCentro = nombresCentros.get(i);
				} else {

					nombreCentro = "Desconocido"; 
				}

				// Verificamos el estado de la reunión
				String estadoReunion;

				if (i < estadoReuniones.size()) {

					estadoReunion = estadoReuniones.get(i);

				} else {
					
					estadoReunion = "Desconocido";
				}

				// Actualizamos la reunión con el nombre del centro y el estado
				otrasReuniones.set(i, new String[] { reunion[0], reunion[1], reunion[2], municipioCentro, nombreCentro, estadoReunion });
			}
			// Enviar las reuniones completas al cliente
			salidaObj.writeObject(otrasReuniones);
			salidaObj.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<String> obtenerMunicipios(ArrayList<String> idsCentros) {
	    JsonParser parser = new JsonParser();
	    String url = "archivos/Centros-Lat-Lon.json";
	    Map<String, String> centrosEducativos = new HashMap<>(); // CCEN -> MUNICIPIO
	    ArrayList<String> municipiosCentros = new ArrayList<>();

	    try {
	        FileReader fr = new FileReader(url);
	        JsonElement datos = parser.parse(fr);

	        if (datos.isJsonObject()) {
	            JsonObject objetoRaiz = datos.getAsJsonObject();

	            // Verificar si tiene la clave "CENTROS"
	            if (objetoRaiz.has("CENTROS")) {
	                JsonArray centrosArray = objetoRaiz.getAsJsonArray("CENTROS");

	                // Rellenar el mapa con los datos de los centros
	                for (JsonElement elemento : centrosArray) {
	                    if (elemento.isJsonObject()) {
	                        JsonObject centro = elemento.getAsJsonObject();
	                        if (centro.has("CCEN") && centro.has("DMUNIC")) {
	                            String idCentro = String.valueOf(centro.get("CCEN").getAsInt());
	                            String municipio = centro.get("DMUNIC").getAsString();
	                            centrosEducativos.put(idCentro, municipio);
	                        }
	                    }
	                }
	            }
	        }

	        // Ordenar los municipios según los IDs proporcionados
	        for (String idCentro : idsCentros) {
	            municipiosCentros.add(centrosEducativos.getOrDefault(idCentro, "Desconocido"));
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al leer el archivo");
	    }

	    return municipiosCentros;
	}


	private ArrayList<String> obtenerCentros(ArrayList<String> idsCentros) {
	    JsonParser parser = new JsonParser();
	    String url = "archivos/Centros-Lat-Lon.json";
	    Map<String, String> centrosEducativos = new HashMap<>(); // CCEN -> NOM
	    ArrayList<String> nombresCentros = new ArrayList<>();

	    try {
	        FileReader fr = new FileReader(url);
	        JsonElement datos = parser.parse(fr);

	        if (datos.isJsonObject()) {
	            JsonObject objetoRaiz = datos.getAsJsonObject();

	            // Verificar si tiene la clave "CENTROS"
	            if (objetoRaiz.has("CENTROS")) {
	                JsonArray centrosArray = objetoRaiz.getAsJsonArray("CENTROS");

	                // Rellenar el mapa con los datos de los centros
	                for (JsonElement elemento : centrosArray) {
	                    if (elemento.isJsonObject()) {
	                        JsonObject centro = elemento.getAsJsonObject();
	                        if (centro.has("CCEN") && centro.has("NOM")) {
	                            String idCentro = String.valueOf(centro.get("CCEN").getAsInt());
	                            String nombreCentro = centro.get("NOM").getAsString();
	                            centrosEducativos.put(idCentro, nombreCentro);
	                        }
	                    }
	                }
	            }
	        }

	        // Ordenar los nombres según los IDs proporcionados
	        for (String idCentro : idsCentros) {
	            nombresCentros.add(centrosEducativos.getOrDefault(idCentro, "Desconocido"));
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	        System.out.println("Error al leer el archivo");
	    }

	    return nombresCentros;
	}
	
	
	
	
	private void modificarEstadoReunion(DataInputStream entrada, DataOutputStream salida) throws IOException {
		
		int idProfesor = entrada.readInt();
		String titulo = entrada.readUTF(); 
		String fecha = entrada.readUTF(); 
		String nuevoEstado = entrada.readUTF(); 
		
		// Llamamos al método en Users para actualizar el estado de la reunión en la base de datos
	    boolean exito = new Users().modificarEstadoReunion(idProfesor, titulo, fecha, nuevoEstado);
	    
	    // Respondemos al cliente si la operación fue exitosa o no
	    salida.writeBoolean(exito);
	    salida.flush();
		
	}
}	