package gov.nih.nci.ncicb.cadsr.bulkloader.ext;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.DefaultProperties;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 20, 2009
 * @since 
 */

public class ExternalDefaultsLoader {

	private static ExternalDefaultsLoader loader = new ExternalDefaultsLoader();
	private DefaultProperties defaultProperties;
	UMLDefaults defaults = UMLDefaults.getInstance();
	
	private ExternalDefaultsLoader() {
		
	}
	
	public static ExternalDefaultsLoader getInstance() {
		return loader;
	}
	
	public void loadDefaults(DefaultProperties _defaultProperties) {
		defaultProperties = _defaultProperties;
		loader.loadDefaults();
	}
	
	private void loadDefaults() {
		String projectName = defaultProperties.getProjectName();
		Float version = Float.valueOf(defaultProperties.getVersion());
		String userName = defaultProperties.getUsername();
		
		try {
			defaults.initParams(projectName, version, userName);
			defaults.initWithDB();
		} catch (PersisterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loader = new NoActionExternalLoader();
	}
	
	private class NoActionExternalLoader extends ExternalDefaultsLoader {
		
		private void loadDefaults() {
			
		}
	}
}
