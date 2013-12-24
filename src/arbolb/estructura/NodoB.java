package arbolb.estructura;

import java.io.Serializable;
import java.util.Vector;

//import org.apache.log4j.Logger;
import common.estructura.Elemento;
import common.swing.ConsolaManager;

public class NodoB implements Serializable {
	private static final long serialVersionUID = 1L;
	
	// Estructura Basica
	protected Elemento[] datos;
	protected NodoB[] hijos;

	// Referencias
	protected NodoB parent;
	protected ArbolB arbol;
	
	// Restricciones (Depende de la implementacion)
	protected int minElem, maxElem;
	
	// Posicion actual
	protected int cantActual; // 0..orden-1

	// Identificador unico
	private int id;
	private static int cantidad = 0;
	
	public static int getCantidad(){
		return cantidad;
	}
	
	public static void setCantidad (int cantidad){
		NodoB.cantidad = cantidad;
	}
	
	public static void resetCantidad(){
		cantidad = 0;
	}
	
	/**
	 * FALTA
	 * @param parent Nodo padre
	 * @param arbol Arbol al que pertenece el nodo
	 * @param maxElem Cantidad maxima de elementos
	 */
	public NodoB(NodoB parent, ArbolB arbol, int minElem, int maxElem) {
		this.maxElem = maxElem;
		this.parent = parent;
		this.arbol = arbol;
		this.datos = new Elemento[getMaxElem() + 1];
		this.hijos = new NodoB[getMaxElem() + 2];
		this.cantActual = 0;
		// FALTA: Por parametro
		this.minElem = minElem;
		
		id = cantidad;
		cantidad++;
	}
	
	public NodoB(NodoB parent, ArbolB arbol) {
		this(parent, arbol, arbol.getOrden()/2 + arbol.getOrden() % 2 - 1, arbol.getOrden() - 1);
	}
	
	public void setParent(NodoB parent) {
		this.parent = parent;
	}	

	public NodoB getParent() {
		return parent;
	}
	
	public int getMinElem(){
		if(esRaiz()){
			return 0;
		}
		return minElem;
	}
	
