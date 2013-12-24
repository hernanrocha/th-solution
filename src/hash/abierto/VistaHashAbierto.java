package hash.abierto;

import common.Vista;
import common.estructura.Elemento;

public class VistaHashAbierto extends Vista {
	
	private static final long serialVersionUID = 1L;
	
	private HashAbierto hash;

	public VistaHashAbierto(HashAbierto hash) {
		this.hash = hash;
		setTipo("HashAbierto " + hash.getTipo());
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		
		// Inicio de grafo
		str += "digraph G { \n";
		str += "graph [ rankdir = \"BT\"]; \n node[shape=box]; \n";
		str += "subgraph Hash{ \n";
	
		// Hash Abierto
		str += hash.toGraph();
		
		//Referencias
		
				str +="subgraph Referencias{\n";
				str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\"> \n"; 
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"><B> VIRGEN </B></TD></TR> \n";
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"><B> OCUPADO </B></TD></TR> \n";
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"><B> BORRADO </B></TD></TR> \n";
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FE9A2E\"><B> FUNCION </B></TD></TR> \n";
				str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B> REFERENCIAS </B></TD></TR>\n";
				str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];};\n";
		
		// Fin de grafo
		str += "};  \n}";
		return str;
	}

	@Override
	public String getInfo() {
		return hash.getInfo();
	}

	@Override
	public void busqueda(Integer e) {
		hash.buscarConInfo(new Elemento(e));
	}

}
