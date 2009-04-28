package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 16, 2009
 * @since 
 */

public class PersisterTestCase extends MainTestCase {

	private static Persister persister = SpringBeansUtil.getPersister();
	
	public void testConceptPersistence() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		caDSRObjects.setConcepts(getObjectClassConcepts());
		
		PersisterResult persisterResult = persister.persist(caDSRObjects);
		
		doAssertions(persisterResult);
	}
	
	public void testObjectClassPersistence() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		caDSRObjects.setObjectClasses(getObjectClassList());
		caDSRObjects.setConcepts(getObjectClassConcepts());
		
		PersisterResult persisterResult = persister.persist(caDSRObjects);
		doAssertions(persisterResult);
	}
	
	public void testPropertiesPersistence() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		caDSRObjects.setProperties(getPropertiesList());
		caDSRObjects.setConcepts(getPropertiesConcepts());
		
		PersisterResult persisterResult = persister.persist(caDSRObjects);
		doAssertions(persisterResult);
	}
	
	public void testDECPersistence() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.addAll(getObjectClassConcepts());
		concepts.addAll(getPropertiesConcepts());
		
		caDSRObjects.setObjectClasses(getObjectClassList());
		caDSRObjects.setProperties(getPropertiesList());
		caDSRObjects.setConcepts(concepts);
		caDSRObjects.setDataElementConcepts(getDECList());
		
		PersisterResult persisterResult = persister.persist(caDSRObjects);
		doAssertions(persisterResult);
	}
	
	public void testVDPersistence() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.addAll(getObjectClassConcepts());
		concepts.addAll(getPropertiesConcepts());
		
		caDSRObjects.setConcepts(concepts);
		caDSRObjects.setValueDomains(getVDList());
		
		PersisterResult persisterResult = persister.persist(caDSRObjects);
		doAssertions(persisterResult);
	}
	
	public void testDEPersistence() {
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
		
		PersisterResult persisterResult = persister.persist(caDSRObjects);
		doAssertions(persisterResult);
	}
	
	private void doAssertions(PersisterResult persisterResult) {
		assertNotNull(persisterResult);
		Exception e = persisterResult.getException();
		if (e != null) {
			e.printStackTrace();
		}
		PersisterStatus status = persisterResult.getStatus();
		assertNotNull(status);
		System.out.println(status.getMessage());
		assertTrue(persisterResult.isSuccessful());
	}
	
}