	public int getMaxElem(){
		return maxElem;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean esHoja(){
		return hijos[0] == null;
	}

	public boolean esRaiz(){
		return parent == null;
	}
		
	public int getCantElementos() {
		int cant = cantActual;
		for (int i=0; i<=cantActual; i++){
			if (hijos[i] != null){
				cant += hijos[i].getCantElementos();
			}
		}
		return cant;
	}

	public int getProfundidad() {
		if (hijos[0] != null){
			return 1 + hijos[0].getProfundidad();
		}else{
			return 1;
		}
	}
	
	public Vector<Elemento> getListaElementos() {
		Vector<Elemento> lista = new Vector<Elemento>();
		
		for (int i=0; i<cantActual; i++){
			if (hijos[i] != null){
				lista.addAll(hijos[i].getListaElementos());
			}
			lista.add(datos[i]);
		}
		
		if (hijos[cantActual] != null){
			lista.addAll(hijos[cantActual].getListaElementos());
		}
		
		return lista;
	}
	
	public Integer elemIndex(Elemento e){
    	int pos = 0;
		while ( (pos < cantActual) && (e.mayorQue(datos[pos])) ){
			pos++;
		}
		
		if ( (pos == cantActual) || e.menorQue(datos[pos]) ){
			return null;
		}else{
			return pos;
		}
    }
	
	public Integer childIndex(NodoB n){
    	int pos = 0;
		while ( (pos <= cantActual) && (n != hijos[pos]) ){
			pos++;
		}
		
		if (n != hijos[pos]){
			return null;
		}else{
			return pos;			
		}
    }
	
	public Elemento getMayor(){
		if(esHoja()){
			return datos[cantActual-1];
		}else{
			return hijos[cantActual].getMayor();
		}
	}
	
	public Elemento[] getDatos() {
		return datos;
	}

	public NodoB[] getHijos() {
		return hijos;
	}
	
	public Vector<NodoB> getNodosHoja(){
		Vector<NodoB> hojas = new Vector<NodoB>();
		if(esHoja()){
			hojas.add(this);
		}else{
			for (int i=0; i<=cantActual; i++){
				if (hijos[i] != null){
					hojas.addAll(hijos[i].getNodosHoja());
				}
			}
		}
		return hojas;
	}
	
	public String toStringSimple(){
		String str = new String();
		str += "[";
		for (int i=0; i<cantActual; i++){
			if(i != 0){
				str+= ",";				
			}
			str+= datos[i].toString();
		}
		str += "]";
		return str;
	}
	
	public String toString(){
		String str = new String();
		str += "Datos:";
		for (int i=0; i<cantActual; i++){
			str+= datos[i].toString();
			str+= ".";
		}
		for (int i=0; i<=cantActual; i++){
			if (hijos[i] != null){
				str+="\n";
				str+= hijos[i].toString();
			}
		}
		return str;
	}

	public ArbolB getArbol() {
		return arbol;
	}
	
	public int getCantActual() {
		return cantActual;
	}

	/***********************************************************************************
	 *                                                                                 *
	 *                             TODO METODOS INSERCION                              *
	 *                                                                                 *
	 ***********************************************************************************/
	
	public void insertar(Elemento e){
		if (esHoja()){
			// Insertar en el nodo
			insertarEfectivo(e);
		}else{
			// Insertar recursivamente
			int pos = 0;
			while ( (pos < cantActual) && (e.mayorQue(datos[pos])) ){
				pos++;
			}
			hijos[pos].insertar(e);
		}
	}
	
	private void insertarEfectivo(Elemento e) {
		insertarEfectivo(e, null, null);
	}
	
	private void insertarEfectivo(Elemento e, NodoB nIzq, NodoB nDer){
		// Buscar posicion a insertar
		int pos = 0;
		while ( (pos < cantActual) && (e.mayorQue(datos[pos])) ){
			pos++;
		}
		
		// Mover todos los mayores un lugar
		for(int i = cantActual; i > pos; i--){
			datos[i] = datos[i-1];
			hijos[i+1] = hijos[i];
		}
		
		// Insertar y actualizar cantidad de elementos
		datos[pos] = e;
		cantActual++;
	
		// Determinar si se debe reemplazar subramas
		if (nIzq != null){
			hijos[pos] = nIzq;
			nIzq.setParent(this);
		}
		if (nDer != null){
			hijos[pos + 1] = nDer;
			nDer.setParent(this);
		}
				
		// Determinar si es necesario aplicar alguna estrategia de crecimiento
		if(cantActual > getMaxElem()){
			arbol.aplicarEstrategiasCrecim(this);
		}
	}
	
	/***********************************************************************************
	 *                                                                                 *
	 *                       TODO ESTRATEGIAS DE CRECIMIENTO                           *
	 *                                                                                 *
	 ***********************************************************************************/
	
	/**
	 * Algoritmo de Split.
	 * 
	 * Metodos que utiliza:
	 *  . getSplit
	 * 
	 * Casos de prueba:
	 *  . Nodo hoja (subIzq y subDer son null)
	 *  . Nodo interno
	 *  . Nodo raiz, genera nueva raiz.
	 * 
	 */
	public boolean split() {
		// Capturar estado previo
		Vector<NodoB> intervinientes = new Vector<NodoB>();
		intervinientes.add(this);
		arbol.setIntervinientes(intervinientes);
		arbol.agregarCaptura("Realizar Split");
		arbol.setIntervinientes(new Vector<NodoB>());
		
		// Paso 1: Dividir el nodo en 2 sub-nodos con un elemento separador
		Split split = getSplit();

		// Paso 2: Generar nueva raiz si es necesario
		if (esRaiz()){
			parent = new NodoB(null, arbol);
			arbol.setRaiz(parent);
		}
		
		// Paso 3: Insertar separador en nodo padre
		parent.insertarEfectivo(split.getMed(), split.getIzq(), split.getDer());
		
		return true;		
	}
	
	private Split getSplit(){
		ConsolaManager.getInstance().escribirInfo("Arbol B", "(SPLIT)  Realizar split");
		
		Split split = new Split();
		
		// Generar nodos izquierdo y derecho
		NodoB izq = new NodoB(null, arbol), der = new NodoB(null, arbol);
		
		// Calcular elemento medio
		int medio = getMaxElem() / 2;		
		
		// Copiar Nodo Izquierda
		for (int i=0; i < medio; i++){
			izq.insertarEfectivo(datos[i]);
			izq.hijos[i] = hijos[i];
			if(!esHoja()) hijos[i].setParent(izq);
		}
		izq.hijos[medio] = hijos[medio];
		if(!esHoja()) hijos[medio].setParent(izq);
		split.setIzq(izq);
		
		// Copiar elemento medio
		split.setMed(datos[medio]);
		
		// Copiar Nodo Derecha
		for (int i=medio+1; i < arbol.getOrden(); i++){
			der.insertarEfectivo(datos[i]);
			der.hijos[i-(medio+1)] = hijos[i];
			if(!esHoja()) hijos[i].setParent(der);
		}
		der.hijos[arbol.getOrden()-(medio+1)] = hijos[arbol.getOrden()];
		if(!esHoja()) hijos[arbol.getOrden()].setParent(der);
		split.setDer(der);

		// Devolver split generado
		return split;
	}
	
	public boolean insertarRedistribucionDerecha(){
		// Asegurarse que sea hoja y haya nodo padre
		if (!esHoja() || esRaiz()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER)  No puede aplicarse en el nodo");
			return false;
		}
		
		// Encontrar posicion en el nodo padre
		int parentIndex = parent.childIndex(this);
		
		// El nodo es el mas derecho del padre
		if(parentIndex == parent.cantActual){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER)  No hay nodo derecho para la redistribucion");			
			return false;
		}
		
