package gov.nih.nci.ncicb.cadsr.qa;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;


public class BL_84_TestCase extends AbstractQATestKase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/qa/84_1.xml",
												"/gov/nih/nci/ncicb/cadsr/qa/84_2.xml",
												"/gov/nih/nci/ncicb/cadsr/qa/84_3.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/qa/83.xls";
	
	public BL_84_TestCase(String name) {
		super(name, XML_IP_FILES, dataURL);
	}

	public void testAltName() {
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		assertNotNull(results);
		assertTrue(results.length == 3);
		for (BulkLoadProcessResult result: results) {
			TransformerResult transformResult = result.getTransformResult();
			assertNotNull(transformResult);
			String fileName = transformResult.getInputParams().getInputFile().getName();
			
			if (fileName.equals("84_1.xml")) {
				assertFalse(result.isSuccessful());
				LoadResult loadResult = result.getLoadResult();
				assertNotNull(loadResult);
				ParseResult parseResult = loadResult.getParseResult();
				assertNotNull(parseResult);
				assertFalse(parseResult.isSuccessful());
			}
			else if (fileName.equals("84_2.xml")) {
				assertFalse(result.isSuccessful());
				LoadResult loadResult = result.getLoadResult();
				assertNotNull(loadResult);
				ValidationResult validationResult = loadResult.getValidationResult();
				assertTrue(validationResult.hasErrors());
			}
			else if (fileName.equals("84_3.xml")) {
				assertTrue(result.isSuccessful());
				assertNotNull(transformResult.getValidationResult());
				assertFalse(transformResult.getValidationResult().hasErrors());
			}
		}
	}
}
