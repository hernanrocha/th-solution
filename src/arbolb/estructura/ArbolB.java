package arbolb.estructura;


import java.util.Vector;

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
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#insertar(common.estructura.Elemento)
	 */
	@Override
	public void insertar(Elemento e){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_INSERTAR_PRE", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$
		raiz.insertar(e);
		agregarCaptura(Messages.getString("ARBOL_INSERTAR_POS", new Object[]{e} )); //$NON-NLS-1$
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_INSERTAR_POS", new Object[]{e} ));		 //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#eliminar(common.estructura.Elemento)
	 */
	@Override
	public void eliminar(Elemento e){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_ELIMINAR_PRE", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		raiz.eliminar(e);
		agregarCaptura(Messages.getString("ARBOL_ELIMINAR_POS", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_ELIMINAR_POS", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#buscar(common.estructura.Elemento)
	 */
	@Override
	public boolean buscar(Elemento e) {
		return raiz.buscar(e);
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#buscarConInfo(common.estructura.Elemento)
	 */
	@Override
	public void buscarConInfo(Elemento e) {
		ConsolaManager.getInstance().escribir(""); //$NON-NLS-1$
		ConsolaManager.getInstance().escribir(Messages.getString("ARBOL_BUSCAR_INFO", new Object[]{e} )); //$NON-NLS-1$
		raiz.buscarConInfo(e);		
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#getInfo()
	 */
	@Override
	public String getInfo() {
		int n = getOrden();
		
		String s ="*" + Messages.getString("ARBOL_INFO_TITULO"); //$NON-NLS-1$ //$NON-NLS-2$
		s+= "\n \n"; //$NON-NLS-1$
		
		s+="+" + Messages.getString("ARBOL_INFO_ORDEN") + ": " + n + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("ARBOL_INFO_INSERCION") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		for (Estrategia e : crecim){
			s+= "    " + e.toString() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		s+="+" + Messages.getString("ARBOL_INFO_ELIMINACION") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		for (Estrategia e : decrec){
			s+= "    " + e.toString() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		s+="+" + Messages.getString("ARBOL_INFO_MIN_ELEM_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MIN_ELEM_DESC", new Object[]{(n/2 + n % 2 - 1)} ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("ARBOL_INFO_MAX_ELEM_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MAX_ELEM_DESC", new Object[]{(n-1)} ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("ARBOL_INFO_MIN_HIJOS_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MIN_HIJOS_DESC", new Object[]{(n/2 + n % 2)} ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("ARBOL_INFO_MAX_HIJOS_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MAX_HIJOS_DESC", new Object[]{(n)} ) + " \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		s+="+" + Messages.getString("ARBOL_INFO_CANTIDAD_ELEM") + ": " + getCantidadElementos() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("ARBOL_INFO_PROFUNDIDAD") + ": " + getProfundidad() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		return s;
	}
	
	//------------------ Estrategias de Crecimiento y Decrecimiento ------------------//
	
	/**
	 * Agregar una estrategia de crecimiento de la estructura
	 * @param e Estrategia de Crecimiento
	 */
	public void addEstrategiaCrecim(Estrategia e) {
		crecim.add(e);
	}
	
	/**
	 * Agregar una estrategia de decrecimiento de la estructura
	 * @param e Estrategia de Decrecimiento
	 */
	public void addEstrategiaDecrec(Estrategia e) {
		decrec.add(e);
	}
	
	/**
	 * Intentar aplicar alguna de las estrategias de crecimiento existentes a un nodo del arbol
	 * @param nodo Nodo a aplicar estrategia
	 * @return <b>true<\b> si se pudo aplicar correctamente y <b>false<\b> en caso contrario
	 */
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
	
	/**
	 * Intentar aplicar alguna de las estrategias de decrecimiento existentes a un nodo del arbol
	 * @param nodo Nodo a aplicar estrategia
	 * @return <b>true<\b> si se pudo aplicar correctamente y <b>false<\b> en caso contrario
	 */
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
	
	//------------------------------ Setters y Getters ------------------------------//
	
	/**
	 * Obtener orden del arbol (cantidad maxima de hijos por nodo)
	 * @return Orden del arbol
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * Obtener cantidad total de elementos del arbol
	 * @return Cantidad de elementos
	 */
	@Override
	public int getCantidadElementos(){
		return raiz.getCantidadElementos();
	}
	
	/**
	 * Obtener profundidad del arbol
	 * @return Profundidad del arbol
	 */
	public int getProfundidad(){
		return raiz.getProfundidad();
	}
	
	/**
	 * Setear raiz del arbol
	 * @param raiz Nueva raiz
	 */
	public void setRaiz(NodoB raiz) {
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_NUEVO_NODO_RAIZ")); //$NON-NLS-1$ //$NON-NLS-2$
		this.raiz = raiz;
	}
	
	/**
	 * Obtener raiz del arbol
	 * @return Nodo raiz
	 */
	public NodoB getRaiz() {
		return raiz;
	}
	
	/**
	 * Obtener Vector de nodos intervinientes durante la aplicacion de una estrategia
	 * @return Vector de nodos intervinientes o lista vacia si no se esta aplicando ninguna estrategia.
	 */
	public Vector<NodoB> getIntervinientes() {
		return intervinientes;
	}

	/**
	 * Setear Vector de nodos intervinientes durante la aplicacion de una estrategia
	 * @param intervinientes Vector de nodos intervinientes
	 */
	public void setIntervinientes(Vector<NodoB> intervinientes) {
		this.intervinientes = intervinientes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return raiz.toString();
	}
	
}
