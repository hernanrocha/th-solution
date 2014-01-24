package hash;

import java.io.Serializable;

import common.Messages;
import common.estructura.Elemento;


public class Celda implements Serializable{
	
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
		String salida=""; //$NON-NLS-1$
		if ( this.getEstado() == Celda.VIRGEN )
			salida = Messages.getString("CELDA_VIRGEN"); //$NON-NLS-1$
		else
			if ( this.getEstado() == Celda.BORRADO )
				salida = Messages.getString("CELDA_BORRADO"); //$NON-NLS-1$
		else
			if ( this.getEstado() == Celda.OCUPADO )
				salida = this.getElementoContenido().toString();
		return salida;
	}
	
	
}
