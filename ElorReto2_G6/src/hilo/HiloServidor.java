package hilo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

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
				default:
					break;
				}
				
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	
	private void iniciarSesion(DataInputStream entrada, DataOutputStream salida) {
		try {
			String usuario = entrada.readUTF();
			String password = entrada.readUTF();
			int usuarioComprobado = new Users().insertarLogin(usuario, password);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private void visualizarProfesores(DataInputStream entrada, DataOutputStream salida, ObjectOutputStream salidaObj) {
		try {
			
			int idProfesor=entrada.readInt();
			
			String[] profesores = new Users().getProfesores(idProfesor);
			
			salidaObj.writeObject(profesores);
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}