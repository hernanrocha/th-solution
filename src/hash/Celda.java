package hash;

import java.io.Serializable;

import common.estructura.Elemento;


public class Celda implements Serializable{
	// Brian
	// Hernan
	private static final long serialVersionUID = 1L;
	
	public static final int VIRGEN = 0;
	public static final int BORRADO = 1;
	public static final int OCUPADO = 2;
	
	
	private Elemento elementoContenido;
	private int estado;
	
	
	public Celda(){
		this.setElementoContenido(null);
		this.setEstado(Celda.VIRGEN);
	}


	public Elemento getElementoContenido() {
		return elementoContenido;
	}


	public void setElementoContenido(Elemento elementoContenido) {
		this.elementoContenido = elementoContenido;
	}


	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}
	
	public String toString(){
		String salida="";
		if ( this.getEstado() == Celda.VIRGEN )
			salida = "V";
		else
			if ( this.getEstado() == Celda.BORRADO )
				salida = "B";
		else
			if ( this.getEstado() == Celda.OCUPADO )
				salida = this.getElementoContenido().toString();
		return salida;
	}
	
	
}
