package hash.abierto;

import hash.Celda;
import hash.HashAbs;

import java.text.DecimalFormat;
import java.util.Vector;

import common.Messages;
import common.estructura.Elemento;
import common.swing.Archivo;
import common.swing.ConsolaManager;

public class HashAbierto extends HashAbs {

	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CANT_RANURAS_SECUNDARIAS = 3;
	public static final double DEFAULT_RHO_DISENIO = 0.9;

	private Vector<Vector<Celda>> espacioDeAlmacenamiento;
	private int ranurasSecundarias;
	private double rhoDeDisenio;
	private int frontera;
	private int cantidadDeBaldesActuales;
	private int MH;
	private int MHprima;
	private boolean separadoSolo;
	private String tipo;
	private DecimalFormat df = new DecimalFormat("0.0000"); //$NON-NLS-1$
	
	public HashAbierto(int baldes,int ranuras,int ranurasSecundarias,double rhoDeDisenio,boolean separadoSolo){
		this.baldes = baldes;
		this.ranuras = ranuras;
		this.ranurasSecundarias = ranurasSecundarias;
		this.rhoDeDisenio = rhoDeDisenio;
		this.frontera = 0;
		this.cantidadDeBaldesActuales = baldes;
		this.MH = baldes;
		this.MHprima = 2 * baldes;
		this.separadoSolo=separadoSolo;
		
		if ( separadoSolo ){
			ConsolaManager.getInstance().escribir(Messages.getString("HASH_ABIERTO_CREANDO_SEP")); //$NON-NLS-1$
			this.tipo=Messages.getString("HASH_ABIERTO_SEP_NOMBRE_PARENTESIS"); //$NON-NLS-1$
		}
		else{
			ConsolaManager.getInstance().escribir(Messages.getString("HASH_ABIERTO_CREANDO_SCL")); //$NON-NLS-1$
			this.tipo=Messages.getString("HASH_ABIERTO_SCL_NOMBRE_PARENTESIS"); //$NON-NLS-1$
		}
		
		//Se crea el espacio de Almacenamiento.
		espacioDeAlmacenamiento = new Vector<Vector<Celda>>();
		for ( int i = 0 ; i < baldes ; i++ ){
			espacioDeAlmacenamiento.add(new Vector<Celda>());
			for ( int j = 0 ; j < ranuras ; j++){
				espacioDeAlmacenamiento.elementAt(i).add(new Celda());
			}
		}
		
	}

	public static void load (Archivo arch, Vector<String> str){
		// Configurar hash hash.abierto

		// Obtener valores
		Integer baldes = Integer.parseInt(str.elementAt(1));
		Integer ranuras = Integer.parseInt(str.elementAt(2));
		Integer ranurasSecundarias = Integer.parseInt(str.elementAt(3));

		double rhoDisenio = 0;
		if (str.size() > 4){
			rhoDisenio = Double.parseDouble(str.elementAt(4)); 
		}


		if (str.elementAt(0).equals("0")){
			// Crear hash
			HashAbierto hash = new HashAbierto(baldes, ranuras, ranurasSecundarias, rhoDisenio,true);

			// Asignar
			arch.agregarAlmacenamiento(hash);

			// Agregar vista
			hash.agregarVista(new VistaHashAbierto(hash));
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}else{
			// Crear hash
			HashAbierto hash = new HashAbierto(baldes, ranuras, ranurasSecundarias, rhoDisenio,false);

			// Asignar
			arch.agregarAlmacenamiento(hash);

			// Agregar vista
			hash.agregarVista(new VistaHashAbierto(hash));
			//Agregar primera captura desde aca.
			hash.agregarCaptura();
		}


	}


	public double getRhoDeDisenio(){
		return this.rhoDeDisenio;
	}
	
	@Override
	public double getRho() {
		// RhoTemporal = # Elementos_t / ( # Baldes_t * # ranuras_t)
		return (double) cantidadRegistros / (cantidadDeBaldesActuales * (ranuras) );
	}

