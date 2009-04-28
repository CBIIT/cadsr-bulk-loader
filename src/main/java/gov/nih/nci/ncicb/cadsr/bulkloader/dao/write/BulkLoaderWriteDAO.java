package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.Collection;


public interface BulkLoaderWriteDAO {

	public void saveConcepts(Collection<Concept> adminComponents);
	public void saveObjectClasses(Collection<ObjectClass> adminComponents);
	public void saveProperties(Collection<Property> adminComponents);
	public void saveDataElementConcepts(Collection<DataElementConcept> adminComponents);
	public void saveValueDomains(Collection<ValueDomain> adminComponents);
	public void saveDataElements(Collection<DataElement> adminComponents);
}
