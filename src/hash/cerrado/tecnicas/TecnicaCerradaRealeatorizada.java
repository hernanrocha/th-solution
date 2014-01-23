package hash.cerrado.tecnicas;

import common.Messages;


public class TecnicaCerradaRealeatorizada extends TecnicaAbs {

	private static final long serialVersionUID = 1L;
	
	public TecnicaCerradaRealeatorizada(int cantidadBaldes){
		super(cantidadBaldes);
		this.nombre = Messages.getString("HASH_CERRADO_TECNICAS_RE_LARGO"); //$NON-NLS-1$
		this.corto = Messages.getString("HASH_CERRADO_TECNICAS_RE_CORTO"); //$NON-NLS-1$
		this.funcion = " - h(x) = x mod " + cantidadBaldes + "."; //$NON-NLS-1$ //$NON-NLS-2$
		this.funcionReasignacion = " - h'(x) = ( h(x) + [i * (x mod ( M - 1 )) + 1]) mod " + cantidadBaldes + ". \n    (i : " + Messages.getString("HASH_CERRADO_TECNICAS_NUMERO_INTENTO") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	@Override
	public int reasignar(int i, int x) {
		// h'(x) = ( h(x) + [ i * ( x mod ( M - 1 ) ) + 1] ) mod M.
		return ( super.h(x) + ( (  i * ( x  % ( cantidadBaldes - 1 ) ) ) + 1) ) % cantidadBaldes;
	}

	@Override
	public String getLista() {
		return null;
	}

}
