package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.util.BulkLoaderDAOUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

public class PropertiesDAOTestCase extends MainTestCase {

	public void testPropertiesDAO() {
		BulkLoaderDAOUtil objectClassDAO = SpringBeansUtil.getPropertyDAO();
		objectClassDAO.saveElements(getCaDSRObjects());
	}
	
	private CaDSRObjects getCaDSRObjects() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		caDSRObjects.setProperties(getPropertiesList());
		caDSRObjects.setConcepts(getPropertiesConcepts());
		
		return caDSRObjects;
	}
	
	
}
