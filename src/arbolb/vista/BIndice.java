package arbolb.vista;

import java.util.Vector;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;
import common.Messages;
import common.estructura.Elemento;

public class BIndice extends VistaArbol {
	private static final long serialVersionUID = 1L;

	public BIndice(ArbolB arbol){
		super(arbol);
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		str += "digraph structs { \n"; //$NON-NLS-1$
		str += "  node [shape=record]; \n splines=line \n"; //$NON-NLS-1$

		if(arbol.getCantElementos() != 0){
			// Referencias
			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n";  //$NON-NLS-1$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B>" + Messages.getString("VISTA_CLAVE") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B>" + Messages.getString("VISTA_DATO") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B>" + Messages.getString("VISTA_PUNTERO") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			if (accion != null){
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n"; //$NON-NLS-1$ //$NON-NLS-2$
			}else{
				//str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}
			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];\n"; //$NON-NLS-1$
			str += "{ rank = source; ref;"; //$NON-NLS-1$
			str += "} \n"; //$NON-NLS-1$

			// Generar archivo de datos
			Vector<Elemento> elementos = arbol.getRaiz().getListaElementos();
			elementos.add(new Elemento(Elemento.FIN_ARCHIVO));
			for (Elemento e : elementos){
				str += "dato" + e + "[ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				str += "<TR> <TD PORT=\"D" + e + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				str += e;
				str += "</TD> </TR> \n"; //$NON-NLS-1$
				str += "<TR> <TD PORT=\"z" + e + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"45\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				str += "D" + e; //$NON-NLS-1$
				str += "</TD> </TR> \n"; //$NON-NLS-1$
				str += "</TABLE>>, shape=none] \n"; //$NON-NLS-1$
			}

			// Mantener alineados los datos
			str += "{ rank = same; "; //$NON-NLS-1$
			for (Elemento e : elementos){
				str += "dato" + e + ";"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			str += "} \n"; //$NON-NLS-1$

			// Estructura del arbol
			str += toGraph(arbol.getRaiz());
		}

		// Fin de grafo
		str += "}"; //$NON-NLS-1$

		return str;
	}

	public String toGraph(NodoB nodo) {
		Elemento finArchivo = new Elemento(Elemento.FIN_ARCHIVO);
		return toGraph(nodo, finArchivo);
	}

	public String toGraph(NodoB nodo, Elemento parent) {
		String str = new String();

		String color = "COLOR=\"" + VistaArbol.COLOR_BORDE_NORMAL + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		if(nodo.getArbol().getIntervinientes().contains(nodo)){
			color = "COLOR=\"" + VistaArbol.COLOR_NODO_INTERVINIENTE + "\""; //$NON-NLS-1$ //$NON-NLS-2$
		}
		
		// Generar nodo propio
		str += nodo.getId() + "[ label= <<TABLE " + color + " BORDER=\"0\" CELLBORDER=\"2\" CELLSPACING=\"0\"> \n"; //$NON-NLS-1$ //$NON-NLS-2$
		str += "<TR> \n"; //$NON-NLS-1$
		for (int i=0; i<nodo.getCantActual(); i++){
			// Puntero
			str += "<TD PORT=\"f" + i + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n";			 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			// Clave
			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> " + //$NON-NLS-1$ //$NON-NLS-2$
					nodo.getDatos()[i].toString() + " </TD> \n"; //$NON-NLS-1$
		}
		// Ultimo Puntero
		str += "<TD PORT=\"f" + nodo.getCantActual() + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		str += "</TR> \n"; //$NON-NLS-1$
		str += "</TABLE>>, shape=none] \n"; //$NON-NLS-1$

		if (!nodo.esHoja()){
			// Generar recursivamente
			for (int i=0; i<nodo.getCantActual(); i++){
				// Nodo hoja y vinculo
				str += toGraph(nodo.getHijos()[i], nodo.getDatos()[i]);
				str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			// Ultimo nodo y vinculo
			str += toGraph(nodo.getHijos()[nodo.getCantActual()], parent);
			str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> " + nodo.getHijos()[nodo.getCantActual()].getId() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}else{
			// Vincular con archivo
			for (int i=0; i<nodo.getCantActual(); i++){
				str += "    " + nodo.getId() + ":f" + i + " -> dato" + nodo.getDatos()[i] + "[color=red];"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			if(parent != null){
				str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> dato" + parent + "[color=red];"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		}

		return str;
	}
	
	@Override
	public String getTipo() {
		return Messages.getString("VISTA_BINDICE_NOMBRE"); //$NON-NLS-1$
	}
}
