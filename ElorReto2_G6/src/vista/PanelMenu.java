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
		btnConsultar.setBounds(135, 153, 270, 34);
		add(btnConsultar);
		
		btnOtrosHorarios = new JButton("CONSULTAR OTROS HORARIOS");
		btnOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOtrosHorarios.setBounds(135, 228, 270, 34);
		add(btnOtrosHorarios);
		
		btnVerReuniones = new JButton("VER REUNIONES");
		btnVerReuniones.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVerReuniones.setBounds(135, 302, 270, 34);
		add(btnVerReuniones);
		
		btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDesconectar.setBounds(10, 11, 133, 34);
		add(btnDesconectar);
		
		JLabel lblFoto1 = new JLabel("New label");
		lblFoto1.setBounds(427, 140, 66, 64);
		add(lblFoto1);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto1, "C:\\Users\\in2dm3-a\\git\\2RDM-6\\ElorReto2_G6\\archivos\\horario.jpg");
		
		JLabel lblFoto2 = new JLabel("New label");
		lblFoto2.setBounds(427, 215, 66, 70);
		add(lblFoto2);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto2, "C:\\Users\\in2dm3-a\\git\\2RDM-6\\ElorReto2_G6\\archivos\\otrosHorarios.png");
		
		JLabel lblFoto3 = new JLabel("New label");
		lblFoto3.setBounds(427, 296, 66, 64);
		add(lblFoto3);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto3, "C:\\Users\\in2dm3-a\\git\\2RDM-6\\ElorReto2_G6\\archivos\\reunion.jpg");

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
