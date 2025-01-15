package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;

public class PanelLogin extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField userTxt;
	private JPasswordField passTxt;
	private JButton btnContinuar;

	/**
	 * Create the panel.
	 */
	public PanelLogin() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblLogin = new JLabel("LOGIN");
		lblLogin.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblLogin.setBounds(276, 151, 141, 28);
		add(lblLogin);
		
		JLabel lblUsuario = new JLabel("USUARIO");
		lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblUsuario.setBounds(145, 214, 104, 28);
		add(lblUsuario);
		
		JLabel lblContrasena = new JLabel("CONTRASEÑA");
		lblContrasena.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblContrasena.setBounds(112, 260, 121, 28);
		add(lblContrasena);
		
		userTxt = new JTextField();
		userTxt.setBounds(259, 214, 255, 28);
		add(userTxt);
		userTxt.setColumns(10);
		
		passTxt = new JPasswordField();
		passTxt.setBounds(259, 260, 255, 28);
		add(passTxt);
		
		btnContinuar = new JButton("CONTINUAR");
		btnContinuar.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnContinuar.setBounds(259, 337, 133, 34);
		add(btnContinuar);
		
		
		JLabel lblCentro = new JLabel();
		lblCentro.setBounds(149, 11, 319, 101);
		add(lblCentro);
		
		// Ajustar la imagen al JLabel
		ImageUtils.ajustarImagenLabel(lblCentro, "C:\\Users\\in2dm3-a\\git\\2RDM-6\\ElorReto2_G6\\archivos\\elorrieta.png");
		

	}

	public JTextField getUserTxt() {
		return userTxt;
	}

	public void setUserTxt(JTextField userTxt) {
		this.userTxt = userTxt;
	}

	public JPasswordField getPassTxt() {
		return passTxt;
	}

	public void setPassTxt(JPasswordField passTxt) {
		this.passTxt = passTxt;
	}

	public JButton getBtnContinuar() {
		return btnContinuar;
	}

	public void setBtnContinuar(JButton btnContinuar) {
		this.btnContinuar = btnContinuar;
	}
}
