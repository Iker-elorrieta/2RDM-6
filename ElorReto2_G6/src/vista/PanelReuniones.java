package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class PanelReuniones extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnVolver;

	/**
	 * Create the panel.
	 */
	public PanelReuniones() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblReuniones = new JLabel("REUNIONES");
		lblReuniones.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblReuniones.setBounds(236, 85, 159, 28);
		add(lblReuniones);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVolver.setBounds(25, 31, 98, 34);
		add(btnVolver);

	}
	public JButton getBtnVolver() {
		return btnVolver;
	}
	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}
}