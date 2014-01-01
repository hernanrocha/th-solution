package arbolb.estrategias;

import common.Messages;

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
		return Messages.getString("ARBOL_CRECIM_REDISTRIBUCION_DERECHA"); //$NON-NLS-1$
	}

}
