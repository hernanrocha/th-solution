package common.swing;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class DialogGenerando extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3548905569646750622L;
	

	public DialogGenerando(mainWindow mainWindow) {
		super(mainWindow.frmAplicacion, "", ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setResizable(false);
		setUndecorated(true);
		setBackground(new Color(Color.TRANSLUCENT));
		setBounds(100, 100, 450, 150);
		
		// Imagen Generando
		JLabel lblGenerando = new JLabel(""); //$NON-NLS-1$
		lblGenerando.setIcon(new ImageIcon(DialogGenerando.class.getResource("/img/generando.png"))); //$NON-NLS-1$
		lblGenerando.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblGenerando, BorderLayout.CENTER);

	}

}
