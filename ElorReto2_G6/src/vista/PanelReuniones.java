package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class PanelReuniones extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnVolver, btnVer;
	private JTable tablaReuniones;
	

	/**
	 * Create the panel.
	 */
	public PanelReuniones() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblReuniones = new JLabel("REUNIONES");
		lblReuniones.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblReuniones.setBounds(332, 76, 159, 28);
		add(lblReuniones);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVolver.setBounds(25, 31, 98, 34);
		add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 168, 735, 273);
		add(scrollPane);
		
		tablaReuniones = new JTable();
		tablaReuniones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPane.setViewportView(tablaReuniones);
		tablaReuniones.setFillsViewportHeight(true);
		
        
		btnVer = new JButton("VER PENDIENTES");
		btnVer.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVer.setBounds(322, 571, 186, 34);
		add(btnVer);

	}
	
	public JTable getTablaReuniones() {
		return tablaReuniones;
	}
	public void setTablaReuniones(JTable tablaReuniones) {
		this.tablaReuniones = tablaReuniones;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}
	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}

	public JButton getBtnVer() {
		return btnVer;
	}

	public void setBtnVer(JButton btnVer) {
		this.btnVer = btnVer;
	}
	
	
}