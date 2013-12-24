package hash.cerrado.tecnicas;

import java.io.Serializable;


public abstract class TecnicaAbs implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String nombre;
	protected String corto;
	protected String funcion;
	protected String funcionReasignacion;
	protected int cantidadBaldes;
	
	public TecnicaAbs(int cantidadBaldes){
		this.cantidadBaldes = cantidadBaldes;
	}
	
	public int h(int x){
		int hTemporal = x % this.cantidadBaldes;
		if ( hTemporal < 0)
			return this.cantidadBaldes + hTemporal;
		return hTemporal;
	}
	
	public abstract int reasignar(int i, int x);
	public abstract String getLista();
	
	public String getNombre(){
		return nombre;
	}
	
	public String getCorto(){
		return corto;
	}
	
	public String getFuncion(){
		return funcion;
	}
	
	public String getFuncionReasignada(){
		return funcionReasignacion;
	}
}