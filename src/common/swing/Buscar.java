package common.swing;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import common.Messages;
import common.Vista;
import common.estructura.Almacenamiento;

public class Buscar extends JDialog {

	private static final long serialVersionUID = -323333444335042128L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private Choice choice;
	private final Vector<Almacenamiento> almace;
	private JButton okButton;
	
	public Buscar(mainWindow mainWindow, Vector<Almacenamiento> almac) {
		super(mainWindow.frmAplicacionDidacticaEstructuras, Messages.getString("SWING_BUSCAR_BUSQUEDA_ELEMENTO"), ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setBounds(100, 100, 320, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("64dlu:grow"), //$NON-NLS-1$
				ColumnSpec.decode("100dlu"), //$NON-NLS-1$
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,},
				new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		this.almace=almac;
		{
			JLabel lblElResmenSaldr = new JLabel(Messages.getString("SWING_BUSCAR_RESUMEN_POR_CONSOLA")); //$NON-NLS-1$
			lblElResmenSaldr.setHorizontalAlignment(SwingConstants.CENTER);
			lblElResmenSaldr.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
			contentPanel.add(lblElResmenSaldr, "3, 2, 4, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblElementoABuscar = new JLabel(Messages.getString("SWING_BUSCAR_ELEMENTO_A_BUSCAR")); //$NON-NLS-1$
			lblElementoABuscar.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			contentPanel.add(lblElementoABuscar, "3, 4"); //$NON-NLS-1$
		}
		{
			textField = new JTextField(6);
			textField.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					//Verificar si la tecla pulsada no es un digito
					char caracter = e.getKeyChar();

					if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) )
					{
						e.consume();  // ignorar el evento de teclado
					}else
						if(textField.getText().length()>=10)
						{
							textField.setText(textField.getText().substring(0, 9));
						}
				}
				@Override
				public void keyReleased(KeyEvent arg0) {
					if(textField.getText().length()>0)
						okButton.setEnabled(true);
					else
						okButton.setEnabled(false);
				}
			});
			textField.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			contentPanel.add(textField, "6, 4, fill, default"); //$NON-NLS-1$
			textField.setColumns(10);

		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, "1, 6, 6, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblElijaUnaEstructura = new JLabel(Messages.getString("SWING_BUSCAR_ELIJA_ESTRUCTURA")); //$NON-NLS-1$
			lblElijaUnaEstructura.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			contentPanel.add(lblElijaUnaEstructura, "3, 8, 4, 1"); //$NON-NLS-1$
		}

		{
			choice = new Choice();
			choice.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			contentPanel.add(choice, "3, 10, 4, 1"); //$NON-NLS-1$

			for (Almacenamiento a : almac) {
				for (Vista v : a.getVistas()){
					choice.add(v.getTipo());
				}
			}	        
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton(Messages.getString("SWING_BUSCAR_BUSCAR")); //$NON-NLS-1$
				okButton.setEnabled(false);
				okButton.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						buscar();
						dispose();
					}
				});
				okButton.setActionCommand(Messages.getString("SWING_BUSCAR_OK")); //$NON-NLS-1$
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	
	private void buscar(){
		Vector<Vista> vistas = new Vector<Vista>();
        for (int i = 0 ; i < almace.size() ; i++){
        	vistas.addAll(almace.elementAt(i).getVistas());
        }
        System.out.println("Hola:" + choice.getItem(choice.getSelectedIndex())); //$NON-NLS-1$
		for (Vista v: vistas){
			if (v.getTipo().equals(choice.getItem(choice.getSelectedIndex()))){
				System.out.println("Vista:"+v.getTipo()); //$NON-NLS-1$
				v.busqueda(Integer.parseInt(textField.getText()));
			}
		}
	}

}
