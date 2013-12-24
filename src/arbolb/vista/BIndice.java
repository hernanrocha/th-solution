package arbolb.vista;

import java.util.Vector;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;
import common.estructura.Elemento;

public class BIndice extends VistaArbol {
	private static final long serialVersionUID = 1L;

	public BIndice(ArbolB arbol){
		super(arbol);
		setTipo("B Índice");
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		str += "digraph structs { \n";
		str += "  node [shape=record]; \n splines=line \n";

		if(arbol.getCantElementos() != 0){
			// Referencias
			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n"; 
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B> Clave </B></TD></TR> \n";
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B> Dato </B></TD></TR> \n";
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B> Puntero </B></TD></TR> \n";
			if (accion != null){
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}else{
				//str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}
			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];\n";
			str += "{ rank = source; ref;";
			str += "} \n";

			// Generar archivo de datos
			Vector<Elemento> elementos = arbol.getRaiz().getListaElementos();
			elementos.add(new Elemento(Elemento.FIN_ARCHIVO));
			for (Elemento e : elementos){
				str += "dato" + e + "[ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n";
				str += "<TR> <TD PORT=\"D" + e + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> \n";
				str += e;
				str += "</TD> </TR> \n";
				str += "<TR> <TD PORT=\"z" + e + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"45\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"> \n";
				str += "D" + e;
				str += "</TD> </TR> \n";
				str += "</TABLE>>, shape=none] \n";
			}

			// Mantener alineados los datos
			str += "{ rank = same; ";
			for (Elemento e : elementos){
				str += "dato" + e + ";";
			}
			str += "} \n";

			// Estructura del arbol
			str += toGraph(arbol.getRaiz());
		}

		// Fin de grafo
		str += "}";

		return str;
	}

	public String toGraph(NodoB nodo) {
		Elemento finArchivo = new Elemento(Elemento.FIN_ARCHIVO);
		return toGraph(nodo, finArchivo);
	}

	public String toGraph(NodoB nodo, Elemento parent) {
		String str = new String();

		String color = "COLOR=\"" + VistaArbol.COLOR_BORDE_NORMAL + "\"";
		if(nodo.getArbol().getIntervinientes().contains(nodo)){
			color = "COLOR=\"" + VistaArbol.COLOR_NODO_INTERVINIENTE + "\"";
		}
		
		// Generar nodo propio
		str += nodo.getId() + "[ label= <<TABLE " + color + " BORDER=\"0\" CELLBORDER=\"2\" CELLSPACING=\"0\"> \n";
		str += "<TR> \n";
		for (int i=0; i<nodo.getCantActual(); i++){
			// Puntero
			str += "<TD PORT=\"f" + i + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n";			
			// Clave
			str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> " +
					nodo.getDatos()[i].toString() + " </TD> \n";
		}
		// Ultimo Puntero
		str += "<TD PORT=\"f" + nodo.getCantActual() + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n";
		str += "</TR> \n";
		str += "</TABLE>>, shape=none] \n";

		if (!nodo.esHoja()){
			// Generar recursivamente
			for (int i=0; i<nodo.getCantActual(); i++){
				// Nodo hoja y vinculo
				str += toGraph(nodo.getHijos()[i], nodo.getDatos()[i]);
				str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n";
			}
			// Ultimo nodo y vinculo
			str += toGraph(nodo.getHijos()[nodo.getCantActual()], parent);
			str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> " + nodo.getHijos()[nodo.getCantActual()].getId() + "\n";
		}else{
			// Vincular con archivo
			for (int i=0; i<nodo.getCantActual(); i++){
				str += "    " + nodo.getId() + ":f" + i + " -> dato" + nodo.getDatos()[i] + "[color=red];";
			}
			if(parent != null){
				str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> dato" + parent + "[color=red];";
			}
		}

		return str;
	}
}
