package arbolb.vista;

import common.Vista;
import common.estructura.Elemento;
import arbolb.estructura.ArbolB;

public abstract class VistaArbol extends Vista{
	private static final long serialVersionUID = 1L;
	
	public static final String COLOR_CLAVE = "#C0FBF9";
	public static final String COLOR_DATO = "#FE9A2E";
	public static final String COLOR_PUNT_NODO = "#A0A0A0";
	public static final String COLOR_PUNT_DATO = "#E0E0E0";

	public static final String COLOR_BORDE_NORMAL = "#000000";

	public static final String COLOR_NODO_INTERVINIENTE = "#FF0000";
	public static final String COLOR_PUNT_BMAS = "#0000CC";
	
	protected ArbolB arbol;
	
	public VistaArbol(ArbolB arbol) {
		this.arbol = arbol;
	}
	
	@Override
	public String getInfo() {
		return arbol.getInfo();
	}
	
	@Override
	public void busqueda(Integer e) {
		arbol.buscarConInfo(new Elemento(e));
	}	
	
}