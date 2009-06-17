package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.List;


public interface BulkLoaderReadDAO {

	public boolean administeredComponentExists(AdminComponent adminComponent);
	public AdminComponent findAdminComponentById(int publicId, double version);
	
	public DataElement findDataElementById(int publicId, double version);
	public List<DataElement> findDataElements(DataElement dataElement);
	
	public DataElementConcept findDataElementConceptById(int publicId, double version);
	public List<DataElementConcept> findDataElementConcepts(DataElementConcept dataElementConcept);
	
	public ObjectClass findObjectClassById(int publicId, double version);
	public ObjectClass findObjectClassByConcepts(List<Concept> concepts);
	public List<ObjectClass> findObjectClasses(ObjectClass objectClass);
	
	public Property findPropertyById(int publicId, double version);
	public Property findPropertyByConcepts(List<Concept> concepts);
	public List<Property> findProperties(Property property);
	
	public ValueDomain findValueDomainsById(int publicId, double version);
	public List<ValueDomain> findValueDomains(ValueDomain valueDomain);
	
	public Concept findConceptByCUI(String cui);
	
	public Context findContextByName(String contextName);
	
	public List<String> getAlternateNameTypes();

}
