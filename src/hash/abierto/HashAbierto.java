package hash.abierto;

import hash.Celda;
import hash.HashAbs;

import java.text.DecimalFormat;
import java.util.Vector;

import common.estructura.Elemento;
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
	private DecimalFormat df = new DecimalFormat("0.0000");
	
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
			ConsolaManager.getInstance().escribir("Creando la estructura de Hash Abierto Separado" + ".");
			this.tipo="(SEP)";
		}
		else{
			ConsolaManager.getInstance().escribir("Creando la estructura de Hash Abierto Separado Con Crecimiento Lineal" + ".");
			this.tipo="(S.C.L.)";
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
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se intentará insertar el elemento" + ": ["+e.getNum()+"].");
		int baldeFinal = 0;
		
		if ( separadoSolo ){
			//Se busca el balde a insertar el elemento.
			int baldeAInsertar = h(e.getNum());
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se aplica h" + "(" + e.getNum() + ") = " + baldeAInsertar +".");
			
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
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Para insertar sin crear baldes mellizos, el Rho temporal (ρt) contando al elemento a insertar debe ser menor o igual al Rho de Diseño (ρd).");
			
		//Si el factor de carga temporal no sobrepasa al factor de diseño.
		if ( this.getRhoMasUnElemento() <= this.getRhoDeDisenio() ){
			
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "El Rho temporal (ρt) contando el elemento a insertar = "+ df.format(getRhoMasUnElemento()) +" <= "+ df.format(getRhoDeDisenio())+" = Rho de Diseño (ρd).");
			
			
			//Se busca el balde a insertar el elemento.
			int baldeAInsertar = h(e.getNum());
			
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se aplica h(" + e.getNum() + ") = " + baldeAInsertar +".");
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Si el balde encontrado es menor que la frontera el elemento cae en un balde particionado.");
			
			//Si el balde encontrado < frontera entonces el elemento cen un balde particionado.
			if ( baldeAInsertar < frontera ){
				baldeAInsertar = hprima(e.getNum());
				ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "El elemento cayó en un balde particionado, se le aplica h'(x) = "+baldeAInsertar+".");
				
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
			
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "El Rho temporal (ρt) contando el elemento a insertar = "+ df.format(getRhoMasUnElemento()) +" > "+ df.format(getRhoDeDisenio())+" = Rho de Diseño (ρd).");
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se crea un balde mellizo.");
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se realeatorizan los dos baldes mellizos según h'(x).");
			
			
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
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se establece la nueva frontera = "+frontera+".");
			
			
			for ( int i = 0 ; i < elementosAReubicar.size() ; i++ ){
				this.insertar(elementosAReubicar.elementAt(i));
			}
			
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Luego de reubicar los baldes mellizos se vuelve a tratar de insertar el elemento : ["+e.getNum()+"].");
			
			//Se inserta el elemento.
			this.insertar(e);
			
			
			//Tengo que actualizar la frontera y los h(x) y h'(x) en caso de que la frontera llegue a su limite.
			if ( frontera == baldes ){
				//Explicación
				ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se duplicó completamente la estructura.");
				ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "La frontera llegó a su límite por lo que vuelve a 0.");
				ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "h(x) se vuelve h'(x).");
				ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "h'(x) se vuelve x mod "+MHprima*2+".");
				
				MH = MHprima;
				MHprima *= 2;
				frontera = 0; 
				baldes = cantidadDeBaldesActuales;
			}
		}
		}
		//Agregamos la captura.
		agregarCaptura();
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se insertó el elemento: [" + e.getNum() + "] en el balde : [" + baldeFinal + "].");
	}

	@Override
	public void eliminar(Elemento e) {
			
		Integer aEliminar = e.get();

		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se intentará eliminar el elemento: ["+aEliminar+"].");
				
		//Se busca el balde.
		int baldeABuscar = h(aEliminar);
		
		//Explicación
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo,"Se aplica h(" + e.getNum() + ") = " + baldeABuscar+".");
		if ( ! separadoSolo )
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Si el balde encontrado es menor que la frontera el elemento pertenece a un balde particionado.");
		
		//Si el balde encontrado < frontera entonces el elemento está en un balde particionado.
		if ( baldeABuscar < frontera ){
			baldeABuscar = hprima(aEliminar);
			//Explicación
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo,"El elemento pertenece a un balde particionado,se le aplica h'(" + e.getNum() + ") = " + baldeABuscar+".");
			
		}
		//Referencia al elemento que se va a devolver.
		int posicionEncontrada = posicionElementoEnBalde(baldeABuscar,aEliminar);
		
		//Si lo encontre seteo como borrado y me fijo si tengo que hacer reasignaciones.
		if ( posicionEncontrada != -1 ){
			//Seteo que se borro
			espacioDeAlmacenamiento.elementAt(baldeABuscar).elementAt(posicionEncontrada).setEstado(Celda.BORRADO);
			cantidadRegistros--;
			
			//Explicación
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo,"Al borrar el elemento si este pertenecía a un balde lógico y el balde se queda sin elementos, se elimina al balde.");
	
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
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se eliminó el elemento: [" + aEliminar + "] dejando su posición como borrada.");
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
		salida += "base [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n";
		for (int j = ranuras - 1 ; j >= 0 ; j--){
			salida +="<TR> \n";
			for (int i = 0 ; i < cantidadDeBaldesActuales ; i++){
				if ( espacioDeAlmacenamiento.elementAt(i).elementAt(j).getEstado() == Celda.OCUPADO )
					salida += "<TD PORT=\"" + i+j + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"> " + espacioDeAlmacenamiento.elementAt(i).elementAt(j).toString() + " </TD> \n";
				else
				if (  espacioDeAlmacenamiento.elementAt(i).elementAt(j).getEstado() == Celda.BORRADO )
					salida += "<TD PORT=\"" + i+j + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"> " + " </TD> \n";
				else
				if (  espacioDeAlmacenamiento.elementAt(i).elementAt(j).getEstado() == Celda.VIRGEN )
					salida += "<TD PORT=\"" + i+j + "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"> " + " </TD> \n";	
			}
		salida +="</TR> \n";
		}
		salida += "<TR> \n";
		for (int i = 0 ; i < cantidadDeBaldesActuales ; i++){
			salida +=  "<TD ALIGN=\"TEXT\" BGCOLOR=\"#FDFF69\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + i + " </B> </TD> \n";
		}
		salida += "\n </TR>";
		salida += "<TR> \n";
		if (frontera > 0)
			salida +=  "<TD COLSPAN=\""+frontera+"\" ALIGN=\"TEXT\" BGCOLOR=\"#FE9A2E\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + "h'(x)" + " </B> </TD> \n";
		salida +=  "<TD COLSPAN=\""+ (baldes-frontera)+"\" ALIGN=\"TEXT\" BGCOLOR=\"#FE9A2E\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + "h(x)" + " </B> </TD> \n";
		if (cantidadDeBaldesActuales - baldes > 0)
			salida +=  "<TD COLSPAN=\""+ (cantidadDeBaldesActuales - baldes)+"\" ALIGN=\"TEXT\" BGCOLOR=\"#FE9A2E\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + "h'(x)" + " </B> </TD> \n";
		salida += "\n </TR>";
		salida += "</TABLE>>, shape=none] \n";
		
		for ( int i = 0 ; i < cantidadDeBaldesActuales; i++ ){
			Vector<Celda> celdasADibujar = new Vector<Celda>();
			for ( int iterador = espacioDeAlmacenamiento.elementAt(i).size() - 1 ; iterador >= ranuras ; iterador-- ){
					celdasADibujar.add(espacioDeAlmacenamiento.elementAt(i).elementAt(iterador));
			}
			
			int actual = 0;
			int cantidad = celdasADibujar.size() / ranurasSecundarias;
			
			for ( int j = 0 ; j < celdasADibujar.size() ; j++ ){
				if ( actual == 0 )
					salida += "flotante" + i + cantidad + " [ label= <<TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\"> \n";
				
				salida +="<TR> \n";

				if ( celdasADibujar.elementAt(j).getEstado() == Celda.OCUPADO )
					salida += "<TD PORT=\"" + i + (ranurasSecundarias - actual) +  "\" ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"> " + celdasADibujar.elementAt(j).toString() + " </TD> \n";
				else
				if ( celdasADibujar.elementAt(j).getEstado() == Celda.BORRADO )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"> " + " </TD> \n";
				else
				if ( celdasADibujar.elementAt(j).getEstado() == Celda.VIRGEN )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"> " + " </TD> \n";	

				salida +="</TR> \n";
				
				actual++;
				
				if ( actual == ranurasSecundarias ){
					actual=0;
					salida += "</TABLE>>, shape=none] \n";
					
					if ( cantidad == 1 )
						salida += "base:" + i + (ranuras - 1) + ":n -> flotante" + i + cantidad + ":" + i + cantidad +":s \n";
					else
						salida += "flotante" + i + (cantidad - 1) + ":"+ i + ranurasSecundarias + ":n -> flotante" + i + cantidad + ":" + i + 1 +":s \n";
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
			s ="*Estructura de Hash Abierto Separado Con Crecimiento Lineal (S.C.L.). \n\n";
		else
			s ="*Estructura de Hash Abierto Separado (SEP). \n\n";
		s+="+Cantidad de Baldes: " + baldes + ".\n";
		s+="+Cantidad de Ranuras: " + ranuras + ".\n";
		
		s+=" -h(x) = x mod "+ MH + "\n";
		if ( ! separadoSolo )
			s+=" -h'(x) = x mod "+ MHprima + "\n";
		s+="+Capacidad de la estructura primaria: " + cantidadDeBaldesActuales * ranuras + " elementos.\n";
		s+="+Cantidad de elementos: " + cantidadRegistros + ".\n";
		if (! separadoSolo){	
		s+="+Frontera: " + frontera + ".\n";
		s+="+Factor de carga (Rho ρ) de diseño:\n     ρ=" + df.format(getRhoDeDisenio()) + ".\n";
		s+="+Factor de carga (Rho ρ) temporal:\n     ρ=" + df.format(getRho()) + ".\n";
		s+="+Factor de carga (Rho ρ) temporal tomando un elemento más:\n     ρ=" + df.format(getRhoMasUnElemento()) + ".\n";
		}
		return s;
	}
	
	public String getTipo(){
		if ( separadoSolo )
			return "SEP";
		return "S.C.L.";
	}


	@Override
	public void buscarConInfo(Elemento e) {

		//Se busca el balde.
		int baldeABuscar = h(e.getNum());
		
		//Explicación
		ConsolaManager.getInstance().escribir("");
		ConsolaManager.getInstance().escribir("Búsqueda del elemento ["+e+"] en la estructura de Hash Abierto " + tipo + ".");
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se aplica h("+e.getNum()+") = "+baldeABuscar+".");
				
		//Si el balde encontrado < frontera entonces el elemento cae en un balde particionado.
		if ( baldeABuscar < frontera ){
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "El elemento debe pertenecer a un balde particionado (ya que h("+e.getNum()+") = "+baldeABuscar+" < "+frontera+" = frontera)por lo que se aplicará h'(x).");
			baldeABuscar = hprima(e.getNum());
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "Se aplica h'("+e.getNum()+") = "+baldeABuscar+".");
		}
		//Si se encontró se devuelve verdadero, sino falso.
		if ( posicionElementoEnBalde (baldeABuscar,e.getNum() ) != -1 ){
			ConsolaManager.getInstance().escribirInfo("Hash Abierto " + tipo, "El elemento ["+e.getNum()+"] pertenece a este balde.");
			ConsolaManager.getInstance().escribir("Se encontró el elemento ["+e+"].");
		}else
			ConsolaManager.getInstance().escribir("No se encontró el elemento ["+e+"].");
	}
}
