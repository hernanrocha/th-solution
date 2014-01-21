package arbolb.vista;

import common.Messages;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;

public class BArchivo extends VistaArbol {
	
	private static final long serialVersionUID = 1L;

	public BArchivo(ArbolB arbol){
		super(arbol);
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		
		// Inicio de grafo
//		str += "digraph structs { \n";
//		str += "  node [shape=record]; \n splines=line \n";
		str += "digraph structs { "; //$NON-NLS-1$
		str += "  node [shape=record]; splines=line; "; //$NON-NLS-1$
		
		if(arbol.getCantElementos() != 0){
			// Estructura del arbol
			str += toGraph(arbol.getRaiz());
			
			//Referencias
//			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n"; 
//			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B> Clave </B></TD></TR> \n";
//			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B> Dato </B></TD></TR> \n";
//			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B> Puntero A Nodo </B></TD></TR> \n";
			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> ";  //$NON-NLS-1$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B>" + Messages.getString("VISTA_CLAVE") + "</B></TD></TR> "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B>" + Messages.getString("VISTA_DATO") + "</B></TD></TR> "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B>" + Messages.getString("VISTA_PUNTERO_NODO") + "</B></TD></TR> "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			if (accion != null){
//				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>"; //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				//str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}
//			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"]\n";
//			str += "{ rank = source; ref";
//			str += "} \n";
			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"]"; //$NON-NLS-1$
			str += "{ rank = source; ref }"; //$NON-NLS-1$
			
			//System.out.println(str);
		
		}
		
		// Fin de grafo
		str += "} \n"; //$NON-NLS-1$

		return str;
	}

	private String toGraph(NodoB nodo) {

		String str = new String();
		
		String color = "COLOR=\"" + VistaArbol.COLOR_BORDE_NORMAL + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		if(nodo.getArbol().getIntervinientes().contains(nodo)){
			color = "COLOR=\"" + VistaArbol.COLOR_NODO_INTERVINIENTE + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		// Generar nodo propio
//		str += nodo.getId() + "[ label= <<TABLE BORDER=\"0\" CELLBORDER=\"2\" CELLSPACING=\"0\" " + color + "> \n";
//		str += "<TR> \n";
		str += nodo.getId() + "[ label= <<TABLE BORDER=\"0\" CELLBORDER=\"2\" CELLSPACING=\"0\" " + color + "> "; //$NON-NLS-1$ //$NON-NLS-2$
		str += "<TR> "; //$NON-NLS-1$
		for (int i=0; i<nodo.getCantActual(); i++){
//			// Puntero
//			str += "<TD PORT=\"f" + i + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"> </TD> \n";			
//			// Clave
//			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\">" +
//			nodo.getDatos()[i].toString() + "</TD> \n";
//			// Dato
//			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\">D" +
//			nodo.getDatos()[i].toString() + "</TD> \n";
			
			// Puntero
			str += "<TD PORT=\"f" + i + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"> </TD> ";			 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			// Clave
			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\">" + //$NON-NLS-1$ //$NON-NLS-2$
			nodo.getDatos()[i].toString() + "</TD> "; //$NON-NLS-1$
			// Dato
			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\">D" + //$NON-NLS-1$ //$NON-NLS-2$
			nodo.getDatos()[i].toString() + "</TD> "; //$NON-NLS-1$
		}
		// Ultimo Puntero
//		str += "<TD PORT=\"f" + nodo.getCantActual() + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"> </TD> \n";
//		str += "</TR> \n";
//		str += "</TABLE>>, shape=none] \n";
		
		str += "<TD PORT=\"f" + nodo.getCantActual() + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"> </TD> "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		str += "</TR> "; //$NON-NLS-1$
		str += "</TABLE>>, shape=none]; "; //$NON-NLS-1$
		
		// Generar recursivamente
		if(!nodo.esHoja()){
			for (int i=0; i<=nodo.getCantActual(); i++){
				str += toGraph(nodo.getHijos()[i]);
				str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + ";"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		}	
		return str;
	}
	
	@Override
	public String getTipo() {
		return Messages.getString("VISTA_BARCHIVO_NOMBRE"); //$NON-NLS-1$
	}
}
