package hash.cerrado;

import common.Vista;
import common.estructura.Elemento;

public class VistaHashCerrado extends Vista {
	private static final long serialVersionUID = 1L;
	
	private HashCerrado hash;
	
	public VistaHashCerrado(HashCerrado hash) {
		
		// TODO Auto-generated constructor stub
		this.hash = hash;
		setTipo("HashCerrado " + hash.getTecnica().getCorto());
	}

	@Override
	public String toGraph(String accion) {
		// TODO Auto-generated method stub
		String str = new String();
		
		// Inicio de grafo
		str += "graph G { \n";
		
		// Hash Cerrado
		str += "graph [ rankdir = \"BT\"]; \n node[shape=box]; \n";
		str += "subgraph Hash{ \n";
		
		str += "base [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n";

		str += hash.toGraph();
		
		str += "</TABLE>>, shape=none] \n";
		
		//Referencias
		
		str +="subgraph Referencias{\n";
		str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\">"; 
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"><B> VIRGEN </B></TD></TR>";
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"><B> OCUPADO </B></TD></TR>";
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"><B> BORRADO </B></TD></TR>";
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B> REFERENCIAS </B></TD></TR>";
		str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];};";
		
		// Fin de grafo
		str += "}; \n}";
		return str;
	}

	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return hash.getInfo();
	}

	@Override
	public void busqueda(Integer e) {
		hash.buscarConInfo(new Elemento(e));
	}

}
