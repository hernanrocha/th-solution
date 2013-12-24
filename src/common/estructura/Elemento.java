package common.estructura;

import java.io.Serializable;

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
			return ""+get();
		}else{
			return "FIN";
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
	
}
