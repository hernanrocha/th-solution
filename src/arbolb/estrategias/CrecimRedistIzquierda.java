package arbolb.estrategias;

import arbolb.estructura.NodoB;

public class CrecimRedistIzquierda extends Estrategia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.insertarRedistribucionIzquierda();
	}

	@Override
	public String toString() {
		return "Redistribucion Izquierda";
	}

}
