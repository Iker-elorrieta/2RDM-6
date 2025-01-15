package hilo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;

public class HiloServidor extends Thread {

	Socket conexionCli;

	public HiloServidor(Socket conexionCli) {
	
		this.conexionCli = conexionCli;
	}

	public void run() {
		
		boolean conectado = false;

		try {
			
			DataOutputStream salida = new DataOutputStream(conexionCli.getOutputStream());
			DataInputStream entrada = new DataInputStream(conexionCli.getInputStream());
			
			while (!conectado) {
				
				  // Mando el primer mensaje al cliente
	            salida.writeUTF("Hola Cliente");
	            
	            // Leo el primer mensaje del cliente
	            String mensajeCliente1 = entrada.readUTF();
	            System.out.println("Mensaje de Cliente: " + mensajeCliente1);
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
}
