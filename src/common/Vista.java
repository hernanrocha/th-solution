package common;


import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import common.graph.GraphViz;
import common.swing.InfoManager;



public abstract class Vista extends JPanel implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int capturaActual = 0;
	private int infoActual = 0;
	private Vector<String> capturas = new Vector<String>();
//	private Vector<Long> seeks = new Vector<Long>();
//	private File archivo;
//	private RandomAccessFile fichero;
	
	
	private Vector<String> info = new Vector<String>();
	private String tipo = new String();

	private String src;	
	
	private JPanel contentPane;
	
	private JLabel imagen;

	private JScrollPane scrollPane;
	
	public Vista(){
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setBounds(0,0,800,600);
		setBorder(new EmptyBorder(3, 3, 3, 3));
		setLayout(new BorderLayout(0, 0));
		
		//Aca se cambia el bordecito.
		setBackground(Color.DARK_GRAY);
		//parent.setContentPane(contentPane);

		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		//Esta es la imagen.
		imagen = new JLabel();
		//Esto la centra en el panel.
		imagen.setHorizontalAlignment(SwingConstants.CENTER);  
		//Esto te pone todo el fondo en blanco.
		scrollPane.getViewport().setBackground(Color.WHITE);
		
		
		// Abrir archivo
//		archivo = new File(getUIClassID() + "_capturas.dat");
//		try {
//			fichero = new RandomAccessFile(archivo, "rw");
//		} catch (FileNotFoundException e) {
//		}
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void agregarCaptura(String accion){
//		try {
//			fichero.seek(fichero.length()); // Colocar puntero al final
//			seeks.add(fichero.getFilePointer()); // Agregar puntero al comienzo de la captura nueva
//			capturaActual = seeks.size() - 1; // Ir a ultima captura
//			fichero.writeBytes(toGraph(accion)); // Escribir
//			System.out.println("Grafo escrito");
//			System.out.println("Puntero al final del archivo:" + fichero.getFilePointer());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		capturas.add(toGraph(accion));
		info.add(getInfo());
	}
	
	public boolean canSiguienteCaptura(){
		return (capturaActual < capturas.size() - 1);
	}
	
	public boolean canAnteriorCaptura(){
		return (capturaActual > 0);
	}
	
	public void siguienteCaptura(){
//		if (capturaActual < seeks.size() - 1){
//			try {
//				capturaActual++;
//				fichero.seek(seeks.elementAt(capturaActual));
//				System.out.println("Puntero actual: " + fichero.getFilePointer() + " (Captura " + capturaActual + ")");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			infoActual++;
//		}
		
		if (capturaActual < capturas.size() - 1){
			capturaActual++;
			infoActual++;
		}
	}
	
	public void anteriorCaptura(){
//		if (capturaActual > 0){
//			try {
//				capturaActual--;
//				fichero.seek(seeks.elementAt(capturaActual));
//				System.out.println("Puntero actual: " + fichero.getFilePointer() + " (Captura " + capturaActual + ")");
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			infoActual--;
//		}
		
		if (capturaActual > 0){
			capturaActual--;
			infoActual--;
		}
	}
	
	public void primeraCaptura(){
//		try {
//			fichero.seek(0);
			capturaActual = 0;
//			System.out.println("Primera captura");
//			System.out.println("Puntero actual: " + fichero.getFilePointer() + " (Captura " + capturaActual + ")");
//		} catch (IOException e) {}
		infoActual = 0;
	}
	
	public void ultimaCaptura(){
//		try {
//			//fichero.seek(fichero.length() - 1);
//			fichero.seek(seeks.lastElement());
//			capturaActual = seeks.size() - 1;
//			System.out.println("Ultima captura");
//			System.out.println("Puntero actual: " + fichero.getFilePointer() + " (Captura " + capturaActual + ")");
//		} catch (IOException e) {}
		
		capturaActual = capturas.size() - 1;
		infoActual = info.size() - 1;
	}
	
	public void generateGraph(){
		GraphViz gv = new GraphViz();
		gv.add(capturas.elementAt(capturaActual));
//		try {
//			System.out.println("Leyendo");
//			gv.add(fichero.readLine());
//		} catch (IOException e) {}
		GraphViz.verificarDirectorio(GraphViz.TEMP_DIR);
		String nombre = GraphViz.TEMP_DIR + "/grafico_" + getTipo() + "." + GraphViz.IMAGE_EXT;  
	    File out = new File(nombre);
	  	gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), GraphViz.IMAGE_EXT ), out );
	}	

	public void agregarTab(JTabbedPane tabArchivo){
	
		// Verificar que existe el directorio temporal
		GraphViz.verificarDirectorio(GraphViz.TEMP_DIR);
		
		
		src = GraphViz.TEMP_DIR + "grafico_" + getTipo() + "." + GraphViz.IMAGE_EXT;  

		System.out.println("Agregar tab: " + src); 
		
		tabArchivo.addTab(getTipo(), this);
		imagen.setIcon(new ImageIcon(src));
		scrollPane.setViewportView(imagen);		
		
		tabArchivo.setSelectedIndex(tabArchivo.getTabCount() - 1);
		
		//InfoManager
		InfoManager.getInstance().escribir(info.elementAt(infoActual));
	}	

	public void actualizar() {
		if(src != null){
			imagen.setIcon(new ImageIcon(GraphViz.getImagen(src)));
			scrollPane.setViewportView(imagen);
			add(scrollPane, BorderLayout.CENTER);
		}

		//InfoManager
		InfoManager.getInstance().escribir(info.elementAt(infoActual));
	}
	
	public String toGraph(){
		return toGraph(null);
	}
	
	public abstract String toGraph(String accion);
	
	public abstract String getInfo();
	
	// Busqueda
	public abstract void busqueda(Integer e);
	
}
