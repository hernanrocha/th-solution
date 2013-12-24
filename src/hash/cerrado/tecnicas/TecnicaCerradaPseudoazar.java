package hash.cerrado.tecnicas;

import java.util.Vector;

public class TecnicaCerradaPseudoazar extends TecnicaAbs {

	private Vector<Integer> lista;
	public TecnicaCerradaPseudoazar(int cantidadBaldes,Vector<Integer> lista) {
		super(cantidadBaldes);
		this.nombre = "Pseudoazar";
		this.corto = "PS";
		this.funcion = " - h(x) = x mod " + cantidadBaldes + ".";
		this.funcionReasignacion = " - h'(x) = ( h(x) + Zi ) mod " + cantidadBaldes + ". \n    (i : Número de intento)";
		this.lista = lista;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public int reasignar(int i, int x) {
		if ( i <= lista.size() )
			return (super.h(x) + lista.elementAt(i - 1)) % cantidadBaldes;
		return -1;
	}

	@Override
	public String getLista() {
		
		return " -Z = " + lista.toString();
	}

}
