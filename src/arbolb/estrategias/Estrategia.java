package arbolb.estrategias;

import java.io.Serializable;

import arbolb.estructura.NodoB;

public abstract class Estrategia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public abstract boolean doAction(NodoB nodo);
	
	public abstract String toString();

}
