package hash;

import common.estructura.Almacenamiento;

public abstract class HashAbs extends Almacenamiento{

	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CANT_BALDES = 7;
	public static final int DEFAULT_CANT_RANURAS = 2;
	
	protected int baldes;
	protected int ranuras;
	
	protected Integer cantidadRegistros = 0;
	
	public abstract double getRho ();
}
