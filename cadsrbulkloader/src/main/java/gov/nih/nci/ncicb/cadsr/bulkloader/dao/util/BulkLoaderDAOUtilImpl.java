package gov.nih.nci.ncicb.cadsr.bulkloader.dao.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.factory.BulkLoaderDAOFactory;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public class BulkLoaderDAOUtilImpl implements BulkLoaderDAOUtil {

	private BulkLoaderDAOFactory daoFactory;
	private BulkLoaderReadDAO readDAO;
	private BulkLoaderWriteDAO writeDAO;
	
	public BulkLoaderDAOUtilImpl(BulkLoaderDAOFactory _daoFactory) {
		daoFactory = _daoFactory;
		
		readDAO = daoFactory.getReadDAO();
		writeDAO = daoFactory.getWriteDAO();
	}

	public synchronized void saveElements(CaDSRObjects caDSRObjects) throws BulkLoaderDAORuntimeException{

		try {
			List<Concept> concepts = caDSRObjects.getConcepts();
			List<ObjectClass> objectClasses = caDSRObjects.getObjectClasses();
			List<Property> properties = caDSRObjects.getProperties();
			List<DataElementConcept> dataElementConcepts = caDSRObjects.getDataElementConcepts();
			List<ValueDomain> valueDomains = caDSRObjects.getValueDomains();
			List<DataElement> dataElements = caDSRObjects.getDataElements();
			
			save(concepts);
			save(objectClasses);
			save(properties);
			save(dataElementConcepts);
			save(valueDomains);
			save(dataElements);
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}
	
	private void save(List<? extends AdminComponent> adminComps) {
		writeDAO.save(adminComps);
	}
	
	public boolean administeredComponentExists(AdminComponent adminComponent) {
		List<AdminComponent> adminComps = readDAO.findAdminComponent(adminComponent);
		
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
		Collection<DataElement> dataElements = readDAO.findAdminComponent(dataElement);
		
		Iterator<DataElement> iter = dataElements.iterator();
		if (iter.hasNext()) {
			return iter.next();
		}
		else {
			return CaDSRObjectsUtil.createDataElement();
		}
	}
	
	public List<DataElement> findDataElements(DataElement dataElement) {
		List<DataElement> dataElements = readDAO.findAdminComponent(dataElement);
		return dataElements;
	}

	public DataElementConcept findDataElementConceptById(int publicId, double version) {
		DataElementConcept dataElementConcept = CaDSRObjectsUtil.createDataElementConcept(publicId, version);
		Collection<DataElementConcept> dataElementConcepts = readDAO.findAdminComponent(dataElementConcept);
		
		Iterator<DataElementConcept> iter = dataElementConcepts.iterator();
		if (iter.hasNext()) {
			return iter.next();
		}
		else {
			return CaDSRObjectsUtil.createDataElementConcept();
		}
	}

	public List<DataElementConcept> findDataElementConcepts(DataElementConcept dataElementConcept) {
		List<DataElementConcept> dataElementConcepts = readDAO.findAdminComponent(dataElementConcept);
		return dataElementConcepts;
	}

	public ObjectClass findObjectClassByConcepts(List<Concept> concepts) {
		ObjectClass objectClass = CaDSRObjectsUtil.createObjectClass(concepts);
		List<ObjectClass> objectClasses = readDAO.findAdminComponent(objectClass);
		
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
		List<ObjectClass> objectClasses = readDAO.findAdminComponent(objectClass);
		
		Iterator<ObjectClass> objectClassIter = objectClasses.iterator();
		if (objectClassIter.hasNext()) {
			return objectClassIter.next();
		}
		else {
			return CaDSRObjectsUtil.createObjectClass();
		}
	}

	public List<ObjectClass> findObjectClasses(ObjectClass objectClass) {
		return readDAO.findAdminComponent(objectClass);
	}

	public List<Property> findProperties(Property property) {
		return readDAO.findAdminComponent(property);
	}

	public Property findPropertiesByConcepts(List<Concept> concepts) {
		Property property = CaDSRObjectsUtil.createProperty(concepts);
		List<Property> properties = readDAO.findAdminComponent(property);
		
		Iterator<Property> propertiesIter = properties.iterator();
		if (propertiesIter.hasNext()) {
			return propertiesIter.next();
		}
		else {
			return CaDSRObjectsUtil.createProperty();
		}
	}

	public Property findPropertiesById(int publicId, double version) {
		Property property = CaDSRObjectsUtil.createProperty(publicId, version);
		List<Property> properties = readDAO.findAdminComponent(property);
		
		Iterator<Property> propertiesIter = properties.iterator();
		if (propertiesIter.hasNext()) {
			return propertiesIter.next();
		}
		else {
			return CaDSRObjectsUtil.createProperty();
		}
	}

	public List<ValueDomain> findValueDomains(ValueDomain valueDomain) {
		return readDAO.findAdminComponent(valueDomain);
	}

	public ValueDomain findValueDomainsById(int publicId, double version) {
		ValueDomain valueDomain = CaDSRObjectsUtil.createValueDomain(publicId, version);
		List<ValueDomain> valueDomains = readDAO.findAdminComponent(valueDomain);
		
		Iterator<ValueDomain> valueDomainsIter = valueDomains.iterator();
		if (valueDomainsIter.hasNext()) {
			return valueDomainsIter.next();
		}
		else {
			return CaDSRObjectsUtil.createValueDomain();
		}
	}
	
}
