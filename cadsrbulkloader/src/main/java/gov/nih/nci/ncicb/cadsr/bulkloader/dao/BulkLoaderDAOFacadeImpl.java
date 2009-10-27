package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.domain.Qualifier;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.factory.BulkLoaderDAOFactory;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.LexEVSDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.dao.ClassificationSchemeDAO;
import gov.nih.nci.ncicb.cadsr.dao.EagerConstants;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class BulkLoaderDAOFacadeImpl implements BulkLoaderDAOFacade {

	private BulkLoaderDAOFactory daoFactory;
	private BulkLoaderReadDAO readDAO;
	private BulkLoaderWriteDAO writeDAO;
	private LexEVSDAO evsDAO;
	
	private HashMap<String, Concept> evsConceptsCache = new HashMap<String, Concept>();
	private HashMap<String, Concept> caDSRConceptsCache = new HashMap<String, Concept>();
	private HashMap<String, ClassificationScheme> classSchemeCache = new HashMap<String, ClassificationScheme>();
	
	private HashMap<String, DataElement> dataElementCacheById = new HashMap<String, DataElement>();
	private HashMap<String, DataElementConcept> dataElementConceptCacheById = new HashMap<String, DataElementConcept>();
	private HashMap<String, ValueDomain> valueDomainCacheById = new HashMap<String, ValueDomain>();
	private HashMap<String, ObjectClass> objectClassCacheById = new HashMap<String, ObjectClass>();
	private HashMap<String, Property> propertyCacheById = new HashMap<String, Property>();
	
	private HashMap<DataElementConcept, DataElementConcept> dataElementConceptCache = new HashMap<DataElementConcept, DataElementConcept>();
	private HashMap<ValueDomain, ValueDomain> valueDomainCache = new HashMap<ValueDomain, ValueDomain>();
	private HashMap<ObjectClass, ObjectClass> objectClassCache = new HashMap<ObjectClass, ObjectClass>();
	private HashMap<Property, Property> propertyCache = new HashMap<Property, Property>();
	
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
		String deIdStr = getAdminCompIdString(publicId, version);
		DataElement cachedDataElement = dataElementCacheById.get(deIdStr);
		
		if (cachedDataElement == null) {
			cachedDataElement = readDAO.findDataElementById(publicId, version);
			dataElementCacheById.put(deIdStr, cachedDataElement);
		}
		
		return cachedDataElement;
	}

	public DataElementConcept findDataElementConceptById(int publicId, double version) {
		String decIdStr = getAdminCompIdString(publicId, version);
		DataElementConcept cachedDataElementConcept = dataElementConceptCacheById.get(decIdStr);
		
		if (cachedDataElementConcept == null) {
			cachedDataElementConcept = readDAO.findDataElementConceptById(publicId, version);
			dataElementConceptCacheById.put(decIdStr, cachedDataElementConcept);
		}
		
		return cachedDataElementConcept;
	}

	public List<DataElementConcept> findDataElementConcepts(DataElementConcept dataElementConcept) {
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
		String vdIdStr = getAdminCompIdString(publicId, version);
		ValueDomain cachedValueDomain = valueDomainCacheById.get(vdIdStr);
		
		if (cachedValueDomain == null) {
			cachedValueDomain = readDAO.findValueDomainsById(publicId, version);
			valueDomainCacheById.put(vdIdStr, cachedValueDomain);
		}
		
		return cachedValueDomain;
	}
	
	public Context findContextByName(String contextName) {
		return readDAO.findContextByName(contextName);
	}
	
	public Concept findEVSConceptByCUI(String cui, boolean includeRetired) {
		Concept cachedConcept = evsConceptsCache.get(cui);
		if (cachedConcept != null) {
			return cachedConcept;
		}
		else {
			DescLogicConcept descLogicConcept = evsDAO.getEVSPreNCItConcept(cui);
			
			Concept concept = getConcept(descLogicConcept, includeRetired);
			evsConceptsCache.put(cui, concept);
			return concept;
		}
	}
	
	public Concept findCaDSRConceptByCUI(String cui) {
		Concept cachedConcept = caDSRConceptsCache.get(cui);
		if (cachedConcept != null) {
			return cachedConcept;
		}
		else {
			Concept concept = readDAO.findCaDSRConceptByCUI(cui);
			caDSRConceptsCache.put(cui, concept);
			return concept;
		}
	}
	
	private Concept getConcept(DescLogicConcept descLogicConcept, boolean includeRetired) {
		Concept concept = CaDSRObjectsUtil.createConcept();
		
		if (descLogicConcept == null) {
			return concept;
		}
		
		if (!includeRetired && descLogicConcept.getIsRetired()) {
			return concept;
		}
		
		String preferredName = null;
		String cui = descLogicConcept.getCode();
		List<gov.nih.nci.evs.domain.Definition> defs = new ArrayList<gov.nih.nci.evs.domain.Definition>();
		List<gov.nih.nci.evs.domain.Definition> altDefs = new ArrayList<gov.nih.nci.evs.domain.Definition>();
		
		Vector<gov.nih.nci.evs.domain.Property> props = descLogicConcept.getPropertyCollection();
		for (gov.nih.nci.evs.domain.Property prop: props) {
			String propName = prop.getName();
			
			if (propName.equalsIgnoreCase("definition")) {
				gov.nih.nci.evs.domain.Definition def = getDef(prop);
				defs.add(def);
			}
			else if (propName.equalsIgnoreCase("alt_definition")) {
				gov.nih.nci.evs.domain.Definition def = getDef(prop);
				altDefs.add(def);
			}
			else if (preferredName == null && prop.getName().equalsIgnoreCase("Preferred_Name")) {
				preferredName = prop.getValue();
			}
		}
		
		if (defs.size() > 0) {
			setDefinitions(concept, defs);
		}
		else {
			setDefinitions(concept, altDefs);
		}
		
		concept.setPreferredName(cui);
		concept.setLongName(preferredName);
		
		return concept;
	}
	
	private void setDefinitions(Concept concept, List<gov.nih.nci.evs.domain.Definition> defs) {
		gov.nih.nci.evs.domain.Definition prefDef = null;
		
		for (gov.nih.nci.evs.domain.Definition def: defs) {
			
			Definition caDSRDef = getCaDSRDefinition(def);
			concept.addDefinition(caDSRDef);
			
			if (prefDef == null) {
				prefDef = def;
			}
			else {
				gov.nih.nci.evs.domain.Source src = def.getSource();
				if (src != null) {
					if (src.getAbbreviation().equalsIgnoreCase("NCI")) {
						prefDef = def;
					}
				}
			}
		}
		if (prefDef != null) {
			concept.setPreferredDefinition(prefDef.getDefinition());
			concept.setDefinitionSource(prefDef.getSource().getAbbreviation());
		}
	}
	
	private Definition getCaDSRDefinition(gov.nih.nci.evs.domain.Definition def) {
		Definition caDSRDef = CaDSRObjectsUtil.createDefinition();
		caDSRDef.setDefinition(def.getDefinition());
		caDSRDef.setType("");
		
		return caDSRDef;
	}
	
	private gov.nih.nci.evs.domain.Definition getDef(gov.nih.nci.evs.domain.Property prop) {
		gov.nih.nci.evs.domain.Definition def = new gov.nih.nci.evs.domain.Definition();
		def.setDefinition(prop.getValue());
		gov.nih.nci.evs.domain.Source defSource = getSource(prop.getQualifierCollection());
		def.setSource(defSource);
		
		return def;
	}
	
	private gov.nih.nci.evs.domain.Source getSource(Vector<Qualifier> qualCollection) {
		
		for (Qualifier qual: qualCollection) {
			if (qual.getName().equalsIgnoreCase("Source")) {
				gov.nih.nci.evs.domain.Source source = new gov.nih.nci.evs.domain.Source();
				source.setAbbreviation(qual.getValue());
				return source;
			}
		}
		
		return null;
	}
	
	public ClassificationScheme getClassificationScheme(ClassificationScheme classScheme) {
		String preferredName = classScheme.getPreferredName();
		if (preferredName != null && classSchemeCache.get(preferredName) != null) {
			return classSchemeCache.get(preferredName);
		}
		
		ClassificationSchemeDAO classSchemeDAO = DAOAccessor.getClassificationSchemeDAO();
		List<String> eager = new ArrayList<String>();
		eager.add(EagerConstants.CS_CSI);
		List<ClassificationScheme> classificationSchemes = classSchemeDAO.find(classScheme, eager);
		
		ClassificationScheme returnCS;
		
		if (classificationSchemes != null && classificationSchemes.size() > 0) {
			returnCS = classificationSchemes.get(0);
		}
		else returnCS = DomainObjectFactory.newClassificationScheme();
		
		classSchemeCache.put(returnCS.getLongName(), returnCS);
		return returnCS;
	}
	
	public ClassificationScheme getClassificationScheme(String csName) {
		ClassificationScheme cs = DomainObjectFactory.newClassificationScheme();
		cs.setPreferredName(csName);
		
		return getClassificationScheme(cs);
	}
	
	public List<ClassSchemeClassSchemeItem> getClassSchemeClassSchemeItems(String csName) {
		ClassificationSchemeDAO csDAO = DAOAccessor.getClassificationSchemeDAO();
		List<ClassSchemeClassSchemeItem> csCSIs = csDAO.getCsCsis(csName);
		
		return csCSIs;
	}
	
	
	public void save(CaDSRObjects caDSRObjects, LoadObjects loadObjects) {
		writeDAO.save(caDSRObjects, loadObjects);
	}
	
	@Override
	public List<String> getAlternateNameTypes() {
		return readDAO.getAlternateNameTypes();
	}
	
	private String getAdminCompIdString(int publicId, double version) {
		return publicId+"v"+version;
	}
	
	public CaDSRObjects loadFromCaDSR(CaDSRObjects cadsrObjects, LoadObjects loadObjects) {
		List<DataElement> dataElements = cadsrObjects.getDataElements();
		List<DataElementConcept> dataElementConcepts = cadsrObjects.getDataElementConcepts();
		List<ValueDomain> valueDomains = cadsrObjects.getValueDomains();
		List<ObjectClass> objectClasses = cadsrObjects.getObjectClasses();
		List<Property> properties = cadsrObjects.getProperties();
		
		if (objectClasses != null) {
			List<ObjectClass> lookedUpObjectClasses = loadObjectClasses(objectClasses);
			cadsrObjects.setObjectClasses(lookedUpObjectClasses);
		}
		
		if (properties != null) {
			List<Property> lookedUpProperties = loadProperties(properties);
			cadsrObjects.setProperties(lookedUpProperties);
		}
		
		if (dataElementConcepts != null) {
			List<DataElementConcept> lookedUpDataElementConcepts = loadDataElementConcepts(dataElementConcepts);
			cadsrObjects.setDataElementConcepts(lookedUpDataElementConcepts);
		}
		
		if (valueDomains != null) {
			List<ValueDomain> lookedUpValueDomains = loadValueDomains(valueDomains, loadObjects);
			cadsrObjects.setValueDomains(lookedUpValueDomains);
		}
		
		if (dataElements != null) {
			List<DataElement> lookedUpDataElements = loadDataElements(dataElements);
			replaceCSCSIs(lookedUpDataElements);
			cadsrObjects.setDataElements(lookedUpDataElements);
		}
		
		return cadsrObjects;
	}
	
	private void replaceCSCSIs(List<? extends AdminComponent> adminComps) {
		for (AdminComponent adminComp: adminComps) {
			replaceCSCSIs(adminComp);
		}
	}
	
	private void replaceCSCSIs(AdminComponent adminComp) {
		List<AdminComponentClassSchemeClassSchemeItem> newACCsCSIs = new ArrayList<AdminComponentClassSchemeClassSchemeItem>();
		
		List<AdminComponentClassSchemeClassSchemeItem> acCsCSIs = adminComp.getAcCsCsis();
		for (AdminComponentClassSchemeClassSchemeItem acCSCSI: acCsCSIs) {
			ClassSchemeClassSchemeItem csCSI = acCSCSI.getCsCsi();
			ClassificationScheme classScheme = csCSI.getCs();
			ClassificationSchemeItem classSchemeItem = csCSI.getCsi();
			
			ClassificationScheme retrievedCS = getClassificationScheme(classScheme);
			List<ClassSchemeClassSchemeItem> retrievedCSCSIs = retrievedCS.getCsCsis();
			for (ClassSchemeClassSchemeItem retrievedCSCSI: retrievedCSCSIs) {
				ClassificationSchemeItem retrievedCSI = retrievedCSCSI.getCsi();
				if (retrievedCSI.getLongName().equalsIgnoreCase(classSchemeItem.getLongName())) {
					AdminComponentClassSchemeClassSchemeItem newAcCsCsi = DomainObjectFactory.newAdminComponentClassSchemeClassSchemeItem();
					newAcCsCsi.setCsCsi(retrievedCSCSI);
					
					newACCsCSIs.add(newAcCsCsi);
				}
			}
		}
		
		adminComp.setAcCsCsis(newACCsCSIs);
	}
	
	private List<ObjectClass> loadObjectClasses(List<ObjectClass> createdObjectClasses) {
		List<ObjectClass> lookedUpOCs = new ArrayList<ObjectClass>();
		
		for (ObjectClass createdOC: createdObjectClasses) {
			List<ObjectClass> foundOCs = findObjectClasses(createdOC);
			if (foundOCs.size() > 0) {
				ObjectClass foundOC = foundOCs.get(0);
				objectClassCacheById.put(foundOC.getPublicId(), foundOC);
				objectClassCache.put(createdOC, foundOC);
				lookedUpOCs.add(foundOC);
			}
			else {
				lookedUpOCs.add(createdOC);
			}
		}
		return lookedUpOCs;
	}
	
	private List<Property> loadProperties(List<Property> createdProperties) {
		List<Property> lookedUpProps = new ArrayList<Property>();
		
		for (Property createdProp: createdProperties) {
			List<Property> foundProps = findProperties(createdProp);
			if (foundProps.size() > 0) {
				Property foundProp = foundProps.get(0);
				propertyCacheById.put(foundProp.getPublicId(), foundProp);
				propertyCache.put(createdProp, foundProp);
				lookedUpProps.add(foundProp);
			}
			else {
				lookedUpProps.add(createdProp);
			}
		}
		return lookedUpProps;
	}
	
	private List<DataElementConcept> loadDataElementConcepts(List<DataElementConcept> createdDataElementConcepts) {
		List<DataElementConcept> lookedUpDECs = new ArrayList<DataElementConcept>();
		
		for (DataElementConcept createdDEC: createdDataElementConcepts) {
			ObjectClass createdOC = createdDEC.getObjectClass();
			Property createdProp = createdDEC.getProperty();
			
			if (createdOC != null) {
				if (objectClassCache.get(createdOC) != null) {
					createdDEC.setObjectClass(objectClassCache.get(createdOC));
				}
			}
			
			if (createdProp != null) {
				if (propertyCache.get(createdProp) != null) {
					createdDEC.setProperty(propertyCache.get(createdProp));
				}
			}
			
			List<DataElementConcept> foundDECs = findDataElementConcepts(createdDEC);
			
			if (foundDECs.size() > 0) {
				DataElementConcept foundDEC = foundDECs.get(0);
				dataElementConceptCacheById.put(foundDEC.getPublicId(), foundDEC);
				dataElementConceptCache.put(createdDEC, foundDEC);
				lookedUpDECs.add(foundDEC);
			}
			else {
				lookedUpDECs.add(createdDEC);
			}
		}
		
		return lookedUpDECs;
	}
	
	private List<ValueDomain> loadValueDomains(List<ValueDomain> createdValueDomains, LoadObjects loadObjects) {
		List<ValueDomain> lookedUpVDs = new ArrayList<ValueDomain>();
		
		for (ValueDomain createdVD: createdValueDomains) {
			List<ValueDomain> foundVDs = findValueDomains(createdVD);
			if (foundVDs.size() > 0) {
				ValueDomain foundVD = foundVDs.get(0);
				valueDomainCacheById.put(foundVD.getPublicId(), foundVD);
				valueDomainCache.put(createdVD, foundVD);
				lookedUpVDs.add(foundVD);
			}
			else {
				setVMContext(createdVD, loadObjects.getLoadContext());
				lookedUpVDs.add(createdVD);
			}
		}
		
		return lookedUpVDs;
	}
	
	private void setVMContext(ValueDomain createdVD, Context loadContext) {
		List<PermissibleValue> permissibleValues = createdVD.getPermissibleValues();
		if (permissibleValues != null) {
			for (PermissibleValue permissibleValue: permissibleValues) {
				ValueMeaning valueMeaning = permissibleValue.getValueMeaning();
				if (valueMeaning != null) {
					valueMeaning.setContext(loadContext);
				}
			}
		}
	}

	private List<DataElement> loadDataElements(List<DataElement> createdDataElements) {
		List<DataElement> lookedUpDEs = new ArrayList<DataElement>();
		
		for (DataElement createdDE: createdDataElements) {
			DataElementConcept lookedUpDEC = createdDE.getDataElementConcept();
			ValueDomain lookedUpVD = createdDE.getValueDomain();
			
			if (lookedUpDEC != null && dataElementConceptCache.get(lookedUpDEC) != null) {
				createdDE.setDataElementConcept(dataElementConceptCache.get(lookedUpDEC));
			}
			
			if (lookedUpVD != null && valueDomainCache.get(lookedUpVD) != null) {
				createdDE.setValueDomain(valueDomainCache.get(lookedUpVD));
			}
			
			List<DataElement> foundDEs = findDataElements(createdDE);
			if (foundDEs.size() > 0) {
				DataElement foundDE = foundDEs.get(0);
				foundDE.removeAlternateNames();
				foundDE.removeDefinitions();
				
				lookedUpDEs.add(foundDE);
				if (createdDE.getAcCsCsis() != null) {
					foundDE.setAcCsCsis(createdDE.getAcCsCsis());
				}
				foundDE = addAlternateNames(createdDE, foundDE);
				foundDE = addRefDocs(createdDE, foundDE);
			}
			else {
				lookedUpDEs.add(createdDE);
			}
		}
		
		return lookedUpDEs;
	}
	
	private DataElement addAlternateNames(DataElement createdDE, DataElement foundDE) {
		if (createdDE!=null && foundDE!=null && createdDE.getAlternateNames() != null) {
			String foundDELongName = foundDE.getLongName();
			for (AlternateName altName: createdDE.getAlternateNames()) {
				String altLongName = altName.getName();
				if (altLongName!=null && (foundDELongName == null || !foundDELongName.equalsIgnoreCase(altLongName))) {
					foundDE.addAlternateName(altName);
				}
			}
		}
		
		return foundDE;
	}
	
	private DataElement addRefDocs(DataElement createdDE, DataElement foundDE) {
		if (createdDE!=null && foundDE!=null && createdDE.getReferenceDocuments() != null) {
			List foundRefDocs = foundDE.getReferenceDocuments();
			if (foundRefDocs == null) {
				foundRefDocs = new ArrayList();
			}
			foundRefDocs.addAll(createdDE.getReferenceDocuments());
		}
		
		return foundDE;
	}
}
