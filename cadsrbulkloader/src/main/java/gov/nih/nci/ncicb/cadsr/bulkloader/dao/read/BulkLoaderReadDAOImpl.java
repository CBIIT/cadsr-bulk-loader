package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.dao.ConceptDAO;
import gov.nih.nci.ncicb.cadsr.dao.DataElementConceptDAO;
import gov.nih.nci.ncicb.cadsr.dao.DataElementDAO;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassDAO;
import gov.nih.nci.ncicb.cadsr.dao.PropertyDAO;
import gov.nih.nci.ncicb.cadsr.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

public class BulkLoaderReadDAOImpl implements BulkLoaderReadDAO {

	private DataSource dataSource;

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@SuppressWarnings("unchecked")
	public <T extends AdminComponent> List<T> findAdminComponent(T adminComp) {
		
		if (adminComp instanceof DataElement) {
			List<T> dataElements = (List<T>)findDataElements((DataElement) adminComp);
			return dataElements;
		}
		else if (adminComp instanceof DataElementConcept) {
			List<T> dataElementConcepts = (List<T>)findDataElementConcepts((DataElementConcept) adminComp);
			return dataElementConcepts;
		}
		else if (adminComp instanceof ObjectClass) {
			List<T> objectClasses = (List<T>)findObjectClasses((ObjectClass) adminComp);
			return objectClasses;
		}
		else if (adminComp instanceof Property) {
			List<T> properties = (List<T>)findProperties((Property) adminComp);
			return properties;
		}
		else if (adminComp instanceof ValueDomain) {
			List<T> vds = (List<T>)findValueDomains((ValueDomain) adminComp);
			return vds;
		}
		return new ArrayList<T>();
	}
	
	public List<DataElement> findDataElements(DataElement dataElement) {
		DataElementDAO deDAO = DAOAccessor.getDataElementDAO();
		List<DataElement> dataElements = deDAO.find(dataElement);
		
		return dataElements;
	}
	
	public List<DataElementConcept> findDataElementConcepts(DataElementConcept dataElementConcept) {
		DataElementConceptDAO decDAO = DAOAccessor.getDataElementConceptDAO();
		List<DataElementConcept> dataElementConcepts = decDAO.find(dataElementConcept);
		
		return dataElementConcepts;
	}
	
	public List<ObjectClass> findObjectClasses(ObjectClass objectClass) {
		ObjectClassDAO ocDAO = DAOAccessor.getObjectClassDAO();
		List<ObjectClass> ocs = ocDAO.find(objectClass);
		
		return ocs;
	}
	
	public List<Property> findProperties(Property property) {
		PropertyDAO propDAO = DAOAccessor.getPropertyDAO();
		List<Property> prop = propDAO.find(property);
		
		return prop;
	}
	
	public List<ValueDomain> findValueDomains(ValueDomain valueDomain) {
		ValueDomainDAO propDAO = DAOAccessor.getValueDomainDAO();
		List<ValueDomain> vd = propDAO.find(valueDomain);
		
		return vd;
	}
	
	public boolean administeredComponentExists(AdminComponent adminComponent) {
		List<AdminComponent> adminComps = findAdminComponent(adminComponent);
		
		if (adminComps.size()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public AdminComponent findAdminComponentById(int publicId, double version) {
		//TODO implement method
		throw new UnsupportedOperationException();
	}
	
	public DataElement findDataElementById(int publicId, double version) {
		DataElement dataElement = CaDSRObjectsUtil.createDataElement(publicId, version);
		Collection<DataElement> dataElements = findDataElements(dataElement);
		
		Iterator<DataElement> iter = dataElements.iterator();
		if (iter.hasNext()) {
			return iter.next();
		}
		else {
			return CaDSRObjectsUtil.createDataElement();
		}
	}

	public DataElementConcept findDataElementConceptById(int publicId, double version) {
		DataElementConcept dataElementConcept = CaDSRObjectsUtil.createDataElementConcept(publicId, version);
		List<DataElementConcept> dataElementConcepts = findAdminComponent(dataElementConcept);
		
		Iterator<DataElementConcept> iter = dataElementConcepts.iterator();
		if (iter.hasNext()) {
			return iter.next();
		}
		else {
			return CaDSRObjectsUtil.createDataElementConcept();
		}
	}

	public ObjectClass findObjectClassByConcepts(List<Concept> concepts) {
		ObjectClass objectClass = CaDSRObjectsUtil.createObjectClass(concepts);
		List<ObjectClass> objectClasses = findObjectClasses(objectClass);
		
		Iterator<ObjectClass> objectClassIter = objectClasses.iterator();
		if (objectClassIter.hasNext()) {
			return objectClassIter.next();
		}
		else {
			return CaDSRObjectsUtil.createObjectClass();
		}
	}

	public ObjectClass findObjectClassById(int publicId, double version) {
		ObjectClass objectClass = CaDSRObjectsUtil.createObjectClass(publicId, version);
		List<ObjectClass> objectClasses = findObjectClasses(objectClass);
		
		Iterator<ObjectClass> objectClassIter = objectClasses.iterator();
		if (objectClassIter.hasNext()) {
			return objectClassIter.next();
		}
		else {
			return CaDSRObjectsUtil.createObjectClass();
		}
	}

	public Property findPropertyByConcepts(List<Concept> concepts) {
		Property property = CaDSRObjectsUtil.createProperty(concepts);
		List<Property> properties = findProperties(property);
		
		Iterator<Property> propertiesIter = properties.iterator();
		if (propertiesIter.hasNext()) {
			return propertiesIter.next();
		}
		else {
			return CaDSRObjectsUtil.createProperty();
		}
	}

	public Property findPropertyById(int publicId, double version) {
		Property property = CaDSRObjectsUtil.createProperty(publicId, version);
		List<Property> properties = findProperties(property);
		
		Iterator<Property> propertiesIter = properties.iterator();
		if (propertiesIter.hasNext()) {
			return propertiesIter.next();
		}
		else {
			return CaDSRObjectsUtil.createProperty();
		}
	}

	public ValueDomain findValueDomainsById(int publicId, double version) {
		ValueDomain valueDomain = CaDSRObjectsUtil.createValueDomain(publicId, version);
		List<ValueDomain> valueDomains = findValueDomains(valueDomain);
		
		Iterator<ValueDomain> valueDomainsIter = valueDomains.iterator();
		if (valueDomainsIter.hasNext()) {
			return valueDomainsIter.next();
		}
		else {
			return CaDSRObjectsUtil.createValueDomain();
		}
	}
	
	public Concept findConceptByCUI(String cui) {
		Concept toSearch = CaDSRObjectsUtil.createConcept(cui);
		List<Concept> foundConcepts = findConcepts(toSearch);
		if (foundConcepts.isEmpty()) {
			return CaDSRObjectsUtil.createConcept();
		}
		else {
			return foundConcepts.get(0);
		}
	}
	
	public List<Concept> findConcepts(Concept concept) {
		ConceptDAO dao = DAOAccessor.getConceptDAO();
		List foundConcepts = dao.find(concept);
		if (foundConcepts != null) {
			return (List<Concept>)foundConcepts;
		}
		else {
			return new ArrayList<Concept>();
		}
	}
	
}
