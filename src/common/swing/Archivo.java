package common.swing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

//import org.apache.log4j.Logger;

import common.Messages;
import common.Vista;
import common.estructura.Almacenamiento;
import common.estructura.Elemento;
import arbolb.estructura.NodoB;

public class Archivo extends JTabbedPane{

	private static final long serialVersionUID = 1L;
	
	private Vector<Almacenamiento> almac = new Vector<Almacenamiento>();
	private String path;
	private boolean changed;
	

	public Archivo() {
		this.setDoubleBuffered(true);
	}

	public Archivo(int tabPlacement) {
		super(tabPlacement);
		
		// Listener. Ir a ultima captura cuando se cambia de tab
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {				
				ConsolaManager.getInstance().escribir("Actualizar Tab");
				Vista v = (Vista) getSelectedComponent();
				if (v != null){
					v.ultimaCaptura();
					v.generateGraph();
					v.actualizar();
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
		
		// Insertar en los distintos almacenamientos
		for (Almacenamiento a : almac){
			if (!a.buscar(e)){
				a.insertar(e);
				
				// Determinar que cambio.
				changed = true;
			}else{
				existe = true;
			}
		}
		
		// Mostrar mensaje si existia en alguna estructura
		if(existe){
			ConsolaManager.getInstance().escribirAdv(Messages.getString("ARCHIVO_ELEMENTO_EXISTENTE")); //$NON-NLS-1$
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

	public void guardar(String path, String name) {
		try {
			if(!path.endsWith(".est")){
				path = path + ".est";
			}
			ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(path));
			salida.writeObject(Messages.getString("ARCHIVO_NOMBRE") + ": " + path); //$NON-NLS-1$
			salida.writeObject(almac);
			salida.writeObject(NodoB.getCantidad());
			salida.close();
			
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

	@SuppressWarnings("unchecked")
	public boolean cargar(String path) {
		try {
			if(!path.endsWith(".est")){
				path = path + ".est";
			}
			ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(path));
			String mensaje = (String) entrada.readObject();
			System.out.println(mensaje);
			//Logger.getLogger("Cargar Estructura").info(mensaje);
			almac = (Vector<Almacenamiento>) entrada.readObject();
			NodoB.setCantidad((int) entrada.readObject());
			entrada.close();
			setPath(path);
						
			//Determino que cambio
			changed = false;
			
			return true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return false;
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
			return false;
		}
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
}
