package common.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Generando extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3548905569646750622L;
	

	public Generando(mainWindow mainWindow) {
		super(mainWindow.frmAplicacionDidacticaEstructuras, "", ModalityType.APPLICATION_MODAL);
		this.setResizable(false);
		setUndecorated(true);
		this.setBackground(new Color(Color.TRANSLUCENT));
		setBounds(100, 100, 450, 150);
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Generando.class.getResource("/img/generando.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel, BorderLayout.CENTER);

	}

}
