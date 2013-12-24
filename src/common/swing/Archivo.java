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
		// TODO Auto-generated constructor stub
		this.setDoubleBuffered(true);
	}

	public Archivo(int tabPlacement) {
		super(tabPlacement);
		
		//Aca agregar el listener.
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent changeEvent) {
				JTabbedPane sourceTabbedPane = (JTabbedPane) changeEvent.getSource();
				int index = sourceTabbedPane.getSelectedIndex();
				Vector<Vista> vistas = new Vector<Vista>();
				for (int i = 0 ; i < almac.size() ; i++){
					vistas.addAll(almac.elementAt(i).getVistas());
				}
				vistas.elementAt(index).setInfo();
			}
		};
		this.addChangeListener(changeListener);


	}
	
	public void agregarAlmacenamiento(Almacenamiento a){
		almac.add(a);
	}
	
	public void insertar(Elemento e){
		boolean existe = false;
		for (Almacenamiento a : almac){
			if (!a.buscar(e)){
				a.insertar(e);
				//Determino que cambio.
				changed = true;
			}else{
				existe = true;
			}
		}
		if(existe){
			ConsolaManager.getInstance().escribirAdv("El elemento ya exist�a en algunas estructuras y no fue agregado nuevamente.");
		}
	}
	
	public void eliminar(Elemento e){
		for (Almacenamiento a : almac){
			if ( a.buscar(e) ){
				a.eliminar(e);
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
			salida.writeObject("Archivo: " + path);
			salida.writeObject(almac);
			salida.writeObject(NodoB.getCantidad());
			salida.close();
			
			setPath(path);
			setName(name);
			
			//Determino que cambio.
			changed = false;
		} catch (FileNotFoundException e) {	} catch (IOException e) { }
		
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
			
			//Logger.getLogger("Cargar Estructura").info("Archivo cargado");
			
			//Determino que cambio
			changed = false;
			
			return true;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			//Logger.getLogger("Cargar Estructura").warn("El archivo no existe");
			return false;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//Logger.getLogger("Cargar Estructura").warn(e1.getStackTrace().toString());
			e1.printStackTrace();
			return false;
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			//Logger.getLogger("Cargar Estructura").warn(e1.getStackTrace().toString());
			e1.printStackTrace();
			return false;
		}
		
	}

	public void primeraCaptura() {
		for (Almacenamiento a : almac){
			a.primeraCaptura();
		}
	}
	
	public void ultimaCaptura() {
		for (Almacenamiento a : almac){
			a.ultimaCaptura();
		}
	}
	
	public void siguienteCaptura() {
		for (Almacenamiento a : almac){
			a.siguienteCaptura();
		}
	}
	
	public void anteriorCaptura() {
		for (Almacenamiento a : almac){
			a.anteriorCaptura();
		}
	}

	public void agregarTab() {
		for (Almacenamiento a : almac){
			a.agregarTab(this);
		}
	}

	public void generateGraph() {
		for (Almacenamiento a : almac){
			a.generateGraph();
		}
	}

	public boolean isChanged() {
		return changed;
	}
	
	public void updateUI(){
		super.updateUI();
		if (almac != null)
			for (Almacenamiento a : almac){
				a.updateUI();
			}
	}
	
	public void actualizar(){
		Vector<Vista> vistas = new Vector<Vista>();
        for (int i = 0 ; i < almac.size() ; i++){
        	vistas.addAll(almac.elementAt(i).getVistas());
        }
        if (getSelectedIndex() >= 0){
        	vistas.elementAt(getSelectedIndex()).setInfo();
        }
	}
	
	public Vector<Almacenamiento> getAlmac(){
		return almac;
	}
}
