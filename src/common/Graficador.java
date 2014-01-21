package common;

import java.util.concurrent.Semaphore;

import common.swing.DialogNuevaEstructura;


public class Graficador extends Thread {
	
	private static final Graficador graficador = new Graficador();	
	private static DialogNuevaEstructura form;
	public Semaphore ready = new Semaphore(0);
	
	public static void setForm(DialogNuevaEstructura form) {
		Graficador.form = form;
	}

	private Graficador(){
		start();
	}

	public void run(){
		waitFormReady();
	}

	private void waitFormReady() {
		while(true){		
			try {
				ready.acquire();
				if(form.chckbxArbolesB.isSelected()){
					form.doConfiguracionArbol();
				}

				if(form.chckbxHashingCerrado.isSelected()){
					form.doConfiguracionHashCerrado();
				}

				if(form.chckbxHashingAbierto.isSelected()){
					form.doConfiguracionHashAbierto();
				}

				form.doConfiguracionFinal();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void configurar() {
		graficador.ready.release();		
	}
	
}
