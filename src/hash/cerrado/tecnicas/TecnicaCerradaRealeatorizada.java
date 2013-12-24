package hash.cerrado.tecnicas;


public class TecnicaCerradaRealeatorizada extends TecnicaAbs {

	private static final long serialVersionUID = 1L;
	
	public TecnicaCerradaRealeatorizada(int cantidadBaldes){
		super(cantidadBaldes);
		this.nombre = "Realeatorizada";
		this.corto = "RE";
		this.funcion = " - h(x) = x mod " + cantidadBaldes + ".";
		this.funcionReasignacion = " - h'(x) = ( h(x) + [i * (x mod ( M - 1 )) + 1]) mod " + cantidadBaldes + ". \n    (i : Número de intento)";
	}
	
	@Override
	public int reasignar(int i, int x) {
		// h'(x) = ( h(x) + [ i * ( x mod ( M - 1 ) ) + 1] ) mod M.
		return ( super.h(x) + ( (  i * ( x  % ( cantidadBaldes - 1 ) ) ) + 1) ) % cantidadBaldes;
	}

	@Override
	public String getLista() {
		// TODO Auto-generated method stub
		return null;
	}

}
