package common.swing;

import hash.abierto.HashAbierto;
import hash.abierto.VistaHashAbierto;
import hash.cerrado.HashCerrado;
import hash.cerrado.VistaHashCerrado;
import hash.cerrado.tecnicas.TecnicaCerradaCuadratica;
import hash.cerrado.tecnicas.TecnicaCerradaPseudoazar;
import hash.cerrado.tecnicas.TecnicaCerradaRealeatorizada;
import hash.cerrado.tecnicas.TecnicaReasignacionLineal;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arbolb.estrategias.CrecimRedistDerecha;
import arbolb.estrategias.CrecimRedistIzquierda;
import arbolb.estrategias.CrecimSplit;
import arbolb.estrategias.DecrecFusionDerecha;
import arbolb.estrategias.DecrecFusionIzquierda;
import arbolb.estrategias.DecrecRedistDerecha;
import arbolb.estrategias.DecrecRedistIzquierda;
import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;
import arbolb.vista.BArchivo;
import arbolb.vista.BHibrido;
import arbolb.vista.BIndice;
import arbolb.vista.BMasClustered;
import arbolb.vista.BMasIndice;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import common.Graficador;
import common.Messages;
import common.estructura.Elemento;
//import org.apache.log4j.Logger;

public class formEstructura extends JDialog {

	private static final long serialVersionUID = 1290782647459590900L;
	
	private static final int SCREEN_INICIO = 0;
	private static final int SCREEN_CONFIGURACION = 1;
	
	private JPanel contentPane;
	
	// MENU PRINCIPAL
	private mainWindow parent;
	
	private JPanel panPrincipal;
	private JTextField txtElementos;
	public JCheckBox chckbxArbolesB;
	public JCheckBox chckbxHashingCerrado;
	public JCheckBox chckbxHashingAbierto;
	
	private int screen = SCREEN_INICIO;
	private Vector<Elemento> elementos;
	
	// MENU ARBOL
	private JPanel panArbol;
	private JLabel lblArbolB;
	
	private JLabel lblOrden;

	private JLabel lblEstrategiasDeInsercion;
	private Component lblEstrategiasDeEliminacion;
	private JList<Object> listInsertar;
	private JList<Object> listEliminar;
	private DefaultListModel<Object> elementosInsertar;
	private DefaultListModel<Object> elementosEliminar;	

	private Component lblTiposDeVista;
	private JCheckBox chckbxBArchivo;
	private JCheckBox chckbxBHibrido;
	private JCheckBox chckbxBIndice;
	private JCheckBox chckbxBMasIndice;
	private JCheckBox chckbxBmasClustered;
	private Generando g;
	private int estructuras;

	private int estActual;

	private JButton btnCancelar;

	private JButton btnSiguiente;

	private Archivo arch;
	private JLabel lblCeldasPorBalde;

	private JSpinner txtCantBaldes;
	private JSpinner txtCantRanuras;
	private JSeparator separator;
	private JCheckBox chk1;
	private JCheckBox chk2;
	private JCheckBox chk4;
	private JCheckBox chk3;
	private JTextField ListaPseudo;
	private JLabel lblNewLabel;
	private JLabel lblVistasDeTcnicas;
	private JSeparator separator_1;
	private JLabel lblNewLabel_2;
	private JSpinner txtBaldesAbierto;
	private JSpinner txtRanurasAbierto;
	private JSpinner txtRanurasSecundariasAbierto;
	private JSpinner txtRhoDisenioAbierto;
	private JSeparator separator_3;
	private JLabel lblValoresCercanosA;
	private JLabel label;
	private JSeparator separator_4;
	private JLabel lblBienvenidoAlAsistente;
	//private JLabel generandoText;
	private JCheckBox chckbxSinElementos;
	private JSeparator separator_5;
	private JLabel lb2;
	private JLabel lb3;
	private JCheckBox chk5;
	private JCheckBox chk6;
	private JSeparator separator_6;
	private JLabel lblTipo;
	private JSeparator separator_7;
	private JLabel lblConfiguracin;
	private JSeparator separator_2;
	private JSpinner txtOrden;
	private JSpinner txtTamCluster;
	private JLabel lblTamaoDeCluster;


	/**
	 * Create the frame.
	 */
	
	public formEstructura(mainWindow mainWindow, Archivo arch) {
		super(mainWindow.frmAplicacionDidacticaEstructuras, Messages.getString("SWING_FORM_NUEVO_ARCHIVO"), ModalityType.APPLICATION_MODAL); //$NON-NLS-1$
		setResizable(false);
		//Logger.getRootLogger().trace("Iniciando asistente para nueva estructura");
		
		Graficador.setForm(this);
		this.parent = mainWindow;
		this.arch = arch;
		
		// Configurar ventana
		setTitle(Messages.getString("SWING_FORM_TITULO")); //$NON-NLS-1$
		setBounds(100, 100, 600,550);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(50, 0));
		
		panPrincipal = new JPanel();
		contentPane.add(panPrincipal);
		panPrincipal.setLayout(new CardLayout(0, 0));
			
		initPanInicio();

		initAcciones(); 
		
		//initPanArbol();
		
		//initPanHashCerrado();
		
		//initPanHashAbierto();
		
