package arbolb.vista;

//import org.apache.log4j.Logger;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;

public class DebugCompleto extends VistaArbol {
	
	private static final long serialVersionUID = 1L;

	public DebugCompleto(ArbolB arbol){
		super(arbol);
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		
		// Inicio de grafo
		str += "digraph structs { \n"; //$NON-NLS-1$
		str += "  node [shape=record]; \n splines=line \n"; //$NON-NLS-1$
		
		// Estructura del arbol
		str += toGraph(arbol.getRaiz());
		
		// Fin de grafo
		str += "}"; //$NON-NLS-1$
		
		return str;
	}

	public String toGraph(NodoB nodo) {

		String str = new String();
		
		// Generar nodo completo
		str += "  " + nodo.getId() +"[label=\""; //$NON-NLS-1$ //$NON-NLS-2$
		for (int i=0; i < nodo.getArbol().getOrden(); i++){
			str += "<f" + i + ">" + " | "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			str+= nodo.getDatos()[i] + " | "; //$NON-NLS-1$
			//str+= "D" + nodo.getDatos()[i] + " | ";
		}
		
		// Colocar puntero final
		str += "<f" + nodo.getArbol().getOrden() + ">\"]; \n"; //$NON-NLS-1$ //$NON-NLS-2$
		
		// Generar recursivamente
		for (int i=0; i<=nodo.getArbol().getOrden(); i++){
			if (nodo.getHijos()[i] != null){
				str += toGraph(nodo.getHijos()[i]);
				str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n";				 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		}		
		
		//Logger.getLogger("Arbol").debug(str);
		return str;
	}
	
	@Override
	public String getTipo(){
		return "DEBUG"; //$NON-NLS-1$
	}
}
