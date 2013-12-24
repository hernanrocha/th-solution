package hash.cerrado.tecnicas;


public class TecnicaCerradaCuadratica extends TecnicaAbs {

	private static final long serialVersionUID = 1L;

	public TecnicaCerradaCuadratica(int cantidadBaldes){
		super(cantidadBaldes);
		this.nombre = "Cuadrática";
		this.corto = "CU";
		this.funcion = " - h(x) = x mod " + cantidadBaldes + ".";
		this.funcionReasignacion = " - h'(x) = ( h(x) + ( i ( i + 1 ) ) / 2 ) mod " + cantidadBaldes + ". \n    (i : N�mero de intento)";
	}
	
	@Override
	public int reasignar(int i, int x) {
		// h'(x) = ( h(x) + ( i ( i + 1 ) ) / 2 ) mod M.
		return ( super.h(x) + ( i * ( i + 1) ) / 2 )  % cantidadBaldes;
	}

	@Override
	public String getLista() {
		// TODO Auto-generated method stub
		return null;
	}

}
