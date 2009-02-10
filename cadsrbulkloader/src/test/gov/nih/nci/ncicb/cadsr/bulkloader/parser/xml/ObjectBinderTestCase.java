package gov.nih.nci.ncicb.cadsr.bulkloader.parser.xml;

import java.io.File;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.AdminRecord_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConceptList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElement_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ItemIdentifier_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParserTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind.ISO11179SchemaBinder;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind.ObjectBinder;

public class ObjectBinderTestCase extends MainTestCase {

	private final String validFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/valid.xml";
	private final String invalidFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/invalid.xml";
	
	private ObjectBinder binder;

	public ObjectBinderTestCase() {
		this.binder = new ISO11179SchemaBinder();
	}
	
	public void testBinder() {
		ClassLoader classLoader = ParserTestCase.class.getClassLoader();
		String filePath = classLoader.getResource(validFilePath).getPath();
		ISO11179Elements elements = binder.bind(new File(filePath));
		assertNotNull(elements);
		
		DataElementList_ISO11179 dataElementList = elements.getDataElements();
		assertNotNull(dataElementList);
		
		DataElementConceptList_ISO11179 dataElementConceptsList = elements.getDataElementConcepts();
		assertNotNull(dataElementConceptsList);
		
		List<DataElement_ISO11179> dataElements = dataElementList.getDataElements();
		assertNotNull(dataElements);
		
		for (DataElement_ISO11179 dataElement: dataElements) {
			String tagId = dataElement.getTagId();
			assertNotNull(tagId);
			
			String decRefId = dataElement.getDecRefId();
			String vdRefId = dataElement.getVdRefId();
			AdminRecord_ISO11179 adminRecord = dataElement.getAdminRecord();
			ItemIdentifier_ISO11179 itemId = adminRecord.getIdentifier();
			String dataId = itemId.getDataIdentifier();
			
			System.out.println("----------------");
			System.out.println("Tag Id: "+tagId);
			System.out.println("DEC Id: "+decRefId);
			System.out.println("VD Id: "+vdRefId);
			System.out.println("Data Id: "+dataId);
			System.out.println("----------------\n");
		}
	}
}
