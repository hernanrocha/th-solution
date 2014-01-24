package common.estructura;

import java.io.Serializable;
import java.util.Vector;

import common.Messages;

public class Elemento implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public static final int FIN_ARCHIVO = -1;
	
	public int num;

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Elemento(int num) {
		super();
		this.num = num;
	}

	public String toString(){
		if (this.num != FIN_ARCHIVO){
			return ""+get(); //$NON-NLS-1$
		}else{
			return Messages.getString("ELEMENTO_FIN"); //$NON-NLS-1$
		}
	}
	
	public boolean mayorQue(Elemento e){
		return num > e.num;
	}
	
	public boolean menorQue(Elemento e){
		return num < e.num;
	}
	
	// Brian
	
	public Integer get(){
		return this.num;
	}

	@Override
	public boolean equals(Object obj){
		if ( obj instanceof Elemento ) {
			Elemento aComparar = (Elemento) obj;
			return this.get().equals(aComparar.get());
		}else{
			return false;
		}
	}
		
	// Obtener vector de elementos (desde cadena de texto)
	public static Vector<Elemento> parseToElements(String str) {
		Vector<Elemento> elementos = new Vector<Elemento>();
		String[] valores = str.split(","); //$NON-NLS-1$

		for (String v : valores){
			try{
				elementos.add(new Elemento(Integer.parseInt( v.trim() )));
			}catch (NumberFormatException e){
				//Logger.getLogger("Insercion").warn("Error al obtener elementos a insertar", e);
			}
		}
		//Logger.getLogger("Insercion").info("Elementos a insertar: " + elementos);
		
		return elementos;
	}
	
	// Obtener vector de elementos (aleatoriamente)
	public static Vector<Elemento> generarDatos(int inicio, int fin, int tiradas){
		Vector<Elemento> elementos = new Vector<Elemento>();		
		
		if (tiradas > fin - inicio + 1){
			System.out.println("Imposible elegir tantos elementos"); //$NON-NLS-1$
			return elementos;
		}
		
		// Generar lista de valores posibles
		Vector<Elemento> lista = new Vector<Elemento>();
		for(int i = inicio; i <= fin; i++){
			lista.add(new Elemento(i));
		}
		System.out.println("Lista completa: " + lista); //$NON-NLS-1$
		
		// Seleccionar aleatoriamente los valores
		for(int i = 0; i < tiradas; i++){
			int index = (int) ( Math.random() * lista.size() );
			elementos.add(lista.remove(index));
		}		
		System.out.println("Seleccionados: " + elementos); //$NON-NLS-1$
		
		return elementos;
	}

	
}
