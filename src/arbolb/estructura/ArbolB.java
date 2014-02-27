package arbolb.estructura;


import java.util.Vector;

import arbolb.estrategias.CrecimRedistDerecha;
import arbolb.estrategias.CrecimRedistIzquierda;
import arbolb.estrategias.CrecimSplit;
import arbolb.estrategias.DecrecFusionDerecha;
import arbolb.estrategias.DecrecFusionIzquierda;
import arbolb.estrategias.DecrecRedistDerecha;
import arbolb.estrategias.DecrecRedistIzquierda;
import arbolb.estrategias.Estrategia;
import arbolb.vista.BArchivo;
import arbolb.vista.BHibrido;
import arbolb.vista.BIndice;
import arbolb.vista.BMasClustered;
import arbolb.vista.BMasIndice;

import common.Messages;
import common.estructura.Almacenamiento;
import common.estructura.Elemento;
import common.swing.Archivo;
import common.swing.ConsolaManager;

public class ArbolB extends Almacenamiento{
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_ORDEN = 4;
	
	
	private static final int I_ORDEN = 0;
	private static final int I_BINDICE = 1;
	private static final int I_BHIBRIDO = 2;
	private static final int I_BARCHIVO = 3;
	private static final int I_BMASINDICE = 4;
	private static final int I_BMASCLUSTERED = 5;
	private static final int I_INS_SPLIT = 6;
	private static final int I_INS_RED_IZQ = 7;
	private static final int I_INS_RED_DER = 8;
	private static final int I_ELIM_FUS_IZQ = 9;
	private static final int I_ELIM_FUS_DER = 10;
	private static final int I_ELIM_RED_IZQ = 11;
	private static final int I_ELIM_RED_DER = 12;
	private static final int I_CLUSTERS = 13;
	
	// Estructura
	private NodoB raiz;
	private int orden;
	
	private Vector<Estrategia> crecim = new Vector<Estrategia>();
	private Vector<Estrategia> decrec = new Vector<Estrategia>();
	
	private Vector<NodoB> intervinientes = new Vector<NodoB>();
	

	//----------------------------------- Constructor ------------------------------------//
	
	/**
	 * Constructor de Arbol B
	 * @param orden Cantidad maxima de nodos hijos
	 */
	public ArbolB(int orden){
		this.orden = orden;
		setRaiz(new NodoB(null, this));
	}
	
	public static void load(Archivo arch, Vector<String> str){
		// Resetear cantidad de nodos
		NodoB.resetCantidad();

		// Orden del arbol
		int orden = ArbolB.DEFAULT_ORDEN;
		try{
			orden = Integer.parseInt(str.get(I_ORDEN));
		}catch (Exception e){

		}

		// Crear arbol
		ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_CREAR_ARBOL_ORDEN") + orden); //$NON-NLS-1$ //$NON-NLS-2$
		ArbolB arbol = new ArbolB(orden);
		arch.agregarAlmacenamiento(arbol);

		//----------------------------------------------------------------------------------------------------------//

		// Agregar vista Arbol B Indice
		if(str.get(I_BINDICE).equals("1")){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BINDICE")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BIndice(arbol));
		}

		// Agregar vista Arbol B Hibrido
		if(str.get(I_BHIBRIDO).equals("1")){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BHIBRIDO")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BHibrido(arbol));
		}

		// Agregar vista Arbol B Archivo
		if(str.get(I_BARCHIVO).equals("1")){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BARCHIVO")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BArchivo(arbol));
		}

		// Agregar vista Arbol B Mas Indice
		if(str.get(I_BMASINDICE).equals("1")){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BMASINDICE")); //$NON-NLS-1$ //$NON-NLS-2$
			arbol.agregarVista(new BMasIndice(arbol));

		}

		// Agregar vista Arbol B Mas Clustered
		if(str.get(I_BMASCLUSTERED).equals("1")){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_NUEVOTIPO_BMASCLUSTERED")); //$NON-NLS-1$ //$NON-NLS-2$
			try{
				int cluster = Integer.parseInt(str.get(I_CLUSTERS));
				System.out.println("cluster " + cluster); //$NON-NLS-1$
				arbol.agregarVista(new BMasClustered(arbol, cluster));
			}catch(Exception e){
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_CLUSTER_INCORRECTO")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.agregarVista(new BMasClustered(arbol));				
			}
		}

		//----------------------------------------------------------------------------------------------------------// 

		// Setear metodo de insercion
		for(int i = 1; i <= 3; i++){
			if (Integer.parseInt(str.get(I_INS_SPLIT)) == i){
				//$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_SPLIT")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaCrecim(new CrecimSplit());
			} else if (Integer.parseInt(str.get(I_INS_RED_IZQ)) == i){ //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_INS_REDISTRIBUCION_IZQUIERDA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaCrecim(new CrecimRedistIzquierda());
			} else if (Integer.parseInt(str.get(I_INS_RED_DER)) == i){ //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_INS_REDISTRIBUCION_DERECHA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaCrecim(new CrecimRedistDerecha());
			}
		}

		//----------------------------------------------------------------------------------------------------------//

		// Setear metodo de eliminacion		
		for(int i = 1; i <= 4; i++){
			if (Integer.parseInt(str.get(I_ELIM_FUS_IZQ)) == i){ //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_FUSION_IZQUIERDA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecFusionIzquierda());
			} else if (Integer.parseInt(str.get(I_ELIM_FUS_DER)) == i){ //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_FUSION_DERECHA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecFusionDerecha());
			} else if (Integer.parseInt(str.get(I_ELIM_RED_IZQ)) == i){ //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_ELIM_REDISTRIBUCION_IZQUIERDA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecRedistIzquierda());
			} else if (Integer.parseInt(str.get(I_ELIM_RED_DER)) == i){ //$NON-NLS-1$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("SWING_FORM_ARBOLB"), Messages.getString("SWING_FORM_AGREGAR_ELIM_REDISTRIBUCION_DERECHA")); //$NON-NLS-1$ //$NON-NLS-2$
				arbol.addEstrategiaDecrec(new DecrecRedistDerecha());
			}
		}

		//----------------------------------------------------------------------------------------------------------//

		// Agregar primera captura.
		arbol.agregarCaptura();
		
		System.out.println("Parametros:");
		String info = "ArbolB";
		info += " " + str.get(0);
		for(int i = 1; i < str.size(); i++){
			info += "," + str.get(i);
		}
		arch.addInfo(info);
		System.out.println("Fin");
	}
	
