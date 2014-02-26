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

public class DialogBuscar extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel paneForm = new JPanel();
	private JTextField txtElemento;
	private Choice choiceEstructura;
	private final Vector<Almacenamiento> almace;
	private JButton btnBuscar;
	
	public DialogBuscar(mainWindow mainWindow, Vector<Almacenamiento> almac) {
		super(mainWindow.frmAplicacion, Messages.getString("SWING_BUSCAR_BUSQUEDA_ELEMENTO"), ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setBounds(100, 100, 320, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		paneForm.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(paneForm, BorderLayout.CENTER);
		paneForm.setLayout(new FormLayout(new ColumnSpec[] {
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
			JLabel lblAyuda = new JLabel(Messages.getString("SWING_BUSCAR_RESUMEN_POR_CONSOLA")); //$NON-NLS-1$
			lblAyuda.setHorizontalAlignment(SwingConstants.CENTER);
			lblAyuda.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
			paneForm.add(lblAyuda, "3, 2, 4, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblElemento = new JLabel(Messages.getString("SWING_BUSCAR_ELEMENTO_A_BUSCAR")); //$NON-NLS-1$
			lblElemento.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(lblElemento, "3, 4"); //$NON-NLS-1$
		}
		{
			txtElemento = new JTextField(6);
			txtElemento.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					//Verificar si la tecla pulsada no es un digito
					char caracter = e.getKeyChar();

					if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) )
					{
						e.consume();  // ignorar el evento de teclado
					}else
						if(txtElemento.getText().length()>=10)
						{
							txtElemento.setText(txtElemento.getText().substring(0, 9));
						}
				}
				@Override
				public void keyReleased(KeyEvent arg0) {
					if(txtElemento.getText().length()>0)
						btnBuscar.setEnabled(true);
					else
						btnBuscar.setEnabled(false);
				}
			});
			txtElemento.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(txtElemento, "6, 4, fill, default"); //$NON-NLS-1$
			txtElemento.setColumns(10);

		}
		{
			JSeparator separator = new JSeparator();
			paneForm.add(separator, "1, 6, 6, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblEstructura = new JLabel(Messages.getString("SWING_BUSCAR_ELIJA_ESTRUCTURA")); //$NON-NLS-1$
			lblEstructura.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(lblEstructura, "3, 8, 4, 1"); //$NON-NLS-1$
		}

		{
			choiceEstructura = new Choice();
			choiceEstructura.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(choiceEstructura, "3, 10, 4, 1"); //$NON-NLS-1$

			for (Almacenamiento a : almac) {
				for (Vista v : a.getVistas()){
					choiceEstructura.add(v.getTipo());
				}
			}	        
		}
		{
			JPanel paneBuscar = new JPanel();
			paneBuscar.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(paneBuscar, BorderLayout.SOUTH);
			{
				btnBuscar = new JButton(Messages.getString("SWING_BUSCAR_BUSCAR")); //$NON-NLS-1$
				btnBuscar.setEnabled(false);
				btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
				btnBuscar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						buscar();
						dispose();
					}
				});
				btnBuscar.setActionCommand(Messages.getString("SWING_BUSCAR_OK")); //$NON-NLS-1$
				paneBuscar.add(btnBuscar);
				getRootPane().setDefaultButton(btnBuscar);
			}
		}
	}
	
	private void buscar(){
		Vector<Vista> vistas = new Vector<Vista>();
        
		// Obtener lista de vistas
		for (Almacenamiento a : almace){
        	vistas.addAll(a.getVistas());
        }
		
		// Encontrar la vista seleccionada y realizar busqueda
        for (Vista v: vistas){
			if (v.getTipo().equals(choiceEstructura.getItem(choiceEstructura.getSelectedIndex()))){
				System.out.println("Vista: " + v.getTipo()); //$NON-NLS-1$
				v.busqueda(Integer.parseInt(txtElemento.getText()));
			}
		}
	}

}
