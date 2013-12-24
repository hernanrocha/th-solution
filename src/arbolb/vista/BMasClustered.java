package arbolb.vista;

import java.util.Vector;

import arbolb.estructura.ArbolB;
import arbolb.estructura.NodoB;
import common.estructura.Elemento;

public class BMasClustered extends VistaArbol{
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_TAM_CLUSTER = 3;
	
	private int tamCluster;

	public BMasClustered(ArbolB arbol, int tamCluster){
		super(arbol);
		setTipo("B+ Clustered");
		this.tamCluster = tamCluster;
	}
	
	public BMasClustered(ArbolB arbol){
		this(arbol, DEFAULT_TAM_CLUSTER);
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		str += "digraph structs { \n";
		str += "  node [shape=record]; \n splines=line \n";
		
		if(arbol.getCantElementos() != 0){

			// Conectar las hojas
			Vector<NodoB> hojas = arbol.getRaiz().getNodosHoja();

			str += "{ rank = same; ";
			for (NodoB nodo : hojas){
				str += nodo.getId() + ";";
			}
			str += "} \n";

			// Generar clusters
			int cantidad = 0;
			Vector<Elemento> elementos = arbol.getRaiz().getListaElementos();
			elementos.add(new Elemento(Elemento.FIN_ARCHIVO));

			for(Elemento e : elementos){
				if(cantidad == 0){
					str += "cluster0[label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n <TR> \n";
				}else if(cantidad % tamCluster == 0){
					int numCluster = cantidad / tamCluster;
					str += "<TD PORT=\"fs\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_BMAS + "\">  </TD> \n";
					str += "</TR> </TABLE>>, shape=none] \n";
					str += "cluster" + numCluster + "[label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n <TR> \n";
				}
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> " + e + "</TD> \n";
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"> D" + e + "</TD> \n";
				cantidad++;
			}

			// Completar ultimo cluster
			while(cantidad % tamCluster != 0){
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"> - </TD> \n";
				str += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"30\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"> - </TD> \n";
				cantidad++;
			}

			if(cantidad != 0){
				str += "<TD PORT=\"fs\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_BMAS + "\">  </TD> \n";
				str += "</TR> </TABLE>>, shape=none] \n";
			}
			
			// Conectar clusters
			for(int i = 1; i < cantidad / tamCluster; i++){
				str += "cluster" + (i-1) + ":fs -> cluster" + i + " [color=blue] \n";
			}
			str += "{ rank = same; ";
			for(int i = 0; i < cantidad / tamCluster; i++){
				str += "cluster" + i + ";";
			}
			str += "} \n";			

			// Referencias
			str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n"; 
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_CLAVE + "\"><B> Clave </B></TD></TR> \n";
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\"><B> Puntero </B></TD></TR> \n";
			str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"" + VistaArbol.COLOR_DATO + "\"><B> Dato </B></TD></TR> \n";
			if (accion != null){
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			}//else{
			//str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B>" + accion + "</B></TD></TR>\n";
			//}
			str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];\n";
			str += "{ rank = source; ref;";
			str += "} \n";

			str += toGraph(arbol.getRaiz());

		}
		
		// Fin de grafo
		str += "}";
		
		return str;
	}

	public String toGraph(NodoB nodo) {
		Elemento finArchivo = new Elemento(Elemento.FIN_ARCHIVO);
		Vector<Elemento> elementos = nodo.getListaElementos();
		elementos.add(finArchivo);
		return toGraph(nodo, finArchivo, elementos);
	}
	
	public String toGraph(NodoB nodo, Elemento parent, Vector<Elemento> listaElementos) {
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
		
		// Puntero al nodo siguiente
//		if(nodo.esHoja()){
//			str += "<TD PORT=\"fs\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"10\" BGCOLOR=\"" + VistaArbol.COLOR_PUNT_NODO + "\">  </TD> \n";
//		}//else{
			//str += "<f" + nodo.getCantActual() + ">\"]; \n";				
		//}
		str += "</TR> \n";
		str += "</TABLE>>, shape=none] \n";

		if (!nodo.esHoja()){
			// Generar recursivamente
			for (int i=0; i<nodo.getCantActual(); i++){
					str += toGraph(nodo.getHijos()[i], nodo.getDatos()[i], listaElementos);
					str += "    " + nodo.getId() + ":f" + i + " -> " + nodo.getHijos()[i].getId() + "\n";
			}
			
			// Conectar con nodos hijos
			str += toGraph(nodo.getHijos()[nodo.getCantActual()], parent, listaElementos);
			str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> " + nodo.getHijos()[nodo.getCantActual()].getId() + "\n";
		}else{
			// Vincular con archivo
			for (int i=0; i<nodo.getCantActual(); i++){
				int numCluster = listaElementos.indexOf(nodo.getDatos()[i]) / tamCluster;
				str += "    " + nodo.getId() + ":f" + i + " -> cluster" + numCluster + "[color=red]; \n";
			}
			if(parent != null){
				int numCluster = listaElementos.indexOf(parent) / tamCluster;
				str += "    " + nodo.getId() + ":f" + nodo.getCantActual() + " -> cluster" + numCluster + "[color=red]; \n";
			}
		}
		
		return str;
	}
}
