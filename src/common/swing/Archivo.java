package common.swing;

import hash.abierto.HashAbierto;
import hash.cerrado.HashCerrado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import arbolb.estructura.ArbolB;

import common.Messages;
import common.Vista;
import common.estructura.Almacenamiento;
import common.estructura.Elemento;

public class Archivo extends JTabbedPane{

	private static final long serialVersionUID = 1L;
	
	private Vector<Almacenamiento> almac = new Vector<Almacenamiento>();
	private String path;
	private boolean changed;
	protected mainWindow window;
	
	String info = new String();

	public Archivo(int tabPlacement, mainWindow windowP) {
		super(tabPlacement);
		setDoubleBuffered(true);
		this.window = windowP;
		
		// Listener. Ir a ultima captura cuando se cambia de tab
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {				
				//ConsolaManager.getInstance().escribir("Actualizar Tab");
				Vista v = (Vista) getSelectedComponent();
				if (v != null){
					// Ir A Ultima Captura
					v.ultimaCaptura();

					// Actualizar pantalla
					window.actualizarImagen();
				}
			}
		};
		this.addChangeListener(changeListener);
	}
	
	public void agregarAlmacenamiento(Almacenamiento a){
		almac.add(a);
	}
	
	public void insertar(Elemento e){
		boolean existe = false;		
		boolean maxCantidad = false;
		
		// Insertar en los distintos almacenamientos
		for (Almacenamiento a : almac){
			if (!a.buscar(e)){
				if (a.getCantidadElementos() >= Almacenamiento.MAX_ELEMENTOS){
					// Se llego a la maxima cantidad de elementos permitida
					maxCantidad = true;
				}else{
					// Insertar
					a.insertar(e);

					// Determinar que cambio.
					changed = true;					
				}				
			}else{
				existe = true;
			}
		}

		// Mostrar mensaje si existia en alguna estructura
		if(existe){
			ConsolaManager.getInstance().escribirAdv(Messages.getString("ARCHIVO_ELEMENTO_EXISTENTE", new Object[]{e.getNum()} )); //$NON-NLS-1$
		}
		
		if(maxCantidad){
			ConsolaManager.getInstance().escribirAdv(Messages.getString("ARCHIVO_CANTIDAD_MAXIMA_ELEMENTOS")); //$NON-NLS-1$
		}
	}
	
	public void eliminar(Elemento e){
		for (Almacenamiento a : almac){
			if ( a.buscar(e) ){
				a.eliminar(e);

				// Determinar que cambio.
				changed = true;
			}				
		}
	}
	
	public void load(mainWindow window, File archivo) {
		
        FileReader fr;
        BufferedReader br;
        try {
        	fr = new FileReader (archivo);
        	br = new BufferedReader(fr);

        	// Lectura del fichero
        	String linea;
        	while((linea = br.readLine()) != null){
        		System.out.println("Linea: " + linea); //$NON-NLS-1$
        		String[] tokens = linea.trim().split(" "); //$NON-NLS-1$

        		String action = tokens[0].toLowerCase();
        		
        		if (action.equals(Messages.getString("SWING_MAIN_INSERTAR_MINUSCULA"))){ //$NON-NLS-1$
        			System.out.println("Insertar");
        			Vector<Elemento> elementos = Elemento.parseToElements(tokens[1]);
        			window.insertarDatos(elementos);
        		}else if (action.equals(Messages.getString("SWING_MAIN_ELIMINAR_MINUSCULA"))){ //$NON-NLS-1$
        			System.out.println("Eliminar");
        			Vector<Elemento> elementos = Elemento.parseToElements(tokens[1]);
        			window.eliminarDatos(elementos);
        		}else if (action.equals("arbolb")){
        			Vector<String> params = getParams(tokens[1]);
        			System.out.println("Agregar arbol");
        			ArbolB.load(this, params);
        		}else if (action.equals("hashcerrado")){
        			Vector<String> params = getParams(tokens[1]);
        			System.out.println("Hash Cerrado");
        			HashCerrado.load(this, params);
        		}else if (action.equals("hashabierto")){
        			Vector<String> params = getParams(tokens[1]);
        			System.out.println("Hash Abierto");
        			HashAbierto.load(this, params);
        		}
        	}
        	
			// Actualizar pantalla
			window.actualizarImagen();
        } catch (FileNotFoundException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
        }
		
	}

	public void guardar(String path, String name) {
		try {
			if(!path.endsWith(".est")){ //$NON-NLS-1$
				path = path + ".est"; //$NON-NLS-1$
			}
			File f = new File(path);

			FileWriter w = new FileWriter(f);
			BufferedWriter bw = new BufferedWriter(w);
			PrintWriter wr = new PrintWriter(bw);  
			
			wr.write(info);
			wr.close();
			bw.close();
			
			setPath(path);
			setName(name);
			
			// Determinar que los cambios estan guardados.
			changed = false;
		} catch (FileNotFoundException e) {	
			
		} catch (IOException e) {
			
		}
		
	}
	
	public void guardar() {
		if (getPath() != null){
			guardar(getPath(), getName());			
		}
	}
	
	private void setPath(String path) {
		this.path = path;		
	}	

	public String getPath() {
		return path;
	}

	public void primeraCaptura() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			v.primeraCaptura();
		}
	}
	
	public void ultimaCaptura() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			v.ultimaCaptura();
		}
	}
	
	public void anteriorCaptura() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			v.anteriorCaptura();
		}
	}
	
	public void siguienteCaptura() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			v.siguienteCaptura();
		}
	}
	
	public boolean canAnteriorCaptura() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			return v.canAnteriorCaptura();
		}
		
		return false;
	}
	
	public boolean canSiguienteCaptura() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			return v.canSiguienteCaptura();
		}
		
		return false;
	}
	
	public void agregarTab() {
		for (Almacenamiento a : almac){
			a.agregarTab(this);
		}
	}

	public void generateGraph() {
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			v.generateGraph();
		}
	}

	public boolean isChanged() {
		return changed;
	}
	
	public void actualizar(){
		Vista v = (Vista) getSelectedComponent();
		if (v != null){
			v.actualizar();
		}
	}
	
	public Vector<Almacenamiento> getAlmac(){
		return almac;
	}
	
	public void addInfo(String str){
		System.out.println("Agregar " + str);
		info += str + "\n";
	}


	
	private Vector<String> getParams(String str){
		
		Vector<String> params = new Vector<String>();
		String[] valores = str.split(","); //$NON-NLS-1$

		for (String v : valores){
			params.add(v.trim());
		}
		
		return params;
		
	}
}
