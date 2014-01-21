package common.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import common.Messages;

public class DialogAcercaDe extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6228527787800000645L;
	private JPanel contentPanel = new JPanel();
	
	public DialogAcercaDe(mainWindow mainWindow) {
		super(mainWindow.frmAplicacion, Messages.getString("SWING_ABOUT_TH_SOLUTION"), ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setTitle(Messages.getString("SWING_ABOUT_TH_SOLUTION_APLIC_DIDACTICA")); //$NON-NLS-1$
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogAcercaDe.class.getResource("/img/icono.png"))); //$NON-NLS-1$
		this.setResizable(false);
		setBounds(100, 100, 450, 450);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		FormLayout fl_contentPanel = new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"), //$NON-NLS-1$
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default)"),}, //$NON-NLS-1$
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,});
		contentPanel.setLayout(fl_contentPanel);
		{
			JLabel imgBrian = new JLabel(""); //$NON-NLS-1$
			imgBrian.setToolTipText(Messages.getString("SWING_ABOUT_CAIMMI")); //$NON-NLS-1$
			imgBrian.setBorder(new LineBorder(Color.BLACK));
			imgBrian.setIcon(new ImageIcon(DialogAcercaDe.class.getResource("/img/brian.png"))); //$NON-NLS-1$
			contentPanel.add(imgBrian, "3, 2"); //$NON-NLS-1$
		}
		{
			JLabel imgHernan = new JLabel(""); //$NON-NLS-1$
			imgHernan.setToolTipText(Messages.getString("SWING_ABOUT_ROCHA")); //$NON-NLS-1$
			imgHernan.setBorder(new LineBorder(Color.BLACK));
			imgHernan.setIcon(new ImageIcon(DialogAcercaDe.class.getResource("/img/hernan.png"))); //$NON-NLS-1$
			contentPanel.add(imgHernan, "7, 2, center, center"); //$NON-NLS-1$
		}
		{
			JLabel lblBrian = new JLabel(Messages.getString("SWING_ABOUT_CAIMMI")); //$NON-NLS-1$
			lblBrian.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblBrian.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblBrian, "3, 4"); //$NON-NLS-1$
		}
		{
			JLabel lblDesarrolladores = new JLabel("      " + Messages.getString("SWING_ABOUT_DESARROLLADORES") + "      "); //$NON-NLS-1$
			lblDesarrolladores.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblDesarrolladores.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblDesarrolladores, "5, 2"); //$NON-NLS-1$
		}
		{
			JLabel lblHernan = new JLabel(Messages.getString("SWING_ABOUT_ROCHA")); //$NON-NLS-1$
			lblHernan.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblHernan.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblHernan, "7, 4"); //$NON-NLS-1$
		}
		{
			JLabel lblMailBrian = new JLabel(Messages.getString("SWING_ABOUT_CAIMMI_MAIL")); //$NON-NLS-1$
			lblMailBrian.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$
			lblMailBrian.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblMailBrian, "3, 6"); //$NON-NLS-1$
		}
		{
			JLabel lblMailHernan = new JLabel(Messages.getString("SWING_ABOUT_ROCHA_MAIL")); //$NON-NLS-1$
			lblMailHernan.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$
			lblMailHernan.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblMailHernan, "7, 6"); //$NON-NLS-1$
		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, "1, 8, 7, 1"); //$NON-NLS-1$
		}
		
		{
			JLabel lblFacultad = new JLabel(Messages.getString("SWING_ABOUT_FACULTAD")); //$NON-NLS-1$
			lblFacultad.setFont(new Font("Tahoma", Font.BOLD, 16)); //$NON-NLS-1$
			lblFacultad.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblFacultad, "3, 10, 5, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblUnicen = new JLabel(Messages.getString("SWING_ABOUT_UNICEN")); //$NON-NLS-1$
			lblUnicen.setHorizontalAlignment(SwingConstants.CENTER);
			lblUnicen.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
			contentPanel.add(lblUnicen, "5, 12"); //$NON-NLS-1$
		}
		{
			JLabel imgUnicen = new JLabel(" "); //$NON-NLS-1$
			imgUnicen.setIcon(new ImageIcon(DialogAcercaDe.class.getResource("/img/unicen.png"))); //$NON-NLS-1$
			imgUnicen.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(imgUnicen, "5, 14"); //$NON-NLS-1$
		}
		{
			JLabel lblProyectoFinal = new JLabel(Messages.getString("SWING_ABOUT_PROYECTO_FINAL")); //$NON-NLS-1$
			lblProyectoFinal.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblProyectoFinal.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblProyectoFinal, "5, 16"); //$NON-NLS-1$
		}
		{
			JLabel lblEstructuras = new JLabel(Messages.getString("SWING_ABOUT_ESTRUCTURAS")); //$NON-NLS-1$
			lblEstructuras.setHorizontalAlignment(SwingConstants.CENTER);
			lblEstructuras.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			contentPanel.add(lblEstructuras, "3, 18, 5, 1"); //$NON-NLS-1$
		}
	}

}
