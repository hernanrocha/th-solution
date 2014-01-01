package arbolb.estrategias;

import common.Messages;

import arbolb.estructura.NodoB;

public class DecrecRedistDerecha extends Estrategia {

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.eliminarRedistribucionDerecha();
	}

	@Override
	public String toString() {
		return Messages.getString("ARBOL_DECRECIM_REDISTRIBUCION_DERECHA"); //$NON-NLS-1$
	}

}
