package hash.cerrado.tecnicas;

import java.util.Vector;

import common.Messages;

public class TecnicaCerradaPseudoazar extends TecnicaAbs {

	private Vector<Integer> lista;
	public TecnicaCerradaPseudoazar(int cantidadBaldes,Vector<Integer> lista) {
		super(cantidadBaldes);
		this.nombre = Messages.getString("HASH_CERRADO_TECNICAS_PS_LARGO"); //$NON-NLS-1$
		this.corto = Messages.getString("HASH_CERRADO_TECNICAS_PS_CORTO"); //$NON-NLS-1$
		this.funcion = " - h(x) = x mod " + cantidadBaldes + "."; //$NON-NLS-1$ //$NON-NLS-2$
		this.funcionReasignacion = " - h'(x) = ( h(x) + Zi ) mod " + cantidadBaldes + ". \n    (i : " + Messages.getString("HASH_CERRADO_TECNICAS_NUMERO_INTENTO") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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
		return " -Z = " + lista.toString(); //$NON-NLS-1$
	}

}
