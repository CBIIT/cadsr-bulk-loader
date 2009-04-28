package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.List;

public class PersisterImpl implements Persister {

	private BulkLoaderDAOFacade dao;
	
	public BulkLoaderDAOFacade getDao() {
		return dao;
	}

	public void setDao(BulkLoaderDAOFacade dao) {
		this.dao = dao;
	}

	public PersisterResult persist(CaDSRObjects cadsrObjects) {
		PersisterResult result = new PersisterResult();
		result.setCaDSRObjects(cadsrObjects);
		
		try {
			List<Concept> concepts = cadsrObjects.getConcepts();
			List<ObjectClass> objectClasses = cadsrObjects.getObjectClasses();
			List<Property> properties = cadsrObjects.getProperties();
			List<DataElementConcept> dataElementConcepts = cadsrObjects.getDataElementConcepts();
			List<ValueDomain> valueDomains = cadsrObjects.getValueDomains();
			List<DataElement> dataElements = cadsrObjects.getDataElements();
			
			dao.saveConcepts(concepts);
			if (objectClasses != null) dao.saveObjectClasses(objectClasses);
			if (properties != null) dao.saveProperties(properties);
			if (dataElementConcepts != null) dao.saveDataElementConcepts(dataElementConcepts);
			if (valueDomains != null) dao.saveValueDomains(valueDomains);
			if (dataElements != null) dao.saveDataElements(dataElements);
			
			result.setStatus(PersisterStatus.SUCCESS);
			
		} catch (Exception e) {
			result.setException(e);
			result.setStatus(PersisterStatus.FAILURE);
		}
		
		return result;
	}

}
