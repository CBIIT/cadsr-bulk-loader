package gov.nih.nci.ncicb.cadsr.qa;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;


public class BL_83_TestCase extends AbstractQATestKase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/qa/83_1.xml",
												"/gov/nih/nci/ncicb/cadsr/qa/83_2.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/qa/83.xls";
	
	public BL_83_TestCase(String name) {
		super(name, XML_IP_FILES, dataURL);
	}

	public void testCtx() {
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		assertNotNull(results);
		assertTrue(results.length == 2);
		for (BulkLoadProcessResult result: results) {
			TransformerResult transformResult = result.getTransformResult();
			assertNotNull(transformResult);
			String fileName = transformResult.getInputParams().getInputFile().getName();
			
			if (fileName.equals("83_1.xml")) {
				assertFalse(result.isSuccessful());
				LoadResult loadResult = result.getLoadResult();
				assertNotNull(loadResult);
				ValidationResult validationResult = loadResult.getValidationResult();
				assertNotNull(validationResult);
				assertNotNull(validationResult.getException());
				assertTrue(validationResult.getException().getMessage().equalsIgnoreCase("gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException: Context  could not be found."));
			}
			else if (fileName.equals("83_2.xml")) {
				assertTrue(result.isSuccessful());
				assertNotNull(transformResult.getValidationResult());
				assertFalse(transformResult.getValidationResult().hasErrors());
			}
		}
	}
}
