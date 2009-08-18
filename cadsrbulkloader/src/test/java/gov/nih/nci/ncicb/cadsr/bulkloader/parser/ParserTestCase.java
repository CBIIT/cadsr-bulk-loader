package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.TranslatorResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator.SchemaValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.TestCaseHelper;

import java.io.File;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ParserTestCase extends TestCaseHelper {

	private Parser parser;
	
	public ParserTestCase() {
		parser = SpringBeansUtil.getInstance().getParser();
	}
	
	protected boolean ignoreVD() {
		return true;
	}
	
	protected boolean isUsePrivateAPI() {
		return true;
	}
	
	public void testParse() {
		File fileToParse = new File("C:\\Docume~1\\mathura2\\Desktop\\test form1_11179.xml");//getValidFile();
		ParseResult parseResult = parser.parse(fileToParse);
		
		assertNotNull(parseResult);
		
		if (!parseResult.isSuccessful()) {
			SchemaValidationResult schemaValidationResult = parseResult.getSchemaValidationResult();
			if (!schemaValidationResult.isValid()) {
				Exception schemaValidationException = schemaValidationResult.getException();
				if (schemaValidationException != null) {
					schemaValidationException.printStackTrace();
				}
			}
			
			TranslatorResult translatorResult = parseResult.getTranslatorResult();
			if (!translatorResult.isSuccessful()) {
				Exception translatorException = translatorResult.getException();
				if (translatorException != null) {
					translatorException.printStackTrace();
				}
			}
		}
		assertTrue(parseResult.isSuccessful());
		assertNotNull(parseResult.getCaDSRObjects());
	}
}
