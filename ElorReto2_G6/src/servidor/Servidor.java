package servidor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import hilo.HiloServidor;

public class Servidor {

	public static void main(String[] args) {
		
		
		//Conexion con el JSON
		JsonParser parser = new JsonParser();
        String url = "archivos/Centros-Lat-Lon.json";
        
        try {
            // Abro el archivo y lo convierto en un "objeto"
            FileReader fr = new FileReader(url);
            JsonElement datos = parser.parse(fr);
            int[] cont = {0};

            
            // Llamo al método para recorrer el json
            comprobarElemento(datos, cont);
            System.out.println("\n\nDatos del JSON correctamente recogidos.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error al leer el archivo");
        }
    
	
		int puerto = 5000;
        System.out.println("\n\nEsperando conexion de cliente...\n");
        ServerSocket servidor;
        boolean conectado = false;
		try {
			servidor = new ServerSocket(puerto);
			while (!conectado) {
				Socket conexionCliente = servidor.accept();
				  System.out.println("Usuario conectado al puerto: " + puerto);
				HiloServidor hiloServidor = new HiloServidor(conexionCliente);
				hiloServidor.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
    private static void comprobarElemento(JsonElement elemento, int[] cont) {
        cont[0] = cont[0] + 1;
        System.out.println("Ejecuciones del método: " + cont[0]);
        if (elemento.isJsonArray()) {
            JsonArray array = elemento.getAsJsonArray();
            // Imprimo el número de elenentos
            System.out.println("Array. Número de elementos: " + array.size());
            // Bucle para leer cada elemento del array
            for (JsonElement element : array) {
                // Vuelvo a llamar al método para hacer otra comprobación entera
                comprobarElemento(element, cont);
            }
            
        }else if(elemento.isJsonObject()) {
            JsonObject objeto = elemento.getAsJsonObject();
            System.out.println("Objeto: ");
            // Bucle para recorrer cada atributo del objeto
            for (Map.Entry<String, JsonElement> entry : objeto.entrySet()) {
                // Recojo e imprimo el nombre del atributo
                System.out.println("Atributo: " + entry.getKey());
                // LLamo de nuevo al método para hacer otra comprobación completa
                comprobarElemento(entry.getValue(), cont);
            }
        }else if (elemento.isJsonPrimitive()) {
                JsonPrimitive primi = elemento.getAsJsonPrimitive();
                // Si es String, lo imprimo
                if (primi.isString()) {
                    System.out.println("\tTexto: " + elemento.getAsString());
                // Si es un número, lo imprimo
                } else if (primi.isNumber()) {
                    System.out.println("\tNúmero: " + elemento.getAsNumber());
                // Si es un booleano, lo imprimo
                } else if (primi.isBoolean()) {
                    System.out.println("\tBooleano: " + elemento.getAsBoolean());
                // Si es otro tipo, lo imprimo
                } else {
                    System.out.println("\tOtro: " + elemento.getAsString());
                }
        }else if (elemento.isJsonNull()) {
            // Lo imprimo
            System.out.println("\tValor: null");
            
        // Por si no es ninguno de las otras opciones
        } else {
            System.out.println("\tTipo de dato desconocido");
        }
     
    
	}
}