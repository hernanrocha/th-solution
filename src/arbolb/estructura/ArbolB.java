package arbolb.estructura;


import java.util.Vector;

//import org.apache.log4j.Logger;

import common.Messages;
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
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_INSERTAR_PRE") + " [" + e + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		raiz.insertar(e);
		agregarCaptura(Messages.getString("ARBOL_INSERTAR_POS") + " [" + e + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_INSERTAR_POS") + " [" + e + "]");		 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	@Override
	public void eliminar(Elemento e){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_ELIMINAR_PRE") + " [" + e + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		raiz.eliminar(e);
		agregarCaptura(Messages.getString("ARBOL_ELIMINAR_POS") + " [" + e + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_ELIMINAR_POS") + " [" + e + "]"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	@Override
	public boolean buscar(Elemento e) {
		// Logger.getLogger("Arbol").info("Buscar elemento " + e);
		return raiz.buscar(e);
	}
	
	//------------------------------ Setters y Getters ------------------------------//
	
	public void setRaiz(NodoB raiz) {
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_NUEVO_NODO_RAIZ")); //$NON-NLS-1$ //$NON-NLS-2$
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
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_CRECIM_INICIAR")); //$NON-NLS-1$ //$NON-NLS-2$
		for (Estrategia e : crecim){
			if (e.doAction(nodo)){
				return true;
			}
		}
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_CRECIM_ERROR")); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}
	
	public boolean aplicarEstrategiasDecrec(NodoB nodo){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_DECRECIM_INICIAR")); //$NON-NLS-1$ //$NON-NLS-2$
		for (Estrategia e : decrec){
			if (e.doAction(nodo)){
				return true;
			}
		}
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_DECRECIM_ERROR")); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}
	
	public String toString(){
		return raiz.toString();
	}

	@Override
	public String getInfo() {
		int n = getOrden();
		
		String s ="*Estructura de Arbol B."; //$NON-NLS-1$
		s+= "\n \n"; //$NON-NLS-1$
		
		s+="+" + "Orden" + ": " + n + ".\n";		 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + "Estrategias de Insercion" + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		for (Estrategia e : crecim){
			s+= "    " + e.toString() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		s+="+" + "Estrategias de Eliminacion" + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		for (Estrategia e : decrec){
			s+= "    " + e.toString() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		s+="+" + "Minimo Numero de Elementos" + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+= "    " + "1 para raiz" + ", " + (n/2 + n % 2 - 1) + " " + "para otro nodo" + "\n";		 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		s+="+" + "Maximo Numero de Elementos" + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + (n-1) + " " + "para cualquier nodo" + "\n";		 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + "Minimo Numero de Hijos" + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+= "    0 " + "para hojas" + ", 2 " + "para raiz" + ", " + (n/2 + n % 2) + " " + "para otro nodo" + "\n";		 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
		s+="+" + "Maximo Numero de Hijos" + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + (n) + " " + "para cualquier nodo" + " \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		s+="+" + "Cantidad de elementos" + ": " + getCantElementos() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + "Profundidad" + ": " + getProfundidad() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
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
		ConsolaManager.getInstance().escribir(""); //$NON-NLS-1$
		ConsolaManager.getInstance().escribir(Messages.getString("ARBOL_BUSCAR_INFO_PRE") + " [" + e + "] " + Messages.getString("ARBOL_BUSCAR_INFO_POS")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		raiz.buscarConInfo(e);
		
	}	

}
