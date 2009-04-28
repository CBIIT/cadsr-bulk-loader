package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.Transformer;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerStatus;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationLineItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.io.File;
import java.util.List;

import junit.framework.TestCase;

public class ExcelTransformerTestCase extends TestCase {

	public void testExcelTransformer() {
		Transformer transformer = SpringBeansUtil.getExcelTransformer();
		TransformerInputParams inputParams = new TransformerInputParams();
		
		inputParams.setInputFile(new File("C:\\Docume~1\\mathura2\\Desktop\\test form1.xml"));
		inputParams.setOutputFile(new File("C:\\Docume~1\\mathura2\\Desktop\\test form1_11179.xml"));
		inputParams.setValidate(true);
		
		TransformerResult result = transformer.transform(inputParams);
		
		TransformerValidationResult validationResult = result.getValidationResult();
		if (validationResult != null) {
			List<TransformerValidationLineItemResult> lineItemResults = validationResult.getLineItemResults();
			for (TransformerValidationLineItemResult lineItemResult: lineItemResults) {
				List<TransformerStatus> statuses = lineItemResult.getStatuses();
				for (TransformerStatus status: statuses) {
					System.out.println(status.getMessage());
				}
			}
		}
		//assertTrue(!result.hasErrors());
	}
}
