package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import gov.nih.nci.ncicb.cadsr.bulkloader.util.TestCaseHelper;

import java.io.File;
import java.net.URL;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 25, 2008
 * @since 
 */

public class SchemaValidatorTestCase extends TestCaseHelper {

	private final String validFilePath = "/gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/valid.xml";
	private final String invalidFilePath = "/gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/invalid.xml";
	
	private SchemaValidator validator;
	
	public void setUp() {
		validator = new SchemaValidatorImpl();
	}
	
	protected boolean ignoreVD() {
		return true;
	}
	
	protected boolean isUsePrivateAPI() {
		return true;
	}
	
	public void testValidFile() {
		File sampleXMLFile = getValidXMLFile();
		SchemaValidationResult validationResult = validator.validate(sampleXMLFile);
		assertTrue(validationResult.isValid());
	}
	
	public void testInvalidFile() {
		File sampleXMLFile = getInvalidXMLFile();
		SchemaValidationResult validationResult = validator.validate(sampleXMLFile);
		assertTrue(validationResult.isValid());
	}
	
	private File getValidXMLFile() {
		File xmlFile = getClasspathFile(validFilePath);
		
		return xmlFile;
	}
	
	private File getInvalidXMLFile() {
		File xmlFile = getClasspathFile(invalidFilePath);
		
		return xmlFile;
	}

}
