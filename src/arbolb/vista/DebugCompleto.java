package arbolb.vista;

//import org.apache.log4j.Logger;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;

public class DebugCompleto extends VistaArbol {
	
	private static final long serialVersionUID = 1L;

	public DebugCompleto(ArbolB arbol){
		super(arbol);
		setTipo("DebugCompleto");
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		
		// Inicio de grafo
		str += "digraph structs { \n";
		str += "  node [shape=record]; \n splines=line \n";
		
		// Estructura del arbol
		str += toGraph(arbol.getRaiz());
		
		// Fin de grafo
		str += "}";
		
		return str;
	}

	public String toGraph(NodoB nodo) {

		String str = new String();
		
		// Generar nodo completo
		str += "  " + nodo.getId() +"[label=\"";
		for (int i=0; i < nodo.getArbol().getOrden(); i++){
			str += "<f" + i + ">" + " | ";
			str+= nodo.getDatos()[i] + " | ";
			//str+= "D" + nodo.getDatos()[i] + " | ";
		}
		
		// Colocar puntero final
		str += "<f" + nodo.getArbol().getOrden() + ">\"]; \n";
		
		// Generar recursivamente
		for (int i=0; i<=nodo.getArbol().getOrden(); i++){
			if (nodo.getHijos()[i] != null){
				str += toGraph(nodo.getHijos()[i]);
				str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n";				
			}
		}		
		
		//Logger.getLogger("Arbol").debug(str);
		return str;
	}
}
