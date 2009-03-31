package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.ncicb.cadsr.dao.DataElementConceptDAO;
import gov.nih.nci.ncicb.cadsr.dao.DataElementDAO;
import gov.nih.nci.ncicb.cadsr.dao.ObjectClassDAO;
import gov.nih.nci.ncicb.cadsr.dao.PropertyDAO;
import gov.nih.nci.ncicb.cadsr.dao.ValueDomainDAO;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

import java.util.ArrayList;
import java.util.List;

public class BulkLoaderReadDAOImpl implements BulkLoaderReadDAO {

	@SuppressWarnings("unchecked")
	public <T extends AdminComponent> List<T> findAdminComponent(T adminComp) {
		
		if (adminComp instanceof DataElement) {
			List<T> dataElements = (List<T>)getDataElement((DataElement) adminComp);
			return dataElements;
		}
		else if (adminComp instanceof DataElementConcept) {
			List<T> dataElementConcepts = (List<T>)getDataElementConcept((DataElementConcept) adminComp);
			return dataElementConcepts;
		}
		else if (adminComp instanceof ObjectClass) {
			List<T> objectClasses = (List<T>)getObjectClasses((ObjectClass) adminComp);
			return objectClasses;
		}
		else if (adminComp instanceof Property) {
			List<T> properties = (List<T>)getProperties((Property) adminComp);
			return properties;
		}
		else if (adminComp instanceof ValueDomain) {
			List<T> vds = (List<T>)getValueDomains((ValueDomain) adminComp);
			return vds;
		}
		return new ArrayList<T>();
	}
	
	private List<DataElement> getDataElement(DataElement dataElement) {
		DataElementDAO deDAO = DAOAccessor.getDataElementDAO();
		List<DataElement> dataElements = deDAO.find(dataElement);
		
		return dataElements;
	}
	
	private List<DataElementConcept> getDataElementConcept(DataElementConcept dataElementConcept) {
		DataElementConceptDAO decDAO = DAOAccessor.getDataElementConceptDAO();
		List<DataElementConcept> dataElementConcepts = decDAO.find(dataElementConcept);
		
		return dataElementConcepts;
	}
	
	private List<ObjectClass> getObjectClasses(ObjectClass objectClass) {
		ObjectClassDAO ocDAO = DAOAccessor.getObjectClassDAO();
		List<ObjectClass> ocs = ocDAO.find(objectClass);
		
		return ocs;
	}
	
	private List<Property> getProperties(Property property) {
		PropertyDAO propDAO = DAOAccessor.getPropertyDAO();
		List<Property> prop = propDAO.find(property);
		
		return prop;
	}
	
	private List<ValueDomain> getValueDomains(ValueDomain valueDomain) {
		ValueDomainDAO propDAO = DAOAccessor.getValueDomainDAO();
		List<ValueDomain> vd = propDAO.find(valueDomain);
		
		return vd;
	}

}
