package arbolb.estrategias;

import common.Messages;

import arbolb.estructura.NodoB;

public class DecrecRedistIzquierda extends Estrategia {

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.eliminarRedistribuicionIzquierda();
	}

	@Override
	public String toString() {
		return Messages.getString("ARBOL_DECRECIM_REDISTRIBUCION_IZQUIERDA"); //$NON-NLS-1$
	}

}
