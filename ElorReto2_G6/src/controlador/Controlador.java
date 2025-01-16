package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hibernate.HibernateUtil;
import modelo.Ciclos;
import modelo.Users;
import vista.PanelLogin;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener {
	private ArrayList<Users> usuarios;
	private vista.Principal vistaPrincipal;
	private PanelLogin panelLogin;
	
	private Socket cliente;
	private DataOutputStream salida;
	private DataInputStream entrada;
	private Users usuario = new Users();
	
	private String user; //Usuario recogido del TextField
	private String pass; //Contraseña recogido del TextField
	
	private String usuarioConectado; //Para almacenar el usuario con el que me he logueado
	private String contrasenaConectada; //Para almacenar la contraseña con el que me he logueado
	
	private static int cicloId = 6; 
	private static String nombreCiclo = "ARI";
	
	private String host = "localhost";
	private int puerto = 5000;
	
	private Session session;

	/*
	 * *** CONSTRUCTORES ***
	 */

	/*
	 * Contructor del objeto controlador
	 * 
	 * @param vistaPrincipal Objeto vista.
	 */
	public Controlador(vista.Principal vistaPrincipal) {
	    this.vistaPrincipal = vistaPrincipal;
	    this.inicializarControlador();
	    this.session = HibernateUtil.getSessionFactory().openSession();
	    cargarUsuariosDesdeBD(); // Cargar los usuarios al iniciar el controlador
	   
	    
	    
	 // Agregar Boton de cierre de ventana para manejar el cierre
	    
 		vistaPrincipal.addWindowListener(new WindowAdapter() {
 			@Override
 			public void windowClosing(WindowEvent e) {
 				
 				cerrarSesion();
 				System.exit(0); 
 			}
 		});
	}

	private void inicializarControlador() {
		

		// Atribuyo los paneles
		panelLogin = this.vistaPrincipal.getPanelLogin();
		this.vistaPrincipal.getPanelMenu();

		
		// Acciones en el Panel Login

		this.vistaPrincipal.getPanelLogin().getBtnContinuar().addActionListener(this);
		this.vistaPrincipal.getPanelLogin().getBtnContinuar()
				.setActionCommand(Principal.enumAcciones.INSERTAR_LOGIN.toString());

		// Acciones en el Panel Menu

		this.vistaPrincipal.getPanelMenu().getBtnConsultar().addActionListener(this);
		this.vistaPrincipal.getPanelMenu().getBtnConsultar()
				.setActionCommand(Principal.enumAcciones.CARGAR_HORARIOS.toString());
		
		this.vistaPrincipal.getPanelMenu().getBtnOtrosHorarios().addActionListener(this);
		this.vistaPrincipal.getPanelMenu().getBtnOtrosHorarios()
				.setActionCommand(Principal.enumAcciones.CARGAR_OTROS_HORARIOS.toString());
		
		this.vistaPrincipal.getPanelMenu().getBtnVerReuniones().addActionListener(this);
		this.vistaPrincipal.getPanelMenu().getBtnVerReuniones()
				.setActionCommand(Principal.enumAcciones.CARGAR_REUNIONES.toString());
		
		this.vistaPrincipal.getPanelMenu().getBtnDesconectar().addActionListener(this);
		this.vistaPrincipal.getPanelMenu().getBtnDesconectar()
				.setActionCommand(Principal.enumAcciones.DESCONECTAR.toString());
		
		// Acciones en el Panel Horarios
		
		this.vistaPrincipal.getPanelHorarios().getBtnVolver().addActionListener(this);
		this.vistaPrincipal.getPanelHorarios().getBtnVolver()
				.setActionCommand(Principal.enumAcciones.CARGAR_MENU.toString());
		
		// Acciones en el Panel Otros Horarios
		
		this.vistaPrincipal.getPanelOtrosHorarios().getBtnVolver().addActionListener(this);
		this.vistaPrincipal.getPanelOtrosHorarios().getBtnVolver()
				.setActionCommand(Principal.enumAcciones.CARGAR_MENU.toString());
		
		// Acciones en el Panel Reuniones
		
		this.vistaPrincipal.getPanelReuniones().getBtnVolver().addActionListener(this);
		this.vistaPrincipal.getPanelReuniones().getBtnVolver()
				.setActionCommand(Principal.enumAcciones.CARGAR_MENU.toString());
		
	

	}

	/*** Tratamiento de las acciones ***/

	@Override
	public void actionPerformed(ActionEvent e) {
		
		Principal.enumAcciones accion = Principal.enumAcciones.valueOf(e.getActionCommand());

		switch (accion) {
		
		case CARGAR_LOGIN:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_LOGIN);
			break;
					
		case CARGAR_MENU:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_MENU);
			break;
			
		case CARGAR_HORARIOS:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_HORARIOS);
			break;
		
		case CARGAR_OTROS_HORARIOS:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_OTROS_HORARIOS);
			break;
		
		case CARGAR_REUNIONES:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_REUNIONES);
			break;
			
			
		case INSERTAR_LOGIN:
			insertarLogin();
			conectarConServidor(cliente,salida,entrada);
			insertarCicloEnBD(accion);
			break;
			
			
		case DESCONECTAR:
			
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_LOGIN);
			JOptionPane.showMessageDialog(null, "Se ha desconectado con exito.");
			break;

		default:
			
			break;

		}

	}


	//Consulta Hibernate para recoger todos los profesores
	
	private void cargarUsuariosDesdeBD() {
	    
		usuarios = new ArrayList<>();

	    try {
	        // Consulta para obtener los usuarios
	        String hql = "FROM Users u WHERE u.tipos.name = 'profesor'";
	        Query q = session.createQuery(hql);
	        
	        @SuppressWarnings("unchecked")
	        List<Users> users = q.list();

	        // Agregar los usuarios al ArrayList 'usuarios'
	        for (Users user : users) {
	            usuarios.add(user);
	            System.out.println("Usuario: " + user.getUsername());
	            System.out.println("Contraseña: " + user.getPassword());
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar los profesores de la base de datos.");
	    }
	}

	//Método de validación de Login 
	public void insertarLogin() {
		
	     user = panelLogin.getUserTxt().getText().trim();
	     pass = new String(panelLogin.getPassTxt().getPassword()).trim();

	    if (!user.isEmpty() && !pass.isEmpty()) {
	        boolean usuarioValido = false;

	        for (Users usu : usuarios) {
	        	
	            if (user.equals(usu.getUsername()) && pass.equals(usu.getPassword())) { 
	            	
	            	usuarioValido = true;
	            	JOptionPane.showMessageDialog(null, "Usuario Correcto");	
	            	
	            	//Almaceno el usuario y la contraseña
	            	usuarioConectado=user;
	            	contrasenaConectada=pass;
	            	
	            	this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_MENU);
	             }
	        }

	        if (!usuarioValido) {
	            JOptionPane.showMessageDialog(null, "Usuario o Contraseña Incorrectos");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Rellene todos los campos");
	    }

	    panelLogin.getUserTxt().setText("");
	    panelLogin.getPassTxt().setText("");
	    
	    
	}
	
	//Conexionado del cliente con el servidor
	
	private void conectarConServidor(Socket cliente, DataOutputStream salida, DataInputStream entrada) {

	       try {
	    	   
			cliente = new Socket(host, puerto); 
			System.out.println("Conectado al servidor con puerto: " + puerto);
			
			entrada = new DataInputStream(cliente.getInputStream());
	        salida = new DataOutputStream(cliente.getOutputStream());
	        

	        // Recibo y contesto al primer mensaje del servidor
	        String preguntaServidor1 = entrada.readUTF();
	        System.out.println(preguntaServidor1);
	        
	        String fraseParaServidor = "Hola Servidor, te mando mi usuario y contraseña.";
	        salida.writeUTF(fraseParaServidor); 
	        
	        // Enviar el usuario y la contraseña al servidor
	        if (usuarioConectado != null && contrasenaConectada != null) {
	        	
	        	int contrasena= Integer.parseInt(contrasenaConectada);
	        	
	            salida.writeUTF(usuarioConectado);
	            salida.writeInt(contrasena);
	        }

	        // Recibo el mensaje con el ID del servidor
	        int miID = entrada.readInt();
	        System.out.println("Mi ID es: "+miID);
		
	       } catch (IOException e) {
			
			e.printStackTrace();
		
	       }
	       
	}


	//Insercion de un ciclo nuevo en la base de datos SQL
	
	private void insertarCicloEnBD(enumAcciones accion) {
	    Transaction tx = null;
	   
	    try {
	        // Comprobar si el ciclo ya existe en la base de datos
	         cicloId = 6; 
	         nombreCiclo = "ARI";

	        Ciclos cicloExistente = (Ciclos) session.createQuery("FROM Ciclos WHERE id = :cicloId")
	                .setParameter("cicloId", cicloId)
	                .uniqueResult();

	        if (cicloExistente == null) {
	            // Si no existe, se inserta un nuevo ciclo
	            tx = session.beginTransaction();
	            System.out.println("Insertando Ciclo nuevo...\n");

	            Ciclos nuevoCiclo = new Ciclos(cicloId, nombreCiclo);
	            session.save(nuevoCiclo);

	            tx.commit();
	            System.out.println("Ciclo ARI insertado con éxito");

	        } else {
	            System.out.println("El ciclo con ID " + cicloId + " ya ha sido insertado previamente.");
	        }

	    } catch (Exception e1) {
	    	
	        if (tx != null)
	            tx.rollback();
	        e1.printStackTrace();
	        
	    }
	}
	
	
	//Metodo para que al desconectarme cierre la sesion
	private void cerrarSesion() {
		 if (session != null && session.isOpen()) {
		        session.close();
		        System.out.println("Sesion Cerrada con exito");
		 }
	}
}
