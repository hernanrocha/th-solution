package hash.cerrado;

import common.Messages;
import common.Vista;
import common.estructura.Elemento;

public class VistaHashCerrado extends Vista {
	private static final long serialVersionUID = 1L;
	
	private HashCerrado hash;
	
	public VistaHashCerrado(HashCerrado hash) {
		this.hash = hash;
	}

	@Override
	public String toGraph(String accion) {
		String str = new String();
		
		// Inicio de grafo
		str += "graph G { \n"; //$NON-NLS-1$
		
		// Hash Cerrado
		str += "graph [ rankdir = \"BT\"]; \n node[shape=box]; \n"; //$NON-NLS-1$
		str += "subgraph Hash{ \n"; //$NON-NLS-1$
		
		str += "base [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n"; //$NON-NLS-1$

		str += hash.toGraph();
		
		str += "</TABLE>>, shape=none] \n"; //$NON-NLS-1$
		
		//Referencias
		
		str +="subgraph Referencias{\n"; //$NON-NLS-1$
		str +="ref [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"3\" COLOR=\"#000000\">";  //$NON-NLS-1$
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"><B> " + Messages.getString("HASH_VISTA_VIRGEN") + " </B></TD></TR>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"><B> " + Messages.getString("HASH_VISTA_OCUPADO") + " </B></TD></TR>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"><B> " + Messages.getString("HASH_VISTA_BORRADO") + " </B></TD></TR>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		str += "<TR><TD ALIGN=\"TEXT\" HEIGHT=\"20\" WIDTH=\"40\" BGCOLOR=\"#FDFF69\" BORDER=\"0\"><B> " + Messages.getString("HASH_VISTA_REFERENCIAS") + " </B></TD></TR>"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		str += "</TABLE>>, shape=box,style=filled,fillcolor=\"#FDFF69\"];};"; //$NON-NLS-1$
		
		// Fin de grafo
		str += "}; \n}"; //$NON-NLS-1$
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
	
	@Override
	public String getTipo(){
		return Messages.getString("HASH_VISTA_CERRADO_NOMBRE") + hash.getTecnica().getCorto(); //$NON-NLS-1$
	}

}
