package arbolb.estrategias;

import common.Messages;

import arbolb.estructura.NodoB;

public class DecrecFusionDerecha extends Estrategia {

	private static final long serialVersionUID = 1L;

	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.getParent().fusion_derecha(nodo);
	}

	@Override
	public String toString() {
		return Messages.getString("ARBOL_DECRECIM_FUSION_DERECHA"); //$NON-NLS-1$
	}

}
