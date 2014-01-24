package arbolb.vista;

import common.Vista;
import common.estructura.Elemento;
import arbolb.estructura.ArbolB;

public abstract class VistaArbol extends Vista{
	private static final long serialVersionUID = 1L;
	
	protected static final String COLOR_CLAVE = "#C0FBF9"; //$NON-NLS-1$
	protected static final String COLOR_DATO = "#FE9A2E"; //$NON-NLS-1$
	protected static final String COLOR_PUNT_NODO = "#A0A0A0"; //$NON-NLS-1$
	protected static final String COLOR_PUNT_DATO = "#E0E0E0"; //$NON-NLS-1$

	protected static final String COLOR_BORDE_NORMAL = "#000000"; //$NON-NLS-1$

	protected static final String COLOR_NODO_INTERVINIENTE = "#FF0000"; //$NON-NLS-1$
	protected static final String COLOR_PUNT_BMAS = "#0066AA"; //$NON-NLS-1$
	
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