		// Encontrar nodo derecho
		NodoB nodoDer = parent.hijos[parentIndex + 1];

		// El nodo derecho esta lleno
		if(nodoDer.cantActual + 1 > nodoDer.getMaxElem()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER) El nodo derecho esta lleno");
			return false;
		}
		
		// El nodo derecho puede darle un elemento	
		ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER)  Realizar redistribucion a derecha");
		
		// Capturar estado previo
		Vector<NodoB> intervinientes = new Vector<NodoB>();
		intervinientes.add(this);
		intervinientes.add(nodoDer);
		intervinientes.add(parent);
		arbol.setIntervinientes(intervinientes);
		arbol.agregarCaptura("Redistribucion a Derecha");
		arbol.setIntervinientes(new Vector<NodoB>());
		
		// Intercambiar elementos
		distribuirDerecha(this, nodoDer, parent, parentIndex);
		
		return true;
	}
	
	public boolean insertarRedistribucionIzquierda(){
		// Asegurarse que sea hoja y haya nodo padre
		if (!esHoja() || esRaiz()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ)  No puede aplicarse en el nodo");
			return false;
		}
		
		// Encontrar posicion en el nodo padre
		int parentIndex = parent.childIndex(this);
		
		// El nodo es el mas izquierdo del padre
		if(parentIndex == 0){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ)  No hay nodo izquierdo para la redistribucion");			
			return false;
		}
		
		// Encontrar nodo izquierdo
		NodoB nodoIzq = parent.hijos[parentIndex - 1];

		// El nodo izquierdo esta lleno
		if(nodoIzq.cantActual + 1 > nodoIzq.getMaxElem()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ) El nodo izquierdo esta lleno");
			return false;
		}
		
		// El nodo derecho puede darle un elemento	
		ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ)  Realizar redistribucion a izquierda");
		
		// Capturar estado previo
		Vector<NodoB> intervinientes = new Vector<NodoB>();
		intervinientes.add(this);
		intervinientes.add(nodoIzq);
		intervinientes.add(parent);
		arbol.setIntervinientes(intervinientes);
		arbol.agregarCaptura("Redistribucion a Izquierda");
		arbol.setIntervinientes(new Vector<NodoB>());
		
		// Intercambiar elementos
		distribuirIzquierda(nodoIzq, this, parent, parentIndex - 1);
		
		return true;
	}
	
	/***********************************************************************************
	 *                                                                                 *
	 *                           TODO METODOS ELIMINACION                              *
	 *                                                                                 *
	 ***********************************************************************************/
	
	// Buscar recursivamente el nodo a eliminar
    public void eliminar(Elemento e){
    	Integer index = elemIndex(e);
    	if(index != null){
    		eliminarEfectivo(index);
    	}else if (esHoja()){
    		//Logger.getLogger("Arbol").warn("El elemento no existe");
    	}else{
			int pos = 0;
			while ( (pos < cantActual) && (e.mayorQue(datos[pos])) ){
				pos++;
			}
			hijos[pos].eliminar(e);
    	}
    }
	
    // Eliminacion efectiva del elemento del nodo en la posicion index
    private void eliminarEfectivo(Integer index) {
		if(esHoja()){
			// Eliminar elemento
			for(int i=index+1; i<cantActual; i++){
				datos[i-1] = datos[i];
			}
			cantActual--;
			datos[cantActual] = null;
			//arbol.agregarCaptura("Elemento eliminado");
			//arbol.agregarCaptura();
			
			// Verificar si es necesario aplicar alguna correccion
			if(cantActual < getMinElem()){
				arbol.aplicarEstrategiasDecrec(this);
			}
		}else{
			// Reemplazar por nodo inmedianto inferior
			Elemento victima = hijos[index].getMayor();
			datos[index] = victima;
			//arbol.agregarCaptura("Reemplazando por " + victima);
			
			// Eliminar nodo inmediato inferior
			hijos[index].eliminar(victima);
		}
	}
        
    private void eliminarPorReacomodo(Integer index){
		// Retrasar nodos del padre
		for(int i = index; i < cantActual; i++){
			datos[i - 1] = datos[i];
			hijos[i] = hijos[i + 1];
		}
		cantActual--;
		datos[cantActual] = null;
		hijos[cantActual + 1] = null;
		if(cantActual < getMinElem()){
			arbol.aplicarEstrategiasDecrec(this);
		}
    }

	/***********************************************************************************
	 *                                                                                 *
	 *                      TODO ESTRATEGIAS DE DECRECIMIENTO                          *
	 *                                                                                 *
	 ***********************************************************************************/
    
    /**
     * Algoritmo de <b>fusion a izquierda</b> de un nodo hijo.
     * 
     * Casos de prueba:
	 *  . Nodo interno.
	 *  . Nodo raiz. Puede decrecer el arbol en un nivel, si la raiz queda sin elementos.
     * 
     * 
     * @param child Elemento hijo a fusionar
     * @return
     * <b>true</b> si puede realizar la fusi�n. <br>
     * <b>false</b> si no lo puede hacer. El nodo izquierdo no existe o tiene demasiados elementos.
     */
	public boolean fusion_izquierda(NodoB child) {
		// Encontrar posicion del hijo
		int index = childIndex(child);
		
		// El nodo es el mas izquierdo del padre
		if(index == 0){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(FUSION IZQ) No hay nodo izquierdo para fusionar");
			return false;
		}
		
		NodoB nodoIzq = getHijos()[index-1];
		int elementos = nodoIzq.cantActual + child.cantActual + 1;	
		
		if(elementos >= arbol.getOrden()){
			// Demasiados elementos para fusionar
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(FUSION IZQ) El nodo no puede quedar con " + elementos + " elementos");
			return false;
		}else{				
			// Capturar estado previo
			Vector<NodoB> intervinientes = new Vector<NodoB>();
			intervinientes.add(this);
			intervinientes.add(nodoIzq);
			intervinientes.add(child);
			arbol.setIntervinientes(intervinientes);
			arbol.agregarCaptura("Fusion a Izquierda");
			arbol.setIntervinientes(new Vector<NodoB>());
			
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(FUSION IZQ) Realizar fusion");
			
			// Pasar datos a nodo hermano izquierdo
			for(int i = 0; i < child.cantActual; i++){
				nodoIzq.insertarEfectivo(child.getDatos()[i], null, child.getHijos()[i+1]);
			}
			//arbol.agregarCaptura("Mover elementos de nodo izquierdo");
			
			// Copiar elemento del padre en nodo izquierdo
			nodoIzq.insertarEfectivo(getDatos()[index - 1], null, child.getHijos()[0]);
			//arbol.agregarCaptura("Mover elemento de padre");
			
			// Correr elementos del padre
			//System.out.println("Index: " + index);
			eliminarPorReacomodo(index);
			//arbol.agregarCaptura("Eliminar separador del padre");
			
			// Determinar si el arbol decrece un nivel
			if(arbol.getRaiz().cantActual == 0){
				arbol.setRaiz(arbol.getRaiz().getHijos()[0]);
				arbol.getRaiz().parent = null;
				//arbol.agregarCaptura("Eliminar raiz antigua");
			}
			
			return true;
		}
	}
	
	public boolean fusion_derecha(NodoB child) {
		// Encontrar posicion del hijo
		int index = childIndex(child);
		
		// El nodo es el mas derecho del padre
		if(index == cantActual){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(FUSION DER) No hay nodo derecho para fusionar");
			return false;
		}
		
		NodoB nodoDer = hijos[index + 1];
		int elementos = nodoDer.cantActual + child.cantActual + 1;	
		
		if(elementos >= arbol.getOrden()){
			// Demasiados elementos para fusionar
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(FUSION DER) El nodo no puede quedar con " + elementos + " elementos");
			return false;
		}else{
			// Capturar estado previo
			Vector<NodoB> intervinientes = new Vector<NodoB>();
			intervinientes.add(this);
			intervinientes.add(nodoDer);
			intervinientes.add(child);
			arbol.setIntervinientes(intervinientes);
			arbol.agregarCaptura("Fusion a Derecha");
			arbol.setIntervinientes(new Vector<NodoB>());
			
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(FUSION DER) Realizar fusion");

			// Pasar datos desde el nodo hermano derecho
			for(int i = 0; i < nodoDer.cantActual; i++){
				child.insertarEfectivo(nodoDer.datos[i], null, nodoDer.hijos[i+1]);
			}
			//arbol.agregarCaptura("Mover elementos de nodo derecho");

			// Copiar elemento del padre en nodo hijo
			child.insertarEfectivo(datos[index], null, nodoDer.hijos[0]);
			//arbol.agregarCaptura("Mover elemento de padre");

			// Correr elementos del padre
			eliminarPorReacomodo(index+1);
			//arbol.agregarCaptura("Eliminar separador del padre");
			
			// Determinar si el arbol decrece un nivel
			if(arbol.getRaiz().cantActual == 0){
				arbol.setRaiz(arbol.getRaiz().hijos[0]);
				arbol.getRaiz().parent = null;
				//arbol.agregarCaptura("Eliminar raiz antigua");
			}
			
			return true;
		}
	}
	
	public boolean eliminarRedistribucionDerecha() {
		// Asegurarse que sea hoja y haya nodo padre
		if (!esHoja() || esRaiz()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER) No puede aplicarse en el nodo");			
			return false;
		}
		
		// Encontrar posicion en el nodo padre
		int parentIndex = parent.childIndex(this);
		
		// El nodo es el mas derecho del padre
		if(parentIndex == parent.cantActual){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER) No hay nodo derecho para redistribuir");
			return false;
		}
		
		NodoB nodoDer = parent.hijos[parentIndex + 1];
		
		if(nodoDer.cantActual - 1 < nodoDer.getMinElem()){
			// El nodo derecho no puede dar elementos
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER) El nodo derecho no puede quedar con " + (nodoDer.cantActual-1) + " elementos");
			return false;
		}

		// El nodo derecho puede darle un elemento		
		ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST DER) Realizar redistribucion a derecha");
		
		// Capturar estado previo
		Vector<NodoB> intervinientes = new Vector<NodoB>();
		intervinientes.add(this);
		intervinientes.add(nodoDer);
		intervinientes.add(parent);
		arbol.setIntervinientes(intervinientes);
		arbol.agregarCaptura("Redistribucion a Derecha");
		arbol.setIntervinientes(new Vector<NodoB>());
		
		// Intercambiar elementos
		distribuirIzquierda(this, nodoDer, parent, parentIndex);
		
		return true;

	}
	
	public boolean eliminarRedistribuicionIzquierda(){
		// Asegurarse que sea hoja y haya nodo padre
		if (!esHoja() || esRaiz()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ) No puede aplicarse en el nodo");			
			return false;
		}
		
		// Encontrar posicion en el nodo padre
		int parentIndex = parent.childIndex(this);
		
		// El nodo es el mas izquierdo del padre
		if(parentIndex == 0){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ) No hay nodo izquierdo para distribuir");			
			return false;
		}
		
		// Encontrar nodo izquierdo
		NodoB nodoIzq = parent.hijos[parentIndex-1];

		// El nodo izquierdo no puede dar elementos 
		if(nodoIzq.cantActual - 1 < nodoIzq.getMinElem()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ) El nodo izquierdo no puede quedar con " + (nodoIzq.cantActual-1) + " elementos");
			return false;
		}
		
		// El nodo izquierdo puede darle un elemento	
		ConsolaManager.getInstance().escribirInfo("Arbol B", "(REDIST IZQ) Realizar redistribucion a izquierda");
		
		// Capturar estado previo
		Vector<NodoB> intervinientes = new Vector<NodoB>();
		intervinientes.add(this);
		intervinientes.add(nodoIzq);
		intervinientes.add(parent);
		arbol.setIntervinientes(intervinientes);
		arbol.agregarCaptura("Redistribucion a Derecha");
		arbol.setIntervinientes(new Vector<NodoB>());
		
		// Intercambiar elementos
		distribuirDerecha(nodoIzq, this, parent, parentIndex - 1);
		
		return true;
	}

	private void distribuirDerecha(NodoB nodoIzq, NodoB nodoDer, NodoB parent, int discrIndex) {
		//Logger.getLogger("Arbol").info("Copiando elemento a derecha");
		
		// Insertar discriminante en nodo destino
		nodoDer.insertarEfectivo(parent.datos[discrIndex]);
		
		// Cambiar discriminante
		parent.datos[discrIndex] = nodoIzq.datos[nodoIzq.cantActual - 1];
		
		// Eliminar en nodo origen
		nodoIzq.cantActual--;
		nodoIzq.datos[nodoIzq.cantActual] = null;
	}
	
	private void distribuirIzquierda(NodoB nodoIzq, NodoB nodoDer, NodoB parent, int discrIndex) {
		//Logger.getLogger("Arbol").info("Copiando elemento a izquierda");
		
		// Insertar discriminante en nodo destino
		nodoIzq.insertarEfectivo(parent.datos[discrIndex]);
		
		// Cambiar discriminante
		parent.datos[discrIndex] = nodoDer.datos[0];
		
		// Eliminar en nodo origen
		for(int i = 1; i < nodoDer.cantActual; i++){
			nodoDer.datos[i - 1] = nodoDer.datos[i];
		}
		nodoDer.cantActual--;
		nodoDer.datos[nodoDer.cantActual] = null;
	}
	
	/***********************************************************************************
	 *                                                                                 *
	 *                          TODO METODOS DE BUSQUEDA                               *
	 *                                                                                 *
	 ***********************************************************************************/

	public boolean buscar(Elemento e) {
		// Verificar si lo encuentra en el propio
		for (int i = 0; i < getCantActual(); i++){
			if (datos[i].equals(e))
				return true;
		}
			
		// Buscar recursivamente
		int pos = 0;
		while ( (pos < cantActual) && (e.mayorQue(datos[pos])) ){
			pos++;
		}
		
		if (hijos[pos] != null){
			return hijos[pos].buscar(e);			
		}
		return false;
	}

	public void buscarConInfo(Elemento e) {
		ConsolaManager.getInstance().escribirInfo("Arbol B", "Buscando elemento en Nodo " + toStringSimple());
		
		// Verificar si lo encuentra en el propio
		for (int i = 0; i < getCantActual(); i++){
			if (datos[i].equals(e)){
				ConsolaManager.getInstance().escribirInfo("Arbol B", "Elemento encontrado en la posicion " + (i + 1) + " del nodo.");
				return;
			}
		}
		
		if (esHoja()){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "No se encontró el elemento.");
			return;
		}
		
		// Buscar recursivamente
		int pos = 0;
		while ( (pos < cantActual) && (e.mayorQue(datos[pos])) ){
			pos++;
		}
		
		if (hijos[pos] != null){
			ConsolaManager.getInstance().escribirInfo("Arbol B", "Accediendo al puntero al nodo número " + (pos + 1) + ".");
			hijos[pos].buscarConInfo(e);			
		}		
	}

}
