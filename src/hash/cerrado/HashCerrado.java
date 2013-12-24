package hash.cerrado;

import java.util.Vector;

import hash.Celda;
import hash.HashAbs;
import hash.cerrado.tecnicas.TecnicaAbs;
import common.estructura.Elemento;
import common.swing.ConsolaManager;


public class HashCerrado extends HashAbs {

	private static final long serialVersionUID = 1L;
	
	public static final int DEFAULT_CANT_BALDES = 7;
	public static final int DEFAULT_CANT_RANURAS = 2;
	
	private Celda[][] espacioDeAlmacenamiento;
	private int baldes;
	private int ranuras;
	private TecnicaAbs tecnica;
	
	public HashCerrado(int baldes,int ranuras,TecnicaAbs tecnica){
		this.setBaldes(baldes);
		this.setRanuras(ranuras);
		this.espacioDeAlmacenamiento = new Celda[baldes][ranuras];
		for ( int j = 0 ; j < ranuras ; j++ )
			for ( int i = 0 ; i < baldes; i++ )
				espacioDeAlmacenamiento[i][j] = new Celda();
		this.tecnica = tecnica;
		
		ConsolaManager.getInstance().escribir("Creando la estructura de Hash Cerrado con técnica: " + tecnica.getNombre() + " ("+tecnica.getCorto()+").");
	}
	
	public int getBaldes() {
		return baldes;
	}

	public void setBaldes(int baldes) {
		this.baldes = baldes;
	}

	public int getRanuras() {
		return ranuras;
	}

	public void setRanuras(int ranuras) {
		this.ranuras = ranuras;
	}
	
	public TecnicaAbs getTecnica(){
		return tecnica;
	}

