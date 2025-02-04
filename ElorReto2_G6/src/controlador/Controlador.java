package controlador;

import java.awt.Color;
import java.awt.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;


import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import vista.PanelHorarios;
import vista.PanelLogin;
import vista.PanelMenu;
import vista.Principal;
import vista.Principal.enumAcciones;

public class Controlador implements ActionListener {
	// private ArrayList<Users> usuariosArrayList;

	private vista.Principal vistaPrincipal;

	private Socket cliente;
	private DataOutputStream salida;
	private DataInputStream entrada;
	private ObjectInputStream entradaObj;
	

	private String usuarioTxt; // Usuario recogido del TextField
	private String passTxt; // Contraseña recogido del TextField
	private int idProfesor; // Id del profesor introducido
	private int idProfesorSeleccionado; // Id del profesor seleccionado en el combobox

	private int opcion;
	private JComboBox<String> comboBox;
	private String host = "localhost";
	private int puerto = 5000;
	private boolean otroProfesor = false; // Variable para determinar si estamos accediento al horario del profesor
											// logueado u otro profesor
	private boolean otraReunion = false; // Variable para determinar si estamos accediento a reuniones pendientes u
											// otras
	private String[][] horarioProfesor;
	private String[][] juntarHYR;

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

		// Atribuyo el combobox del Panel Otros Horarios
		comboBox = this.vistaPrincipal.getPanelOtrosHorarios().getCmbProfesor();

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

		this.vistaPrincipal.getPanelReuniones().getBtnVer().addActionListener(this);
		this.vistaPrincipal.getPanelReuniones().getBtnVer()
				.setActionCommand(Principal.enumAcciones.CARGAR_PENDIENTES.toString());

		// Acciones en el Panel Reuniones Pendientes

		this.vistaPrincipal.getPanelReunionesPendientes().getBtnVolver().addActionListener(this);
		this.vistaPrincipal.getPanelReunionesPendientes().getBtnVolver()
				.setActionCommand(Principal.enumAcciones.CARGAR_MENU.toString());

		this.vistaPrincipal.getPanelReunionesPendientes().getBtnAceptar().addActionListener(this);
		this.vistaPrincipal.getPanelReunionesPendientes().getBtnAceptar()
				.setActionCommand(Principal.enumAcciones.ACEPTAR_REUNION.toString());

		this.vistaPrincipal.getPanelReunionesPendientes().getBtnRechazar().addActionListener(this);
		this.vistaPrincipal.getPanelReunionesPendientes().getBtnRechazar()
				.setActionCommand(Principal.enumAcciones.RECHAZAR_REUNION.toString());

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
			//Hago que al volver atras desde el panel horario se resetee el id del profesor
			otroProfesor=false;
			break;

