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

import modelo.Users;

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
	            
	            // Leo el usuario y contraseña mandados por el cliente
	            String mensajeUsuario = entrada.readUTF();
	            System.out.println("Usuario: " + mensajeUsuario);
	            
	            int mensajeContra = entrada.readInt();
	            System.out.println("Contrasena: " + mensajeContra);
	           
	            //Busco el id del usuario
				int idUsuarioLogueado= buscarId(mensajeUsuario,mensajeContra);
				
				//Mando el ID al Cliente
				salida.writeInt(idUsuarioLogueado);
				

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
	}

	private int buscarId(String mensajeUsuario, int mensajeContra) {
		int idUsuarioLogueado=0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = null;
		
		try {
			
			session = sessionFactory.openSession();
		        // Consulta para obtener los usuarios
		    String hql = "FROM Users u WHERE u.username= :usuario and u.password= :contrasena"; 
			  Query query = session.createQuery(hql);
			  query.setParameter("usuario", mensajeUsuario);
			  query.setParameter("contrasena", String.valueOf(mensajeContra));
				  
				 @SuppressWarnings("unchecked")
				List<Users> lusuarios = (List<Users>) query.list();
				 
				 for (Users usuario : lusuarios) {
		             System.out.println("ID: " + usuario.getId());
		             idUsuarioLogueado=usuario.getId();
		         }

		    } catch (Exception e) {
		        e.printStackTrace();
		        JOptionPane.showMessageDialog(null, "Error al cargar los profesores de la base de datos.");
		    }
		
		
		session.close();
		return idUsuarioLogueado;
	}

	
	
}
