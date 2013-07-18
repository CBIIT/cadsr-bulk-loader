/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelForm;
import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelQuestion;
import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.unmarshall.ExcelUnMarshaller;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshaller;

import java.util.List;

import junit.framework.TestCase;


public class ExcelTransformerTestCase extends TestCase {

	private static final String dataURL1 = "/gov/nih/nci/ncicb/cadsr/8_29_1.xml";
	private static final String dataURL2 = "/gov/nih/nci/ncicb/cadsr/8_29_3.xml";
	
	public void testMultipleFilesTransformation() {
		TransformerUnMarshaller unmarshaller = new ExcelUnMarshaller();
		TransformerUnMarshallResult result1 = unmarshaller.read(ExcelTransformerTestCase.class.getResourceAsStream(dataURL1));
		TransformerUnMarshallResult result2 = unmarshaller.read(ExcelTransformerTestCase.class.getResourceAsStream(dataURL2));
		
		assertNotNull(result1);
		assertNotNull(result2);
		
		ExcelForm form1 = (ExcelForm)result1.getUnMarshalledObject();
		ExcelForm form2= (ExcelForm)result2.getUnMarshalledObject();
		
		assertNotNull(form1);
		assertNotNull(form2);
		
		List<ExcelQuestion> questions1 = form1.getQuestions();
		List<ExcelQuestion> questions2 = form2.getQuestions();
		
		assertEquals(questions1.size(), questions2.size());
	}
	
}
