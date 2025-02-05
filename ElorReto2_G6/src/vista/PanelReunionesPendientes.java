package vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class PanelReunionesPendientes extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnVolver,btnAceptar,btnRechazar;
	private JTable tablaPendientes,tablaOtros;
	

	/**
	 * Create the panel.
	 */
	public PanelReunionesPendientes() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblReuniones = new JLabel("REUNIONES PENDIENTES");
		lblReuniones.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblReuniones.setBounds(254, 76, 373, 28);
		add(lblReuniones);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVolver.setBounds(25, 31, 98, 34);
		add(btnVolver);
		
		JScrollPane scrollPendientes = new JScrollPane();
		scrollPendientes.setBounds(82, 142, 686, 173);
		add(scrollPendientes);
		
		tablaPendientes = new JTable();
		tablaPendientes.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		scrollPendientes.setViewportView(tablaPendientes);
		tablaPendientes.setFillsViewportHeight(true);
		
		
		tablaPendientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaPendientes.setRowSelectionAllowed(true);
		tablaPendientes.setCellSelectionEnabled(true);
		tablaPendientes.setDefaultEditor(Object.class, null);

		
		btnAceptar = new JButton("ACEPTAR");
		btnAceptar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnAceptar.setBounds(158, 342, 118, 34);
		add(btnAceptar);
		
		btnRechazar = new JButton("RECHAZAR");
		btnRechazar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnRechazar.setBounds(586, 342, 118, 34);
		add(btnRechazar);
		
		JLabel lblOtrasReuniones = new JLabel("OTRAS REUNIONES");
		lblOtrasReuniones.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblOtrasReuniones.setBounds(288, 415, 373, 28);
		add(lblOtrasReuniones);
		
		JScrollPane scrollPane_Otros = new JScrollPane();
		scrollPane_Otros.setBounds(82, 475, 686, 123);
		add(scrollPane_Otros);
		
		tablaOtros = new JTable();
		tablaOtros.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaOtros.setAutoResizeMode(JTable.HEIGHT);
		scrollPane_Otros.setViewportView(tablaOtros);
		tablaOtros.setFillsViewportHeight(true);


	}
	

	public JTable getTablaPendientes() {
		return tablaPendientes;
	}

	public void setTablaPendientes(JTable tablaPendientes) {
		this.tablaPendientes = tablaPendientes;
	}

	public JButton getBtnVolver() {
		return btnVolver;
	}
	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}


	public JTable getTablaOtros() {
		return tablaOtros;
	}

	public void setTablaOtros(JTable tablaOtros) {
		this.tablaOtros = tablaOtros;
	}


	public JButton getBtnAceptar() {
		return btnAceptar;
	}


	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}


	public JButton getBtnRechazar() {
		return btnRechazar;
	}


	public void setBtnRechazar(JButton btnRechazar) {
		this.btnRechazar = btnRechazar;
	}
	
	
	
}