	@Override
	public void insertar(Elemento aInsertar){
		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se intentará insertar el elemento: ["+aInsertar.getNum()+"].");
				
		//Se debe asegurar que no se sobrecargue la estructura.
		if ( this.getRho() < 1.0 ){
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Hay espacio en la estructura.");
			
		//Se obtiene el balde y la posición del mismo a donde se va a insertar.
		int baldeAInsertar = tecnica.h(aInsertar.get());
		int posicionAInsertar = posicionLibreBalde(baldeAInsertar);
		int baldeFinal = baldeAInsertar;
		boolean tecPseudo = false;
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se aplica h(" + aInsertar.getNum() + ") = " + baldeAInsertar +".");
				
		if ( posicionAInsertar != -1){
			
			//Si existe una celda con lugar para insertar.
			espacioDeAlmacenamiento[baldeAInsertar][posicionAInsertar].setElementoContenido(aInsertar);
			espacioDeAlmacenamiento[baldeAInsertar][posicionAInsertar].setEstado(Celda.OCUPADO);
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se encontró lugar en el balde.");
			
		}else{
			
			//Si NO existe lugar en el balde para insertar el elemento.
			//Voy iterando los intentos para reasignar hasta poder insertar el elemento.
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "No hay espacio en el balde.");
			ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se aplicará h'("+aInsertar.getNum()+").");
			
			
			boolean insertado = false;
			
			for ( int i = 1; !insertado ; i++){

				//Explicación.
				ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se intenta insertar el elemento: [" + aInsertar.getNum() + "] intento : [" + i  + "].");
				
				//Se obtiene el balde y la posición del mismo a donde se va a insertar.
				int baldeAInsertarReasignado = tecnica.reasignar(i,aInsertar.get());
				if ( baldeAInsertarReasignado >= 0){
					
					//Explicación.
					ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Balde Reasignado por h'("+aInsertar.getNum()+") = "+baldeAInsertarReasignado+".");
					
					int posicionAInsertarReasignado = posicionLibreBalde(baldeAInsertarReasignado);
			
					if ( posicionAInsertarReasignado != -1 ){
						//Si existe una celda en el nuevo balde determinado con lugar para insertar, se inserta.
						espacioDeAlmacenamiento[baldeAInsertarReasignado][posicionAInsertarReasignado].setElementoContenido(aInsertar);
						espacioDeAlmacenamiento[baldeAInsertarReasignado][posicionAInsertarReasignado].setEstado(Celda.OCUPADO);
						insertado = true;
						baldeFinal = baldeAInsertarReasignado;
					}else
						ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Imposible insertar el elemento : [" + aInsertar.getNum() + "] en el balde : [" + baldeAInsertarReasignado  + "].");
				}else{
					//Es la tecnica pseudoazar y no encuentra balde
					insertado = true;
					tecPseudo = true;
					ConsolaManager.getInstance().escribirAdv("La lista pseudoazar no provee suficiente información para un nuevo intento.");
					ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Imposible insertar el elemento : [" + aInsertar.getNum() + "].");
				}
			}
		}
		//Se pudo insertar por lo tanto se incrementa la cantidad de registros.
		this.cantidadRegistros++;
		if ( ! tecPseudo )
			ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se insertó el elemento: [" + aInsertar.getNum() + "] en el balde : [" + baldeFinal + "].");
		agregarCaptura();
		}else{
			//Se agrega otra captura para no quedar en desorden con las demas.
			agregarCaptura();
			//No se pudo insertar.	
			ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "No se pudo insertar el elemento en la estructura de Hash Cerrado por haber llegado al límite.");
		}
	}

	
	@Override
	public void eliminar(Elemento e) {
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se intentará eliminar el elemento: ["+e.getNum()+"].");
				
		Integer aEliminar = e.get();
		Celda celdaADesocupar = this.buscarCelda(aEliminar);
		
		if ( celdaADesocupar != null){
			celdaADesocupar.setEstado(Celda.BORRADO);
			this.cantidadRegistros--;
			//Explicación.
			ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se eliminó el elemento: [" + aEliminar + "] dejando su posición como borrada.");
			agregarCaptura();
		}
	}

	@Override
	public boolean buscar(Elemento e) {
		Integer aBuscar = e.get();
		
		Celda celdaABuscar = buscarCelda(aBuscar);
		
		if ( celdaABuscar != null ){
			return true;
		}else{
			return false;
		}
	}

	private Celda buscarCelda(Integer aBuscar){

		//Se obtiene el balde  y se busca si el elemento se encuentra dentro de él.		
		int baldeABuscar = tecnica.h(aBuscar);
		int posicionABuscar = posicionElementoEnBalde(baldeABuscar,aBuscar);
		//Referencia al elemento que se va a devolver.
		Celda celdaSalida = null;

		if ( posicionABuscar != -1){

			//Si dentro del balde se encontró al elemento y se conoce su posicón, se devuelve el elemento.
			celdaSalida = espacioDeAlmacenamiento[baldeABuscar][posicionABuscar];
		}else{

			//Si NO se encuentra el elemento en el balde, se debe buscar en otro balde.
			if ( !baldeConRanurasVirgenes(baldeABuscar) ){

				//Voy iterando los intentos para reasignar hasta poder encontrar el elemento o determinar que no se encuentra.
				boolean encontrado = false;
				boolean fin = false;
				Vector<Integer> recorridos = new Vector<Integer>();
				recorridos.add(baldeABuscar);

				for ( int i = 1; !encontrado && !fin ; i++){

					//Se obtiene el balde.
					int baldeABuscarReasignado = tecnica.reasignar(i,aBuscar);

					if ( baldeABuscarReasignado >= 0 ){

						//Evitamos los ciclos
						if ( ! recorridos.contains(baldeABuscarReasignado) ){

							//Se busca el elemento dentro del balde actual.
							int posicionABuscarReasignado = posicionElementoEnBalde(baldeABuscarReasignado,aBuscar);

							//Lo agregamos al vector de visitados.
							recorridos.add(baldeABuscarReasignado);

							if ( posicionABuscarReasignado != -1 ){

								//Si se encontró el elemento en el balde se lo devuelve.
								celdaSalida = espacioDeAlmacenamiento[baldeABuscarReasignado][posicionABuscarReasignado];
								encontrado = true;
							}else{
								if ( this.baldeConRanurasVirgenes(baldeABuscarReasignado) ){

									//Si se encuentra una ranura virgén se finaliza la búsqueda.
									fin = true;
								}
							}
						}else{
							fin = true;
						}

					}else{
						//Es la tecnica pseudoazar y no tiene más información para seguir buscando.
						fin = true;
						encontrado = true;
					}
				}
			}
		}
		//Se devuelve el elemento encontrado o null en caso de que no exista.
		return celdaSalida;

	}
	
	@Override
	public double getRho() {
		//Rho = # Registros / ( # baldes * # ranuras ).
		return (double) this.cantidadRegistros / ( this.baldes * this.ranuras );
	}


	private int posicionLibreBalde(Integer balde){
		int resultado = -1;
		for ( int i = 0; i < getRanuras() && resultado == -1; i++){
			if ( espacioDeAlmacenamiento[balde][i].getEstado() == Celda.VIRGEN || espacioDeAlmacenamiento[balde][i].getEstado() == Celda.BORRADO ){
				resultado = i;
			}
		}
		return resultado;
	}

	private int posicionElementoEnBalde(Integer balde,Integer clave){
		int resultado = -1;
		for ( int i = 0; i < getRanuras() && resultado == -1; i++){
			if ( espacioDeAlmacenamiento[balde][i].getEstado() == Celda.VIRGEN ){
				return resultado;
			}else{	
				if ( espacioDeAlmacenamiento[balde][i].getElementoContenido().get().equals(clave) ){
					resultado = i;
				}
			}
		}
		return resultado;
	}

	private boolean baldeConRanurasVirgenes(Integer balde){
		boolean resultado = false;
		for ( int i = 0; i < getRanuras() && !resultado ; i++){
			if ( espacioDeAlmacenamiento[balde][i].getEstado() == Celda.VIRGEN ){
				resultado = true;
			}
		}
		return resultado;
	}
	
	public String toGraph(){
		String salida = new String();
		for (int j = getRanuras() - 1 ; j >= 0 ; j--){
			salida +="<TR> \n";
			for (int i = 0 ; i < getBaldes() ; i++){
				if ( espacioDeAlmacenamiento[i][j].getEstado() == Celda.OCUPADO )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"> " + espacioDeAlmacenamiento[i][j].toString() + " </TD> \n";
				else
				if ( espacioDeAlmacenamiento[i][j].getEstado() == Celda.BORRADO )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"> " + " </TD> \n";
				else
				if ( espacioDeAlmacenamiento[i][j].getEstado() == Celda.VIRGEN )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"> " + " </TD> \n";	
			}
		salida +="</TR> \n";
		}
		salida += "<TR> \n";
		for (int i = 0 ; i < getBaldes() ; i++){
			salida +=  "<TD ALIGN=\"TEXT\" BGCOLOR=\"#FDFF69\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + i + " </B> </TD> \n";
		}
		salida += "\n </TR>";
		return salida;
	}

	@Override
	public String getInfo() {
		String s ="*Estructura de Hash Cerrado. \n \n";
		s+="+Cantidad de Baldes: " + baldes + ".\n";
		s+="+Cantidad de Ranuras: " + ranuras + ".\n";
		s+="+Técnica: " + tecnica.getNombre() + " ("+tecnica.getCorto()+")\n";
		s+=tecnica.getFuncion() + "\n";
		s+=tecnica.getFuncionReasignada() + "\n";
		if (tecnica.getCorto().equals("PS"))
			s+=tecnica.getLista() + "\n";
		s+="+Capacidad de la estructura: " + baldes * ranuras + " elementos.\n";
		s+="+Cantidad de elementos: " + cantidadRegistros + ".\n";
		s+="+Factor de carga (Rho ρ) temporal:\n     ρ=" + getRho() + ".\n";
		return s;
	}


	@Override
	public void buscarConInfo(Elemento e) {
		Integer aBuscar = e.getNum();
		
		//Explicación
		ConsolaManager.getInstance().escribir("");
		ConsolaManager.getInstance().escribir("Búsqueda del elemento ["+e+"] en la estructura de Hash Cerrado con técnica: " + tecnica.getNombre() + " ("+tecnica.getCorto()+") .");
		Celda celdaABuscar = buscarCeldaConInfo(aBuscar);
		
		if ( celdaABuscar != null ){
			//Explicación
			ConsolaManager.getInstance().escribir("Se encontró el elemento ["+e+"].");
		}else{
			//Explicación
			ConsolaManager.getInstance().escribir("No se encontró el elemento ["+e+"].");
		}
	}
	
	private Celda buscarCeldaConInfo(Integer aBuscar){

		//Se obtiene el balde  y se busca si el elemento se encuentra dentro de él.		
		int baldeABuscar = tecnica.h(aBuscar);
		int posicionABuscar = posicionElementoEnBalde(baldeABuscar,aBuscar);
		//Referencia al elemento que se va a devolver.
		Celda celdaSalida = null;

		//Explicación.
		ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se aplica h("+aBuscar+") = "+baldeABuscar+" y se busca si el elemento existe en el balde.");

			if ( posicionABuscar != -1){

				//Si dentro del balde se encontró al elemento y se conoce su posicón, se devuelve el elemento.
				celdaSalida = espacioDeAlmacenamiento[baldeABuscar][posicionABuscar];
			}else{

				if ( baldeConRanurasVirgenes(baldeABuscar) ){
					//Explicación
					ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se encontró una ranura vírgen por lo que se finaliza la búsqueda.");
				}else{
					
				//Si NO se encuentra el elemento en el balde, se debe buscar en otro balde.

				//Explicación.
				ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "No se encontró el elemento en el balde encontrado.");

				//Voy iterando los intentos para reasignar hasta poder encontrar el elemento o determinar que no se encuentra.
				boolean encontrado = false;
				boolean fin = false;
				Vector<Integer> recorridos = new Vector<Integer>();
				recorridos.add(baldeABuscar);

				for ( int i = 1; !encontrado && !fin ; i++){

					//Se obtiene el balde.
					int baldeABuscarReasignado = tecnica.reasignar(i,aBuscar);

					if ( baldeABuscarReasignado >= 0 ){

						//Explicación
						ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se aplica h'("+aBuscar+") = "+baldeABuscarReasignado+" con intento ["+i+"] y se busca si el elemento existe en el balde.");

						//Evitamos los ciclos
						if ( ! recorridos.contains(baldeABuscarReasignado) ){

							//Se busca el elemento dentro del balde actual.
							int posicionABuscarReasignado = posicionElementoEnBalde(baldeABuscarReasignado,aBuscar);

							//Lo agregamos al vector de visitados.
							recorridos.add(baldeABuscarReasignado);

							if ( posicionABuscarReasignado != -1 ){

								//Si se encontró el elemento en el balde se lo devuelve.
								celdaSalida = espacioDeAlmacenamiento[baldeABuscarReasignado][posicionABuscarReasignado];
								encontrado = true;
							}else{
								if ( this.baldeConRanurasVirgenes(baldeABuscarReasignado) ){

									//Explicación
									ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se encontró una ranura vírgen por lo que se finaliza la búsqueda.");

									//Si se encuentra una ranura virgén se finaliza la búsqueda.
									fin = true;
								}
							}
						}else{
							//Explicación
							ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se llegó a un ciclo por lo que se finaliza la búsqueda.");
							fin = true;
						}

					}else{
						//Es la tecnica pseudoazar y no tiene más información para seguir buscando.
						fin = true;
						encontrado = true;
						ConsolaManager.getInstance().escribirAdv("La lista pseudoazar no provee suficiente información para un nuevo intento.");
						ConsolaManager.getInstance().escribirInfo("Hash Cerrado " + "("+ tecnica.getCorto() +")", "Se finaliza la búsqueda.");
					}
				}
			}
		}
		//Se devuelve el elemento encontrado o null en caso de que no exista.
		return celdaSalida;

	}

}
