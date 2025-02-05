package vista;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;


	//Acciones
	public static  enum enumAcciones{
		
		CARGAR_LOGIN,
		CARGAR_MENU,
		CARGAR_HORARIOS,
		CARGAR_OTROS_HORARIOS,
		CARGAR_REUNIONES,
		CARGAR_PENDIENTES,
		
		INSERTAR_LOGIN,
		SELECCIONAR_PROFESOR,
		ACEPTAR_REUNION,
		RECHAZAR_REUNION,
		DESCONECTAR,
		
		
		
		
	}
	
	
	private JPanel panelContenedor;
	
	
	private PanelLogin panelLogin;
	private PanelMenu panelMenu;
	private PanelHorarios panelHorarios;
	private PanelOtrosHorarios panelOtrosHorarios;
	private PanelReuniones panelReuniones;
	private PanelReunionesPendientes panelReunionesPendientes;
	
	public Principal() {
		
		//Panel que contiene todo el contenido de la ventana
		mCrearPanelContenedor(); 

		//Panel de Login
		mCrearPanelLogin();
		
		//Panel Menu
		mCrearPanelMenu();
		
		//Panel de Horarios
		mCrearPanelHorarios();
		
		//Panel de Otros Horarios
		mCrearPanelOtrosHorarios();
		
		//Panel de Reuniones
		mCrearPanelReuniones();
		
		//Panel de Reuniones Pendientes
		mCrearPanelReunionesPendientes();
		
		
	}



	private void mCrearPanelContenedor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(600, 200, 641, 475);
		panelContenedor = new JPanel();
		panelContenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(panelContenedor);
		panelContenedor.setLayout(null);
		
	}
	
	 private void mCrearPanelLogin() {
			
		panelLogin = new PanelLogin();
		panelLogin.setBackground(new Color(255, 255, 255));
		panelLogin.setBounds(0, 0, 984, 561);
		panelContenedor.add(panelLogin);
		panelLogin.setVisible(true);
		
	}
	
	 
	private void mCrearPanelMenu() {
		
		panelMenu = new PanelMenu();
		panelMenu.setBackground(new Color(255, 255, 255));
		panelMenu.setBounds(0, 0, 984, 561);
		panelContenedor.add(panelMenu);
		panelMenu.setVisible(false);
	}
	
    private void mCrearPanelHorarios() {
    	panelHorarios = new PanelHorarios();
    	panelHorarios.setBackground(new Color(255, 255, 255));
    	panelHorarios.setBounds(0, 0, 840, 480);
		panelContenedor.add(panelHorarios);
		panelHorarios.setVisible(false);
		
	}
    
    private void mCrearPanelOtrosHorarios() {
    	panelOtrosHorarios = new PanelOtrosHorarios();
    	panelOtrosHorarios.setBackground(new Color(255, 255, 255));
    	panelOtrosHorarios.setBounds(0, 0, 984, 561);
		panelContenedor.add(panelOtrosHorarios);
		panelOtrosHorarios.setVisible(false);
		
	}
    
    
	private void mCrearPanelReuniones() {
		panelReuniones = new PanelReuniones();
		panelReuniones.setBackground(new Color(255, 255, 255));
		panelReuniones.setBounds(0, 0, 840, 675);
		panelContenedor.add(panelReuniones);
		panelReuniones.setVisible(false);
		
	}

	private void mCrearPanelReunionesPendientes() {
		panelReunionesPendientes = new PanelReunionesPendientes();
		panelReunionesPendientes.setBackground(new Color(255, 255, 255));
		panelReunionesPendientes.setBounds(0, 0, 840, 740);
		panelContenedor.add(panelReunionesPendientes);
		panelReunionesPendientes.setVisible(false);
		
	}
	
	
	
	//Visualizar Paneles
	
	
	public void mVisualizarPaneles(enumAcciones panel) {
		
		panelLogin.setVisible(false);
		panelMenu.setVisible(false);
		panelHorarios.setVisible(false);
		panelOtrosHorarios.setVisible(false);
		panelReuniones.setVisible(false);
		panelReunionesPendientes.setVisible(false);
		
		switch (panel) {
			
	        case CARGAR_LOGIN:
	            panelLogin.setVisible(true);
	            setBounds(600, 200, 641, 475); 
	            break;
	
	        case CARGAR_MENU:
	            panelMenu.setVisible(true);
	            setBounds(600, 200, 641, 475); 
	            break;
	
	        case CARGAR_HORARIOS:
	            panelHorarios.setVisible(true);
	            setBounds(600, 200, 840, 480);
	            break;
	
	        case CARGAR_OTROS_HORARIOS:
	            panelOtrosHorarios.setVisible(true);
	            setBounds(600, 200, 641, 475); 
	            break;
	
	        case CARGAR_REUNIONES:
	            panelReuniones.setVisible(true);
	            setBounds(600, 200, 840, 675);
	            break;
	         
	        case CARGAR_PENDIENTES:
	            panelReunionesPendientes.setVisible(true);
	            //setBounds(600, 200, 993, 723);
	            setBounds(600, 100, 850, 740);
	            break;
	
	        default:
	            break;
		}
	}
	
	
	//Getters Setters Contenedor
	
	public JPanel getPanelContenedor() {
		return panelContenedor;
	}

	public void setPanelContenedor(JPanel panelContenedor) {
		this.panelContenedor = panelContenedor;
	}

	//Getters Setters Login
	
	public PanelLogin getPanelLogin() {
		return panelLogin;
	}

	public void setPanelLogin(PanelLogin panelLogin) {
		this.panelLogin = panelLogin;
	}
	
	//Getters Setters Menu
	public PanelMenu getPanelMenu() {
		return panelMenu;
	}

	public void setPanelMenu(PanelMenu panelMenu) {
		this.panelMenu = panelMenu;
	}

	//Getters Setters Horarios

	public PanelHorarios getPanelHorarios() {
		return panelHorarios;
	}


	public void setPanelHorarios(PanelHorarios panelHorarios) {
		this.panelHorarios = panelHorarios;
	}

	//Getters Setters Otros Horarios

	public PanelOtrosHorarios getPanelOtrosHorarios() {
		return panelOtrosHorarios;
	}


	public void setPanelOtrosHorarios(PanelOtrosHorarios panelOtrosHorarios) {
		this.panelOtrosHorarios = panelOtrosHorarios;
	}

	//Getters Setters Reuniones

	public PanelReuniones getPanelReuniones() {
		return panelReuniones;
	}


	public void setPanelReuniones(PanelReuniones panelReuniones) {
		this.panelReuniones = panelReuniones;
	}


	//Getters Setters Reuniones Pendientes
	
	public PanelReunionesPendientes getPanelReunionesPendientes() {
		return panelReunionesPendientes;
	}



	public void setPanelReunionesPendientes(PanelReunionesPendientes panelReunionesPendientes) {
		this.panelReunionesPendientes = panelReunionesPendientes;
	}
	
	
	
}