		case CARGAR_HORARIOS:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_HORARIOS);
			mMostrarHorarios(otroProfesor);
			break;

		case CARGAR_OTROS_HORARIOS:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_OTROS_HORARIOS);
			mMostrarProfesores();
			break;

		case CARGAR_REUNIONES:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_REUNIONES);
			mMostrarReuniones();
			break;

		case CARGAR_PENDIENTES:
			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_PENDIENTES);
			mMostrarReunionesPedientes(otraReunion);
			break;

		case INSERTAR_LOGIN:

			insertarLogin(); // Traspaso del usuario y contraseña introducidos en los TextField
			break;

		case SELECCIONAR_PROFESOR:
			profesorSeleccionado(comboBox);

			break;

		case ACEPTAR_REUNION:
			// Llamamos a modificarEstadoReunion con "Aceptada"
			modificarEstadoReunion("aceptada");
			break;

		case RECHAZAR_REUNION:
			// Llamamos a modificarEstadoReunion con "Rechazada"
			modificarEstadoReunion("denegada");
			break;

		case DESCONECTAR:

			this.vistaPrincipal.mVisualizarPaneles(Principal.enumAcciones.CARGAR_LOGIN);
			JOptionPane.showMessageDialog(null, "Se ha desconectado con exito.");
			break;

		default:

			break;

		}

	}

	private void insertarLogin() {
		opcion = 1;

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

	// Metodo Para Mostrar Horarios del profesor seleccionado

	private JComboBox<String> mMostrarProfesores() {
		opcion = 3;

		try {

			salida.writeInt(opcion);
			salida.writeInt(idProfesor);
			salida.flush();

			// Recojo el array de profesores
			String[] profesorado = (String[]) entradaObj.readObject();

			// Lo adhiero al comboBox
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

	private void profesorSeleccionado(JComboBox<String> comboBox) {
		opcion = 4;
		String profesorSeleccionado = (String) comboBox.getSelectedItem();

		try {
			salida.writeInt(opcion);
			salida.writeUTF(profesorSeleccionado);

			// Actualiza el ID del profesor seleccionado
			idProfesorSeleccionado = entrada.readInt();

			System.out.println("ID Profesor Seleccionado: " + idProfesorSeleccionado);
			JOptionPane.showMessageDialog(null, "Profesor Seleccionado Correctamente");
			otroProfesor = true;
			// Muestra los horarios del profesor seleccionado
			mMostrarHorarios(otroProfesor);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Metodo Para Mostrar Horarios del profesor

	private void mMostrarHorarios(boolean otroProfesor) {

		opcion = 2;

		try {
			salida.writeInt(opcion);

			// Utiliza idProfesor para cargar el horario del profesor logueado o sino el ID
			// del profesor seleccionado en el comboBox
			if (!otroProfesor) {

				salida.writeInt(idProfesor);
			} else {

				salida.writeInt(idProfesorSeleccionado);
			}
			salida.flush();

			horarioProfesor = (String[][]) entradaObj.readObject();

			cargarTabla(horarioProfesor, this.vistaPrincipal.getPanelHorarios().getTablaHorarios());

			if (otroProfesor) {

				this.vistaPrincipal.mVisualizarPaneles(enumAcciones.CARGAR_HORARIOS);
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Metodo Para Mostrar Reuniones y horarios del profesor

	private void mMostrarReuniones() {
		opcion = 2;

		try {
			salida.writeInt(opcion);
			salida.writeInt(idProfesor);
			salida.flush();

			horarioProfesor = (String[][]) entradaObj.readObject();

			opcion = 5;

			salida.writeInt(opcion);

			salida.writeInt(idProfesor);
			salida.flush();

			String[][] reunionProfesor = (String[][]) entradaObj.readObject();

			juntarHYR = new String[reunionProfesor.length][reunionProfesor[0].length];

			for (int i = 0; i < juntarHYR.length; i++) {
				for (int j = 0; j < juntarHYR[0].length; j++) {
					String horario = horarioProfesor[i][j];
					String reunion = reunionProfesor[i][j];
					if (!horario.isEmpty() && !reunion.isEmpty()) {
						juntarHYR[i][j] = horario + " + " + reunion;
					} else if (!horario.isEmpty() && reunion.isEmpty()) {
						juntarHYR[i][j] = horario;
					} else if (horario.isEmpty() && !reunion.isEmpty()) {
						juntarHYR[i][j] = reunion;
					} else {
						juntarHYR[i][j] = "";
					}
				}
			}

			cargarTabla(juntarHYR, this.vistaPrincipal.getPanelReuniones().getTablaReuniones());
			

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void mMostrarReunionesPedientes(boolean otraReunion) {

		try {

			salida.writeInt(opcion = 6);

			if (opcion == 6) {
				// Utiliza idProfesor para cargar las reuniones pendientes

				salida.writeInt(idProfesor);
				salida.flush();
				ArrayList<String[]> reunionPendiente = (ArrayList<String[]>) entradaObj.readObject();
				cargarPendientes(reunionPendiente,
						this.vistaPrincipal.getPanelReunionesPendientes().getTablaPendientes());

			}

			salida.writeInt(opcion = 7);

			if (opcion == 7) {

				// Utiliza idProfesor para cargar las reuniones que no son pendientes
				salida.writeInt(idProfesor);
				salida.flush();
				ArrayList<String[]> otrasReuniones = (ArrayList<String[]>) entradaObj.readObject();
				cargarPendientes(otrasReuniones, this.vistaPrincipal.getPanelReunionesPendientes().getTablaOtros());

			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void modificarEstadoReunion(String nuevoEstado) {

		int filaSeleccionada = this.vistaPrincipal.getPanelReunionesPendientes().getTablaPendientes().getSelectedRow();

		if (filaSeleccionada == -1) {
			JOptionPane.showMessageDialog(null, "Selecciona una reunión primero.");
			
		}

		JTable tablaPendientes = this.vistaPrincipal.getPanelReunionesPendientes().getTablaPendientes();
		DefaultTableModel modeloPendientes = (DefaultTableModel) tablaPendientes.getModel();

		// Obtener datos de la fila seleccionada
		String titulo = (String) modeloPendientes.getValueAt(filaSeleccionada, 0);
		String fecha = (String) modeloPendientes.getValueAt(filaSeleccionada, 1);
		String aula = (String) modeloPendientes.getValueAt(filaSeleccionada, 2);
		String municipio = (String) modeloPendientes.getValueAt(filaSeleccionada, 3);
		String centroEducativo = (String) modeloPendientes.getValueAt(filaSeleccionada, 4);

		opcion = 8;
		try {
			// Enviar actualización al servidor para modificar los estados de las reuniones

			salida.writeInt(opcion); 

			salida.writeInt(idProfesor);
			salida.writeUTF(titulo);
			salida.writeUTF(fecha);
			salida.writeUTF(nuevoEstado);
			salida.flush();

			 // Recibir la respuesta del servidor
	        boolean exito = entrada.readBoolean();

	        if (exito) {
	            // Actualizar visualmente
	            modeloPendientes.removeRow(filaSeleccionada);

	            JTable tablaOtros = this.vistaPrincipal.getPanelReunionesPendientes().getTablaOtros();
	            DefaultTableModel modeloOtros = (DefaultTableModel) tablaOtros.getModel();
	            modeloOtros.addRow(new String[] { titulo, fecha, aula, municipio, centroEducativo, nuevoEstado });
	        } else {
	            JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado de la reunión.");
	        }
			
			

		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al actualizar la reunión en la base de datos.");
		}
		 
	}

	// Método para cargar el horario del profesor + Las Reuniones
	private void cargarTabla(String[][] horarioProfesor, JTable tablaHorarios) {
		DefaultTableModel modelo = new DefaultTableModel(horarioProfesor,
				new String[] { "Hora/Día", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes" }) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false; // Las celdas no son editables
			}
		};
		
		tablaHorarios.setModel(modelo);
		DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				

				JTextArea textArea = new JTextArea();
				textArea.setText(value == null ? "" : value.toString());
				textArea.setWrapStyleWord(true);
				textArea.setLineWrap(true);
				System.out.println("ESTADOS ACTUALES:" + textArea.getText().toString()); 
				// Obtener el valor de la celda (el estado de la reunión)
	            String cellValue = value == null ? "" : value.toString();
	            Color bgColor = Color.WHITE;  // Color de fondo por defecto

	            if (cellValue.contains("pendiente")) {
	                bgColor = Color.YELLOW; // Color amarillo si el estado es Pendiente
	            } else if (cellValue.contains("aceptada")) {
	                bgColor = Color.GREEN; // Color verde si el estado es Confirmado
	            } else if (cellValue.contains("denegada")) {
	                bgColor = Color.RED; // Color rojo si el estado es Cancelado
	            } else if (cellValue.contains("conflicto")) {
	                bgColor = Color.GRAY; // Color gris si el estado es conflicto
	            }else {
	            	bgColor = Color.WHITE; // Color rojo si el estado es Cancelado
	            }

	            textArea.setBackground(bgColor); // Asignar el color de fondo a la celda
				return textArea;
			}
		};

		for (int i = 0; i < tablaHorarios.getColumnCount(); i++) {
			tablaHorarios.getColumnModel().getColumn(i).setCellRenderer(renderizador);
		}

		tablaHorarios.setRowHeight(50);
		
		// Actualizar la visualización de la tabla
		tablaHorarios.revalidate();
		tablaHorarios.repaint();
		
	}

	// Metodo para cargar las reuniones que estan y no pendientes del profesor

	private void cargarPendientes(ArrayList<String[]> reunionPendiente, JTable tablaPendientes) {

		String[] columnas = { "Titulo", "Fecha", "Aula", "Municipio", "Centro Educativo", "Estado" };

		// Convierte el ArrayList en un array bidimensional directamente
		String[][] datos = reunionPendiente.toArray(new String[0][]);

		DefaultTableModel modelo = new DefaultTableModel(datos, columnas) {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tablaPendientes.setModel(modelo);

		DefaultTableCellRenderer renderizador = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {

				JTextArea textArea = new JTextArea();
				textArea.setText(value == null ? "" : value.toString());
				textArea.setWrapStyleWord(true);
				textArea.setLineWrap(true);

				return textArea;
			}
		};

		for (int i = 0; i < tablaPendientes.getColumnCount(); i++) {
			tablaPendientes.getColumnModel().getColumn(i).setCellRenderer(renderizador);
		}

		tablaPendientes.setRowHeight(50);
		tablaPendientes.revalidate();
		tablaPendientes.repaint();
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