package gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.io.File;

public class ObjectBinderTestCase extends MainTestCase {

	public void testBind() {
		try {
			File toTest = getValidFile();
			ObjectBinder objBinder = SpringBeansUtil.getObjectBinder();
			ISO11179Elements isoElements = objBinder.bind(toTest);
			assertNotNull(isoElements);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
