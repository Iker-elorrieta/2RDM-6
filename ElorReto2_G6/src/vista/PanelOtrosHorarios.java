package vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;

public class PanelOtrosHorarios extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton btnVolver,btnSeleccionar;
	private JComboBox cmbProfesor;
	/**
	 * Create the panel.
	 */
	public PanelOtrosHorarios() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblOtrosHorarios = new JLabel("OTROS HORARIOS");
		lblOtrosHorarios.setFont(new Font("Tahoma", Font.PLAIN, 29));
		lblOtrosHorarios.setBounds(180, 93, 271, 28);
		add(lblOtrosHorarios);
		
		btnVolver = new JButton("VOLVER");
		btnVolver.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnVolver.setBounds(25, 31, 98, 34);
		add(btnVolver);
		
		JLabel lblElige = new JLabel("ELIGE UN PROFESOR");
		lblElige.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblElige.setBounds(201, 159, 271, 28);
		add(lblElige);
		
		cmbProfesor = new JComboBox();
		cmbProfesor.setFont(new Font("Tahoma", Font.PLAIN, 21));
		cmbProfesor.setBounds(99, 212, 403, 28);
		add(cmbProfesor);
		
		btnSeleccionar = new JButton("SELECCIONAR PROFESOR");
		btnSeleccionar.setBounds(209, 267, 191, 28);
		add(btnSeleccionar);

	}
	public JButton getBtnVolver() {
		return btnVolver;
	}
	public void setBtnVolver(JButton btnVolver) {
		this.btnVolver = btnVolver;
	}
	public JComboBox getCmbProfesor() {
		return cmbProfesor;
	}
	public void setCmbProfesor(JComboBox cmbProfesor) {
		this.cmbProfesor = cmbProfesor;
	}
	public JButton getBtnSeleccionar() {
		return btnSeleccionar;
	}
	public void setBtnSeleccionar(JButton btnSeleccionar) {
		this.btnSeleccionar = btnSeleccionar;
	}
	
}
