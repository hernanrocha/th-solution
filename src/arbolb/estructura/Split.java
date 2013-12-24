package arbolb.estructura;

import common.estructura.Elemento;

public class Split{
	private NodoB izq, der;
	private Elemento med;
	
	public Split(){}

	public Split(NodoB izq, Elemento med, NodoB der) {
		this.izq = izq;
		this.med = med;
		this.der = der;
	}

	public NodoB getIzq() {
		return izq;
	}

	public NodoB getDer() {
		return der;
	}

	public Elemento getMed() {
		return med;
	}

	public void setIzq(NodoB izq2) {
		this.izq = izq2;
	}

	public void setDer(NodoB der2) {
		this.der = der2;
	}

	public void setMed(Elemento med) {
		this.med = med;
	}
	
}
