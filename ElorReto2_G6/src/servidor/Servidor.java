package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import hilo.HiloServidor;

public class Servidor {

	public static void main(String[] args) {
	
		int puerto = 5000;
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
		
			e.printStackTrace();
		}	
	}
}