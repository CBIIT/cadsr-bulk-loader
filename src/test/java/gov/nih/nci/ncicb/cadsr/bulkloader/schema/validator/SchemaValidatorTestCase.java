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
		URL xmlFileURL = classLoader.getResource(validFilePath);
		File xmlFile = new File("c:/docume~1/mathura2/desktop/test form1_11179.xml");//new File(xmlFileURL.getPath());
		
		return xmlFile;
	}
	
	private File getInvalidXMLFile() {
		URL xmlFileURL = classLoader.getResource(invalidFilePath);
		File xmlFile = new File(xmlFileURL.getPath());
		
		return xmlFile;
	}

}
