package arbolb.estrategias;

import java.io.Serializable;

import arbolb.estructura.NodoB;

/**
 * Estrategia.java
 * 
 * Clase abstracta que permite aplicar estrategias de crecimiento y decrecimiento a los nodos del arbol
 */
public abstract class Estrategia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Metodo que aplica una estrategia
	 * @param nodo Nodo a aplicar la estrategia
	 * @return <b>true<\b> si se pudo aplicar correctamente y <b>false<\b> en caso contrario
	 */
	public abstract boolean doAction(NodoB nodo);

}
