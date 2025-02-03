package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class PanelHorarios extends JPanel {

	private static final long serialVersionUID = 1L;

	private JButton btnVolver;
	private JTable tablaHorarios;
	/**
	 * Create the panel.
	 */
	public PanelHorarios() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblHorarios = new JLabel("HORARIOS");
		lblHorarios.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblHorarios.setBounds(337, 37, 159, 28);
		add(lblHorarios);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVolver.setBounds(25, 31, 98, 34);
		add(btnVolver);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(49, 97, 735, 289);
		add(scrollPane);
		
		tablaHorarios = new JTable();
		scrollPane.setViewportView(tablaHorarios);

	}
	public JButton getBtnVolver() {
		return btnVolver;
	}
	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}
	public JTable getTablaHorarios() {
		return tablaHorarios;
	}
	public void setTablaHorarios(JTable tablaHorarios) {
		this.tablaHorarios = tablaHorarios;
	}
	
	
}
