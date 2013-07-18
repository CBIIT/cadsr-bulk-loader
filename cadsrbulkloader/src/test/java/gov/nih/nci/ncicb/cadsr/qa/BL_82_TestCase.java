/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.qa;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationLineItemResult;


public class BL_82_TestCase extends AbstractQATestKase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/qa/82_1.xml",
												"/gov/nih/nci/ncicb/cadsr/qa/82_2.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/qa/82.xls";
	
	public BL_82_TestCase(String name) {
		super(name, XML_IP_FILES, dataURL);
	}

	public void testCSI() {
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		assertNotNull(results);
		assertTrue(results.length == 2);
		for (BulkLoadProcessResult result: results) {
			TransformerResult transformResult = result.getTransformResult();
			assertNotNull(transformResult);
			String fileName = transformResult.getInputParams().getInputFile().getName();
			
			if (fileName.equals("82_1.xml")) {
				assertFalse(result.isSuccessful());
				assertNotNull(transformResult.getValidationResult());
				assertTrue(transformResult.getValidationResult().hasErrors());
				TransformerValidationLineItemResult itemResult = transformResult.getValidationResult().getLineItemResults().get(0);
				assertTrue(itemResult.getStatuses().get(0).getMessage().equalsIgnoreCase("Classification Scheme Item cannot be blank"));
			}
			else if (fileName.equals("82_2.xml")) {
				assertTrue(result.isSuccessful());
				assertNotNull(transformResult.getValidationResult());
				assertFalse(transformResult.getValidationResult().hasErrors());
			}
		}
	}
}
