package common.swing;

import java.awt.BorderLayout;
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
import common.estructura.Elemento;

public class DialogGenerarElementos extends JDialog {

	private static final long serialVersionUID = -323333444335042128L;
	private final JPanel paneForm = new JPanel();
	private JTextField txtElemento;
	private JButton btnGenerar;
	private JTextField txtMinimo;
	private JTextField txtMaximo;
	private Archivo archivo;
	private mainWindow window;
	
	public DialogGenerarElementos(mainWindow mainWindow, Archivo archivo) {
		super(mainWindow.frmAplicacion, Messages.getString("SWING_GENERAR_TITULO"), ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setBounds(100, 100, 320, 200);
		setLocationRelativeTo(null);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		paneForm.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(paneForm, BorderLayout.CENTER);
		paneForm.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("min:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("64dlu:grow"),
				ColumnSpec.decode("100dlu"),
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		this.window = mainWindow;
		this.archivo = archivo;
		{
			JLabel lblAyuda = new JLabel(Messages.getString("SWING_GENERAR_TIP")); //$NON-NLS-1$
			lblAyuda.setHorizontalAlignment(SwingConstants.CENTER);
			lblAyuda.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
			paneForm.add(lblAyuda, "2, 2, 3, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblCantidad = new JLabel(Messages.getString("SWING_GENERAR_ELEMENTOS_CANTIDAD")); //$NON-NLS-1$
			lblCantidad.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(lblCantidad, "2, 4"); //$NON-NLS-1$
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
						btnGenerar.setEnabled(true);
					else
						btnGenerar.setEnabled(false);
				}
			});
			txtElemento.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(txtElemento, "4, 4, fill, default"); //$NON-NLS-1$
			txtElemento.setColumns(10);

		}
		{
			JSeparator separator = new JSeparator();
			paneForm.add(separator, "1, 6, 4, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblMinimo = new JLabel(Messages.getString("SWING_GENERAR_ELEMENTOS_MINIMO")); //$NON-NLS-1$
			lblMinimo.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			paneForm.add(lblMinimo, "2, 8, right, default"); //$NON-NLS-1$
		}
		{
			txtMinimo = new JTextField(10);
			txtMinimo.setFont(new Font("Tahoma", Font.BOLD, 12));
			paneForm.add(txtMinimo, "4, 8, fill, default");
		}
		{
			JLabel lblMaximo = new JLabel(Messages.getString("SWING_GENERAR_ELEMENTOS_MAXIMO")); //$NON-NLS-1$
			lblMaximo.setFont(new Font("Tahoma", Font.BOLD, 12));
			paneForm.add(lblMaximo, "2, 10, right, default");
		}
		{
			txtMaximo = new JTextField(10);
			txtMaximo.setFont(new Font("Tahoma", Font.BOLD, 12));
			paneForm.add(txtMaximo, "4, 10, fill, default");
		}
		{
			JPanel paneGenerar = new JPanel();
			paneGenerar.setLayout(new FlowLayout(FlowLayout.CENTER));
			getContentPane().add(paneGenerar, BorderLayout.SOUTH);
			{
				btnGenerar = new JButton(Messages.getString("SWING_GENERAR_GENERAR")); //$NON-NLS-1$
				btnGenerar.setEnabled(false);
				btnGenerar.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
				btnGenerar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						generar();
					
						dispose();
					}
				});
				btnGenerar.setActionCommand(Messages.getString("SWING_GENERAR_OK")); //$NON-NLS-1$
				paneGenerar.add(btnGenerar);
				getRootPane().setDefaultButton(btnGenerar);
			}
		}
	}
	
	private void generar(){
		// Parsear
		int min = Integer.parseInt(txtMinimo.getText());
		int max = Integer.parseInt(txtMaximo.getText());
		int cant = Integer.parseInt(txtElemento.getText());
		
		// Generar
		Vector<Elemento> elementos = Elemento.generarDatos(min, max, cant);
		
		// Insertar
		window.insertarDatos(elementos);
	}
		

}