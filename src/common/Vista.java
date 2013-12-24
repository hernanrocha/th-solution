package common;


import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
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
	}
	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;

	}
	
	public String toGraph(){
		return toGraph(null);
	}
	
	public abstract String toGraph(String accion);
	
	public abstract String getInfo();
	
	public void agregarCaptura(String accion){
		capturas.add(toGraph(accion));
		info.add(getInfo());
	}
	
	public void siguienteCaptura(){
		if (capturaActual < capturas.size() - 1){
			capturaActual++;	
			infoActual++;
		}
	}
	
	public void anteriorCaptura(){
		if (capturaActual > 0){
			capturaActual--;		
			infoActual--;
		}
	}
	
	public void primeraCaptura(){
		capturaActual = 0;
		infoActual = 0;
	}
	
	public void ultimaCaptura(){
		capturaActual = capturas.size() - 1;
		infoActual = info.size() - 1;
	}
	
	public void generateGraph(){
		GraphViz gv = new GraphViz();
		gv.add(capturas.elementAt(capturaActual));
		GraphViz.verificarDirectorio(GraphViz.TEMP_DIR);
		String nombre = GraphViz.TEMP_DIR + "/grafico_" + getTipo() + "." + GraphViz.IMAGE_EXT;
	    File out = new File(nombre);
	  	gv.writeGraphToFile( gv.getGraph( gv.getDotSource(), GraphViz.IMAGE_EXT ), out );
	}	

	public void agregarTab(JTabbedPane tabArchivo){
	
		GraphViz.verificarDirectorio(GraphViz.TEMP_DIR);
		src = GraphViz.TEMP_DIR + "/grafico_" + getTipo() + "." + GraphViz.IMAGE_EXT;

		System.out.println("Agregar tab: " + src);
		
		tabArchivo.addTab(getTipo(), this);
		imagen.setIcon(new ImageIcon(src));
		scrollPane.setViewportView(imagen);
		
		
		tabArchivo.setSelectedIndex(tabArchivo.getTabCount() - 1);
		//InfoManager
		InfoManager.getInstance().escribir(info.elementAt(infoActual));
	}
	
	@Override
	public void updateUI(){
		super.updateUI();
		if(src != null){
			imagen.setIcon(new ImageIcon(GraphViz.getImagen(src)));
			scrollPane.setViewportView(imagen);
			add(scrollPane, BorderLayout.CENTER);
		}
	}

	public void setInfo(){
		//InfoManager
		InfoManager.getInstance().escribir(info.elementAt(infoActual));
	}
	
	//Busqueda
	public abstract void busqueda(Integer e);
	
}