	public double getRhoMasUnElemento() {
		// RhoTemporal = # Elementos_t  + 1 / ( # Baldes_t * # ranuras_t)
		return ((double) cantidadRegistros + 1) / (cantidadDeBaldesActuales * (ranuras) );
	}
	
	@Override
	public void insertar(Elemento e) {
		
		//Explicación.
//		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se intentará insertar el elemento" + ": ["+e.getNum()+"].");
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_INTENTAR_INSERTAR", new Object[]{e.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$
		int baldeFinal = 0;
		
		if ( separadoSolo ){
			//Se busca el balde a insertar el elemento.
			int baldeAInsertar = h(e.getNum());
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_APLICAR_H", new Object[]{e.getNum(), baldeAInsertar})); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			
			//Si no hay más lugares en el balde lógico se le crean más ranuras.
			if ( ! this.baldeConRanurasVirgenes(baldeAInsertar) ){
				//Creo mas ranuras y lo inserto
				for ( int i = 0 ; i < ranurasSecundarias ; i++){
					espacioDeAlmacenamiento.elementAt(baldeAInsertar).add(new Celda());
				}
			}
			
			//Se inserta en el balde.
			int posicionAInsertar = this.posicionLibreBalde(baldeAInsertar);
			espacioDeAlmacenamiento.elementAt(baldeAInsertar).elementAt(posicionAInsertar).setElementoContenido(e);
			espacioDeAlmacenamiento.elementAt(baldeAInsertar).elementAt(posicionAInsertar).setEstado(Celda.OCUPADO);
			
			//Se incrementa la cantidad de elementos.
			cantidadRegistros++;
			baldeFinal= baldeAInsertar;
			
		}else
		{	
		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_1")); //$NON-NLS-1$ //$NON-NLS-2$
			
		//Si el factor de carga temporal no sobrepasa al factor de diseño.
		if ( this.getRhoMasUnElemento() <= this.getRhoDeDisenio() ){
			
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_2", new Object[]{df.format(getRhoMasUnElemento()), df.format(getRhoDeDisenio())} )); //$NON-NLS-1$ //$NON-NLS-2$
			
			
			//Se busca el balde a insertar el elemento.
			int baldeAInsertar = h(e.getNum());
			
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_3", new Object[]{e.getNum(), baldeAInsertar} )); //$NON-NLS-1$ //$NON-NLS-2$
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_4")); //$NON-NLS-1$ //$NON-NLS-2$
			
			//Si el balde encontrado < frontera entonces el elemento cen un balde particionado.
			if ( baldeAInsertar < frontera ){
				baldeAInsertar = hprima(e.getNum());
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_5", new Object[]{baldeAInsertar} )); //$NON-NLS-1$ //$NON-NLS-2$
				
			}
			
			//Si no hay más lugares en el balde lógico se le crean más ranuras.
			if ( ! this.baldeConRanurasVirgenes(baldeAInsertar) ){
				//Creo mas ranuras y lo inserto
				for ( int i = 0 ; i < ranurasSecundarias ; i++){
					espacioDeAlmacenamiento.elementAt(baldeAInsertar).add(new Celda());
				}
			}
			
			//Se inserta en el balde.
			int posicionAInsertar = this.posicionLibreBalde(baldeAInsertar);
			espacioDeAlmacenamiento.elementAt(baldeAInsertar).elementAt(posicionAInsertar).setElementoContenido(e);
			espacioDeAlmacenamiento.elementAt(baldeAInsertar).elementAt(posicionAInsertar).setEstado(Celda.OCUPADO);
			
			//Se incrementa la cantidad de elementos.
			cantidadRegistros++;
			//Se asigna el balde final.
			baldeFinal= baldeAInsertar;
			

		}else{
		//Se crea un balde mellizo.
			
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_6", new Object[]{df.format(getRhoMasUnElemento()), df.format(getRhoDeDisenio())} )); //$NON-NLS-1$ //$NON-NLS-2$
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_7")); //$NON-NLS-1$ //$NON-NLS-2$
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_8")); //$NON-NLS-1$ //$NON-NLS-2$
			
			
			// Se crea un balde mellizo al correspondiente balde según su orden lineal.
			espacioDeAlmacenamiento.add(new Vector<Celda>());
			for ( int i = 0 ; i < this.ranuras ; i++){
				espacioDeAlmacenamiento.lastElement().add(new Celda());
			}
		
			//Se incrementa la cantidad de baldes.
			cantidadDeBaldesActuales++;
			
			//Se realeatorizan los dos baldes mellizos según h'(x).
			//Obtengo todos los elementos de los baldes y los reubico.
			Vector<Elemento> elementosAReubicar = new Vector<Elemento>();
			for ( int i = 0 ; i < espacioDeAlmacenamiento.elementAt(cantidadDeBaldesActuales - baldes - 1).size() ; i++ ){
				if ( espacioDeAlmacenamiento.elementAt(cantidadDeBaldesActuales - baldes - 1).elementAt(i).getEstado() == Celda.OCUPADO ){
					elementosAReubicar.add(espacioDeAlmacenamiento.elementAt(cantidadDeBaldesActuales - baldes - 1).elementAt(i).getElementoContenido());
					cantidadRegistros--;
				}
			}
			espacioDeAlmacenamiento.elementAt(cantidadDeBaldesActuales - baldes - 1).clear();
			for ( int i = 0 ; i < this.ranuras ; i++){
				espacioDeAlmacenamiento.elementAt(cantidadDeBaldesActuales - baldes -1).add(new Celda());
			}
			
			//Se corre la frontera.
			frontera++;
			//Explicación
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_9", new Object[]{frontera} )); //$NON-NLS-1$ //$NON-NLS-2$
			
			
			for ( int i = 0 ; i < elementosAReubicar.size() ; i++ ){
				this.insertar(elementosAReubicar.elementAt(i));
			}
			
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_10", new Object[]{e.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$
			
			//Se inserta el elemento.
			this.insertar(e);
			
			
			//Tengo que actualizar la frontera y los h(x) y h'(x) en caso de que la frontera llegue a su limite.
			if ( frontera == baldes ){
				//Explicación
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_11")); //$NON-NLS-1$ //$NON-NLS-2$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_12")); //$NON-NLS-1$ //$NON-NLS-2$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_13")); //$NON-NLS-1$ //$NON-NLS-2$
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_14", new Object[]{MHprima*2} )); //$NON-NLS-1$ //$NON-NLS-2$
				
				MH = MHprima;
				MHprima *= 2;
				frontera = 0; 
				baldes = cantidadDeBaldesActuales;
			}
		}
		}
		//Agregamos la captura.
		agregarCaptura();
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_ELEMENTO_INSERTADO", new Object[]{e.getNum(), baldeFinal} )); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void eliminar(Elemento e) {
			
		Integer aEliminar = e.get();

		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_ELEMENTO_A_INSERTAR", new Object[]{aEliminar} )); //$NON-NLS-1$ //$NON-NLS-2$
				
		//Se busca el balde.
		int baldeABuscar = h(aEliminar);
		
		//Explicación
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo,Messages.getString("HASH_ABIERTO_EXPLICACION_15", new Object[]{e.getNum(), baldeABuscar} )); //$NON-NLS-1$ //$NON-NLS-2$
		if ( ! separadoSolo )
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_16")); //$NON-NLS-1$ //$NON-NLS-2$
		
		//Si el balde encontrado < frontera entonces el elemento está en un balde particionado.
		if ( baldeABuscar < frontera ){
			baldeABuscar = hprima(aEliminar);
			//Explicación
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo,Messages.getString("HASH_ABIERTO_EXPLICACION_17", new Object[]{e.getNum(), baldeABuscar} )); //$NON-NLS-1$ //$NON-NLS-2$
			
		}
		//Referencia al elemento que se va a devolver.
		int posicionEncontrada = posicionElementoEnBalde(baldeABuscar,aEliminar);
		
		//Si lo encontre seteo como borrado y me fijo si tengo que hacer reasignaciones.
		if ( posicionEncontrada != -1 ){
			//Seteo que se borro
			espacioDeAlmacenamiento.elementAt(baldeABuscar).elementAt(posicionEncontrada).setEstado(Celda.BORRADO);
			cantidadRegistros--;
			
			//Explicación
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo,Messages.getString("HASH_ABIERTO_EXPLICACION_18")); //$NON-NLS-1$ //$NON-NLS-2$
	
			//Variable Auxiliar
			int borrados = 0;
			
			//Tengo que fijarme si el problema esta en las ranuras secundarias.
				
			int parado = 0;
			for ( int actual = ranuras ; actual < espacioDeAlmacenamiento.elementAt(baldeABuscar).size() ; actual ++){
				//Si encuentro celdas borradas o virgenes aumento el contador de borrados.
				if (espacioDeAlmacenamiento.elementAt(baldeABuscar).elementAt(actual).getEstado() == Celda.BORRADO || espacioDeAlmacenamiento.elementAt(baldeABuscar).elementAt(actual).getEstado() == Celda.VIRGEN ){
					borrados++;
				}
				//Aumento donde estoy parado dentro de una ranura secundaria.
				parado++;
				//Llegué al limite de la ranura secundaria.
				if ( parado == ranurasSecundarias){
					//Vuelvo al principio.
					parado = 0;
					//Si estan todos vacios los debo eliminar.
					if ( borrados == ranurasSecundarias){
							
						for ( int i = 0 ; i < ranurasSecundarias ; i++){
							espacioDeAlmacenamiento.elementAt(baldeABuscar).removeElementAt(actual);
							actual--;
						}	
					}
					//Reseteo el contador de borrados.
					borrados = 0;
				}
			}	
		}
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_ELEMENTO_ELIMINADO", new Object[]{aEliminar} )); //$NON-NLS-1$ //$NON-NLS-2$
		//Se Agrega la captura
		agregarCaptura();
	}

	@Override
	public boolean buscar(Elemento e) {
		
		//Se busca el balde.
		int baldeABuscar = h(e.getNum());
		
		//Si el balde encontrado < frontera entonces el elemento en un balde particionado.
		if ( baldeABuscar < frontera ){
			baldeABuscar = hprima(e.getNum());
		}
		//Si se encontró se devuelve verdadero, sino falso.
		if ( posicionElementoEnBalde (baldeABuscar,e.getNum() ) != -1 ){
			return true;
		}
		return false;
	}
	
	private int h(int x){
		int hTemporal = x % MH;
		if ( hTemporal < 0 ){
			return MH + hTemporal;
		}
		return hTemporal;
	}
	
	private int hprima(int x){
		int hTemporal = x % MHprima;
		if ( hTemporal < 0 )
			return MH + hTemporal;
		return hTemporal;
	}
	
	private boolean baldeConRanurasVirgenes(Integer balde){
		boolean resultado = false;
		for ( int i = 0; i < espacioDeAlmacenamiento.elementAt(balde).size() && !resultado ; i++){
			if ( espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getEstado() == Celda.VIRGEN  || espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getEstado() == Celda.BORRADO){
				resultado = true;
			}
		}
		return resultado;
	}
	
	private int posicionLibreBalde(Integer balde){
		int resultado = -1;
		for ( int i = 0; i <espacioDeAlmacenamiento.elementAt(balde).size() && resultado == -1; i++){
			if ( espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getEstado() == Celda.VIRGEN || espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getEstado() == Celda.BORRADO){
				resultado = i;
			}
		}
		return resultado;
	}
	
	private int posicionElementoEnBalde(Integer balde,Integer clave){
		int resultado = -1;
		for ( int i = 0; i < espacioDeAlmacenamiento.elementAt(balde).size() && resultado == -1; i++){
			if ( espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getEstado() == Celda.VIRGEN ){
				return resultado;
			}else{	
				if ( espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getElementoContenido().get().equals(clave) && espacioDeAlmacenamiento.elementAt(balde).elementAt(i).getEstado() == Celda.OCUPADO ){
					resultado = i;
				}
			}
		}
		return resultado;
	}
	
	public String toGraph(){
		String salida = new String();
		salida += "base [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n"; //$NON-NLS-1$
		for (int j = ranuras - 1 ; j >= 0 ; j--){
			salida +="<TR> \n"; //$NON-NLS-1$
			for (int i = 0 ; i < cantidadDeBaldesActuales ; i++){
				if ( espacioDeAlmacenamiento.elementAt(i).elementAt(j).getEstado() == Celda.OCUPADO )
					salida += "<TD PORT=\"" + i+j + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"> " + espacioDeAlmacenamiento.elementAt(i).elementAt(j).toString() + " </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				else
				if (  espacioDeAlmacenamiento.elementAt(i).elementAt(j).getEstado() == Celda.BORRADO )
					salida += "<TD PORT=\"" + i+j + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"> " + " </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				else
				if (  espacioDeAlmacenamiento.elementAt(i).elementAt(j).getEstado() == Celda.VIRGEN )
					salida += "<TD PORT=\"" + i+j + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"> " + " </TD> \n";	 //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		salida +="</TR> \n"; //$NON-NLS-1$
		}
		salida += "<TR> \n"; //$NON-NLS-1$
		for (int i = 0 ; i < cantidadDeBaldesActuales ; i++){
			salida +=  "<TD ALIGN=\"TEXT\" BGCOLOR=\"#FDFF69\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + i + " </B> </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		salida += "\n </TR>"; //$NON-NLS-1$
		salida += "<TR> \n"; //$NON-NLS-1$
		if (frontera > 0)
			salida +=  "<TD COLSPAN=\""+frontera+"\" ALIGN=\"TEXT\" BGCOLOR=\"#FE9A2E\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + "h'(x)" + " </B> </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		salida +=  "<TD COLSPAN=\""+ (baldes-frontera)+"\" ALIGN=\"TEXT\" BGCOLOR=\"#FE9A2E\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + "h(x)" + " </B> </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		if (cantidadDeBaldesActuales - baldes > 0)
			salida +=  "<TD COLSPAN=\""+ (cantidadDeBaldesActuales - baldes)+"\" ALIGN=\"TEXT\" BGCOLOR=\"#FE9A2E\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + "h'(x)" + " </B> </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		salida += "\n </TR>"; //$NON-NLS-1$
		salida += "</TABLE>>, shape=none] \n"; //$NON-NLS-1$
		
		for ( int i = 0 ; i < cantidadDeBaldesActuales; i++ ){
			Vector<Celda> celdasADibujar = new Vector<Celda>();
			for ( int iterador = espacioDeAlmacenamiento.elementAt(i).size() - 1 ; iterador >= ranuras ; iterador-- ){
					celdasADibujar.add(espacioDeAlmacenamiento.elementAt(i).elementAt(iterador));
			}
			
			int actual = 0;
			int cantidad = celdasADibujar.size() / ranurasSecundarias;
			
			for ( int j = 0 ; j < celdasADibujar.size() ; j++ ){
				if ( actual == 0 )
					salida += "flotante" + i + cantidad + " [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				
				salida +="<TR> \n"; //$NON-NLS-1$

				if ( celdasADibujar.elementAt(j).getEstado() == Celda.OCUPADO )
					salida += "<TD PORT=\"" + i + (ranurasSecundarias - actual) +  "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"> " + celdasADibujar.elementAt(j).toString() + " </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				else
				if ( celdasADibujar.elementAt(j).getEstado() == Celda.BORRADO )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"> " + " </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				else
				if ( celdasADibujar.elementAt(j).getEstado() == Celda.VIRGEN )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"> " + " </TD> \n";	 //$NON-NLS-1$ //$NON-NLS-2$

				salida +="</TR> \n"; //$NON-NLS-1$
				
				actual++;
				
				if ( actual == ranurasSecundarias ){
					actual=0;
					salida += "</TABLE>>, shape=none] \n"; //$NON-NLS-1$
					
					if ( cantidad == 1 )
						salida += "base:" + i + (ranuras - 1) + ":n -> flotante" + i + cantidad + ":" + i + cantidad +":s \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					else
						salida += "flotante" + i + (cantidad - 1) + ":"+ i + ranurasSecundarias + ":n -> flotante" + i + cantidad + ":" + i + 1 +":s \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
					cantidad--;
				}				
			}	
		}
		return salida;
	}

	@Override
	public String getInfo() {
		String s;
		if ( ! separadoSolo )
			s ="*" + Messages.getString("HASH_ABIERTO_INFO_TITULO_SCL") + "\n\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		else
			s ="*" + Messages.getString("HASH_ABIERTO_INFO_TITULO_SEP") + "\n\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("HASH_ABIERTO_INFO_CANTIDAD_BALDES") + ": " + baldes + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("HASH_ABIERTO_INFO_CANTIDAD_RANURAS") + ": " + ranuras + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		s+=" -h(x) = x mod "+ MH + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		if ( ! separadoSolo )
			s+=" -h'(x) = x mod "+ MHprima + "\n"; //$NON-NLS-1$ //$NON-NLS-2$
		
		s+="+" + Messages.getString("HASH_ABIERTO_INFO_CAPACIDAD") + ": " + cantidadDeBaldesActuales * ranuras + " " + "elementos" + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		s+="+" + Messages.getString("HASH_ABIERTO_INFO_CANTIDAD_ELEMENTOS") + ": " + cantidadRegistros + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		
		if (! separadoSolo){
			s+="+" + Messages.getString("HASH_ABIERTO_INFO_FRONTERA") + ": " + frontera + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			s+="+" + Messages.getString("HASH_ABIERTO_INFO_RHO_DISENIO") + ":\n     " + Messages.getString("HASH_CERRADO_INFO_RHO") + "=" + df.format(getRhoDeDisenio()) + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			s+="+" + Messages.getString("HASH_ABIERTO_INFO_RHO_TEMPORAL") + ":\n     " + Messages.getString("HASH_CERRADO_INFO_RHO") + "=" + df.format(getRho()) + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
			s+="+" + Messages.getString("HASH_ABIERTO_INFO_RHO_TEMPORAL_MAS_UNO") + ":\n     "+ Messages.getString("HASH_CERRADO_INFO_RHO") + "=" + df.format(getRhoMasUnElemento()) + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
		}
		return s;
	}
	
	public String getTipo(){
		if ( separadoSolo )
			return Messages.getString("HASH_ABIERTO_SEP_NOMBRE"); //$NON-NLS-1$
		return Messages.getString("HASH_ABIERTO_SCL_NOMBRE"); //$NON-NLS-1$
	}


	@Override
	public void buscarConInfo(Elemento e) {

		//Se busca el balde.
		int baldeABuscar = h(e.getNum());
		
		//Explicación
		ConsolaManager.getInstance().escribir(""); //$NON-NLS-1$
		ConsolaManager.getInstance().escribir(Messages.getString("HASH_ABIERTO_BUSQUEDA_ELEMENTO", new Object[]{e, tipo} )); //$NON-NLS-1$
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_21", new Object[]{e.getNum(), baldeABuscar} )); //$NON-NLS-1$ //$NON-NLS-2$
				
		//Si el balde encontrado < frontera entonces el elemento cae en un balde particionado.
		if ( baldeABuscar < frontera ){
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_22", new Object[]{e.getNum(), baldeABuscar, frontera} )); //$NON-NLS-1$ //$NON-NLS-2$
			baldeABuscar = hprima(e.getNum());
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_EXPLICACION_23", new Object[]{e.getNum(), baldeABuscar} )); //$NON-NLS-1$ //$NON-NLS-2$
		}
		//Si se encontró se devuelve verdadero, sino falso.
		if ( posicionElementoEnBalde (baldeABuscar,e.getNum() ) != -1 ){
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_ABIERTO_NOMBRE") + tipo, Messages.getString("HASH_ABIERTO_ELEMENTO_PERTENECE", new Object[]{e.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$
			ConsolaManager.getInstance().escribir(Messages.getString("HASH_ABIERTO_ELEMENTO_ENCONTRADO", new Object[]{e} )); //$NON-NLS-1$
		}else
			ConsolaManager.getInstance().escribir(Messages.getString("HASH_ABIERTO_ELEMENTO_NO_ENCONTRADO", new Object[]{e} )); //$NON-NLS-1$ 
	}
}
