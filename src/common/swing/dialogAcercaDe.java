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

public class dialogAcercaDe extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6228527787800000645L;
	private JPanel contentPanel = new JPanel();
	
	public dialogAcercaDe(mainWindow mainWindow) {
		super(mainWindow.frmAplicacionDidacticaEstructuras, Messages.getString("SWING_ABOUT_TH_SOLUTION"), ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setTitle(Messages.getString("SWING_ABOUT_TH_SOLUTION_APLIC_DIDACTICA")); //$NON-NLS-1$
		setIconImage(Toolkit.getDefaultToolkit().getImage(dialogAcercaDe.class.getResource("/img/icono.png"))); //$NON-NLS-1$
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
			JLabel brian = new JLabel(""); //$NON-NLS-1$
			brian.setToolTipText(Messages.getString("SWING_ABOUT_CAIMMI")); //$NON-NLS-1$
			brian.setBorder(new LineBorder(Color.BLACK));
			brian.setIcon(new ImageIcon(dialogAcercaDe.class.getResource("/img/brian.png"))); //$NON-NLS-1$
			contentPanel.add(brian, "3, 2"); //$NON-NLS-1$
		}
		{
			JLabel hernan = new JLabel(""); //$NON-NLS-1$
			hernan.setToolTipText(Messages.getString("SWING_ABOUT_ROCHA")); //$NON-NLS-1$
			hernan.setBorder(new LineBorder(Color.BLACK));
			hernan.setIcon(new ImageIcon(dialogAcercaDe.class.getResource("/img/hernan.png"))); //$NON-NLS-1$
			contentPanel.add(hernan, "7, 2, center, center"); //$NON-NLS-1$
		}
		{
			JLabel lblCaimmibrian = new JLabel(Messages.getString("SWING_ABOUT_CAIMMI")); //$NON-NLS-1$
			lblCaimmibrian.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblCaimmibrian.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblCaimmibrian, "3, 4"); //$NON-NLS-1$
		}
		{
			JLabel lblNewLabel = new JLabel("      " + Messages.getString("SWING_ABOUT_DESARROLLADORES") + "      "); //$NON-NLS-1$
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel, "5, 2"); //$NON-NLS-1$
		}
		{
			JLabel lblRochaHernnGabriel = new JLabel(Messages.getString("SWING_ABOUT_ROCHA")); //$NON-NLS-1$
			lblRochaHernnGabriel.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblRochaHernnGabriel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblRochaHernnGabriel, "7, 4"); //$NON-NLS-1$
		}
		{
			JLabel lblBcaimmigmailcom = new JLabel(Messages.getString("SWING_ABOUT_CAIMMI_MAIL")); //$NON-NLS-1$
			lblBcaimmigmailcom.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$
			lblBcaimmigmailcom.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblBcaimmigmailcom, "3, 6"); //$NON-NLS-1$
		}
		{
			JLabel lblNewLabel_1 = new JLabel(Messages.getString("SWING_ABOUT_ROCHA_MAIL")); //$NON-NLS-1$
			lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11)); //$NON-NLS-1$
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_1, "7, 6"); //$NON-NLS-1$
		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, "1, 8, 7, 1"); //$NON-NLS-1$
		}
		
		{
			JLabel lblNewLabel_2 = new JLabel(Messages.getString("SWING_ABOUT_FACULTAD")); //$NON-NLS-1$
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16)); //$NON-NLS-1$
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_2, "3, 10, 5, 1"); //$NON-NLS-1$
		}
		{
			JLabel lblUnicen = new JLabel(Messages.getString("SWING_ABOUT_UNICEN")); //$NON-NLS-1$
			lblUnicen.setHorizontalAlignment(SwingConstants.CENTER);
			lblUnicen.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
			contentPanel.add(lblUnicen, "5, 12"); //$NON-NLS-1$
		}
		{
			JLabel label = new JLabel(" "); //$NON-NLS-1$
			label.setIcon(new ImageIcon(dialogAcercaDe.class.getResource("/img/unicen.png"))); //$NON-NLS-1$
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(label, "5, 14"); //$NON-NLS-1$
		}
		{
			JLabel lblProyectoFinal = new JLabel(Messages.getString("SWING_ABOUT_PROYECTO_FINAL")); //$NON-NLS-1$
			lblProyectoFinal.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			lblProyectoFinal.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblProyectoFinal, "5, 16"); //$NON-NLS-1$
		}
		{
			JLabel lblProyectoFinalEstructuras = new JLabel(Messages.getString("SWING_ABOUT_ESTRUCTURAS")); //$NON-NLS-1$
			lblProyectoFinalEstructuras.setHorizontalAlignment(SwingConstants.CENTER);
			lblProyectoFinalEstructuras.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			contentPanel.add(lblProyectoFinalEstructuras, "3, 18, 5, 1"); //$NON-NLS-1$
		}
	}

}
