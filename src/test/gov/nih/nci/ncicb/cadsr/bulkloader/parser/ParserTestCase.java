package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import java.io.File;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ParserTestCase extends MainTestCase {

	private Parser parser;
	
	public ParserTestCase() {
		parser = SpringBeansUtil.getParser();
	}
	
	public void testParse() {
		File fileToParse = getValidFile();
		CaDSRObjects caDSRObjects = parser.parse(fileToParse);
		
		assertNotNull(caDSRObjects);
		
		List<ObjectClass> ocs = caDSRObjects.getObjectClasses();
		assertNotNull(ocs);
	}
}
