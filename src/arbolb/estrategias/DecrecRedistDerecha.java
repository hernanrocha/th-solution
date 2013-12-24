package arbolb.estrategias;

import arbolb.estructura.NodoB;

public class DecrecRedistDerecha extends Estrategia {

	private static final long serialVersionUID = 1L;
	
	@Override
	public boolean doAction(NodoB nodo) {
		return nodo.eliminarRedistribucionDerecha();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Redistribucion Derecha";
	}

}
