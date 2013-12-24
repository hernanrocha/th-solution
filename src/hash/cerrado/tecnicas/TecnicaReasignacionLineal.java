package hash.cerrado.tecnicas;


public class TecnicaReasignacionLineal extends TecnicaAbs {

	private static final long serialVersionUID = 1L;

	public TecnicaReasignacionLineal(int cantidadBaldes){
		super(cantidadBaldes);
		this.nombre = "Reasignación Lineal";
		this.corto =  "RL";
		this.funcion = " - h(x) = x mod " + cantidadBaldes + ".";
		this.funcionReasignacion = " - h'(x) = ( h(x) + i ) mod " + cantidadBaldes + ". \n    (i : Número de intento)";
	}
	
	@Override
	public int reasignar(int i, int x) {
		// h'(x) = ( h(x) + i ) mod M.
		return ( super.h(x) + i) % cantidadBaldes;
	}

	@Override
	public String getLista() {
		// TODO Auto-generated method stub
		return null;
	}

}
