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
		super(mainWindow.frmAplicacionDidacticaEstructuras, "Nuvevo archivo de estructuras", ModalityType.APPLICATION_MODAL);
		setResizable(false);
		//Logger.getRootLogger().trace("Iniciando asistente para nueva estructura");
		
		Graficador.setForm(this);
		this.parent = mainWindow;
		this.arch = arch;
		
		// Configurar ventana
		setTitle("Asistente de configuraci\u00F3n de una nueva estructura.");
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
		panPrincipal.add(panInicio, "name_43606110650234");
		
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
		
		label = new JLabel("");
		label.setIcon(new ImageIcon(formEstructura.class.getResource("/img/dataWizard.png")));
		panInicio.add(label, "1, 2");
		
		lblBienvenidoAlAsistente = new JLabel("Bienvenido al asistente de configuraci\u00F3n.");
		lblBienvenidoAlAsistente.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenidoAlAsistente.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panInicio.add(lblBienvenidoAlAsistente, "3, 2");
		
		separator_4 = new JSeparator();
		panInicio.add(separator_4, "1, 4, 3, 1");
		
		JLabel lblElementosIniciales = new JLabel(" Elementos Iniciales:");
		lblElementosIniciales.setFont(new Font("Tahoma", Font.BOLD, 12));
		panInicio.add(lblElementosIniciales, "1, 6, left, center");
			
			chckbxSinElementos = new JCheckBox("Sin Elementos");
			chckbxSinElementos.setSelected(true);
			chckbxSinElementos.addItemListener(new ItemListener() {
				public void itemStateChanged(ItemEvent arg0) {
					txtElementos.setEnabled(!txtElementos.isEnabled());
				}
			});
			chckbxSinElementos.setFont(new Font("Tahoma", Font.BOLD, 12));
			panInicio.add(chckbxSinElementos, "3, 6");
		
		txtElementos = new JTextField();
		txtElementos.setEnabled(false);
		txtElementos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtElementos.setText("");
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
		
			panInicio.add(txtElementos, "3, 8, fill, fill");
			txtElementos.setColumns(10);
			lblElementosIniciales.setLabelFor(txtElementos);
		
		separator_5 = new JSeparator();
		panInicio.add(separator_5, "1, 10, 3, 1");
		
		JLabel lblEstructuras = new JLabel(" Estructuras:");
		lblEstructuras.setFont(new Font("Tahoma", Font.BOLD, 12));
		panInicio.add(lblEstructuras, "1, 12, left, top");
		
		chckbxArbolesB = new JCheckBox("Arboles B");
		chckbxArbolesB.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxArbolesB.setSelected(true);
		panInicio.add(chckbxArbolesB, "3, 12, left, top");
		
		chckbxHashingCerrado = new JCheckBox("Hashing Cerrado");
		chckbxHashingCerrado.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxHashingCerrado.setSelected(true);
		panInicio.add(chckbxHashingCerrado, "3, 14, left, top");
		
		chckbxHashingAbierto = new JCheckBox("Hashing Abierto");
		chckbxHashingAbierto.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxHashingAbierto.setSelected(true);
		panInicio.add(chckbxHashingAbierto, "3, 16, fill, top");		
		
	}
		
	private void initAcciones() {
		JPanel panAcciones = new JPanel();
		FlowLayout fl_panAcciones = (FlowLayout) panAcciones.getLayout();
		fl_panAcciones.setVgap(1);
		contentPane.add(panAcciones, BorderLayout.SOUTH);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setIcon(new ImageIcon(formEstructura.class.getResource("/img/cancel.png")));
		btnCancelar.setMnemonic('c');
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {		
				dispose();
			}
		});
		panAcciones.add(btnCancelar);
		
		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setIcon(new ImageIcon(formEstructura.class.getResource("/img/next.png")));
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
		panPrincipal.add(panArbol, "panArbol");
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
				ColumnSpec.decode("13dlu"),
				FormFactory.MIN_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("20px"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.LINE_GAP_ROWSPEC,
				FormFactory.PREF_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				RowSpec.decode("12dlu"),
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

		lblArbolB = new JLabel("");
		lblArbolB.setIcon(new ImageIcon(formEstructura.class.getResource("/img/imArbol.png")));
		panArbol.add(lblArbolB, "1, 2, 12, 1, center, default");

		separator_6 = new JSeparator();
		panArbol.add(separator_6, "1, 4, 12, 1");

		// ------------------------------ ORDEN ------------------------------- //


		lblOrden = new JLabel(" Orden:");
		lblOrden.setHorizontalAlignment(SwingConstants.CENTER);
		lblOrden.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblOrden.setBounds(27, 77, 46, 14);
		panArbol.add(lblOrden, "1, 6, center, center");

		elementosInsertar = new DefaultListModel<Object>();
		elementosInsertar.addElement("Redistribucion Derecha");
		elementosInsertar.addElement("Split");
		elementosInsertar.addElement("Redistribucion Izquierda");

		txtOrden = new JSpinner();
		txtOrden.setModel(new SpinnerNumberModel(5, 3, 10, 1));
		txtOrden.setFont(new Font("Tahoma", Font.BOLD, 12));
		panArbol.add(txtOrden, "2, 6, left, default");

		separator_2 = new JSeparator();
		panArbol.add(separator_2, "1, 8, 12, 1");


		// ----------------------------- INSERCION ----------------------------- //


		lblEstrategiasDeInsercion = new JLabel("Estrategias de Inserci\u00F3n:");
		lblEstrategiasDeInsercion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstrategiasDeInsercion.setBounds(27, 125, 140, 14);
		panArbol.add(lblEstrategiasDeInsercion, "1, 10, center, center");

		listInsertar = new JList<Object>();
		listInsertar.setBorder(new LineBorder(new Color(0, 0, 0)));
		listInsertar.setModel(elementosInsertar);
		panArbol.add(listInsertar, "2, 10, 10, 3, fill, fill");

		JButton btnInsertarSubir = new JButton("Subir");
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
		panArbol.add(btnInsertarSubir, "12, 10, center, default");

		JButton btnInsertarBajar = new JButton("Bajar");
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
		panArbol.add(btnInsertarBajar, "12, 12, center, default");

		JButton btnEliminarSubir = new JButton("Subir");
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
		elementosEliminar.addElement("Redistribucion Izquierda");
		elementosEliminar.addElement("Redistribucion Derecha");
		elementosEliminar.addElement("Fusion Izquierda");
		elementosEliminar.addElement("Fusion Derecha");


		// ---------------------------- ELIMINACION ---------------------------- //


		lblEstrategiasDeEliminacion = new JLabel(" Estrategias de Eliminaci\u00F3n: ");
		lblEstrategiasDeEliminacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstrategiasDeEliminacion.setBounds(27, 180, 140, 14);
		panArbol.add(lblEstrategiasDeEliminacion, "1, 14, center, center");

		listEliminar = new JList<Object>();
		listEliminar.setBorder(new LineBorder(new Color(0, 0, 0)));
		listEliminar.setModel(elementosEliminar);
		panArbol.add(listEliminar, "2, 14, 10, 3, fill, fill");
		panArbol.add(btnEliminarSubir, "12, 14, center, default");



		JButton btnEliminarBajar = new JButton("Bajar");
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
		panArbol.add(btnEliminarBajar, "12, 16, center, default");

		separator_7 = new JSeparator();
		panArbol.add(separator_7, "1, 18, 12, 1");


		// ------------------------- TIPOS DE ARBOLES ------------------------- //


		lblTiposDeVista = new JLabel("Tipos de Vista");
		lblTiposDeVista.setFont(new Font("Tahoma", Font.BOLD, 14));
		panArbol.add(lblTiposDeVista, "1, 20, center, default");

		chckbxBArchivo = new JCheckBox("B Archivo");
		chckbxBArchivo.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBArchivo.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});
		chckbxBArchivo.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxBArchivo.setSelected(true);
		panArbol.add(chckbxBArchivo, "1, 22, left, default");

		chckbxBMasIndice = new JCheckBox("B+ Indice");
		chckbxBMasIndice.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxBMasIndice.setSelected(true);
		panArbol.add(chckbxBMasIndice, "2, 22");
		chckbxBMasIndice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBMasIndice.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});

		chckbxBHibrido = new JCheckBox("B Hibrido");
		chckbxBHibrido.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxBHibrido.setSelected(true);
		panArbol.add(chckbxBHibrido, "1, 24, left, default");
		chckbxBHibrido.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBHibrido.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});

		chckbxBmasClustered = new JCheckBox("B+ Clustered");
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
		chckbxBmasClustered.setFont(new Font("Tahoma", Font.BOLD, 12));


		panArbol.add(chckbxBmasClustered, "2, 24");

		chckbxBIndice = new JCheckBox("B Indice");
		chckbxBIndice.setFont(new Font("Tahoma", Font.BOLD, 12));
		chckbxBIndice.setSelected(true);
		panArbol.add(chckbxBIndice, "1, 26, left, default");
		chckbxBIndice.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if (chckbxBIndice.isSelected()){
					btnSiguiente.setEnabled(true);
				}else
					if ( (!chckbxBArchivo.isSelected()) && (!chckbxBMasIndice.isSelected()) && (!chckbxBHibrido.isSelected()) && (!chckbxBmasClustered.isSelected()) && (!chckbxBIndice.isSelected()))
						btnSiguiente.setEnabled(false);
			}
		});

		lblTamaoDeCluster = new JLabel("Tama\u00F1o  de Cluster");
		lblTamaoDeCluster.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panArbol.add(lblTamaoDeCluster, "2, 26");

		txtTamCluster = new JSpinner();
		txtTamCluster.setModel(new SpinnerNumberModel(3, 1, 10, 1));
		txtTamCluster.setValue(3);
		panArbol.add(txtTamCluster, "4, 26");



	}
	
	private void initPanHashCerrado() {
		
		JPanel panHashCerrado = new JPanel();
		panHashCerrado.setBorder(new LineBorder(new Color(0, 0, 0)));
		panPrincipal.add(panHashCerrado, "panHashCerrado");
		panHashCerrado.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("max(10dlu;pref)"),
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
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(formEstructura.class.getResource("/img/imHashC.png")));
		panHashCerrado.add(lblNewLabel, "2, 2, 3, 1");
		
		separator_1 = new JSeparator();
		panHashCerrado.add(separator_1, "2, 4, 3, 1");
		
		JLabel lblNewLabel_1 = new JLabel(" Cantidad de Baldes");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashCerrado.add(lblNewLabel_1, "2, 6, left, center");
		
		txtCantBaldes = new JSpinner();
		txtCantBaldes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCantBaldes.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				//System.out.println("ahora tengo " + txtCantBaldes.getValue());
			}
		});
		txtCantBaldes.setToolTipText("( 1 .. 30 )");
		txtCantBaldes.setModel(new SpinnerNumberModel(7, 1, 30, 1));
		panHashCerrado.add(txtCantBaldes, "4, 6, left, default");
		
		lblCeldasPorBalde = new JLabel(" Cantidad de Celdas por Balde");
		lblCeldasPorBalde.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashCerrado.add(lblCeldasPorBalde, "2, 8, left, default");
		
		txtCantRanuras = new JSpinner();
		txtCantRanuras.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtCantRanuras.setModel(new SpinnerNumberModel(2, 1, 30, 1));
		panHashCerrado.add(txtCantRanuras, "4, 8, left, default");
		
		separator = new JSeparator();
		panHashCerrado.add(separator, "2, 10, 3, 1");
		
		lblVistasDeTcnicas = new JLabel(" Vistas de T\u00E9cnicas");
		lblVistasDeTcnicas.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashCerrado.add(lblVistasDeTcnicas, "2, 12, left, default");
		
		chk1 = new JCheckBox(" T\u00E9cnica de Reasignaci\u00F3n Lineal");
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
		chk1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashCerrado.add(chk1, "4, 12");
		
		chk2 = new JCheckBox(" T\u00E9cnica Cuadr\u00E1tica");
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
		chk2.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashCerrado.add(chk2, "4, 14");
		
		chk3 = new JCheckBox(" T\u00E9cnica Realeatorizada");
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
		chk3.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashCerrado.add(chk3, "4, 16");
		
		chk4 = new JCheckBox(" T\u00E9cnica PseudoAzar");
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
		chk4.setFont(new Font("Tahoma", Font.BOLD, 12));
		chk4.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				ListaPseudo.setEnabled(chk4.isSelected() );	
				lb2.setVisible(chk4.isSelected());
				lb3.setVisible(chk4.isSelected());
			}
		});
		
		lb2 = new JLabel("La lista deber\u00EDa tener");
		lb2.setHorizontalAlignment(SwingConstants.CENTER);
		lb2.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lb2.setVisible(false);
		panHashCerrado.add(lb2, "2, 18");
		panHashCerrado.add(chk4, "4, 18");
		
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
		
		lb3 = new JLabel("elementos diferentes");
		lb3.setHorizontalAlignment(SwingConstants.CENTER);
		lb3.setVisible(false);
		lb3.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panHashCerrado.add(lb3, "2, 20");
		ListaPseudo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ListaPseudo.setEnabled(false);
		panHashCerrado.add(ListaPseudo, "4, 20, fill, default");
		ListaPseudo.setColumns(10);
				
	}

	private void initPanHashAbierto() {
		JPanel panHashAbierto = new JPanel();
		panHashAbierto.setBorder(new LineBorder(new Color(0, 0, 0)));
		panPrincipal.add(panHashAbierto, "panHashAbierto");
		panHashAbierto.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("10dlu"),
				FormFactory.MIN_COLSPEC,
				ColumnSpec.decode("pref:grow"),},
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
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(formEstructura.class.getResource("/img/imHashA.png")));
		panHashAbierto.add(lblNewLabel_2, "2, 2, 4, 1");
		
		separator_7 = new JSeparator();
		panHashAbierto.add(separator_7, "1, 4, 5, 1");
		
		lblTipo = new JLabel(" Tipo:");
		lblTipo.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(lblTipo, "2, 6");
		
		chk5 = new JCheckBox("Separado");
		chk5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if ( chk5.isSelected() )
					btnSiguiente.setEnabled(true);
				if ( !chk5.isSelected() && ! chk6.isSelected() )
					btnSiguiente.setEnabled(false);
			}
		});
		chk5.setSelected(true);
		chk5.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(chk5, "2, 8");
		
		chk6 = new JCheckBox("Separado con Crecimiento Lineal");
		chk6.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				if ( chk6.isSelected() )
					btnSiguiente.setEnabled(true);
				if ( !chk5.isSelected() && ! chk6.isSelected() )
					btnSiguiente.setEnabled(false);
			}
		});
		chk6.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(chk6, "2, 10");
		
		separator_6 = new JSeparator();
		panHashAbierto.add(separator_6, "1, 12, 5, 1");
		
		lblConfiguracin = new JLabel(" Configuraci\u00F3n:");
		lblConfiguracin.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(lblConfiguracin, "2, 14");
		
		JLabel lblNewLabel_1 = new JLabel(" Cantidad de Baldes");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(lblNewLabel_1, "2, 16, left, center");
		
		txtBaldesAbierto = new JSpinner();
		txtBaldesAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtBaldesAbierto.setModel(new SpinnerNumberModel(7, 1, 30, 1));
		panHashAbierto.add(txtBaldesAbierto, "4, 16, fill, default");
		
		lblCeldasPorBalde = new JLabel(" Cantidad de Ranuras por Balde");
		lblCeldasPorBalde.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(lblCeldasPorBalde, "2, 18, left, default");
		
		txtRanurasAbierto = new JSpinner();
		txtRanurasAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtRanurasAbierto.setModel(new SpinnerNumberModel(2, 1, 30, 1));
		panHashAbierto.add(txtRanurasAbierto, "4, 18, fill, default");
		
		JLabel lblRanurasSecundarias = new JLabel(" Cantidad de Ranuras Secundarias");
		lblRanurasSecundarias.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(lblRanurasSecundarias, "2, 20, left, default");
		
		txtRanurasSecundariasAbierto = new JSpinner();
		txtRanurasSecundariasAbierto.setModel(new SpinnerNumberModel(2, 1, 30, 1));
		txtRanurasSecundariasAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12));	
		panHashAbierto.add(txtRanurasSecundariasAbierto, "4, 20, fill, default");
		
		JLabel lblRhoDisenio = new JLabel(" Rho de Dise\u00F1o");
		lblRhoDisenio.setFont(new Font("Tahoma", Font.BOLD, 12));
		panHashAbierto.add(lblRhoDisenio, "2, 22, left, default");
		
		txtRhoDisenioAbierto = new JSpinner();
		txtRhoDisenioAbierto.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txtRhoDisenioAbierto.setModel(new SpinnerNumberModel(0.9, 0.01, 100.0, 0.05));
		panHashAbierto.add(txtRhoDisenioAbierto, "4, 22, left, default");
		
		lblValoresCercanosA = new JLabel(" TIP: Valores cercanos a 1 son m\u00E1s eficientes.");
		lblValoresCercanosA.setFont(new Font("Tahoma", Font.ITALIC, 12));
		panHashAbierto.add(lblValoresCercanosA, "5, 22");
		
		separator_3 = new JSeparator();
		panHashAbierto.add(separator_3, "1, 26, 5, 1");
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
			String[] valores = txtElementos.getText().split(",");
			for (String v : valores){
				try{
					elementos.add(new Elemento(Integer.parseInt( v.trim() )));
				}catch (NumberFormatException e){
					ConsolaManager.getInstance().escribirAdv("Error al obtener elementos iniciales");
					//Logger.getLogger("NuevaEstructura").warn("Error al obtener elementos iniciales");
				}
			}		
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Elementos iniciales: " + elementos);
		}else{
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Estructura sin Elementos");
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
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Crear arbol de orden " + orden);
		ArbolB arbol = new ArbolB(orden);
		arch.agregarAlmacenamiento(arbol);

		//----------------------------------------------------------------------------------------------------------//

		// Agregar vista Arbol B Indice
		if(chckbxBIndice.isSelected()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Crear nuevo tipo de arbol: B Indice");
			arbol.agregarVista(new BIndice(arbol));
		}

		// Agregar vista Arbol B Hibrido
		if(chckbxBHibrido.isSelected()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Crear nuevo tipo de arbol: B Hibrido");
			arbol.agregarVista(new BHibrido(arbol));
		}

		// Agregar vista Arbol B Archivo
		if(chckbxBArchivo.isSelected()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Crear nuevo tipo de arbol: B Archivo");
			arbol.agregarVista(new BArchivo(arbol));
		}

		// Agregar vista Arbol B Mas Indice
		if(chckbxBMasIndice.isSelected()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Crear nuevo tipo de arbol: B+ Indice");
			arbol.agregarVista(new BMasIndice(arbol));

		}

		// Agregar vista Arbol B Mas Clustered
		if(chckbxBmasClustered.isSelected()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Crear nuevo tipo de arbol: B+ Clustered");
			try{
				int cluster = (int) txtTamCluster.getValue();
				System.out.println("cluster " + cluster);
				arbol.agregarVista(new BMasClustered(arbol, cluster));
			}catch(Exception e){
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Tamaño de cluster incorrecto.");
				arbol.agregarVista(new BMasClustered(arbol));				
			}
		}

		//----------------------------------------------------------------------------------------------------------// 

		// Setear metodo de insercion
		@SuppressWarnings("rawtypes")
		DefaultListModel modeloInsertar = (DefaultListModel) listInsertar.getModel();
		for(int i = 0; i < modeloInsertar.getSize(); i++){
			switch (modeloInsertar.elementAt(i).toString()) {
			case "Split":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de insercion: Split");
				arbol.addEstrategiaCrecim(new CrecimSplit());
				break;
			case "Redistribucion Izquierda":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de insercion: Redistribucion Izquierda");
				arbol.addEstrategiaCrecim(new CrecimRedistIzquierda());
				break;
			case "Redistribucion Derecha":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de insercion: Redistribucion Derecha");
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
			case "Fusion Izquierda":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de eliminacion: Fusion Izquierda");
				arbol.addEstrategiaDecrec(new DecrecFusionIzquierda());
				break;
			case "Fusion Derecha":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de eliminacion: Fusion Derecha");
				arbol.addEstrategiaDecrec(new DecrecFusionDerecha());
				break;
			case "Redistribucion Izquierda":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de eliminacion: Redistribucion Izquierda");
				arbol.addEstrategiaDecrec(new DecrecRedistIzquierda());
				break;
			case "Redistribucion Derecha":
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Agregar metodo de eliminacion: Redistribucion Derecha");
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
			String[] valores = ListaPseudo.getText().split(",");
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
		parent.tabsArchivos.addTab("Sin Título", arch);
		arch.agregarTab();
		arch.ultimaCaptura();
		
		parent.actualizarImagen();
		
		
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