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

public class dialogAcercaDe extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6228527787800000645L;
	private JPanel contentPanel = new JPanel();
	
	public dialogAcercaDe(mainWindow mainWindow) {
		super(mainWindow.frmAplicacionDidacticaEstructuras, "Acerca De T.H.Solution", ModalityType.APPLICATION_MODAL);
		setTitle("Acerca De T.H.Solution. Aplicaci\u00F3n Did\u00E1ctica");
		setIconImage(Toolkit.getDefaultToolkit().getImage(dialogAcercaDe.class.getResource("/img/icono.png")));
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
				ColumnSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(45dlu;default)"),},
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
			JLabel brian = new JLabel("");
			brian.setToolTipText("CAIMMI, Brian");
			brian.setBorder(new LineBorder(Color.BLACK));
			brian.setIcon(new ImageIcon(dialogAcercaDe.class.getResource("/img/brian.png")));
			contentPanel.add(brian, "3, 2");
		}
		{
			JLabel hernan = new JLabel("");
			hernan.setToolTipText("ROCHA, Hern\u00E1n Gabriel");
			hernan.setBorder(new LineBorder(Color.BLACK));
			hernan.setIcon(new ImageIcon(dialogAcercaDe.class.getResource("/img/hernan.png")));
			contentPanel.add(hernan, "7, 2, center, center");
		}
		{
			JLabel lblCaimmibrian = new JLabel("CAIMMI,Brian");
			lblCaimmibrian.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblCaimmibrian.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblCaimmibrian, "3, 4");
		}
		{
			JLabel lblNewLabel = new JLabel("      Desarrolladores      ");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel, "5, 2");
		}
		{
			JLabel lblRochaHernnGabriel = new JLabel("ROCHA, Hernán Gabriel");
			lblRochaHernnGabriel.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblRochaHernnGabriel.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblRochaHernnGabriel, "7, 4");
		}
		{
			JLabel lblBcaimmigmailcom = new JLabel("bcaimmi@gmail.com");
			lblBcaimmigmailcom.setFont(new Font("Tahoma", Font.ITALIC, 11));
			lblBcaimmigmailcom.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblBcaimmigmailcom, "3, 6");
		}
		{
			JLabel lblNewLabel_1 = new JLabel("hernanrocha93@gmail.com");
			lblNewLabel_1.setFont(new Font("Tahoma", Font.ITALIC, 11));
			lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_1, "7, 6");
		}
		{
			JSeparator separator = new JSeparator();
			contentPanel.add(separator, "1, 8, 7, 1");
		}
		
		{
			JLabel lblNewLabel_2 = new JLabel("FACULTAD DE CIENCIAS EXACTAS");
			lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblNewLabel_2, "3, 10, 5, 1");
		}
		{
			JLabel lblUnicen = new JLabel("UNICEN");
			lblUnicen.setHorizontalAlignment(SwingConstants.CENTER);
			lblUnicen.setFont(new Font("Tahoma", Font.BOLD, 14));
			contentPanel.add(lblUnicen, "5, 12");
		}
		{
			JLabel label = new JLabel(" ");
			label.setIcon(new ImageIcon(dialogAcercaDe.class.getResource("/img/unicen.png")));
			label.setHorizontalAlignment(SwingConstants.RIGHT);
			contentPanel.add(label, "5, 14");
		}
		{
			JLabel lblProyectoFinal = new JLabel("Proyecto Final");
			lblProyectoFinal.setFont(new Font("Tahoma", Font.BOLD, 12));
			lblProyectoFinal.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(lblProyectoFinal, "5, 16");
		}
		{
			JLabel lblProyectoFinalEstructuras = new JLabel("Estructuras de Almacenamiento de Datos");
			lblProyectoFinalEstructuras.setHorizontalAlignment(SwingConstants.CENTER);
			lblProyectoFinalEstructuras.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblProyectoFinalEstructuras, "3, 18, 5, 1");
		}
	}

}
