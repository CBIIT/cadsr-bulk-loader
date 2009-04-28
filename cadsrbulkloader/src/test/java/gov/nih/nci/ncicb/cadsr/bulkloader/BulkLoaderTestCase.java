package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationStatus;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.io.File;

public class BulkLoaderTestCase extends MainTestCase {
	
	public void testBulkLoader() {
		CaDSRBulkLoader bulkLoader = SpringBeansUtil.getBulkLoader();
		File fileToLoad = new File("C:\\Docume~1\\mathura2\\Desktop\\11179sample2.xml"); //getValidFile();
		
		UserSelections.getInstance().setProperty("ignore-vd", new Boolean(false));
		UserPreferences.getInstance().setUsePrivateApi(true);
		
		LoaderInput input = new LoaderInput();
		input.setFileToLoad(fileToLoad);
		input.setValidate(true);
		
		LoadResult result = bulkLoader.load(input);
		assertNotNull(result);
		
		Throwable exception = result.getExceptionTrace();
		if (exception!= null) exception.printStackTrace();
		assertNull("Test failed as there was an exception thrown",exception);
		
		ValidationResult validationResult = result.getValidationResult();
		assertNotNull(validationResult);
		
		ValidationStatus validationStatus = validationResult.getValidationStatus();
		assertTrue("Validation did not pass",validationStatus.isSuccessful());
		
		LoadStatus status = result.getLoadStatus();
		assertTrue("Load failed!",status.equals(LoadStatus.SUCCESSFUL));
	}
}
