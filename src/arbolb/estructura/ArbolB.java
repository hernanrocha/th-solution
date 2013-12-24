package arbolb.estructura;

import java.util.Vector;

//import org.apache.log4j.Logger;

import common.estructura.Almacenamiento;
import common.estructura.Elemento;
import common.swing.ConsolaManager;

import arbolb.estrategias.Estrategia;

public class ArbolB extends Almacenamiento{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_ORDEN = 4;
	
	// Estructura
	private NodoB raiz;
	private int orden;
	
	private Vector<Estrategia> crecim = new Vector<Estrategia>();
	private Vector<Estrategia> decrec = new Vector<Estrategia>();
	
	private Vector<NodoB> intervinientes = new Vector<NodoB>();
	

	//----------------------------------- Constructor ------------------------------------//
	
	/**
	 * Constructor de Arbol B
	 * @param orden Cantidad maxima de nodos hijos
	 */
	public ArbolB(int orden){
		this.orden = orden;
		setRaiz(new NodoB(null, this));
	}	
	
	//------------------------------ Interfaz Almacenamiento ------------------------------//
	
	/**
	 * Insertar elemento en el arbol
	 * @param e Elemento a insertar
	 */
	@Override
	public void insertar(Elemento e){
		// ρ
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Se insertará el elemento [" + e + "]");
		raiz.insertar(e);
		agregarCaptura("Se inserto el elemento [" + e + "]");
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Se insertó el elemento [" + e + "]");		
	}
	
	@Override
	public void eliminar(Elemento e){
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Se eliminará el elemento [" + e + "]");
		raiz.eliminar(e);
		agregarCaptura("Se elimino el elemento [" + e + "]");
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Se eliminó el elemento [" + e + "]");
	}
	
	@Override
	public boolean buscar(Elemento e) {
		// Logger.getLogger("Arbol").info("Buscar elemento " + e);
		return raiz.buscar(e);
	}
	
	//------------------------------ Setters y Getters ------------------------------//
	
	public void setRaiz(NodoB raiz) {
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Nuevo nodo raiz");
		this.raiz = raiz;
	}

	public NodoB getRaiz() {
		return raiz;
	}

	public int getOrden() {
		return orden;
	}
	
	/*public Vector<String> getElementos(){
		return raiz.getElementos();
	}*/

	public int getCantElementos(){
		return raiz.getCantElementos();
	}
	
	public int getProfundidad(){
		return raiz.getProfundidad();
	}
	

	//------------------ Estrategias de Crecimiento y Decrecimiento ------------------//
	
	public void addEstrategiaCrecim(Estrategia e) {
		crecim.add(e);
	}
	
	public void addEstrategiaDecrec(Estrategia e) {
		decrec.add(e);
	}	
	
	public boolean aplicarEstrategiasCrecim(NodoB nodo){
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Nodo lleno. Iniciar estrategias de crecimiento");
		for (Estrategia e : crecim){
			if (e.doAction(nodo)){
				return true;
			}
		}
		ConsolaManager.getInstance().escribirInfo("Arbol B", "No se pudo aplicar ninguna estrategia de crecimiento");
		return false;
	}
	
	public boolean aplicarEstrategiasDecrec(NodoB nodo){
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Nodo con pocos elementos. Iniciar estrategias de decrecimiento");
		for (Estrategia e : decrec){
			if (e.doAction(nodo)){
				return true;
			}
		}
		ConsolaManager.getInstance().escribirInfo("Arbol B", "No se pudo aplicar ninguna estrategia de decrecimiento");
		return false;
	}
	
	public String toString(){
		return raiz.toString();
	}

	@Override
	public String getInfo() {
		int n = getOrden();
		
		String s ="*Estructura de Arbol B. \n \n";
		
		s+="+Orden: " + n + ".\n";		
		s+="+Estrategias de Insercion: \n";
		for (Estrategia e : crecim){
			s+= "    " + e.toString() + "\n";
		}
		s+="+Estrategias de Eliminacion: \n";
		for (Estrategia e : decrec){
			s+= "    " + e.toString() + "\n";
		}
		
		s+="+Minimo Numero de Elementos: " + "\n";
		s+= "    1 para raiz, " + (n/2 + n % 2 - 1) + " para otro nodo\n";		
		s+="+Maximo Numero de Elementos: " + "\n";
		s+="    " + (n-1) + " para cualquier nodo\n";		
		s+="+Minimo Numero de Hijos: " + "\n";
		s+= "    0 para hojas, 2 para raiz, " + (n/2 + n % 2) + " para otro nodo\n";		
		s+="+Maximo Numero de Hijos: " + "\n";
		s+="    " + (n) + " para cualquier nodo \n";
		
		s+="+Cantidad de elementos: " + getCantElementos() + "\n";
		s+="+Profundidad: " + getProfundidad() + "\n";
		
		return s;
	}

	public Vector<NodoB> getIntervinientes() {
		return intervinientes;
	}

	public void setIntervinientes(Vector<NodoB> intervinientes) {
		this.intervinientes = intervinientes;
	}

	@Override
	public void buscarConInfo(Elemento e) {
		ConsolaManager.getInstance().escribir("");
		ConsolaManager.getInstance().escribir("Búsqueda del elemento [" + e + "] en la estructura de Arbol B.");
		raiz.buscarConInfo(e);
		
	}	

}
