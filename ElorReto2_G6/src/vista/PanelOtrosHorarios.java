package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;

public class PanelOtrosHorarios extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnVolver;

	/**
	 * Create the panel.
	 */
	public PanelOtrosHorarios() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblOtrosHorarios = new JLabel("OTROS HORARIOS");
		lblOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblOtrosHorarios.setBounds(182, 85, 244, 28);
		add(lblOtrosHorarios);
		
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
