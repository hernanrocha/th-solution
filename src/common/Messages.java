package common;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "common.messages"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	//private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("en", "US"));

	private Messages() {
		
	}

	public static void setLanguage(String lang){
		switch (lang) {
		case "es_AR": //$NON-NLS-1$
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("es", "AR")); //$NON-NLS-1$ //$NON-NLS-2$
			break;
		case "en_US": //$NON-NLS-1$
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("en", "US")); //$NON-NLS-1$ //$NON-NLS-2$
			break;
		default:
			ResourceBundle.getBundle(BUNDLE_NAME);
			break;
		}
	}
	
	public static String getString(String key) {
		try {
			//Locale lugar = new Locale("es", "AR");
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static String getString(String key, Object[] args){
		try {
			// Obtener cadena
			String res = RESOURCE_BUNDLE.getString(key);
			
//			String sentence = "Define, Measure, Analyze, Design and Verify";
//
//		    String str = sentence.replace("ands", "");
//		    System.out.println(str);
//		    String[][] array = {{"1.1", "1.2"}, {"2.1", "2.2"}, {"3.1", "3.2"}};
//		    System.out.println(array.length);
//		    System.out.println(array[1].length);
			
			// Realizar reemplazo en argumentos
			for(int i = 0; i < args.length; i++){
				//Object[] arg = args[i];
				
				//if (arg.length == 2){
				//	res = res.replace("{" + arg[0] + "}", arg[1].toString());
				//}else if (arg.length == 1){
				//	res = res.replace("{" + i + "}", arg[0].toString());
				//}
				
				res = res.replace("{" + i + "}", args[i].toString()); //$NON-NLS-1$ //$NON-NLS-2$
			}
			
			return res;
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
		

	}
	
}
