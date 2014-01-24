package hash.cerrado;

import java.util.Vector;

import hash.Celda;
import hash.HashAbs;
import hash.cerrado.tecnicas.TecnicaAbs;
import common.Messages;
import common.estructura.Elemento;
import common.swing.ConsolaManager;


public class HashCerrado extends HashAbs {

	private static final long serialVersionUID = 1L;

	protected Celda[][] espacioDeAlmacenamiento;
	private TecnicaAbs tecnica;
	
	public HashCerrado(int baldes,int ranuras,TecnicaAbs tecnica){
		this.setBaldes(baldes);
		this.setRanuras(ranuras);
		this.espacioDeAlmacenamiento = new Celda[baldes][ranuras];
		for ( int j = 0 ; j < ranuras ; j++ )
			for ( int i = 0 ; i < baldes; i++ )
				espacioDeAlmacenamiento[i][j] = new Celda();
		this.tecnica = tecnica;
		
		ConsolaManager.getInstance().escribir(Messages.getString("HASH_CERRADO_CREANDO_ESTRUCTURA", new Object[]{tecnica.getNombre(), tecnica.getCorto()} )); //$NON-NLS-1$
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
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_1", new Object[]{aInsertar.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				
		//Se debe asegurar que no se sobrecargue la estructura.
		if ( this.getRho() < 1.0 ){
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_HAY_ESPACIO")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			
		//Se obtiene el balde y la posición del mismo a donde se va a insertar.
		int baldeAInsertar = tecnica.h(aInsertar.get());
		int posicionAInsertar = posicionLibreBalde(baldeAInsertar);
		int baldeFinal = baldeAInsertar;
		boolean tecPseudo = false;
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_APLICA_H", new Object[]{aInsertar.getNum(), baldeAInsertar} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				
		if ( posicionAInsertar != -1){
			
			//Si existe una celda con lugar para insertar.
			espacioDeAlmacenamiento[baldeAInsertar][posicionAInsertar].setElementoContenido(aInsertar);
			espacioDeAlmacenamiento[baldeAInsertar][posicionAInsertar].setEstado(Celda.OCUPADO);
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_SE_ENCONTRO_LUGAR")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			
		}else{
			
			//Si NO existe lugar en el balde para insertar el elemento.
			//Voy iterando los intentos para reasignar hasta poder insertar el elemento.
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_NO_HAY_ESPACIO")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_APLICARA_H_PRIMA", new Object[]{aInsertar.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			
			
			boolean insertado = false;
			
			for ( int i = 1; !insertado ; i++){

				//Explicación.
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INTENTA_INSERTAR", new Object[]{aInsertar.getNum(), i} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				
				//Se obtiene el balde y la posición del mismo a donde se va a insertar.
				int baldeAInsertarReasignado = tecnica.reasignar(i,aInsertar.get());
				if ( baldeAInsertarReasignado >= 0){
					
					//Explicación.
					ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_BALDE_REASIGNADO", new Object[]{aInsertar.getNum(), baldeAInsertarReasignado} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					
					int posicionAInsertarReasignado = posicionLibreBalde(baldeAInsertarReasignado);
			
					if ( posicionAInsertarReasignado != -1 ){
						//Si existe una celda en el nuevo balde determinado con lugar para insertar, se inserta.
						espacioDeAlmacenamiento[baldeAInsertarReasignado][posicionAInsertarReasignado].setElementoContenido(aInsertar);
						espacioDeAlmacenamiento[baldeAInsertarReasignado][posicionAInsertarReasignado].setEstado(Celda.OCUPADO);
						insertado = true;
						baldeFinal = baldeAInsertarReasignado;
					}else
						ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_IMPOSIBLE_INSERTAR_EN_BALDE", new Object[]{aInsertar.getNum(), baldeAInsertarReasignado} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}else{
					//Es la tecnica pseudoazar y no encuentra balde
					insertado = true;
					tecPseudo = true;
					ConsolaManager.getInstance().escribirAdv(Messages.getString("HASH_CERRADO_PSEUDO_INFO_INSUFICIENTE")); //$NON-NLS-1$
					ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_IMPOSIBLE_INSERTAR", new Object[]{aInsertar.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}
			}
		}
		//Se pudo insertar por lo tanto se incrementa la cantidad de registros.
		this.cantidadRegistros++;
		if ( ! tecPseudo )
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INSERTADO_EN_BALDE", new Object[]{aInsertar.getNum(), baldeFinal} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		agregarCaptura();
		}else{
			//Se agrega otra captura para no quedar en desorden con las demas.
			agregarCaptura();
			//No se pudo insertar.	
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_2")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}
	}

	
	@Override
	public void eliminar(Elemento e) {
		
		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_3", new Object[]{e.getNum()} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				
		Integer aEliminar = e.get();
		Celda celdaADesocupar = this.buscarCelda(aEliminar);
		
		if ( celdaADesocupar != null){
			celdaADesocupar.setEstado(Celda.BORRADO);
			this.cantidadRegistros--;
			//Explicación.
			ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_4", new Object[]{aEliminar} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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

		if ( posicionABuscar != -1 ){

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
				if ( espacioDeAlmacenamiento[balde][i].getElementoContenido().get().equals(clave) && espacioDeAlmacenamiento[balde][i].getEstado() != Celda.BORRADO ){
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
			salida +="<TR> \n"; //$NON-NLS-1$
			for (int i = 0 ; i < getBaldes() ; i++){
				if ( espacioDeAlmacenamiento[i][j].getEstado() == Celda.OCUPADO )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#E8E8E8\"> " + espacioDeAlmacenamiento[i][j].toString() + " </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				else
				if ( espacioDeAlmacenamiento[i][j].getEstado() == Celda.BORRADO )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#FF3232\"> " + " </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
				else
				if ( espacioDeAlmacenamiento[i][j].getEstado() == Celda.VIRGEN )
					salida += "<TD ALIGN=\"TEXT\" HEIGHT=\"40\" WIDTH=\"40\" BGCOLOR=\"#C0FBF9\"> " + " </TD> \n";	 //$NON-NLS-1$ //$NON-NLS-2$
			}
		salida +="</TR> \n"; //$NON-NLS-1$
		}
		salida += "<TR> \n"; //$NON-NLS-1$
		for (int i = 0 ; i < getBaldes() ; i++){
			salida +=  "<TD ALIGN=\"TEXT\" BGCOLOR=\"#FDFF69\" HEIGHT=\"20\" WIDTH=\"40\"> <B> " + i + " </B> </TD> \n"; //$NON-NLS-1$ //$NON-NLS-2$
		}
		salida += "\n </TR>"; //$NON-NLS-1$
		return salida;
	}

	@Override
	public String getInfo() {
		String s ="*" + Messages.getString("HASH_CERRADO_INFO_TITULO") + " \n \n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("HASH_CERRADO_INFO_BALDES") + ": " + baldes + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("HASH_CERRADO_INFO_RANURAS") + ": " + ranuras + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("HASH_CERRADO_INFO_TECNICA") + ": " + tecnica.getNombre() + " ("+tecnica.getCorto()+")\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
		s+=tecnica.getFuncion() + "\n"; //$NON-NLS-1$
		s+=tecnica.getFuncionReasignada() + "\n"; //$NON-NLS-1$
		if (tecnica.getCorto().equals("PS")) //$NON-NLS-1$
			s+=tecnica.getLista() + "\n"; //$NON-NLS-1$
		s+="+" + Messages.getString("HASH_CERRADO_INFO_CAPACIDAD", new Object[]{(baldes * ranuras)} ) +".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="+" + Messages.getString("HASH_CERRADO_INFO_CANTIDAD_ELEMENTOS") + ": " + cantidadRegistros + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		s+="+" + Messages.getString("HASH_CERRADO_INFO_RHO_TEMPORAL") + ":\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		s+="     " + Messages.getString("HASH_CERRADO_INFO_RHO") + " = " + getRho() + ".\n"; //$NON-NLS-1$ //$NON-NLS-2$
		return s;
	}


	@Override
	public void buscarConInfo(Elemento e) {
		Integer aBuscar = e.getNum();
		
		//Explicación
		ConsolaManager.getInstance().escribir(""); //$NON-NLS-1$
		ConsolaManager.getInstance().escribir(Messages.getString("HASH_CERRADO_BUSQUEDA", new Object[]{e, tecnica.getNombre(), tecnica.getCorto()} )); //$NON-NLS-1$
		Celda celdaABuscar = buscarCeldaConInfo(aBuscar);
		
		if ( celdaABuscar != null ){
			//Explicación
			ConsolaManager.getInstance().escribir(Messages.getString("HASH_CERRADO_ELEMENTO_ENCONTRADO", new Object[]{e} )); //$NON-NLS-1$
		}else{
			//Explicación
			ConsolaManager.getInstance().escribir(Messages.getString("HASH_CERRADO_ELEMENTO_NO_ENCONTRADO", new Object[]{e} )); //$NON-NLS-1$
		}
	}
	
	private Celda buscarCeldaConInfo(Integer aBuscar){

		//Se obtiene el balde  y se busca si el elemento se encuentra dentro de él.		
		int baldeABuscar = tecnica.h(aBuscar);
		int posicionABuscar = posicionElementoEnBalde(baldeABuscar,aBuscar);
		//Referencia al elemento que se va a devolver.
		Celda celdaSalida = null;

		//Explicación.
		ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_5", new Object[]{aBuscar, baldeABuscar} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

			if ( posicionABuscar != -1){

				//Si dentro del balde se encontró al elemento y se conoce su posicón, se devuelve el elemento.
				celdaSalida = espacioDeAlmacenamiento[baldeABuscar][posicionABuscar];
			}else{

				if ( baldeConRanurasVirgenes(baldeABuscar) ){
					//Explicación
					ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_6")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
				}else{
					
				//Si NO se encuentra el elemento en el balde, se debe buscar en otro balde.

				//Explicación.
				ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_7")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

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
						ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_8", new Object[]{aBuscar, baldeABuscarReasignado, i} )); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

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
									ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_9")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$

									//Si se encuentra una ranura virgén se finaliza la búsqueda.
									fin = true;
								}
							}
						}else{
							//Explicación
							ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_INFORMACION_10")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
							fin = true;
						}

					}else{
						//Es la tecnica pseudoazar y no tiene más información para seguir buscando.
						fin = true;
						encontrado = true;
						ConsolaManager.getInstance().escribirAdv(Messages.getString("HASH_CERRADO_INFORMACION_11")); //$NON-NLS-1$
						ConsolaManager.getInstance().escribirInfo(Messages.getString("HASH_CERRADO_NOMBRE") + "("+ tecnica.getCorto() +")", Messages.getString("HASH_CERRADO_BUSQUEDA_FINALIZADA")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
					}
				}
			}
		}
		//Se devuelve el elemento encontrado o null en caso de que no exista.
		return celdaSalida;

	}

}
