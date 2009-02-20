package gov.nih.nci.ncicb.cadsr.bulkloader;

import java.io.File;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

public class BulkLoaderTestCase extends MainTestCase {
	
	public void testBulkLoader() {
		CaDSRBulkLoader bulkLoader = SpringBeansUtil.getBulkLoader();
		File fileToLoad = getValidFile();
		
		UserSelections.getInstance().setProperty("ignore-vd", new Boolean(false));
		UserPreferences.getInstance().setUsePrivateApi(true);
		
		LoadResult result = bulkLoader.load(fileToLoad);
		assertNotNull(result);
	}
}
