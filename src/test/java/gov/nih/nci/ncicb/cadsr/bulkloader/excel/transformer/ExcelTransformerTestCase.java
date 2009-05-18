package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.Transformer;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

public class ExcelTransformerTestCase extends MainTestCase {

	private static Transformer transformer = SpringBeansUtil.getInstance().getExcelTransformer();
	private static String dataFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/excel/transformer";
	
	public void testValidTransform() {
		String inputFile = dataFilePath+"/valid_form1.xml";
		String outputFile = dataFilePath+"/valid_form1_11179.xml";
		TransformerInputParams inputParams = getParams(inputFile, outputFile);
		TransformerResult result = transformer.transform(inputParams);
		
		assertTrue(!result.hasErrors());
	}
	
	public void testValidationErrors() {
		String inputFile = dataFilePath+"/header_error_form1.xml";
		String outputFile = dataFilePath+"/header_error_form1_11179.xml";
		TransformerInputParams inputParams = getParams(inputFile, outputFile);
		
		TransformerResult result = transformer.transform(inputParams);
		TransformerValidationResult validationResult = result.getValidationResult();
		
		assertNotNull(result);
		assertNotNull(validationResult);
		assertTrue(result.hasErrors());
		assertTrue(validationResult.hasErrors());
	}
	
	public void testUnMarshallErrors() {
		String inputFile = dataFilePath+"/invalid_ques_form1.xml";
		String outputFile = dataFilePath+"/invalid_ques_form1_11179.xml";
		TransformerInputParams inputParams = getParams(inputFile, outputFile);
		
		TransformerResult result = transformer.transform(inputParams);
		TransformerUnMarshallResult unMarshallResult = result.getUnmarshallerResult();
		
		assertNotNull(result);
		assertNotNull(unMarshallResult);
		assertTrue(result.hasErrors());
		assertTrue(unMarshallResult.hasErrors());
	}
	
	
}
