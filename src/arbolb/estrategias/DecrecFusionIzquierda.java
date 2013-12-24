package arbolb.estrategias;

import arbolb.estructura.NodoB;

public class DecrecFusionIzquierda extends Estrategia {

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.getParent().fusion_izquierda(nodo);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Fusion Izquierda";
	}

}
