package arbolb.vista;

import java.util.Vector;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;
import common.Messages;
import common.estructura.Elemento;

public class BMasClustered extends VistaArbol{
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_TAM_CLUSTER = 3;
	
	private int tamCluster;

	public BMasClustered(ArbolB arbol, int tamCluster){
		super(arbol);
		this.tamCluster = tamCluster;
	}
	
	public BMasClustered(ArbolB arbol){
		this(arbol, DEFAULT_TAM_CLUSTER);
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		str += "digraph structs { \n"; //$NON-NLS-1$
		str += "  node [shape=record]; \n splines=line \n"; //$NON-NLS-1$
		
		if(arbol.getCantElementos() != 0){

			// Conectar las hojas
			Vector<NodoB> hojas = arbol.getRaiz().getNodosHoja();

			str += "{ rank = same; "; //$NON-NLS-1$
			for (NodoB nodo : hojas){
				str += nodo.getId() + ";"; //$NON-NLS-1$
			}
			str += "} \n"; //$NON-NLS-1$

			// Generar clusters
			int cantidad = 0;
			Vector<Elemento> elementos = arbol.getRaiz().getListaElementos();
			elementos.add(new Elemento(Elemento.FIN_ARCHIVO));

			for(Elemento e : elementos){
				if(cantidad == 0){
					str += "cluster0[label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n <TR> \n"; //$NON-NLS-1$
				}else if(cantidad % tamCluster == 0){
					int numCluster = cantidad / tamCluster;
					str += "<TD PORT=\"fs\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_BMAS + "\">  </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
					str += "</TR> </TABLE>>, shape=none] \n"; //$NON-NLS-1$
					str += "cluster" + numCluster + "[label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n <TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				}
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> " + e + "</TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"> D" + e + "</TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				cantidad++;
			}

			// Completar ultimo cluster
			while(cantidad % tamCluster != 0){
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> - </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"> - </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				cantidad++;
			}

			if(cantidad != 0){
				str += "<TD PORT=\"fs\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_BMAS + "\">  </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				str += "</TR> </TABLE>>, shape=none] \n"; //$NON-NLS-1$
			}
			
			// Conectar clusters
			for(int i = 1; i < cantidad / tamCluster; i++){
				str += "cluster" + (i-1) + ":fs -> cluster" + i + " [color=blue] \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
			str += "{ rank = same; "; //$NON-NLS-1$
			for(int i = 0; i < cantidad / tamCluster; i++){
				str += "cluster" + i + ";"; //$NON-NLS-1$ //$NON-NLS-2$
			}
			str += "} \n";			 //$NON-NLS-1$

			// Referencias
			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n";  //$NON-NLS-1$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B>" + Messages.getString("VISTA_CLAVE") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B>" + Messages.getString("VISTA_PUNTERO") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_BMAS + "\"><B>" + Messages.getString("VISTA_PUNTERO_SIGUIENTE_HOJA") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B>" + Messages.getString("VISTA_DATO") + "</B></TD></TR> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			if (accion != null){
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n"; //$NON-NLS-1$ //$NON-NLS-2$
			}//else{
			//str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			//}
			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];\n"; //$NON-NLS-1$
			str += "{ rank = source; ref;"; //$NON-NLS-1$
			str += "} \n"; //$NON-NLS-1$

			str += toGraph(arbol.getRaiz());

		}
		
		// Fin de grafo
		str += "}"; //$NON-NLS-1$
		
		return str;
	}

	private String toGraph(NodoB nodo) {
		Elemento finArchivo = new Elemento(Elemento.FIN_ARCHIVO);
		Vector<Elemento> elementos = nodo.getListaElementos();
		elementos.add(finArchivo);
		return toGraph(nodo, finArchivo, elementos);
	}
	
	private String toGraph(NodoB nodo, Elemento parent, Vector<Elemento> listaElementos) {
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
		str += "<TD PORT=\"f" + nodo.getCantActual() + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n";	 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		// Puntero al nodo siguiente
//		if(nodo.esHoja()){
//			str += "<TD PORT=\"fs\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n";
//		}//else{
			//str += "<f" + nodo.getCantActual() + ">\"]; \n";				
		//}
		str += "</TR> \n"; //$NON-NLS-1$
		str += "</TABLE>>, shape=none] \n"; //$NON-NLS-1$

		if (!nodo.esHoja()){
			// Generar recursivamente
			for (int i=0; i<nodo.getCantActual(); i++){
					str += toGraph(nodo.getHijos()[i], nodo.getDatos()[i], listaElementos);
					str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			
			// Conectar con nodos hijos
			str += toGraph(nodo.getHijos()[nodo.getCantActual()], parent, listaElementos);
			str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> " + nodo.getHijos()[nodo.getCantActual()].getId() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}else{
			// Vincular con archivo
			for (int i=0; i<nodo.getCantActual(); i++){
				int numCluster = listaElementos.indexOf(nodo.getDatos()[i]) / tamCluster;
				str += "    " + nodo.getId() + ":f" + i + " -> cluster" + numCluster + "[color=red]; \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
			if(parent != null){
				int numCluster = listaElementos.indexOf(parent) / tamCluster;
				str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> cluster" + numCluster + "[color=red]; \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			}
		}
		
		return str;
	}
	
	@Override
	public String getTipo() {
		return Messages.getString("VISTA_BMASCLUSTERED_NOMBRE"); //$NON-NLS-1$
	}
}
