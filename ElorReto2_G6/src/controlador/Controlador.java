package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


import hibernate.HibernateUtil;
import modelo.Users;
import vista.PanelLogin;
import vista.Principal;

public class Controlador implements ActionListener {
	private ArrayList<Users> usuarios;
	private vista.Principal vistaPrincipal;
	private PanelLogin panelLogin;

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
	    cargarUsuariosDesdeBD(); // Cargar los usuarios al iniciar el controlador
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
			break;

		default:
			
			break;

		}

	}

	//Consulta Hibernate para recoger todos los profesores
	
	private void cargarUsuariosDesdeBD() {
		
		usuarios = new ArrayList<>(); 
		 
		SessionFactory sesion = HibernateUtil.getSessionFactory();
        Session session = sesion.openSession();
        
        try {
            
        	// Consulta para obtener los profesores
        	
            String hql =  "FROM Users u WHERE u.tipos.name = 'profesor'";
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

        session.close();
        sesion.close();
		
	}


	public void insertarLogin() {
		
	    String usuario = panelLogin.getUserTxt().getText().trim();
	    String pass = new String(panelLogin.getPassTxt().getPassword()).trim();

	    if (!usuario.isEmpty() && !pass.isEmpty()) {
	        boolean usuarioValido = false;

	        for (Users usu : usuarios) {
	        	
	            if (usuario.equals(usu.getUsername()) && pass.equals(usu.getPassword())) { 
	            	
	            	usuarioValido = true;
	            	JOptionPane.showMessageDialog(null, "Usuario Correcto");	
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
	

	
	
	
	
}
