package arbolb.estrategias;

import arbolb.estructura.NodoB;

public class CrecimRedistDerecha extends Estrategia {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.insertarRedistribucionDerecha();
	}

	@Override
	public String toString() {
		return "Redistribucion Derecha";
	}

}
