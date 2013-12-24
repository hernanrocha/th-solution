package arbolb.vista;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;

public class BArchivo extends VistaArbol {
	
	private static final long serialVersionUID = 1L;

	public BArchivo(ArbolB arbol){
		super(arbol);
		setTipo("B Archivo");
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		
		// Inicio de grafo
		str += "digraph structs { \n";
		str += "  node [shape=record]; \n splines=line \n";
		
		if(arbol.getCantElementos() != 0){
			// Estructura del arbol
			str += toGraph(arbol.getRaiz());
			
			//Referencias
			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n"; 
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B> Clave </B></TD></TR> \n";
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B> Dato </B></TD></TR> \n";
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B> Puntero A Nodo </B></TD></TR> \n";
			if (accion != null){
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}else{
				//str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}
			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"]\n";
			str += "{ rank = source; ref";
			str += "} \n";
			
			//System.out.println(str);
		
		}
		
		// Fin de grafo
		str += "}";
		
		return str;
	}

	public String toGraph(NodoB nodo) {

		String str = new String();
		
		String color = "COLOR=\"" + VistaArbol.COLOR_BORDE_NORMAL + "\"";
		if(nodo.getArbol().getIntervinientes().contains(nodo)){
			color = "COLOR=\"" + VistaArbol.COLOR_NODO_INTERVINIENTE + "\"";
		}
		
		// Generar nodo propio
		str += nodo.getId() + "[ label= <<TABLE BORDER=\"0\" CELLBORDER=\"2\" CELLSPACING=\"0\" " + color + "> \n";
		str += "<TR> \n";
		for (int i=0; i<nodo.getCantActual(); i++){
			// Puntero
			str += "<TD PORT=\"f" + i + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"> </TD> \n";			
			// Clave
			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\">" +
			nodo.getDatos()[i].toString() + "</TD> \n";
			// Dato
			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\">D" +
			nodo.getDatos()[i].toString() + "</TD> \n";
		}
		// Ultimo Puntero
		str += "<TD PORT=\"f" + nodo.getCantActual() + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"> </TD> \n";
		str += "</TR> \n";
		str += "</TABLE>>, shape=none] \n";
		
		// Generar recursivamente
		if(!nodo.esHoja()){
			for (int i=0; i<=nodo.getCantActual(); i++){
				str += toGraph(nodo.getHijos()[i]);
				str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n";
			}
		}		
		
		return str;
	}
	
	
}