		//initGeneracion();

	}
	
	private void initPanInicio() {
		JPanel panInicio = new JPanel();
		panPrincipal.add(panInicio, "name_43606110650234"); //$NON-NLS-1$
		
		panInicio.setBorder(new LineBorder(new Color(0, 0, 0)));
		panInicio.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.MIN_COLSPEC,
				FormFactory.MIN_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.PARAGRAPH_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		label = new JLabel(""); //$NON-NLS-1$
		label.setIcon(new ImageIcon(formEstructura.class.getResource("/img/dataWizard.png"))); //$NON-NLS-1$
		panInicio.add(label, "1, 2"); //$NON-NLS-1$
		
		lblBienvenidoAlAsistente = new JLabel(Messages.getString("SWING_FORM_BIENVENIDO")); //$NON-NLS-1$
		lblBienvenidoAlAsistente.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenidoAlAsistente.setFont(new Font("Tahoma", Font.PLAIN, 18)); //$NON-NLS-1$
		panInicio.add(lblBienvenidoAlAsistente, "3, 2"); //$NON-NLS-1$
		
		separator_4 = new JSeparator();
		panInicio.add(separator_4, "1, 4, 3, 1"); //$NON-NLS-1$
		
		JLabel lblElementosIniciales = new JLabel(Messages.getString("SWING_FORM_ELEMENTOS_INICIALES") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		lblElementosIniciales.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panInicio.add(lblElementosIniciales, "1, 6, left, center"); //$NON-NLS-1$
			
			chckbxSinElementos = new JCheckBox(Messages.getString("SWING_FORM_SIN_ELEMENTOS")); //$NON-NLS-1$
			chckbxSinElementos.setSelected(true);
			chckbxSinElementos.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					txtElementos.setEnabled(!txtElementos.isEnabled());
				}
			});
			chckbxSinElementos.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
			panInicio.add(chckbxSinElementos, "3, 6"); //$NON-NLS-1$
		
		txtElementos = new JTextField();
		txtElementos.setEnabled(false);
		txtElementos.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		txtElementos.setText(""); //$NON-NLS-1$
		txtElementos.addKeyListener(new KeyAdapter(){
			   public void keyTyped(KeyEvent e)
			   {
			      char caracter = e.getKeyChar();

			      // Verificar si la tecla pulsada no es un digito
			      if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',' ) )
			      {
			         e.consume();  // ignorar el evento de teclado
			      }
						    	  
			   }});
		
			panInicio.add(txtElementos, "3, 8, fill, fill"); //$NON-NLS-1$
			txtElementos.setColumns(10);
			lblElementosIniciales.setLabelFor(txtElementos);
		
		separator_5 = new JSeparator();
		panInicio.add(separator_5, "1, 10, 3, 1"); //$NON-NLS-1$
		
		JLabel lblEstructuras = new JLabel(Messages.getString("SWING_FORM_21")); //$NON-NLS-1$
		lblEstructuras.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panInicio.add(lblEstructuras, "1, 12, left, top"); //$NON-NLS-1$
		
		chckbxArbolesB = new JCheckBox(Messages.getString("SWING_FORM_ARBOLES_B")); //$NON-NLS-1$
		chckbxArbolesB.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxArbolesB.setSelected(true);
		panInicio.add(chckbxArbolesB, "3, 12, left, top"); //$NON-NLS-1$
		
		chckbxHashingCerrado = new JCheckBox(Messages.getString("SWING_FORM_HASH_CERRADO")); //$NON-NLS-1$
		chckbxHashingCerrado.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxHashingCerrado.setSelected(true);
		panInicio.add(chckbxHashingCerrado, "3, 14, left, top"); //$NON-NLS-1$
		
		chckbxHashingAbierto = new JCheckBox(Messages.getString("SWING_FORM_HASH_ABIERTO")); //$NON-NLS-1$
		chckbxHashingAbierto.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxHashingAbierto.setSelected(true);
		panInicio.add(chckbxHashingAbierto, "3, 16, fill, top");		 //$NON-NLS-1$
		
	}
		
	private void initAcciones() {
		JPanel panAcciones = new JPanel();
		FlowLayout fl_panAcciones = (FlowLayout) panAcciones.getLayout();
		fl_panAcciones.setVgap(1);
		contentPane.add(panAcciones, BorderLayout.SOUTH);
		
		btnCancelar = new JButton(Messages.getString("SWING_FORM_CANCELAR")); //$NON-NLS-1$
		btnCancelar.setIcon(new ImageIcon(formEstructura.class.getResource("/img/cancel.png"))); //$NON-NLS-1$
		btnCancelar.setMnemonic('c');
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				dispose();
			}
		});
		panAcciones.add(btnCancelar);
		
		btnSiguiente = new JButton(Messages.getString("SWING_FORM_SIGUIENTE")); //$NON-NLS-1$
		btnSiguiente.setIcon(new ImageIcon(formEstructura.class.getResource("/img/next.png"))); //$NON-NLS-1$
		btnSiguiente.setMnemonic('s');
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnSiguiente.setEnabled(false);
				siguienteMenu();
				btnSiguiente.setEnabled(true);
			}
		});
		panAcciones.add(btnSiguiente);		
	}
	
	private void initPanArbol() {
		panArbol = new JPanel();
		panPrincipal.add(panArbol, "panArbol"); //$NON-NLS-1$
		panArbol.setBorder(new EmptyBorder(10, 10, 10, 10));
		panArbol.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.MIN_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.BUTTON_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				ColumnSpec.decode("13dlu"), //$NON-NLS-1$
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"), //$NON-NLS-1$
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("12dlu"), //$NON-NLS-1$
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
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
				FormFactory.DEFAULT_ROWSPEC,}));

		lblArbolB = new JLabel(""); //$NON-NLS-1$
		lblArbolB.setIcon(new ImageIcon(formEstructura.class.getResource("/img/imArbol.png"))); //$NON-NLS-1$
		panArbol.add(lblArbolB, "1, 2, 12, 1, center, default"); //$NON-NLS-1$

		separator_6 = new JSeparator();
		panArbol.add(separator_6, "1, 4, 12, 1"); //$NON-NLS-1$

		// ------------------------------ ORDEN ------------------------------- //


		lblOrden = new JLabel(Messages.getString("SWING_FORM_45")); //$NON-NLS-1$
		lblOrden.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrden.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		lblOrden.setBounds(27, 77, 46, 14);
		panArbol.add(lblOrden, "1, 6, center, center"); //$NON-NLS-1$

		elementosInsertar = new DefaultListModel<Object>();
		elementosInsertar.addElement("Redistribucion Derecha"); //$NON-NLS-1$
		elementosInsertar.addElement("Split"); //$NON-NLS-1$
		elementosInsertar.addElement("Redistribucion Izquierda"); //$NON-NLS-1$

		txtOrden = new JSpinner();
		txtOrden.setModel(new SpinnerNumberModel(5, 3, 10, 1));
		txtOrden.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panArbol.add(txtOrden, "2, 6, left, default"); //$NON-NLS-1$

		separator_2 = new JSeparator();
		panArbol.add(separator_2, "1, 8, 12, 1"); //$NON-NLS-1$


		// ----------------------------- INSERCION ----------------------------- //


		lblEstrategiasDeInsercion = new JLabel(Messages.getString("SWING_FORM_ESTRATEGIAS_INSERCION") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		lblEstrategiasDeInsercion.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		lblEstrategiasDeInsercion.setBounds(27, 125, 140, 14);
		panArbol.add(lblEstrategiasDeInsercion, "1, 10, center, center"); //$NON-NLS-1$

		listInsertar = new JList<Object>();
		listInsertar.setBorder(new LineBorder(new Color(0, 0, 0)));
		listInsertar.setModel(elementosInsertar);
		panArbol.add(listInsertar, "2, 10, 10, 3, fill, fill"); //$NON-NLS-1$

		JButton btnInsertarSubir = new JButton(Messages.getString("SWING_FORM_SUBIR")); //$NON-NLS-1$
		btnInsertarSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int index = listInsertar.getSelectedIndex();

					DefaultListModel<Object> modelo = (DefaultListModel<Object>) listInsertar.getModel();	
					swap(modelo, index, index-1);
					listInsertar.setSelectedIndex(index - 1);
				}catch (Exception e2){

				}
			}
		});
		panArbol.add(btnInsertarSubir, "12, 10, center, default"); //$NON-NLS-1$

		JButton btnInsertarBajar = new JButton(Messages.getString("SWING_FORM_BAJAR")); //$NON-NLS-1$
		btnInsertarBajar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int index = listInsertar.getSelectedIndex();

					DefaultListModel<Object> modelo = (DefaultListModel<Object>) listInsertar.getModel();	
					swap(modelo, index, index+1);
					listInsertar.setSelectedIndex(index + 1);
				}catch (Exception e1){					
				}
			}
		});
		panArbol.add(btnInsertarBajar, "12, 12, center, default"); //$NON-NLS-1$

		JButton btnEliminarSubir = new JButton(Messages.getString("SWING_FORM_SUBIR")); //$NON-NLS-1$
		btnEliminarSubir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					int index = listEliminar.getSelectedIndex();

					DefaultListModel<Object> modelo = (DefaultListModel<Object>) listEliminar.getModel();	
					swap(modelo, index, index-1);
					listEliminar.setSelectedIndex(index - 1);
				}catch (Exception e2){

				}

			}
		});

		elementosEliminar = new DefaultListModel<Object>();
		elementosEliminar.addElement("Redistribucion Izquierda"); //$NON-NLS-1$
		elementosEliminar.addElement("Redistribucion Derecha"); //$NON-NLS-1$
		elementosEliminar.addElement("Fusion Izquierda"); //$NON-NLS-1$
		elementosEliminar.addElement("Fusion Derecha"); //$NON-NLS-1$


		// ---------------------------- ELIMINACION ---------------------------- //


		lblEstrategiasDeEliminacion = new JLabel(Messages.getString("SWING_FORM_ESTRATEGIAS_ELIMINACION") + ":"); //$NON-NLS-1$ //$NON-NLS-2$
		lblEstrategiasDeEliminacion.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		lblEstrategiasDeEliminacion.setBounds(27, 180, 140, 14);
		panArbol.add(lblEstrategiasDeEliminacion, "1, 14, center, center"); //$NON-NLS-1$

		listEliminar = new JList<Object>();
		listEliminar.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEliminar.setModel(elementosEliminar);
		panArbol.add(listEliminar, "2, 14, 10, 3, fill, fill"); //$NON-NLS-1$
		panArbol.add(btnEliminarSubir, "12, 14, center, default"); //$NON-NLS-1$



		JButton btnEliminarBajar = new JButton(Messages.getString("SWING_FORM_BAJAR")); //$NON-NLS-1$
		btnEliminarBajar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					int index = listEliminar.getSelectedIndex();

					DefaultListModel<Object> modelo = (DefaultListModel<Object>) listEliminar.getModel();	
					swap(modelo, index, index+1);
					listEliminar.setSelectedIndex(index + 1);
				}catch (Exception e1){

				}
			}
		});
		panArbol.add(btnEliminarBajar, "12, 16, center, default"); //$NON-NLS-1$

		separator_7 = new JSeparator();
		panArbol.add(separator_7, "1, 18, 12, 1"); //$NON-NLS-1$


		// ------------------------- TIPOS DE ARBOLES ------------------------- //


		lblTiposDeVista = new JLabel(Messages.getString("SWING_FORM_TIPOS_VISTA")); //$NON-NLS-1$
		lblTiposDeVista.setFont(new Font("Tahoma", Font.BOLD, 14)); //$NON-NLS-1$
		panArbol.add(lblTiposDeVista, "1, 20, center, default"); //$NON-NLS-1$

		chckbxBArchivo = new JCheckBox(Messages.getString("SWING_FORM_BARCHIVO")); //$NON-NLS-1$
		chckbxBArchivo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBArchivo.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});
		chckbxBArchivo.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxBArchivo.setSelected(true);
		panArbol.add(chckbxBArchivo, "1, 22, left, default"); //$NON-NLS-1$

		chckbxBMasIndice = new JCheckBox(Messages.getString("SWING_FORM_BMASINDICE")); //$NON-NLS-1$
		chckbxBMasIndice.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxBMasIndice.setSelected(true);
		panArbol.add(chckbxBMasIndice, "2, 22"); //$NON-NLS-1$
		chckbxBMasIndice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBMasIndice.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});

		chckbxBHibrido = new JCheckBox(Messages.getString("SWING_FORM_BHIBRIDO")); //$NON-NLS-1$
		chckbxBHibrido.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxBHibrido.setSelected(true);
		panArbol.add(chckbxBHibrido, "1, 24, left, default"); //$NON-NLS-1$
		chckbxBHibrido.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBHibrido.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});

		chckbxBmasClustered = new JCheckBox(Messages.getString("SWING_FORM_BMASCLUSTERED")); //$NON-NLS-1$
		chckbxBmasClustered.setSelected(true);
		chckbxBmasClustered.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				lblTamaoDeCluster.setVisible(chckbxBmasClustered.isSelected());	
				txtTamCluster.setVisible(chckbxBmasClustered.isSelected());	
				if (chckbxBmasClustered.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});
		chckbxBmasClustered.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$


		panArbol.add(chckbxBmasClustered, "2, 24"); //$NON-NLS-1$

		chckbxBIndice = new JCheckBox(Messages.getString("SWING_FORM_BINDICE")); //$NON-NLS-1$
		chckbxBIndice.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chckbxBIndice.setSelected(true);
		panArbol.add(chckbxBIndice, "1, 26, left, default"); //$NON-NLS-1$
		chckbxBIndice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBIndice.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});

		lblTamaoDeCluster = new JLabel(Messages.getString("SWING_FORM_CLUSTER")); //$NON-NLS-1$
		lblTamaoDeCluster.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
		panArbol.add(lblTamaoDeCluster, "2, 26"); //$NON-NLS-1$

		txtTamCluster = new JSpinner();
		txtTamCluster.setModel(new SpinnerNumberModel(3, 1, 10, 1));
		txtTamCluster.setValue(3);
		panArbol.add(txtTamCluster, "4, 26"); //$NON-NLS-1$



	}
	
	private void initPanHashCerrado() {
		
		JPanel panHashCerrado = new JPanel();
		panHashCerrado.setBorder(new LineBorder(new Color(0, 0, 0)));
		panPrincipal.add(panHashCerrado, "panHashCerrado"); //$NON-NLS-1$
		panHashCerrado.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("max(10dlu;pref)"), //$NON-NLS-1$
				FormFactory.GLUE_COLSPEC,},
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblNewLabel = new JLabel(""); //$NON-NLS-1$
		lblNewLabel.setIcon(new ImageIcon(formEstructura.class.getResource("/img/imHashC.png"))); //$NON-NLS-1$
		panHashCerrado.add(lblNewLabel, "2, 2, 3, 1"); //$NON-NLS-1$
		
		separator_1 = new JSeparator();
		panHashCerrado.add(separator_1, "2, 4, 3, 1"); //$NON-NLS-1$
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("SWING_FORM_BALDES")); //$NON-NLS-1$
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashCerrado.add(lblNewLabel_1, "2, 6, left, center"); //$NON-NLS-1$
		
		txtCantBaldes = new JSpinner();
		txtCantBaldes.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		txtCantBaldes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("ahora tengo " + txtCantBaldes.getValue());
			}
		});
		txtCantBaldes.setToolTipText("( 1 .. 30 )"); //$NON-NLS-1$
		txtCantBaldes.setModel(new SpinnerNumberModel(7, 1, 30, 1));
		panHashCerrado.add(txtCantBaldes, "4, 6, left, default"); //$NON-NLS-1$
		
		lblCeldasPorBalde = new JLabel(Messages.getString("SWING_FORM_CELDAS")); //$NON-NLS-1$
		lblCeldasPorBalde.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashCerrado.add(lblCeldasPorBalde, "2, 8, left, default"); //$NON-NLS-1$
		
		txtCantRanuras = new JSpinner();
		txtCantRanuras.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		txtCantRanuras.setModel(new SpinnerNumberModel(2, 1, 30, 1));
		panHashCerrado.add(txtCantRanuras, "4, 8, left, default"); //$NON-NLS-1$
		
		separator = new JSeparator();
		panHashCerrado.add(separator, "2, 10, 3, 1"); //$NON-NLS-1$
		
		lblVistasDeTcnicas = new JLabel(Messages.getString("SWING_FORM_VISTAS_TECNICAS")); //$NON-NLS-1$
		lblVistasDeTcnicas.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashCerrado.add(lblVistasDeTcnicas, "2, 12, left, default"); //$NON-NLS-1$
		
		chk1 = new JCheckBox(Messages.getString("SWING_FORM_REASIGNACION_LINEAL")); //$NON-NLS-1$
		chk1.setSelected(true);
		chk1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( chk1.isSelected() )
					btnSiguiente.setEnabled(true);
				else{
					if (( ! chk1.isSelected() ) && ( ! chk2.isSelected() ) && ( ! chk3.isSelected() ) && ( ! chk4.isSelected() ))
						btnSiguiente.setEnabled(false);
				}
			}
		});
		chk1.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashCerrado.add(chk1, "4, 12"); //$NON-NLS-1$
		
		chk2 = new JCheckBox(Messages.getString("SWING_FORM_CUADRATICA")); //$NON-NLS-1$
		chk2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( chk2.isSelected() )
					btnSiguiente.setEnabled(true);
				else{
					if (( ! chk1.isSelected() ) && ( ! chk2.isSelected() ) && ( ! chk3.isSelected() ) && ( ! chk4.isSelected() ))
						btnSiguiente.setEnabled(false);
				}
			}
		});
		chk2.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashCerrado.add(chk2, "4, 14"); //$NON-NLS-1$
		
		chk3 = new JCheckBox(Messages.getString("SWING_FORM_REALEATORIZADA")); //$NON-NLS-1$
		chk3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( chk3.isSelected() )
					btnSiguiente.setEnabled(true);
				else{
					if (( ! chk1.isSelected() ) && ( ! chk2.isSelected() ) && ( ! chk3.isSelected() ) && ( ! chk4.isSelected() ))
						btnSiguiente.setEnabled(false);
				}
			}
		});
		chk3.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashCerrado.add(chk3, "4, 16"); //$NON-NLS-1$
		
		chk4 = new JCheckBox(Messages.getString("SWING_FORM_PSEUDOASAR")); //$NON-NLS-1$
		chk4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if ( chk4.isSelected() )
					btnSiguiente.setEnabled(true);
				else{
					if (( ! chk1.isSelected() ) && ( ! chk2.isSelected() ) && ( ! chk3.isSelected() ) && ( ! chk4.isSelected() ))
						btnSiguiente.setEnabled(false);
				}
			}
		});
		chk4.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		chk4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ListaPseudo.setEnabled(chk4.isSelected() );	
				lb2.setVisible(chk4.isSelected());
				lb3.setVisible(chk4.isSelected());
			}
		});
		
		lb2 = new JLabel(Messages.getString("SWING_FORM_LISTA_DEBE_TENER")); //$NON-NLS-1$
		lb2.setHorizontalAlignment(SwingConstants.CENTER);
		lb2.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
		lb2.setVisible(false);
		panHashCerrado.add(lb2, "2, 18"); //$NON-NLS-1$
		panHashCerrado.add(chk4, "4, 18"); //$NON-NLS-1$
		
		ListaPseudo = new JTextField();
		ListaPseudo.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				 char caracter = e.getKeyChar();

			      // Verificar si la tecla pulsada no es un digito
			      if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',' ) )
			      {
			         e.consume();  // ignorar el evento de teclado
			      }
						 
			}
		});
		
		lb3 = new JLabel(Messages.getString("SWING_FORM_ELEMENTOS_DIFERENTES")); //$NON-NLS-1$
		lb3.setHorizontalAlignment(SwingConstants.CENTER);
		lb3.setVisible(false);
		lb3.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
		panHashCerrado.add(lb3, "2, 20"); //$NON-NLS-1$
		ListaPseudo.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		ListaPseudo.setEnabled(false);
		panHashCerrado.add(ListaPseudo, "4, 20, fill, default"); //$NON-NLS-1$
		ListaPseudo.setColumns(10);
				
	}

	private void initPanHashAbierto() {
		JPanel panHashAbierto = new JPanel();
		panHashAbierto.setBorder(new LineBorder(new Color(0, 0, 0)));
		panPrincipal.add(panHashAbierto, "panHashAbierto"); //$NON-NLS-1$
		panHashAbierto.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("10dlu"), //$NON-NLS-1$
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("pref:grow"),}, //$NON-NLS-1$
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
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		lblNewLabel_2 = new JLabel(""); //$NON-NLS-1$
		lblNewLabel_2.setIcon(new ImageIcon(formEstructura.class.getResource("/img/imHashA.png"))); //$NON-NLS-1$
		panHashAbierto.add(lblNewLabel_2, "2, 2, 4, 1"); //$NON-NLS-1$
		
		separator_7 = new JSeparator();
		panHashAbierto.add(separator_7, "1, 4, 5, 1"); //$NON-NLS-1$
		
		lblTipo = new JLabel(Messages.getString("SWING_FORM_TIPO")); //$NON-NLS-1$
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblTipo, "2, 6"); //$NON-NLS-1$
		
		chk5 = new JCheckBox(Messages.getString("SWING_FORM_SEPARADO")); //$NON-NLS-1$
		chk5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if ( chk5.isSelected() )
					btnSiguiente.setEnabled(true);
				if ( !chk5.isSelected() && ! chk6.isSelected() )
					btnSiguiente.setEnabled(false);
			}
		});
		chk5.setSelected(true);
		chk5.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(chk5, "2, 8"); //$NON-NLS-1$
		
		chk6 = new JCheckBox(Messages.getString("SWING_FORM_SEPARADO_CRECIM_LINEAL")); //$NON-NLS-1$
		chk6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if ( chk6.isSelected() )
					btnSiguiente.setEnabled(true);
				if ( !chk5.isSelected() && ! chk6.isSelected() )
					btnSiguiente.setEnabled(false);
			}
		});
		chk6.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(chk6, "2, 10"); //$NON-NLS-1$
		
		separator_6 = new JSeparator();
		panHashAbierto.add(separator_6, "1, 12, 5, 1"); //$NON-NLS-1$
		
		lblConfiguracin = new JLabel(Messages.getString("SWING_FORM_CONFIGURACION")); //$NON-NLS-1$
		lblConfiguracin.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblConfiguracin, "2, 14"); //$NON-NLS-1$
		
		JLabel lblNewLabel_1 = new JLabel(Messages.getString("SWING_FORM_BALDES")); //$NON-NLS-1$
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblNewLabel_1, "2, 16, left, center"); //$NON-NLS-1$
		
		txtBaldesAbierto = new JSpinner();
		txtBaldesAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		txtBaldesAbierto.setModel(new SpinnerNumberModel(7, 1, 30, 1));
		panHashAbierto.add(txtBaldesAbierto, "4, 16, fill, default"); //$NON-NLS-1$
		
		lblCeldasPorBalde = new JLabel(Messages.getString("SWING_FORM_RANURAS")); //$NON-NLS-1$
		lblCeldasPorBalde.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblCeldasPorBalde, "2, 18, left, default"); //$NON-NLS-1$
		
		txtRanurasAbierto = new JSpinner();
		txtRanurasAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		txtRanurasAbierto.setModel(new SpinnerNumberModel(2, 1, 30, 1));
		panHashAbierto.add(txtRanurasAbierto, "4, 18, fill, default"); //$NON-NLS-1$
		
		JLabel lblRanurasSecundarias = new JLabel(Messages.getString("SWING_FORM_RANURAS_SECUNDARIAS")); //$NON-NLS-1$
		lblRanurasSecundarias.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblRanurasSecundarias, "2, 20, left, default"); //$NON-NLS-1$
		
		txtRanurasSecundariasAbierto = new JSpinner();
		txtRanurasSecundariasAbierto.setModel(new SpinnerNumberModel(2, 1, 30, 1));
		txtRanurasSecundariasAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12));	 //$NON-NLS-1$
		panHashAbierto.add(txtRanurasSecundariasAbierto, "4, 20, fill, default"); //$NON-NLS-1$
		
		JLabel lblRhoDisenio = new JLabel(Messages.getString("SWING_FORM_RHO_DISENIO")); //$NON-NLS-1$
		lblRhoDisenio.setFont(new Font("Tahoma", Font.BOLD, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblRhoDisenio, "2, 22, left, default"); //$NON-NLS-1$
		
		txtRhoDisenioAbierto = new JSpinner();
		txtRhoDisenioAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12)); //$NON-NLS-1$
		txtRhoDisenioAbierto.setModel(new SpinnerNumberModel(0.9, 0.01, 100.0, 0.05));
		panHashAbierto.add(txtRhoDisenioAbierto, "4, 22, left, default"); //$NON-NLS-1$
		
		lblValoresCercanosA = new JLabel(Messages.getString("SWING_FORM_TIP")); //$NON-NLS-1$
		lblValoresCercanosA.setFont(new Font("Tahoma", Font.ITALIC, 12)); //$NON-NLS-1$
		panHashAbierto.add(lblValoresCercanosA, "5, 22"); //$NON-NLS-1$
		
		separator_3 = new JSeparator();
		panHashAbierto.add(separator_3, "1, 26, 5, 1"); //$NON-NLS-1$
	}
	
	//MENU DE GENERACION
	private void initGeneracion(){
		// Ventana "Generando"
		
		//Alternativa 1.
		//dispose();
/*
		contentPane.removeAll();
		
		panGeneracion = new JPanel();
		contentPane.add(panGeneracion);
		generandoText = new JLabel("");
		generandoText.setIcon(new ImageIcon(formEstructura.class.getResource("/img/generando.png")));
		panGeneracion.add(generandoText);
		
		this.setPreferredSize(new Dimension(500,130));
		pack();
		
		setVisible(false);
		//dispose();
		//setUndecorated(true);
		this.setLocationRelativeTo(null);
		txtElementos = null;
		setVisible(true);
		*/
		this.setVisible(false);
		g = new Generando(parent);
		g.setLocationRelativeTo(null);
		g.setVisible(true);
		g.setAlwaysOnTop(true);
		g.setModal(true);
		g.setModalityType(ModalityType.APPLICATION_MODAL);
		
	}
		
		
	
	private void siguienteMenu() {
		switch (screen) {
		// Pantalla de inicio
		case SCREEN_INICIO:
			if ( (chckbxArbolesB.isSelected()) || (chckbxHashingCerrado.isSelected()) || (chckbxHashingAbierto.isSelected()) ){
				doConfiguracionInicial();
			}
			break;
		case SCREEN_CONFIGURACION:
			((CardLayout) panPrincipal.getLayout()).next(panPrincipal);		
			if (estActual < estructuras){
				estActual++;		
			}else{
				//Cambiado por mi
				Graficador.configurar();
				initGeneracion();
				
			}
			break;
		default:
			//Logger.getLogger("NuevaEstructura").warn("Pantalla actual desconocida");
			break;
		}
		
	}
	
	public void doConfiguracionInicial() {
		
		// Obtener elementos inciales
		
		if ( ! chckbxSinElementos.isSelected() ){
			elementos = new Vector<Elemento>();
			String[] valores = txtElementos.getText().split(","); //$NON-NLS-1$
			for (String v : valores){
				try{
					elementos.add(new Elemento(Integer.parseInt( v.trim() )));
				}catch (NumberFormatException e){
					ConsolaManager.getInstance().escribirAdv(Messages.getString("SWING_FORM_ERROR_ELEMENTOS_INICIALES")); //$NON-NLS-1$
					//Logger.getLogger("NuevaEstructura").warn("Error al obtener elementos iniciales");
				}
			}		
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_ELEMENTOS_INCIALES") + ": " + elementos); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		}else{
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_ESTRUCTURA_SIN_ELEMENTOS")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		estructuras = 0;
		// Avanzar al siguiente menu
		if(chckbxArbolesB.isSelected()){
			estructuras++;
			initPanArbol();
		}
		
		if(chckbxHashingCerrado.isSelected()){
			estructuras++;
			initPanHashCerrado();
		}
		
		if(chckbxHashingAbierto.isSelected()){
			estructuras++;
			initPanHashAbierto();
		}

		// Siguiente ventana
		((CardLayout) panPrincipal.getLayout()).next(panPrincipal);
		screen = SCREEN_CONFIGURACION;
		estActual = 1;
	}

	public void doConfiguracionArbol() {
		// Resetear cantidad de nodos
		NodoB.resetCantidad();

		// Orden del arbol
		int orden = ArbolB.DEFAULT_ORDEN;
		try{
			orden = (int) txtOrden.getValue();
		}catch (Exception e){

		}

		// Crear arbol
		ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_CREAR_ARBOL_ORDEN") + orden); //$NON-NLS-1$ //$NON-NLS-2$
		ArbolB arbol = new ArbolB(orden);
		arch.agregarAlmacenamiento(arbol);

		//----------------------------------------------------------------------------------------------------------//

		// Agregar vista Arbol B Indice
		if(chckbxBIndice.isSelected()){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BINDICE")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BIndice(arbol));
		}

		// Agregar vista Arbol B Hibrido
		if(chckbxBHibrido.isSelected()){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BHIBRIDO")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BHibrido(arbol));
		}

		// Agregar vista Arbol B Archivo
		if(chckbxBArchivo.isSelected()){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BARCHIVO")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BArchivo(arbol));
		}

		// Agregar vista Arbol B Mas Indice
		if(chckbxBMasIndice.isSelected()){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BMASINDICE")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BMasIndice(arbol));

		}

		// Agregar vista Arbol B Mas Clustered
		if(chckbxBmasClustered.isSelected()){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BMASCLUSTERED")); //$NON-NLS-1$ //$NON-NLS-2$
			try{
				int cluster = (int) txtTamCluster.getValue();
				System.out.println("cluster " + cluster); //$NON-NLS-1$
				arbol.agregarVista(new BMasClustered(arbol, cluster));
			}catch(Exception e){
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_CLUSTER_INCORRECTO")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.agregarVista(new BMasClustered(arbol));				
			}
		}

		//----------------------------------------------------------------------------------------------------------// 

		// Setear metodo de insercion
		@SuppressWarnings("rawtypes")
		DefaultListModel modeloInsertar = (DefaultListModel) listInsertar.getModel();
		for(int i = 0; i < modeloInsertar.getSize(); i++){
			switch (modeloInsertar.elementAt(i).toString()) {
			case "Split": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_SPLIT")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaCrecim(new CrecimSplit());
				break;
			case "Redistribucion Izquierda": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_INS_REDISTRIBUCION_IZQUIERDA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaCrecim(new CrecimRedistIzquierda());
				break;
			case "Redistribucion Derecha": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_INS_REDISTRIBUCION_DERECHA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaCrecim(new CrecimRedistDerecha());
				break;
			default:
				//Logger.getLogger("Arbol").warn("Metodo de eliminacion desconocido");
				break;
			}
		}

		//----------------------------------------------------------------------------------------------------------//

		// Setear metodo de eliminacion		
		@SuppressWarnings("rawtypes")
		DefaultListModel modeloEliminar = (DefaultListModel) listEliminar.getModel();
		for(int i = 0; i < modeloEliminar.getSize(); i++){
			switch (modeloEliminar.elementAt(i).toString()) {
			case "Fusion Izquierda": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_FUSION_IZQUIERDA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecFusionIzquierda());
				break;
			case "Fusion Derecha": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_FUSION_DERECHA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecFusionDerecha());
				break;
			case "Redistribucion Izquierda": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_ELIM_REDISTRIBUCION_IZQUIERDA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecRedistIzquierda());
				break;
			case "Redistribucion Derecha": //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_ELIM_REDISTRIBUCION_DERECHA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecRedistDerecha());
				break;
			default:
				//Logger.getLogger("Arbol").warn("Metodo de eliminacion desconocido");
				break;
			}
		}		

		//----------------------------------------------------------------------------------------------------------//

		// Agregar primera captura.
		arbol.agregarCaptura();

	}
	
	public void doConfiguracionHashCerrado() {
		// Configurar hash cerrado
		
		// Obtener valores
		int baldes = HashCerrado.DEFAULT_CANT_BALDES;
		int ranuras = HashCerrado.DEFAULT_CANT_RANURAS;
		
		try{
			baldes = (int) txtCantBaldes.getValue();
			ranuras = (int) txtCantRanuras.getValue();
		}catch (Exception e){ }
		
		if ( chk1.isSelected() ){
			// Crear hash
			HashCerrado hash = new HashCerrado(baldes, ranuras, new TecnicaReasignacionLineal(baldes));

			// Asignar
			arch.agregarAlmacenamiento(hash);
			
			// Agregar vista
			hash.agregarVista(new VistaHashCerrado(hash));
			
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}

		if ( chk2.isSelected() ){
			// Crear hash
			HashCerrado hash = new HashCerrado(baldes, ranuras, new TecnicaCerradaCuadratica(baldes));

			// Asignar
			arch.agregarAlmacenamiento(hash);
			
			// Agregar vista
			hash.agregarVista(new VistaHashCerrado(hash));
			
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}
		
		if ( chk3.isSelected() ){
			// Crear hash
			HashCerrado hash = new HashCerrado(baldes, ranuras, new TecnicaCerradaRealeatorizada(baldes));

			// Asignar
			arch.agregarAlmacenamiento(hash);
			
			// Agregar vista
			hash.agregarVista(new VistaHashCerrado(hash));
			
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}
		
		if ( chk4.isSelected() ){
			
			Vector<Integer> lista = new Vector<Integer>();
			String[] valores = ListaPseudo.getText().split(","); //$NON-NLS-1$
			for (String v : valores){
				try{
					lista.add(Integer.parseInt( v.trim() ));
				}catch (NumberFormatException e){}
			}
			// Crear hash
			HashCerrado hash = new HashCerrado(baldes, ranuras, new TecnicaCerradaPseudoazar(baldes,lista));

			// Asignar
			arch.agregarAlmacenamiento(hash);
			
			// Agregar vista
			hash.agregarVista(new VistaHashCerrado(hash));
			
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}
		
	}
	
	public void doConfiguracionHashAbierto() {
		// Configurar hash hash.abierto
		
		// Obtener valores
		Integer baldes = HashAbierto.DEFAULT_CANT_BALDES;
		Integer ranuras = HashAbierto.DEFAULT_CANT_RANURAS;
		Integer ranurasSecundarias = HashAbierto.DEFAULT_CANT_RANURAS_SECUNDARIAS;
		double rhoDisenio = HashAbierto.DEFAULT_RHO_DISENIO; 
		
		try{
			baldes = (Integer) txtBaldesAbierto.getValue();
			ranuras = (Integer) txtRanurasAbierto.getValue();
			ranurasSecundarias = (Integer) txtRanurasSecundariasAbierto.getValue();
			rhoDisenio = (Double) txtRhoDisenioAbierto.getValue();
		}catch (Exception e){
			//Logger.getLogger("HASHABIERTO").warn("Error al cargar valores de hash hash.abierto");
		}
		
		if (chk5.isSelected()){
			// Crear hash
			HashAbierto hash = new HashAbierto(baldes, ranuras, ranurasSecundarias, rhoDisenio,true);

			// Asignar
			arch.agregarAlmacenamiento(hash);
			
			// Agregar vista
			hash.agregarVista(new VistaHashAbierto(hash));
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}
		
		if (chk6.isSelected()){
			// Crear hash
			HashAbierto hash = new HashAbierto(baldes, ranuras, ranurasSecundarias, rhoDisenio,false);

			// Asignar
			arch.agregarAlmacenamiento(hash);
			
			// Agregar vista
			hash.agregarVista(new VistaHashAbierto(hash));
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}
		
		

	}
	
	public void doConfiguracionFinal() {
		
		if ( ! chckbxSinElementos.isSelected() ){
			// Insertar elementos
			if ( ! elementos.isEmpty() )
				for(Elemento e : elementos){
					arch.insertar(e);
				}
			
		}
		
		// Actualizar pantalla
		parent.tabsArchivos.addTab(Messages.getString("SWING_FORM_SIN_TITULO"), arch); //$NON-NLS-1$
		arch.ultimaCaptura();
		
		parent.actualizarImagen();
		arch.agregarTab();
		
		
		g.dispose();
		
		//Cerramos el asistente
		dispose();
	}

	private void swap(DefaultListModel<Object> modelo, int a, int b) {
		Object aux = modelo.getElementAt(a);
		modelo.setElementAt(modelo.getElementAt(b), a);
		modelo.setElementAt(aux, b);
		
	}

}