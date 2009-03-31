package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.util.BulkLoaderDAOUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;

import java.util.ArrayList;
import java.util.List;

public class ValueDomainDAOTestCase extends MainTestCase {

	public void testCreateVD() {
		BulkLoaderDAOUtil dao = SpringBeansUtil.getVDDAO();
		UserSelections.getInstance().setProperty("ignore-vd", new Boolean(false));
		dao.saveElements(getCaDSRObjects());
	}
	
	private CaDSRObjects getCaDSRObjects() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.addAll(getObjectClassConcepts());
		concepts.addAll(getPropertiesConcepts());
		
		caDSRObjects.setConcepts(concepts);
		caDSRObjects.setValueDomains(getVDList());
		
		return caDSRObjects;
	}
}
