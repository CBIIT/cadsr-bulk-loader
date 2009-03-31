package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.util.BulkLoaderDAOUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.util.ArrayList;
import java.util.List;

public class DataElementDAOTestCase extends MainTestCase {

	public void testCreateDE() {
		BulkLoaderDAOUtil dao = SpringBeansUtil.getDEDAO();
		UserSelections.getInstance().setProperty("ignore-vd", new Boolean(false));
		UserPreferences.getInstance().setUsePrivateApi(true);
		
		dao.saveElements(getCaDSRObjects());
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
		caDSRObjects.setValueDomains(getVDList());
		caDSRObjects.setDataElements(getDEList());
		
		return caDSRObjects;
	}
}
