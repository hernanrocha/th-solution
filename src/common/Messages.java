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
		case "es_AR":
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("es", "AR"));
			break;
		case "en_US":
			RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME, new Locale("en", "US"));
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
			//return RESOURCE_BUNDLE.
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
