package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
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
	private ObjectInputStream entradaObj;
	
	private String usuarioTxt; // Usuario recogido del TextField
	private String passTxt; // Contraseña recogido del TextField
	private int idProfesor; // Id del profesor introducido
	private int idProfesorSeleccionado;  // Id del profesor seleccionado en el combobox
	
	private int opcion;
	private JComboBox<String> comboBox;

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
	
	private void incializarServidor() {
	
		try {
			cliente = new Socket(host, puerto);
			salida = new DataOutputStream(cliente.getOutputStream());
			entrada = new DataInputStream(cliente.getInputStream());
			entradaObj = new ObjectInputStream(cliente.getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void inicializarControlador() {
		
		//Atribuyo el combobox del Panel Otros Horarios
		comboBox= this.vistaPrincipal.getPanelOtrosHorarios().getCmbProfesor();
		

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
		
		this.vistaPrincipal.getPanelOtrosHorarios().getBtnSeleccionar().addActionListener(this);
		this.vistaPrincipal.getPanelOtrosHorarios().getBtnSeleccionar()
				.setActionCommand(Principal.enumAcciones.SELECCIONAR_PROFESOR.toString());

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
			mMostrarProfesores(accion);
			break;

		case CARGAR_REUNIONES:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_REUNIONES);
			break;

		case INSERTAR_LOGIN:
			
			insertarLogin(accion); // Traspaso del usuario y contraseña introducidos en los TextField
			break;
			
		case SELECCIONAR_PROFESOR:
			profesorSeleccionado(comboBox,accion);
			
			break;

		case DESCONECTAR:

			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_LOGIN);
			JOptionPane.showMessageDialog(null, "Se ha desconectado con exito.");
			break;

		default:

			break;

		}

	}


	private void insertarLogin(enumAcciones accion) {
		opcion=1;

		try {
			usuarioTxt = this.vistaPrincipal.getPanelLogin().getUserTxt().getText().trim();
			passTxt = new String(this.vistaPrincipal.getPanelLogin().getPassTxt().getPassword()).trim();

			if (!usuarioTxt.isEmpty() && !passTxt.isEmpty()) {

				salida.writeInt(opcion);
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

	// Metodo Para Mostrar Horarios del profesor Logeado
	
	private void mMostrarHorarios(enumAcciones accion) {
	    opcion = 2;
	    try {
	        salida.writeInt(opcion);
	        
	        // Utiliza idProfesor para cargar el horario del profesor logueado
	        salida.writeInt(idProfesor); 
	        salida.flush();

	        String[][] horarioProfesor = (String[][]) entradaObj.readObject();

	        cargarHorario(horarioProfesor, this.vistaPrincipal.getPanelHorarios().getTablaHorarios());

	    } catch (IOException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}


	// Metodo Para Mostrar Horarios del profesor seleccionado
	
	private JComboBox<String> mMostrarProfesores(enumAcciones accion) {
		opcion=3;
		try {
			
			salida.writeInt(opcion);
			salida.writeInt(idProfesor);
			salida.flush();
			
			//Recojo el array de profesores
			String[] profesorado= (String[]) entradaObj.readObject();
			
			//Lo adhiero al comboBox
			comboBox.removeAllItems();
			// Itera sobre el array y añade cada profesor al comboBox
			for (String profesor : profesorado) {
			    comboBox.addItem(profesor);
			}
			
			// Mensaje de confirmación
	        JOptionPane.showMessageDialog(null, "Profesores cargados correctamente.");

	    } catch (IOException | ClassNotFoundException e) {
	    	
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al cargar los profesores.");
	    }
		return comboBox;
	}
	
	// Metodo Para Mostrar El comboBox con los demás profesores
	
	private void profesorSeleccionado(JComboBox<String> comboBox, enumAcciones accion) {
	    opcion = 4;
	    String profesorSeleccionado = (String) comboBox.getSelectedItem();

	    try {
	        salida.writeInt(opcion);
	        salida.writeUTF(profesorSeleccionado);

	        // Actualiza el ID del profesor seleccionado 
	        idProfesorSeleccionado = entrada.readInt(); 
	        
	        System.out.println("ID Profesor Seleccionado: " + idProfesorSeleccionado);
	        JOptionPane.showMessageDialog(null, "Profesor Seleccionado Correctamente");

	        // Muestra los horarios del profesor seleccionado 
	        mostrarHorariosSeleccionado();

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	// Metodo Para Mostrar Horarios del profesor Seleccionado en el comboBox
	
	private void mostrarHorariosSeleccionado() {
		 opcion = 2;
		    try {
		        salida.writeInt(opcion);
		        
		        // Utiliza el ID del profesor seleccionado 
		        salida.writeInt(idProfesorSeleccionado); 
		        salida.flush();

		        String[][] horarioProfesor = (String[][]) entradaObj.readObject();

		        cargarHorario(horarioProfesor, this.vistaPrincipal.getPanelHorarios().getTablaHorarios());
		        this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_HORARIOS);

		    } catch (IOException | ClassNotFoundException e) {
		        e.printStackTrace();
		    }	
	}

	// Metodo para cargar el horario del profesor

	private void cargarHorario(String[][] horarioProfesor, JTable tablaHorarios) {
		
		DefaultTableModel modelo = new DefaultTableModel(horarioProfesor,
				new String[] { "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaHorarios.setModel(modelo);
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