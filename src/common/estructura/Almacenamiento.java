package common.estructura;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.JTabbedPane;

import common.Vista;

public abstract class Almacenamiento implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Vector<Vista> vistas = new Vector<Vista>();
	
	public abstract void insertar(Elemento e);
	
	public abstract void eliminar(Elemento e);
	
	public abstract boolean buscar(Elemento e);

	public abstract void buscarConInfo(Elemento e);

	public abstract String getInfo();
	
	// 1. Agregar vistas al almacenamiento
	public void agregarVista(Vista v){
		vistas.add(v);
	}
	
	// 2. Agregar al archivo las vistas cargadas anteriormente
	public void agregarTab(JTabbedPane tabArchivo){
		for(Vista v: vistas){
			v.agregarTab(tabArchivo);
		}
	}
	
	// 3. Agregar capturas a las vistas
	public void agregarCaptura(String accion){
		for(Vista v: vistas){
			v.agregarCaptura(accion);
		}
	}	
	
	public void agregarCaptura(){
		agregarCaptura(null);
	}
	
	public Vector<Vista> getVistas(){
		return vistas;
	}
}
