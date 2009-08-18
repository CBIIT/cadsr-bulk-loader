package gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.TestCaseHelper;

import java.io.File;

public class ObjectBinderTestCase extends TestCaseHelper {

	protected boolean ignoreVD() {
		return true;
	}
	
	protected boolean isUsePrivateAPI() {
		return true;
	}
	
	public void testBind() {
		try {
			File toTest = getValidFile();
			ObjectBinder objBinder = SpringBeansUtil.getInstance().getObjectBinder();
			ISO11179Elements isoElements = objBinder.bind(toTest);
			assertNotNull(isoElements);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
