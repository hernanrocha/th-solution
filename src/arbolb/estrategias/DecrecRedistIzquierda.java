package arbolb.estrategias;

import arbolb.estructura.NodoB;

public class DecrecRedistIzquierda extends Estrategia {

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.eliminarRedistribuicionIzquierda();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Redistribucion Izquierda";
	}

}
