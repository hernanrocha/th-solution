package common.swing;
import java.awt.Color;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;


public class ConsolaManager {

	private static JTextPane Consola;
	private static JScrollPane scr;
	private static ConsolaManager instancia ;
	private HashMap<String,SimpleAttributeSet> estilos = new HashMap<String,SimpleAttributeSet>();
	
	private ConsolaManager (){
		
	}
	
	private  ConsolaManager( JTextPane con,JScrollPane scr ){
		ConsolaManager.setConsola(con);
		ConsolaManager.setScr(scr);
		
		//Estilos
		
		//Hash Cerrado
		 SimpleAttributeSet keyWord1 = new SimpleAttributeSet();
	     StyleConstants.setForeground(keyWord1, Color.RED);
	     StyleConstants.setBackground(keyWord1, Color.YELLOW);
	     StyleConstants.setBold(keyWord1, true);
	     estilos.put("HashCerrado",keyWord1);
	     
	     //Hash Abierto
	     SimpleAttributeSet keyWord2 = new SimpleAttributeSet();
	     StyleConstants.setForeground(keyWord2, Color.BLACK);
	     StyleConstants.setBackground(keyWord2, Color.ORANGE);
	     StyleConstants.setBold(keyWord2, true);
	     estilos.put("HashAbierto",keyWord2);
	   
	     //Arbol
	     SimpleAttributeSet keyWord3 = new SimpleAttributeSet();
	     StyleConstants.setForeground(keyWord3, Color.BLUE);
	     StyleConstants.setBackground(keyWord3, Color.CYAN);
	     StyleConstants.setBold(keyWord3, true);
	     estilos.put("Arbol",keyWord3);
		
	}
	
	public static ConsolaManager getInstance(){
		if ( instancia == null ){
			instancia = new ConsolaManager();
		}
		return instancia;
	}
	
	public static ConsolaManager getInstance(JTextPane con,JScrollPane scr){
		if ( instancia == null ){
			instancia = new ConsolaManager(con,scr);
		}
		return instancia;
	}
	
	public  void escribir (String s){
		// Con esto se escribe en la consola.
		try {
			  if ( getConsola() != null )
				  getConsola().getDocument().insertString(getConsola().getDocument().getLength(), ">> "+s+"\n", null);
			  	  getScr().getVerticalScrollBar().setValue(getScr().getVerticalScrollBar().getMaximum());
		   } catch(BadLocationException exc) {}
	}
	
	public void escribirAdv (String s){
		// Con esto se escribe en la consola.
		try {
			final StyleContext cont = StyleContext.getDefaultStyleContext();
	        final javax.swing.text.AttributeSet attr = (javax.swing.text.AttributeSet) cont.addAttribute(cont.getEmptySet(), StyleConstants.Foreground, Color.RED);
			  if ( getConsola() != null )
				  getConsola().getDocument().insertString(getConsola().getDocument().getLength(), ">> "+s+"\n", attr);
			  	  getScr().getVerticalScrollBar().setValue(getScr().getVerticalScrollBar().getMaximum());
		   } catch(BadLocationException exc) {}
	}
	
	public void escribirAdv (String s1,String s2){
		// Con esto se escribe en la consola.
		try {
			
			SimpleAttributeSet keyWord = null;
			
			if ( s1.contains("Cerrado") ){
				keyWord = estilos.get("HashCerrado");
			}else
			if (s1.contains("Abierto") ){
				keyWord = estilos.get("HashAbierto");
			}else
			if (s1.contains("B") )
				keyWord = estilos.get("Arbol");	
	        
	        if ( getConsola() != null )
				  getConsola().getDocument().insertString(getConsola().getDocument().getLength(), "<< "+ s1 +" >> ", keyWord);
					
	        getScr().getVerticalScrollBar().setValue(getScr().getVerticalScrollBar().getMaximum());
		   } catch(BadLocationException exc) {}
	}
	
	public void escribirInfo (String s1,String s2){
		// Con esto se escribe en la consola.
		try { 

			SimpleAttributeSet keyWord = null;
			
			if ( s1.contains("Cerrado") ){
				keyWord = estilos.get("HashCerrado");
			}else
			if (s1.contains("Abierto") ){
				keyWord = estilos.get("HashAbierto");
			}else
			if (s1.contains("B") )
				keyWord = estilos.get("Arbol");	
			
	        if ( getConsola() != null ){
				  getConsola().getDocument().insertString(getConsola().getDocument().getLength(), "<< "+ s1 +" >> ", keyWord);
				  getConsola().getDocument().insertString(getConsola().getDocument().getLength(), " " + s2 + "\n", null);
				  getScr().getVerticalScrollBar().setValue(getScr().getVerticalScrollBar().getMaximum());	
		   }
		} catch(BadLocationException exc) { }
	}
	
	public void borrar(){
		getConsola().setText("");
	}

	public static JTextPane getConsola() {
		return Consola;
	}

	public static void setConsola(JTextPane consola) {
		Consola = consola;
	}

	public static JScrollPane getScr() {
		return scr;
	}

	public static void setScr(JScrollPane scr) {
		ConsolaManager.scr = scr;
	}
}
