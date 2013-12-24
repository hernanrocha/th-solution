package hash;

import common.estructura.Almacenamiento;

public abstract class HashAbs extends Almacenamiento{

	private static final long serialVersionUID = 1L;
	protected Integer cantidadRegistros = 0;
	
	public abstract double getRho ();
}
