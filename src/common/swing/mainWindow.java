package common.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

// import org.apache.log4j.Logger;

import common.FiltroArchivoEstructura;
import common.Messages;
import common.estructura.Almacenamiento;
import common.estructura.Elemento;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Toolkit;

import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.Cursor;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import common.swing.ConsolaManager;
import javax.swing.JRadioButtonMenuItem;

public class mainWindow {
	// EGit desde Eclipse

	// Ruta del archivo de ayuda
	//private static final String DIR_MANUAL = "./doc/manual.pdf"; // Linux
	private static final String DIR_MANUAL = "doc/manual.pdf"; // Windows
	
	// Ruta de salida estandar
	//private static final String DIR_SALIDA = "./logs.log"; // Linux
	private static final String DIR_SALIDA = "logs.log";  // Windows
	//private static final String DIR_SALIDA = System.getProperty("user.home") + "logs.log";  // Windows (Fix)
	
	// Ruta de salida de errores
	//private static final String DIR_ERR = "./logs.log"; // Linux
	private static final String DIR_ERR = "logs.log";  // Windows
	
	
	public JFrame frmAplicacionDidacticaEstructuras;
	public Archivo archivo;
	private JTextField CampoDeTexto;
	public JTabbedPane tabsArchivos;
	private Box verticalBox;
	private JTextPane Informacion;
	public JTextPane Consola;
	private JScrollPane scrollConsola;
	private JButton btnInsertar;
	private JButton btnEliminar;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		final String[] argumentos = args;
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					//Windows Style
					//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");	
					
					//	UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
					mainWindow window = new mainWindow();
					window.frmAplicacionDidacticaEstructuras.setVisible(true);
					
					// Propiedades
					System.out.println(System.getProperty("os.name"));
					System.out.println(System.getProperty("os.arch"));
					System.out.println(System.getProperty("os.version"));
					System.out.println(System.getProperty("user.dir"));
					System.out.println(System.getProperty("user.home"));
					
