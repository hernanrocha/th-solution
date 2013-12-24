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
	
	public void generateGraph() {
		// TODO Auto-generated method stub
		for(Vista v : vistas){
			v.generateGraph();
		}
	}
	
	public void agregarCaptura(){
		agregarCaptura(null);
	}
	
	public void agregarCaptura(String accion){
		for(Vista v: vistas){
			v.agregarCaptura(accion);
		}
	}
	
	public void anteriorCaptura(){
		for(Vista v: vistas){
			v.anteriorCaptura();
		}
	}
	
	public void siguienteCaptura(){
		for(Vista v: vistas){
			v.siguienteCaptura();
		}
	}
	
	public void ultimaCaptura(){
		for(Vista v: vistas){
			v.ultimaCaptura();
		}
	}

	public void primeraCaptura(){
		for(Vista v: vistas){
			v.primeraCaptura();
		}
	}
	
	public void agregarVista(Vista v){
		vistas.add(v);
	}
	
	public void agregarTab(JTabbedPane tabArchivo){
		for(Vista v: vistas){
			v.agregarTab(tabArchivo);
		}
	}
	
	public abstract String getInfo();
	
	public void updateUI(){
		for(Vista v: vistas){
			v.updateUI();
		}
	}
	
	public Vector<Vista> getVistas(){
		return vistas;
	}
}
