package common;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class FiltroArchivoEstructura extends FileFilter {

	private static final String EXTENSION_ESTRUCTURA = "est"; //$NON-NLS-1$
	//private static final String EXTENSION_ACCESO_DRIECTO = "lnk";
	
	private final String[] okFileExtensions = new String[] {EXTENSION_ESTRUCTURA};

	@Override
	public boolean accept(File file) {
		// TODO Auto-generated method stub
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
		return Messages.getString("FILTRO_ARCHIVO_ESTRUCTURA") + " (*.est)"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
