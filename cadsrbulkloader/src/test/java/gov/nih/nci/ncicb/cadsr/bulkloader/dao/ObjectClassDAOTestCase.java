package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.util.BulkLoaderDAOUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

public class ObjectClassDAOTestCase extends MainTestCase {

	public void testObjectClassDAO() {
		BulkLoaderDAOUtil objectClassDAO = SpringBeansUtil.getObjectClassDAO();
		objectClassDAO.saveElements(getCaDSRObjects());
	}
	
	private CaDSRObjects getCaDSRObjects() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		caDSRObjects.setObjectClasses(getObjectClassList());
		caDSRObjects.setConcepts(getObjectClassConcepts());
		
		return caDSRObjects;
	}
	
	
}
