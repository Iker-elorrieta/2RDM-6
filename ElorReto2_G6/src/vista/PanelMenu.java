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
		lblMenu.setBounds(253, 37, 141, 28);
		add(lblMenu);
		
		btnConsultar = new JButton("CONSULTAR HORARIO");
		btnConsultar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConsultar.setBounds(134, 105, 270, 34);
		add(btnConsultar);
		
		btnOtrosHorarios = new JButton("CONSULTAR OTROS HORARIOS");
		btnOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnOtrosHorarios.setBounds(134, 183, 270, 34);
		add(btnOtrosHorarios);
		
		btnVerReuniones = new JButton("VER REUNIONES");
		btnVerReuniones.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVerReuniones.setBounds(134, 261, 270, 34);
		add(btnVerReuniones);
		
		btnDesconectar = new JButton("DESCONECTAR");
		btnDesconectar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnDesconectar.setBounds(134, 335, 270, 34);
		add(btnDesconectar);
		
		JLabel lblFoto1 = new JLabel("New label");
		lblFoto1.setBounds(426, 92, 66, 64);
		add(lblFoto1);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto1, "archivos\\horario.jpg");
		
		JLabel lblFoto2 = new JLabel("New label");
		lblFoto2.setBounds(426, 167, 66, 70);
		add(lblFoto2);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto2, "archivos\\otrosHorarios.png");
		
		JLabel lblFoto3 = new JLabel("New label");
		lblFoto3.setBounds(426, 248, 66, 64);
		add(lblFoto3);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto3, "archivos\\reunion.jpg");
		
		JLabel lblFoto4 = new JLabel("New label");
		lblFoto4.setBounds(426, 322, 66, 64);
		add(lblFoto4);

		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblFoto4, "archivos\\logout.png");
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
