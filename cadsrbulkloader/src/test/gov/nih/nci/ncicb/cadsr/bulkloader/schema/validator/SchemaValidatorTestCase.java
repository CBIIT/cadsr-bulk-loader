package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import gov.nih.nci.ncicb.cadsr.MainTestCase;

import java.io.File;
import java.net.URL;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 25, 2008
 * @since 
 */

public class SchemaValidatorTestCase extends MainTestCase {

	private final String validFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/valid.xml";
	private final String invalidFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/invalid.xml";
	
	private SchemaValidator validator;
	private ClassLoader classLoader;
	
	public void setUp() {
		validator = new SchemaValidatorImpl();
		classLoader = SchemaValidatorTestCase.class.getClassLoader();
	}
	
	public void testValidFile() {
		try {
			File sampleXMLFile = getValidXMLFile();
			validator.validate(sampleXMLFile);
		} catch (SchemaValidationException e) {
			e.printStackTrace();
			fail("Validation of sample XML file failed!");
		}
	}
	
	public void testInvalidFile() {
		Exception exception = null;
		try {
			File sampleXMLFile = getInvalidXMLFile();
			validator.validate(sampleXMLFile);
		} catch (SchemaValidationException e) {
			exception = e;
		}
		
		assertNotNull("Invalid file validation passed!",exception);
	}
	
	private File getValidXMLFile() {
		URL xmlFileURL = classLoader.getResource(validFilePath);
		File xmlFile = new File(xmlFileURL.getPath());
		
		return xmlFile;
	}
	
	private File getInvalidXMLFile() {
		URL xmlFileURL = classLoader.getResource(invalidFilePath);
		File xmlFile = new File(xmlFileURL.getPath());
		
		return xmlFile;
	}

}