					// Argumentos
					System.out.println("Inicio argumentos (v2)");
					for (String s : argumentos){
						ConsolaManager.getInstance().escribirInfo("Arbol B", s);
						//ConsolaManager.getInstance().escribirInfo("Arbol B", );
						System.out.println(s);
						//window.abrirEstructura(s);
					}
					System.out.println("Fin argumentos (v2)");
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//Logger.getRootLogger().trace("Iniciando aplicacion");
		File errFile = new File(DIR_ERR);
		File outFile = new File(DIR_SALIDA);
		PrintStream err = null;
		PrintStream out = null;
		try {
			err = new PrintStream(errFile);
			out = new PrintStream(outFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		//System.setErr(err);
		//System.setOut(out);
		
		// Inicializar ventana principal
		frmAplicacionDidacticaEstructuras = new JFrame();
		frmAplicacionDidacticaEstructuras.setIconImage(Toolkit.getDefaultToolkit().getImage(mainWindow.class.getResource("/img/icono.png")));
		frmAplicacionDidacticaEstructuras.setTitle("T.H.Solution. Aplicaci\u00F3n Did\u00E1ctica de Estructuras de Almacenamiento");
		frmAplicacionDidacticaEstructuras.setBounds(100, 100, 1200, 600);
		frmAplicacionDidacticaEstructuras.setLocationRelativeTo(null);
		frmAplicacionDidacticaEstructuras.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAplicacionDidacticaEstructuras.getContentPane().setLayout(new BorderLayout(0, 0));
		frmAplicacionDidacticaEstructuras.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		
		//Cosa loca para que el fondo de los tabbed panes sean del color comun del jframe. Asi el Generando se e lindo.
		Color c = frmAplicacionDidacticaEstructuras.getBackground();
		UIManager.put("TabbedPane.contentAreaColor", new Color(c.getRed(),c.getGreen(), c.getBlue())); 
		
		
		// Inicializar panel de menu derecho
		JPanel panel = new JPanel();
		frmAplicacionDidacticaEstructuras.getContentPane().add(panel, BorderLayout.EAST);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		verticalBox = Box.createVerticalBox();
		verticalBox.setBorder(new LineBorder(Color.GRAY));
		verticalBox.setPreferredSize(new Dimension(340,0));
		panel.add(verticalBox);
		
		CampoDeTexto = new JTextField();
		//Centramos su texto.
		CampoDeTexto.setHorizontalAlignment(JTextField.CENTER);
		CampoDeTexto.addKeyListener(new KeyAdapter(){
		   public void keyTyped(KeyEvent e)
		   {
		      char caracter = e.getKeyChar();

		      // Verificar si la tecla pulsada no es un digito
		      if (((caracter < '0') || (caracter > '9')) && (caracter != '\b' /*corresponde a BACK_SPACE*/) && (caracter != ',' ) )
		      {
		         e.consume();  // ignorar el evento de teclado
		      }
		   }			@Override
			public void keyReleased(KeyEvent e) {
			   if (archivo != null && CampoDeTexto.getText().length() > 0){
				   btnInsertar.setEnabled(true);
				   btnEliminar.setEnabled(true);
			   }else{
				   btnInsertar.setEnabled(false);
			   	   btnEliminar.setEnabled(false);
			   }
			}
});
		CampoDeTexto.setFont(new Font("Verdana", Font.BOLD, 14));
		CampoDeTexto.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		
		JLabel lblInformacin = new JLabel("Informaci\u00F3n ");
		lblInformacin.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblInformacin.setHorizontalAlignment(SwingConstants.LEFT);
		lblInformacin.setFont(new Font("Verdana", Font.BOLD, 16));
		verticalBox.add(lblInformacin);
		
		JSeparator separator_2 = new JSeparator();
		verticalBox.add(separator_2);
	
		Informacion = new JTextPane();
		Informacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		Informacion.setEditable(false);
		Informacion.setPreferredSize(new Dimension(0, 400));
		Informacion.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		verticalBox.add(Informacion);
		
		JSeparator separator_3 = new JSeparator();
		verticalBox.add(separator_3);
		
		JLabel lblAcciones = new JLabel("Insertar/Eliminar Elementos");
		lblAcciones.setFont(new Font("Verdana", Font.BOLD, 16));
		lblAcciones.setHorizontalAlignment(SwingConstants.CENTER);
		lblAcciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(lblAcciones);
		verticalBox.add(CampoDeTexto);
		CampoDeTexto.setColumns(10);
		
		//Creo un Layout para ordenar los botones de Insertar, Eliminar y Buscar.
		Box BotonBoxAcciones = Box.createHorizontalBox();
		BotonBoxAcciones.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		verticalBox.add(BotonBoxAcciones);
		
		btnInsertar = new JButton("Insertar");
		btnInsertar.setEnabled(false);
		btnInsertar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnInsertar.setHorizontalAlignment(SwingConstants.LEADING);
		btnInsertar.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnInsertar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				insertarDatos();
			}
		});
		BotonBoxAcciones.add(btnInsertar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setEnabled(false);
		btnEliminar.setFont(new Font("Verdana", Font.BOLD, 12));
		btnEliminar.setHorizontalAlignment(SwingConstants.TRAILING);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eliminarDatos();
			}
		});
		btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		BotonBoxAcciones.add(btnEliminar);
		
		initBotonesCaptura();
		
		//Consola
		Consola = new JTextPane();
		Consola.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		Consola.setEditable(false);
		Consola.setToolTipText("Consola");
		Consola.setText("Bienvenido!!\r\n");
		Consola.setPreferredSize(new Dimension(0, 175));
		Consola.setDoubleBuffered(true);
		Consola.setFont(new Font("Verdana", Font.BOLD, 12));
		frmAplicacionDidacticaEstructuras.getContentPane().add(Consola, BorderLayout.SOUTH);
			
		//Scroll de la Consola
	    scrollConsola = new JScrollPane(Consola);
		scrollConsola.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollConsola.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollConsola.setPreferredSize(new Dimension(0,175));
		frmAplicacionDidacticaEstructuras.getContentPane().add(scrollConsola, BorderLayout.SOUTH);
		
		//Asignamos el ConsolaManager
		ConsolaManager.getInstance(Consola,scrollConsola);
		InfoManager.getInstance(Informacion);		
		
		//Panel de Consola
		final JPanel PanelConsola = new JPanel();
		scrollConsola.setColumnHeaderView(PanelConsola);
		GridBagLayout gbl_PanelConsola = new GridBagLayout();
		gbl_PanelConsola.columnWidths = new int[]{94, 19, 19, 19, 0};
		gbl_PanelConsola.rowHeights = new int[]{21, 0};
		gbl_PanelConsola.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_PanelConsola.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		PanelConsola.setLayout(gbl_PanelConsola);
		
		//Bot�n de Borrar Consola
		final JButton Borrar = new JButton("");
		Borrar.setBorder(null);
		Borrar.setToolTipText("Borrar Consola");
		//Insets !! Importante para que el boton sea chiquito.
		Borrar.setMargin(new java.awt.Insets(2, 2, 2, 2));
		Borrar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Consola.setText("");
			}
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Borrar.setBorder(new LineBorder(Color.GRAY, 1));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Borrar.setBorder(null);
			}
		});
		
		
		//Label que dice CONSOLA
		JLabel Cons = new JLabel("  Consola   ");
		Cons.setHorizontalAlignment(SwingConstants.LEFT);
		Cons.setAlignmentX(1.0f);
		Cons.setFont(new Font("Verdana", Font.BOLD, 16));
		GridBagConstraints gbc_Cons = new GridBagConstraints();
		gbc_Cons.anchor = GridBagConstraints.EAST;
		gbc_Cons.insets = new Insets(0, 0, 0, 5);
		gbc_Cons.gridx = 0;
		gbc_Cons.gridy = 0;
		PanelConsola.add(Cons, gbc_Cons);
		Borrar.setSelectedIcon(new ImageIcon(mainWindow.class.getResource("/img/borrar_consola.png")));
		Borrar.setIcon(new ImageIcon(mainWindow.class.getResource("/img/borrar_consola.png")));
		Borrar.setPreferredSize(new Dimension(19, 18));
		Borrar.setMargin(new Insets(0, 0, 0, 0));
		Borrar.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_Borrar = new GridBagConstraints();
		gbc_Borrar.anchor = GridBagConstraints.EAST;
		gbc_Borrar.insets = new Insets(0, 0, 0, 5);
		gbc_Borrar.gridx = 1;
		gbc_Borrar.gridy = 0;
		PanelConsola.add(Borrar, gbc_Borrar);
		
		//Bot�n de maximizar.
		final JButton Max = new JButton("");
		//Bot�n de minimzar.
		final JButton Min = new JButton("");
		Max.setEnabled(false);
		Max.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Consola.setPreferredSize(new Dimension( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,175));
				scrollConsola.setPreferredSize(new Dimension( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,175));
				frmAplicacionDidacticaEstructuras.pack();
				frmAplicacionDidacticaEstructuras.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
				Max.setEnabled(false);
				Min.setEnabled(true);
			}
		});
		Max.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				if ( Max.isEnabled() )
				Max.setBorder(new LineBorder(Color.GRAY, 1));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Max.setBorder(null);
			}
		});
		
		//Bot�n de minimizar.
		Min.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if ( Min.isEnabled() )
				Min.setBorder(new LineBorder(Color.GRAY, 1));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				Min.setBorder(null);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				//Achicar la consola aca.
				Consola.setPreferredSize(new Dimension( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,0));
				scrollConsola.setPreferredSize(new Dimension( java.awt.Toolkit.getDefaultToolkit().getScreenSize().width,24));
				frmAplicacionDidacticaEstructuras.pack();
				frmAplicacionDidacticaEstructuras.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
				Max.setEnabled(true);
				Min.setEnabled(false);
			}
		});
		Min.setAlignmentX(Component.CENTER_ALIGNMENT);
		Min.setBorder(null);
		Min.setToolTipText("Minimizar Consola");
		//Insets !! Importante para que el boton sea chiquito.
		Min.setMargin(new java.awt.Insets(2, 2, 2, 2));
		Min.setPreferredSize(new Dimension(19, 18));
		Min.setMargin(new Insets(0, 0, 0, 0));
		Min.setIcon(new ImageIcon(mainWindow.class.getResource("/img/minimizar.png")));
		GridBagConstraints gbc_Min = new GridBagConstraints();
		gbc_Min.insets = new Insets(0, 0, 0, 5);
		gbc_Min.gridx = 2;
		gbc_Min.gridy = 0;
		PanelConsola.add(Min, gbc_Min);
		Max.setAlignmentX(Component.CENTER_ALIGNMENT);
		Max.setBorder(null);
		Max.setToolTipText("Maximizar Consola");
		//Insets !! Importante para que el boton sea chiquito.
		Max.setMargin(new java.awt.Insets(2, 2, 2, 2));
		Max.setPreferredSize(new Dimension(19, 18));
		Max.setMargin(new Insets(0, 0, 0, 0));
		Max.setIcon(new ImageIcon(mainWindow.class.getResource("/img/maximizar.png")));
		GridBagConstraints gbc_Max = new GridBagConstraints();
		gbc_Max.gridx = 3;
		gbc_Max.gridy = 0;
		PanelConsola.add(Max, gbc_Max);
	
		// Inicializar panel principal	
		tabsArchivos = new JTabbedPane(JTabbedPane.TOP);
		tabsArchivos.setForeground(Color.BLACK);
		tabsArchivos.setBackground(Color.WHITE);
		frmAplicacionDidacticaEstructuras.getContentPane().add(tabsArchivos, BorderLayout.CENTER);
		
		
		// Inicializar menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frmAplicacionDidacticaEstructuras.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setMnemonic('a');
		menuBar.add(mnArchivo);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirEstructura();
			}
		});
		
		JMenuItem mntmNuevaEstructura = new JMenuItem("Nueva Estructura          ");
		mntmNuevaEstructura.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
		mntmNuevaEstructura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nuevaEstructura();
			}
		});
		mnArchivo.add(mntmNuevaEstructura);
		mntmAbrir.setMnemonic('a');
		mnArchivo.add(mntmAbrir);
		
		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarEstructura();
			}
		});
		mntmGuardar.setMnemonic('g');
		mnArchivo.add(mntmGuardar);
		
		JMenuItem mntmGuardarComo = new JMenuItem("Guardar Como");
		mntmGuardarComo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mntmGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardarComoEstructura();
			}
		});
		mnArchivo.add(mntmGuardarComo);
		
		JMenuItem mntmCerrarEstructura = new JMenuItem("Cerrar Estructura");
		mntmCerrarEstructura.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrarEstructura();
			}
		});
		
		JSeparator separator = new JSeparator();
		mnArchivo.add(separator);
		mnArchivo.add(mntmCerrarEstructura);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_MASK));
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Agregar ventana modal para preguntar si desea guardar.
				//Si esta todo guardado ( incluir booleano ) entonces la aplicacion se tendria que cerrar sin
				//mostrar la ventana modal.
				System.exit(0);
			}
		});
		
		JSeparator separator_1 = new JSeparator();
		mnArchivo.add(separator_1);
		mnArchivo.add(mntmSalir);
		
		JMenu mnBuscar = new JMenu("Buscar");
		menuBar.add(mnBuscar);
		
		JMenuItem mntmRealizarBusqueda = new JMenuItem("Realizar B\u00FAsqueda");
		mntmRealizarBusqueda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_MASK));
		mntmRealizarBusqueda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirbusqueda();
			}
		});
		mnBuscar.add(mntmRealizarBusqueda);
		
		JMenu mnIdioma = new JMenu("Idioma");
		menuBar.add(mnIdioma);
		
		
		ButtonGroup group = new ButtonGroup();
		
		JRadioButtonMenuItem rdbtnmntmEspaolargentina = new JRadioButtonMenuItem("Español (Argentina)");
		rdbtnmntmEspaolargentina.setSelected(true);
		rdbtnmntmEspaolargentina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Messages.setLanguage("es_AR");
			}
		});
		mnIdioma.add(rdbtnmntmEspaolargentina);
		rdbtnmntmEspaolargentina.setSelected(true);
		group.add(rdbtnmntmEspaolargentina);
		
		JRadioButtonMenuItem rdbtnmntmInglesus = new JRadioButtonMenuItem("Ingles (US)");
		rdbtnmntmInglesus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Messages.setLanguage("en_US");
			}
		});
		mnIdioma.add(rdbtnmntmInglesus);
		group.add(rdbtnmntmInglesus);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAyuda = new JMenuItem("Ayuda - Manual");
		mntmAyuda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		mntmAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File file = new File(DIR_MANUAL);
					Desktop.getDesktop().open(file);
				} catch (Exception e) {}
			}
		});
		mnAyuda.add(mntmAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("Acerca de...");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirAyuda();
			}
		});
		mnAyuda.add(mntmAcercaDe);
	}
	
	private void initBotonesCaptura() {
		
		JSeparator separator = new JSeparator();
		verticalBox.add(separator);
		
		JLabel lblCapturas = new JLabel("Capturas");
		lblCapturas.setFont(new Font("Verdana", Font.BOLD, 16));
		lblCapturas.setAlignmentX(Component.CENTER_ALIGNMENT);
		verticalBox.add(lblCapturas);
		
		//Creo un Layout para ordenar los botones.
		Box BotonBox = Box.createHorizontalBox();
		BotonBox.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		verticalBox.add(BotonBox);
		
		
		JButton btnPrimero = new JButton("<<");
		btnPrimero.setFont(new Font("Verdana", Font.BOLD, 12));
		btnPrimero.setToolTipText("Primera Captura");
		btnPrimero.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnPrimero.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (archivo != null){
					archivo.primeraCaptura();
				}
				actualizarImagen();
			}
		});
		
		BotonBox.add(btnPrimero);	
		JButton btnAnterior = new JButton("<");
		btnAnterior.setToolTipText("Captura Anterior");
		btnAnterior.setFont(new Font("Verdana", Font.BOLD, 12));
		btnAnterior.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (archivo != null){
					archivo.anteriorCaptura();
				}
				actualizarImagen();
			}
		});
		BotonBox.add(btnAnterior);

		JButton btnSiguiente = new JButton(">");
		btnSiguiente.setToolTipText("Captura Siguiente");
		btnSiguiente.setFont(new Font("Verdana", Font.BOLD, 12));
		btnSiguiente.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (archivo != null){
					archivo.siguienteCaptura();
				}
				actualizarImagen();
			}
		});
		BotonBox.add(btnSiguiente);
		
		JButton btnUltimo = new JButton(">>");
		btnUltimo.setToolTipText("\u00DAltima Captura");
		btnUltimo.setFont(new Font("Verdana", Font.BOLD, 12));
		btnUltimo.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (archivo != null){
					archivo.ultimaCaptura();
				}
				actualizarImagen();
			}
		});
		BotonBox.add(btnUltimo);		
	}
	
	protected void nuevaEstructura() {
		
		//Borramos la consola.
		Consola.setText("");
		
		if (archivo == null){
			//Logger.getLogger("ARCHIVO").info("Creando nueva estructura");
			
			// Construir archivo nuevo
			archivo = new Archivo(JTabbedPane.BOTTOM);
			
			// Agregar pesta�a referida al archivo
			archivo.setBackground(Color.WHITE);
			archivo.setForeground(Color.BLACK);
	        
	        // Asistente para configurar archivo		
			formEstructura nuevaEstructura = new formEstructura(this, archivo);
			nuevaEstructura.setVisible(true);
			nuevaEstructura.setAlwaysOnTop(true);
			nuevaEstructura.setModal(true);
			nuevaEstructura.setModalityType(ModalityType.APPLICATION_MODAL);			
		}else{
			cerrarEstructura();
			nuevaEstructura();
		}		
	}
	
	protected void abrirEstructura() {

		//Borramos la consola.
		Consola.setText("");
		
		if (archivo == null){

			//Logger.getLogger("ARCHIVO").info("Abrir archivo");
			
			//Crear un objeto FileChooser
	        JFileChooser fc = new JFileChooser();
	        
	        //Mostrar la ventana para abrir archivo y recoger la respuesta
	        fc.setFileFilter(new FiltroArchivoEstructura());
	        int respuesta = fc.showOpenDialog(null);
	        
	        //Comprobar si se ha pulsado Aceptar
	        if (respuesta == JFileChooser.APPROVE_OPTION){
	            //Crear un objeto File con el archivo elegido
	            File archivoElegido = fc.getSelectedFile();
	            
	            //Mostrar el nombre del archivo en un campo de texto
	            System.out.println(archivoElegido.getPath());
	            	            	            
	            // Intenta cargar archivo y mostrarlo en pantalla
	    		archivo = new Archivo(JTabbedPane.BOTTOM);
	    		if(archivo.cargar(archivoElegido.getPath())){

		            // Agregar pesta�a referida al archivo
		    		archivo.setBackground(Color.WHITE);
		    		tabsArchivos.addTab(archivoElegido.getName(), archivo);
		    		
		    		// Agrega vistas al archivo
		    		archivo.ultimaCaptura();
					actualizarImagen();	
		    		archivo.agregarTab();
	    		}else{
	    			archivo = null;
	    		}
	        }
		}else{
			cerrarEstructura();
			abrirEstructura();
		}		
	}
	
	protected void abrirEstructura(String est){
		File archivoElegido = new File(est);
		
		// Intenta cargar archivo y mostrarlo en pantalla
		archivo = new Archivo(JTabbedPane.BOTTOM);
		if(archivo.cargar(archivoElegido.getPath())){

            // Agregar pesta�a referida al archivo
    		archivo.setBackground(Color.WHITE);
    		tabsArchivos.addTab(archivoElegido.getName(), archivo);
    		
    		// Agrega vistas al archivo
    		archivo.ultimaCaptura();
			actualizarImagen();
    		archivo.agregarTab();
		}else{
			archivo = null;
		}
	}
	
	protected void guardarEstructura(){
		if (archivo != null){
			//Logger.getLogger("ARCHIVO").info("Guardar archivo");
			
			// Verificar si tiene referencia a una direccion
			if (archivo.getPath() != null){
				archivo.guardar();
			}else{
				guardarComoEstructura();
			}
		}else{
			//Logger.getLogger("ARCHIVO").info("Ningun archivo a guardar");
		}
	}

	protected void guardarComoEstructura() {
		if (archivo != null){		
			//Logger.getLogger("ARCHIVO").info("Guardando archivo como");

			// Crear un objeto FileChooser
			JFileChooser fc = new JFileChooser();

			// Mostrar la ventana para abrir archivo y recoger la respuesta
			fc.setFileFilter(new FiltroArchivoEstructura());
			int respuesta = fc.showSaveDialog(null);

			// Comprobar si se ha pulsado Aceptar
			if (respuesta == JFileChooser.APPROVE_OPTION){
				// Crear un objeto File con el archivo elegido
				File archivoElegido = fc.getSelectedFile();
				// Mostrar el nombre del archvivo en un campo de texto
				System.out.println(archivoElegido.getPath());

				archivo.guardar(archivoElegido.getPath(), archivoElegido.getName());
				tabsArchivos.setTitleAt(tabsArchivos.getSelectedIndex(), archivoElegido.getName());
			}	
		}	
	}
	
	protected void cerrarEstructura() {
		if ( (archivo != null) && (archivo.isChanged()) ){
			// Preguntar si desea guardar los cambios
			//Logger.getLogger("ARCHIVO").warn("¿Desea guardar los cambios?");
			//esto desps sacarlo que esta mal
			archivo = null;
		}else{
			//Logger.getLogger("ARCHIVO").info("Archivo cerrado sin cambios");
			archivo = null;
		}
		
		//Aca tenemos que sacar los tabs.
		if ( tabsArchivos.getSelectedIndex() >= 0)
		tabsArchivos.remove(tabsArchivos.getSelectedIndex());
		//Borramos el panel de informacion.
		Informacion.setText("");
	}

	protected void eliminarDatos() {
		if (archivo != null){

			// Obtener elementos
			Vector<Elemento> elementos = new Vector<Elemento>();
			String[] valores = CampoDeTexto.getText().split(",");
			for (String v : valores){
				try{
					elementos.add(new Elemento(Integer.parseInt( v.trim() )));
				}catch (NumberFormatException e){
					//Logger.getLogger("Eliminacion").warn("Error al obtener elementos a eliminar", e);
				}
			}		
			// Logger.getLogger("Eliminacion").info("Elementos a eliminar: " + elementos);

			// Eliminar elementos
			for (Elemento e : elementos){
				archivo.eliminar(e);
			}

			archivo.ultimaCaptura();
			actualizarImagen();		
		}else{
			// System.out.println("Ningun archivo cargado para eliminar datos");
		}
		
		CampoDeTexto.setText("");
		
	}

	protected void insertarDatos() {
		
		if (archivo != null){

			long tiempoInicial = System.currentTimeMillis();

			// Obtener elementos
			Vector<Elemento> elementos = new Vector<Elemento>();
			String[] valores = CampoDeTexto.getText().split(",");

			for (String v : valores){
				try{
					elementos.add(new Elemento(Integer.parseInt( v.trim() )));
				}catch (NumberFormatException e){
					//Logger.getLogger("Insercion").warn("Error al obtener elementos a insertar", e);
				}
			}		
			//Logger.getLogger("Insercion").info("Elementos a insertar: " + elementos);

			// Insertar elementos en el archivo
			for (Elemento e : elementos){
				archivo.insertar(e);
			}


			long tiempoMedio = System.currentTimeMillis();

			// Actualizar pantalla
			archivo.ultimaCaptura();
			actualizarImagen();

			long tiempoFinal = System.currentTimeMillis();
			
			System.out.println("Insertar datos: " + (tiempoMedio - tiempoInicial));
			System.out.println("Mostrar en pantalla: " + (tiempoFinal - tiempoMedio));
		}else{
			System.out.println("Ningun archivo cargado para insertar datos");
		}
		
		CampoDeTexto.setText("");
		
	}

	public void actualizarImagen() {
		// Renderizar imagen actual
		if (archivo != null){
			archivo.generateGraph();
			archivo.actualizar();
		}else{
			System.out.println("Ninguna estructura a actualizar");
		}

	}
	
	private void abrirAyuda(){
		// Asistente para configurar archivo
		dialogAcercaDe about = new dialogAcercaDe(this);
		about.setVisible(true);
		about.setAlwaysOnTop(true);
		about.setModal(true);
		about.setModalityType(ModalityType.APPLICATION_MODAL);	
		
	}
	
	private void abrirbusqueda() {	
	if (archivo != null){
		Vector<Almacenamiento> almac = archivo.getAlmac();
		if ( almac != null && almac.size() > 0 ){
			Buscar b = new Buscar(this,almac);
			b.setVisible(true);
			b.setAlwaysOnTop(true);
			b.setModal(true);
			b.setModalityType(ModalityType.APPLICATION_MODAL);	
		}
	}
	}

}
