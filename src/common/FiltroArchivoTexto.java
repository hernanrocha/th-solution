package common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivoTexto extends FileFilter {

	//private static final String EXTENSION_ESTRUCTURA = "est";
	private static final String EXTENSION_ARCHIVO_TEXTO = "txt"; //$NON-NLS-1$
	
	private final String[] okFileExtensions = new String[] {EXTENSION_ARCHIVO_TEXTO};

	@Override
	public boolean accept(File file) {
		for (String extension : okFileExtensions){
			if (file.getName().toLowerCase().endsWith(extension)){
				return true;
			}
		}
		if(file.isDirectory()){
			return true;
		}
		return false;
	}

	@Override
	public String getDescription() {
		return Messages.getString("FILTRO_ARCHIVO_TEXTO") + " (*.txt)"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
