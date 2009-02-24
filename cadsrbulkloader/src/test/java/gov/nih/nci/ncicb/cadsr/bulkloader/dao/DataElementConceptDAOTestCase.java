package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

import java.util.ArrayList;
import java.util.List;

public class DataElementConceptDAOTestCase extends MainTestCase {

	public void testDECCreate() {
		BulkLoaderDAO dao = SpringBeansUtil.getDECDAO();
		dao.saveElementsAndDependencies(getCaDSRObjects());
	}
	
	private CaDSRObjects getCaDSRObjects() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.addAll(getObjectClassConcepts());
		concepts.addAll(getPropertiesConcepts());
		
		caDSRObjects.setObjectClasses(getObjectClassList());
		caDSRObjects.setProperties(getPropertiesList());
		caDSRObjects.setConcepts(concepts);
		caDSRObjects.setDataElementConcepts(getDECList());
		
		return caDSRObjects;
	}
	
	
}
