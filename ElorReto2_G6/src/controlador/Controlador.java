package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import vista.PanelHorarios;
import vista.PanelLogin;
import vista.PanelMenu;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener {
	// private ArrayList<Users> usuariosArrayList;

	private vista.Principal vistaPrincipal;
	private PanelLogin panelLogin;
	private PanelMenu panelMenu;
	private PanelHorarios panelHorario;

	private Socket cliente;
	private DataOutputStream salida;
	private DataInputStream entrada;

	private String usuarioTxt; // Usuario recogido del TextField
	private String passTxt; // Contraseña recogido del TextField
	private int idProfesor; // Id del profesor introducido

	private String host = "localhost";
	private int puerto = 5000;

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
		incializarServidor(); // Inicializacion de conexionado con el servidor

		// Agregar Boton de cierre de ventana para manejar el cierre

		vistaPrincipal.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				cerrarSesion();
			}
		});
	}

	private void inicializarControlador() {

		// Atribuyo los paneles
		panelLogin = this.vistaPrincipal.getPanelLogin();
		panelMenu = this.vistaPrincipal.getPanelMenu();
		panelHorario = this.vistaPrincipal.getPanelHorarios();

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
			mMostrarHorarios(accion);
			break;

		case CARGAR_OTROS_HORARIOS:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_OTROS_HORARIOS);
			break;

		case CARGAR_REUNIONES:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_REUNIONES);
			break;

		case INSERTAR_LOGIN:
			
			insertarLogin(accion); // Traspaso del usuario y contraseña introducidos en los TextField
			break;

		case DESCONECTAR:

			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_LOGIN);
			JOptionPane.showMessageDialog(null, "Se ha desconectado con exito.");
			break;

		default:

			break;

		}

	}

	private void incializarServidor() {
		// TODO Auto-generated method stub
		try {
			cliente = new Socket(host, puerto);
			salida = new DataOutputStream(cliente.getOutputStream());
			entrada = new DataInputStream(cliente.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void insertarLogin(enumAcciones accion) {
		// TODO Auto-generated method stub

		try {
			usuarioTxt = this.vistaPrincipal.getPanelLogin().getUserTxt().getText().trim();
			passTxt = new String(this.vistaPrincipal.getPanelLogin().getPassTxt().getPassword()).trim();

			if (!usuarioTxt.isEmpty() && !passTxt.isEmpty()) {

				salida.writeInt(1);
				salida.writeUTF(usuarioTxt); // Mando el valor del txtField
				salida.writeUTF(passTxt); // Mando el valor del passField
				salida.flush();

				idProfesor = entrada.readInt();
				System.out.println("Mi ID: " + idProfesor);

				if (idProfesor != 0) {
					JOptionPane.showMessageDialog(null, "Profesor Logueado Correctamente.");
					this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_MENU);
				} else {
					JOptionPane.showMessageDialog(null, "El profesor ingresado no existe.");
				}
			} else if (usuarioTxt.isEmpty() || passTxt.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Rellene todos los campos");
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	// Metodo Para Mostrar Horarios del profesor seleccionado
	private void mMostrarHorarios(enumAcciones accion) {

		DefaultTableModel dtm = new DefaultTableModel();

		dtm.addColumn("HORARIOS");
		dtm.setRowCount(0);

		panelHorario.getTablaHorarios().setModel(dtm);
		panelHorario.revalidate();
		panelHorario.repaint();

	}

	// Metodo para que al desconectarme cierre la sesion

	private void cerrarSesion() {
		try {

			// Cerrar el socket y los flujos si están inicializados

			if (salida != null) {
				salida.close();
				System.out.println("salida cerrado.");
			}
			if (entrada != null) {
				entrada.close();
				System.out.println("entrada cerrado.");
			}
			if (cliente != null && !cliente.isClosed()) {
				cliente.close();
				System.out.println("Socket cerrado.");
			}

			JOptionPane.showMessageDialog(null, "Sesión cerrada correctamente.");

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "No se ha podido cerrar la sesión.");
		} finally {

			System.exit(0);
		}
	}

}
