package hilo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.Socket;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hibernate.HibernateUtil;
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
			
			DataOutputStream salida = new DataOutputStream(conexionCli.getOutputStream());
			DataInputStream entrada = new DataInputStream(conexionCli.getInputStream());
			
			while (!conectado) {
				
				opcion = entrada.readInt();
				
				switch (opcion) {
				case 1:
					iniciarSesion(entrada, salida);
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
	
	
}