	//------------------------------ Interfaz Almacenamiento ------------------------------//
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#insertar(common.estructura.Elemento)
	 */
	@Override
	public void insertar(Elemento e){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_INSERTAR_PRE", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$
		raiz.insertar(e);
		agregarCaptura(Messages.getString("ARBOL_INSERTAR_POS", new Object[]{e} )); //$NON-NLS-1$
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_INSERTAR_POS", new Object[]{e} ));		 //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#eliminar(common.estructura.Elemento)
	 */
	@Override
	public void eliminar(Elemento e){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_ELIMINAR_PRE", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		raiz.eliminar(e);
		agregarCaptura(Messages.getString("ARBOL_ELIMINAR_POS", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_ELIMINAR_POS", new Object[]{e} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#buscar(common.estructura.Elemento)
	 */
	@Override
	public boolean buscar(Elemento e) {
		return raiz.buscar(e);
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#buscarConInfo(common.estructura.Elemento)
	 */
	@Override
	public void buscarConInfo(Elemento e) {
		ConsolaManager.getInstance().escribir(""); //$NON-NLS-1$
		ConsolaManager.getInstance().escribir(Messages.getString("ARBOL_BUSCAR_INFO", new Object[]{e} )); //$NON-NLS-1$
		raiz.buscarConInfo(e);		
	}
	
	/* (non-Javadoc)
	 * @see common.estructura.Almacenamiento#getInfo()
	 */
	@Override
	public String getInfo() {
		int n = getOrden();
		int i;
		
		String s ="*" + Messages.getString("ARBOL_INFO_TITULO"); //$NON-NLS-1$ //$NON-NLS-2$
		s+= "\n \n"; //$NON-NLS-1$
		
		s+="+" + Messages.getString("ARBOL_INFO_ORDEN") + ": " + n + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("ARBOL_INFO_INSERCION") + ": \n     "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		i = 1;
		for (Estrategia e : crecim){
			s+= i + ") " + e.toString() + ". "; //$NON-NLS-1$ //$NON-NLS-2$
			i++;
		}
		s+="\n";
		i = 1;
		s+="+" + Messages.getString("ARBOL_INFO_ELIMINACION") + ": \n     "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		for (Estrategia e : decrec){
			s+= i + ") " + e.toString() + ". "; //$NON-NLS-1$ //$NON-NLS-2$
			i++;
		}
		s+="\n";
		
		s+="+" + Messages.getString("ARBOL_INFO_MIN_ELEM_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MIN_ELEM_DESC", new Object[]{(n/2 + n % 2 - 1)} ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("ARBOL_INFO_MAX_ELEM_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MAX_ELEM_DESC", new Object[]{(n-1)} ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("ARBOL_INFO_MIN_HIJOS_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MIN_HIJOS_DESC", new Object[]{(n/2 + n % 2)} ) + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("ARBOL_INFO_MAX_HIJOS_TITULO") + ": \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="    " + Messages.getString("ARBOL_INFO_MAX_HIJOS_DESC", new Object[]{(n)} ) + " \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		
		s+="+" + Messages.getString("ARBOL_INFO_CANTIDAD_ELEM") + ": " + getCantidadElementos() + "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("ARBOL_INFO_PROFUNDIDAD") + ": " + getProfundidad(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		return s;
	}
	
	//------------------ Estrategias de Crecimiento y Decrecimiento ------------------//
	
	/**
	 * Agregar una estrategia de crecimiento de la estructura
	 * @param e Estrategia de Crecimiento
	 */
	public void addEstrategiaCrecim(Estrategia e) {
		crecim.add(e);
	}
	
	/**
	 * Agregar una estrategia de decrecimiento de la estructura
	 * @param e Estrategia de Decrecimiento
	 */
	public void addEstrategiaDecrec(Estrategia e) {
		decrec.add(e);
	}
	
	/**
	 * Intentar aplicar alguna de las estrategias de crecimiento existentes a un nodo del arbol
	 * @param nodo Nodo a aplicar estrategia
	 * @return <b>true<\b> si se pudo aplicar correctamente y <b>false<\b> en caso contrario
	 */
	public boolean aplicarEstrategiasCrecim(NodoB nodo){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_CRECIM_INICIAR")); //$NON-NLS-1$ //$NON-NLS-2$
		for (Estrategia e : crecim){
			if (e.doAction(nodo)){
				return true;
			}
		}
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_CRECIM_ERROR")); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}
	
	/**
	 * Intentar aplicar alguna de las estrategias de decrecimiento existentes a un nodo del arbol
	 * @param nodo Nodo a aplicar estrategia
	 * @return <b>true<\b> si se pudo aplicar correctamente y <b>false<\b> en caso contrario
	 */
	public boolean aplicarEstrategiasDecrec(NodoB nodo){
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_DECRECIM_INICIAR")); //$NON-NLS-1$ //$NON-NLS-2$
		for (Estrategia e : decrec){
			if (e.doAction(nodo)){
				return true;
			}
		}
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_DECRECIM_ERROR")); //$NON-NLS-1$ //$NON-NLS-2$
		return false;
	}
	
	//------------------------------ Setters y Getters ------------------------------//
	
	/**
	 * Obtener orden del arbol (cantidad maxima de hijos por nodo)
	 * @return Orden del arbol
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * Obtener cantidad total de elementos del arbol
	 * @return Cantidad de elementos
	 */
	@Override
	public int getCantidadElementos(){
		return raiz.getCantidadElementos();
	}
	
	/**
	 * Obtener profundidad del arbol
	 * @return Profundidad del arbol
	 */
	public int getProfundidad(){
		return raiz.getProfundidad();
	}
	
	/**
	 * Setear raiz del arbol
	 * @param raiz Nueva raiz
	 */
	public void setRaiz(NodoB raiz) {
		ConsolaManager.getInstance().escribirInfo(Messages.getString("ARBOL_NOMBRE"), Messages.getString("ARBOL_NUEVO_NODO_RAIZ")); //$NON-NLS-1$ //$NON-NLS-2$
		this.raiz = raiz;
	}
	
	/**
	 * Obtener raiz del arbol
	 * @return Nodo raiz
	 */
	public NodoB getRaiz() {
		return raiz;
	}
	
	/**
	 * Obtener Vector de nodos intervinientes durante la aplicacion de una estrategia
	 * @return Vector de nodos intervinientes o lista vacia si no se esta aplicando ninguna estrategia.
	 */
	public Vector<NodoB> getIntervinientes() {
		return intervinientes;
	}

	/**
	 * Setear Vector de nodos intervinientes durante la aplicacion de una estrategia
	 * @param intervinientes Vector de nodos intervinientes
	 */
	public void setIntervinientes(Vector<NodoB> intervinientes) {
		this.intervinientes = intervinientes;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return raiz.toString();
	}
	
}
