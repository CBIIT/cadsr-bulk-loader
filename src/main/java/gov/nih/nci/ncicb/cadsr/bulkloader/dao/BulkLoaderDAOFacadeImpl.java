package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.domain.Qualifier;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.factory.BulkLoaderDAOFactory;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.LexEVSDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class BulkLoaderDAOFacadeImpl implements BulkLoaderDAOFacade {

	private BulkLoaderDAOFactory daoFactory;
	private BulkLoaderReadDAO readDAO;
	private BulkLoaderWriteDAO writeDAO;
	private LexEVSDAO evsDAO;
	
	private HashMap<String, Concept> evsConceptsCache = new HashMap<String, Concept>();
	
	public BulkLoaderDAOFacadeImpl(BulkLoaderDAOFactory _daoFactory) {
		daoFactory = _daoFactory;
		
		readDAO = daoFactory.getReadDAO();
		writeDAO = daoFactory.getWriteDAO();
	}
	
	public LexEVSDAO getEvsDAO() {
		return evsDAO;
	}

	public void setEvsDAO(LexEVSDAO evsDAO) {
		this.evsDAO = evsDAO;
	}

	public boolean administeredComponentExists(AdminComponent adminComponent) {
		return readDAO.administeredComponentExists(adminComponent);
	}

	public AdminComponent findAdminComponentById(int publicId, double version) {
		return readDAO.findAdminComponentById(publicId, version);
	}

	public DataElement findDataElementById(int publicId, double version) {
		return readDAO.findDataElementById(publicId, version);
	}

	public DataElementConcept findDataElementConceptById(int publicId,
			double version) {
		return readDAO.findDataElementConceptById(publicId, version);
	}

	public List<DataElementConcept> findDataElementConcepts(
			DataElementConcept dataElementConcept) {
		return readDAO.findDataElementConcepts(dataElementConcept);
	}

	public List<DataElement> findDataElements(DataElement dataElement) {
		return readDAO.findDataElements(dataElement);
	}

	public ObjectClass findObjectClassByConcepts(List<Concept> concepts) {
		return readDAO.findObjectClassByConcepts(concepts);
	}

	public ObjectClass findObjectClassById(int publicId, double version) {
		return readDAO.findObjectClassById(publicId, version);
	}

	public List<ObjectClass> findObjectClasses(ObjectClass objectClass) {
		return readDAO.findObjectClasses(objectClass);
	}

	public List<Property> findProperties(Property property) {
		return readDAO.findProperties(property);
	}

	public Property findPropertyByConcepts(List<Concept> concepts) {
		return readDAO.findPropertyByConcepts(concepts);
	}

	public Property findPropertyById(int publicId, double version) {
		return readDAO.findPropertyById(publicId, version);
	}

	public List<ValueDomain> findValueDomains(ValueDomain valueDomain) {
		return readDAO.findValueDomains(valueDomain);
	}

	public ValueDomain findValueDomainsById(int publicId, double version) {
		return readDAO.findValueDomainsById(publicId, version);
	}
	
	public void saveConcepts(Collection<Concept> adminComponents) {
		writeDAO.saveConcepts(adminComponents);
	}

	public void saveDataElementConcepts(
			Collection<DataElementConcept> adminComponents) {
		writeDAO.saveDataElementConcepts(adminComponents);
	}

	public void saveDataElements(Collection<DataElement> adminComponents) {
		writeDAO.saveDataElements(adminComponents);
	}

	public void saveObjectClasses(Collection<ObjectClass> adminComponents) {
		writeDAO.saveObjectClasses(adminComponents);
	}

	public void saveProperties(Collection<Property> adminComponents) {
		writeDAO.saveProperties(adminComponents);
	}

	public void saveValueDomains(Collection<ValueDomain> adminComponents) {
		writeDAO.saveValueDomains(adminComponents);
	}
	
	public Concept findConceptByCUI(String cui) {
		Concept cachedConcept = evsConceptsCache.get(cui);
		if (cachedConcept != null) {
			return cachedConcept;
		}
		else {
			DescLogicConcept descLogicConcept = evsDAO.getDescLogicConcept(cui);
			
			Concept concept = getConcept(descLogicConcept);
			evsConceptsCache.put(cui, concept);
			return concept;
		}
	}
	
	private Concept getConcept(DescLogicConcept descLogicConcept) {
		Concept concept = CaDSRObjectsUtil.createConcept();
		
		if (descLogicConcept == null) {
			return concept;
		}
		
		String preferredName = null;
		String definition = null;
		String source = null;
		String cui = descLogicConcept.getCode();
		
		Vector<gov.nih.nci.evs.domain.Property> props = descLogicConcept.getPropertyCollection();
		for (gov.nih.nci.evs.domain.Property prop: props) {
			if (definition == null && prop.getName().equalsIgnoreCase("definition")) {
				definition = prop.getValue();
			}
			else if (preferredName == null && prop.getName().equalsIgnoreCase("Preferred_Name")) {
				preferredName = prop.getValue();
			}
			Vector<Qualifier> qualCollection = prop.getQualifierCollection();
			for (Qualifier qual: qualCollection) {
				if (qual.getName().equalsIgnoreCase("Source")) {
					source = qual.getValue();
					break;
				}
			}
		}
		concept.setPreferredName(cui);
		concept.setLongName(preferredName);
		concept.setPreferredDefinition(definition);
		concept.setEvsSource(source);
		
		return concept;
	}
	
}
