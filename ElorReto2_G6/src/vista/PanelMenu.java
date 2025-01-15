package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class PanelMenu extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnConsultar, btnOtrosHorarios, btnVerReuniones, btnDesconectar;

	/**
	 * Create the panel.
	 */
	public PanelMenu() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblMenu = new JLabel("MENU");
		lblMenu.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblMenu.setBounds(254, 85, 141, 28);
		add(lblMenu);
		
		btnConsultar = new JButton("CONSULTAR HORARIO");
		btnConsultar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConsultar.setBounds(173, 154, 270, 34);
		add(btnConsultar);
		
		btnOtrosHorarios = new JButton("CONSULTAR OTROS HORARIOS");
		btnOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOtrosHorarios.setBounds(173, 229, 270, 34);
		add(btnOtrosHorarios);
		
		btnVerReuniones = new JButton("VER REUNIONES");
		btnVerReuniones.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVerReuniones.setBounds(173, 303, 270, 34);
		add(btnVerReuniones);
		
		btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDesconectar.setBounds(10, 11, 133, 34);
		add(btnDesconectar);

	}

	public JButton getBtnConsultar() {
		return btnConsultar;
	}

	public void setBtnConsultar(JButton btnConsultar) {
		this.btnConsultar = btnConsultar;
	}

	public JButton getBtnOtrosHorarios() {
		return btnOtrosHorarios;
	}

	public void setBtnOtrosHorarios(JButton btnOtrosHorarios) {
		this.btnOtrosHorarios = btnOtrosHorarios;
	}

	public JButton getBtnVerReuniones() {
		return btnVerReuniones;
	}

	public void setBtnVerReuniones(JButton btnVerReuniones) {
		this.btnVerReuniones = btnVerReuniones;
	}

	public JButton getBtnDesconectar() {
		return btnDesconectar;
	}

	public void setBtnDesconectar(JButton btnDesconectar) {
		this.btnDesconectar = btnDesconectar;
	}
	
	
}
