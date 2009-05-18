package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.CaDSRBulkLoader;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadStatus;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.TranslatorResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationStatus;

import java.io.File;
import java.util.List;

public class BulkLoaderTestCase extends MainTestCase {
	
	protected boolean ignoreVD() {
		return true;
	}
	
	protected boolean isUsePrivateAPI() {
		return true;
	}
	
	public void testBulkLoader() {
		CaDSRBulkLoader bulkLoader = SpringBeansUtil.getInstance().getBulkLoader();
		File fileToLoad = new File("C:\\Docume~1\\mathura2\\Desktop\\test form1_11179.xml"); //getValidFile();
		
		LoaderInput input = new LoaderInput();
		input.setFileToLoad(fileToLoad);
		input.setValidate(true);
		
		LoadProperties loadProperties = getDefaultLoadProperties();
		
		LoadResult result = bulkLoader.load(input, loadProperties);
		assertNotNull(result);
		
		Throwable exception = result.getExceptionTrace();
		if (exception!= null) exception.printStackTrace();
		assertNull("Test failed as there was an exception thrown",exception);
		
		ParseResult parseResult = result.getParseResult();
		assertNotNull(parseResult);
		
		TranslatorResult<CaDSRObjects> translatorResult = parseResult.getTranslatorResult();
		assertNotNull(translatorResult);
		if(!translatorResult.isSuccessful()) {
			translatorResult.getException().printStackTrace();
		}
		ValidationResult validationResult = result.getValidationResult();
		assertNotNull(validationResult);
		
		ValidationStatus validationStatus = validationResult.getValidationStatus();
		assertTrue("Validation did not pass",validationStatus.isSuccessful());
		
		if (validationResult.hasErrors()) {
			List<ValidationItemResult> itemResults = validationResult.getItemResults();
			for (ValidationItemResult itemResult: itemResults) {
				System.out.println(itemResult.getItem().getClass()+": "+itemResult.getMessage());
			}
		}
		LoadStatus status = result.getLoadStatus();
		assertTrue("Load failed!",status.equals(LoadStatus.SUCCESSFUL));
	}
}
