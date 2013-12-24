package common.swing;

//import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

public class InfoManager {

	private static JTextPane display;
	//private static JScrollPane scr;
	private static InfoManager instancia;
	
	private InfoManager (){}
	
	private  InfoManager( JTextPane dis ){
		InfoManager.setDisplay(dis);
	}
	
	public static InfoManager getInstance(){
		if ( instancia == null ){
			instancia = new InfoManager();
		}
		return instancia;
	}
	
	public static InfoManager getInstance(JTextPane con){
		if ( instancia == null ){
			instancia = new InfoManager(con);
		}
		return instancia;
	}

	public static JTextPane getDisplay() {
		return display;
	}

	public static void setDisplay(JTextPane display) {
		InfoManager.display = display;
	}
	
	public  void escribir (String s){
		// Con esto se escribe en la consola.
		try {
			  if ( getDisplay() != null )
				  getDisplay().setText("");
				  getDisplay().getDocument().insertString(getDisplay().getDocument().getLength(),s+"\n", null);
			  //Aca iria la scroll.
		   } catch(BadLocationException exc) {}
	}
	
	
}
