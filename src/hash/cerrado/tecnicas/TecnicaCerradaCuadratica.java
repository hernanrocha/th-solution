package hash.cerrado.tecnicas;

import common.Messages;


public class TecnicaCerradaCuadratica extends TecnicaAbs {

	private static final long serialVersionUID = 1L;

	public TecnicaCerradaCuadratica(int cantidadBaldes){
		super(cantidadBaldes);
		this.nombre = Messages.getString("HASH_CERRADO_TECNICAS_CU_LARGO"); //$NON-NLS-1$
		this.corto = Messages.getString("HASH_CERRADO_TECNICAS_CU_CORTO"); //$NON-NLS-1$
		this.funcion = " - h(x) = x mod " + cantidadBaldes + "."; //$NON-NLS-1$ //$NON-NLS-2$
		this.funcionReasignacion = " - h'(x) = ( h(x) + ( i ( i + 1 ) ) / 2 ) mod " + cantidadBaldes + ". \n    (i : " + Messages.getString("HASH_CERRADO_TECNICAS_NUMERO_INTENTO") + ")"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	}
	
	@Override
	public int reasignar(int i, int x) {
		// h'(x) = ( h(x) + ( i ( i + 1 ) ) / 2 ) mod M.
		return ( super.h(x) + ( i * ( i + 1) ) / 2 )  % cantidadBaldes;
	}

	@Override
	public String getLista() {
		return null;
	}

}
