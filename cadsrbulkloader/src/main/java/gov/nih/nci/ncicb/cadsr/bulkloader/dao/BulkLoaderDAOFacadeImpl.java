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
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
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
	private HashMap<ConceptualDomain, ConceptualDomain> conceptualDomainCache = new HashMap<ConceptualDomain, ConceptualDomain>();
	
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
		String ocIdStr = getAdminCompIdString(publicId, version);
		ObjectClass cachedObjectClass = objectClassCacheById.get(ocIdStr);
		
		if (cachedObjectClass == null) {
			cachedObjectClass = readDAO.findObjectClassById(publicId, version);
			objectClassCacheById.put(ocIdStr, cachedObjectClass);
		}
		
		return cachedObjectClass;
	}

	public List<ObjectClass> findObjectClasses(ObjectClass objectClass) {
		return readDAO.findObjectClasses(objectClass);
	}
	
	public List<ObjectClass> findObjectClassesByName(ObjectClass objectClass) {
		return readDAO.findObjectClassesByName(objectClass);
	}

	public List<Property> findProperties(Property property) {
		return readDAO.findProperties(property);
	}
	
	public List<Property> findPropertiesByName(Property property) {
		return readDAO.findPropertiesByName(property);
	}

	public Property findPropertyByConcepts(List<Concept> concepts) {
		return readDAO.findPropertyByConcepts(concepts);
	}

	public Property findPropertyById(int publicId, double version) {
		String propIdStr = getAdminCompIdString(publicId, version);
		Property cachedProperty = propertyCacheById.get(propIdStr);
		
		if (cachedProperty == null) {
			cachedProperty = readDAO.findPropertyById(publicId, version);
			propertyCacheById.put(propIdStr, cachedProperty);
		}
		
		return cachedProperty;
	}

	public List<ValueDomain> findValueDomains(ValueDomain valueDomain) {
		return readDAO.findValueDomains(valueDomain);
	}

	public ValueDomain findValueDomainById(int publicId, double version) {
		String vdIdStr = getAdminCompIdString(publicId, version);
		ValueDomain cachedValueDomain = valueDomainCacheById.get(vdIdStr);
		
		if (cachedValueDomain == null) {
			cachedValueDomain = readDAO.findValueDomainById(publicId, version);
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
	
	/**
	 * @TODO move this method to a different class
	 */
	public CaDSRObjects loadFromCaDSR(final CaDSRObjects cadsrObjects, LoadObjects loadObjects) {
		
		processObjectClasses(cadsrObjects);
		processProperties(cadsrObjects);
		processDataElementConcepts(cadsrObjects, loadObjects);
		processValueDomains(cadsrObjects, loadObjects);
		processDataElements(cadsrObjects);
		
		return cadsrObjects;
	}
	
	private void processObjectClasses(CaDSRObjects caDSRObjects) {
		List<ObjectClass> objectClasses = caDSRObjects.getObjectClasses();
		
		if (objectClasses != null) {
			List<ObjectClass> lookedUpObjectClasses = loadObjectClasses(objectClasses, new AdminComponentLoaderCallback<ObjectClass>() {

				@Override
				public void doProcess(ObjectClass originalOC, ObjectClass foundOC) {
					if (originalOC != null && foundOC != null 
							&& originalOC != foundOC 
							&& foundOC.getPublicId() != null) {
						addCSCSIs(originalOC, foundOC);
						foundOC.removeAlternateNames();
						foundOC.removeDefinitions();
						
						objectClassCache.put(originalOC, foundOC);
						objectClassCacheById.put(foundOC.getPublicId(), foundOC);
					}
					
					replaceCSCSIs(foundOC);
				}
			});
			caDSRObjects.setObjectClasses(lookedUpObjectClasses);
		}
	}
	
	private void processProperties(CaDSRObjects caDSRObjects) {
		List<Property> properties = caDSRObjects.getProperties();
		
		if (properties != null) {
			List<Property> lookedUpProperties = loadProperties(properties, new AdminComponentLoaderCallback<Property>() {

				@Override
				public void doProcess(Property originalProp, Property foundProp) {
					if (originalProp != null && foundProp != null 
							&& originalProp != foundProp 
							&& foundProp.getPublicId() != null) {
						addCSCSIs(originalProp, foundProp);
						foundProp.removeAlternateNames();
						foundProp.removeDefinitions();
						
						propertyCache.put(originalProp, foundProp);
						propertyCacheById.put(foundProp.getPublicId(), foundProp);
					}
					
					replaceCSCSIs(foundProp);
				}
			});
			caDSRObjects.setProperties(lookedUpProperties);
		}
	}
	
	private void processDataElementConcepts(CaDSRObjects caDSRObjects, final LoadObjects loadObjects) {
		List<DataElementConcept> dataElementConcepts = caDSRObjects.getDataElementConcepts();
		if (dataElementConcepts != null) {
			final List<ObjectClass> foundDECOCs = new ArrayList<ObjectClass>();
			final List<Property> foundDECProps = new ArrayList<Property>();
			
			List<DataElementConcept> lookedUpDataElementConcepts = loadDataElementConcepts(dataElementConcepts, new AdminComponentLoaderCallback<DataElementConcept>() {

				@Override
				public void doProcess(DataElementConcept originalDEC, DataElementConcept foundDEC) {
					if (originalDEC != null && foundDEC != null 
							&& originalDEC != foundDEC 
							&& foundDEC.getPublicId() != null) {
						
						addCSCSIs(originalDEC, foundDEC);
						ObjectClass foundOC = foundDEC.getObjectClass();
						Property foundProp = foundDEC.getProperty();
						
						if (foundOC != null && foundOC.getPublicId() != null) {
							if (objectClassCacheById.get(foundOC.getPublicId()) == null) {
								objectClassCacheById.put(foundOC.getPublicId(), foundOC);
								
								addCSCSIs(originalDEC, foundOC);
								foundDECOCs.add(foundOC);
							}
						}
						if (foundProp != null && foundProp.getPublicId() != null) {
							if (propertyCacheById.get(foundProp.getPublicId()) == null) {
								propertyCacheById.put(foundProp.getPublicId(), foundProp);
								
								addCSCSIs(originalDEC, foundProp);
								foundDECProps.add(foundProp);
							}
						}
						
						if (originalDEC.getPublicId() == null) {
							foundDEC.setPublicId(null);
							foundDEC.setVersion(null);
							foundDEC.setConceptualDomain(originalDEC.getConceptualDomain());
							foundDEC.setContext(originalDEC.getContext());
						}
					}
					else {
						ConceptualDomain originalCD = originalDEC.getConceptualDomain();
						if (originalCD != null && (originalCD.getPublicId() != null || originalCD.getLongName() != null || originalCD.getPreferredName() != null)) {
							originalDEC.setConceptualDomain(findConceptualDomain(originalCD));
						}
						else {
							originalDEC.setConceptualDomain(loadObjects.getLoadConceptualDomain());
						}
					}
					replaceCSCSIs(foundDEC);
				}
			});
			
			caDSRObjects.setDataElementConcepts(lookedUpDataElementConcepts);
			
			addObjectClasses(caDSRObjects, foundDECOCs);
			addProperties(caDSRObjects, foundDECProps);
		}
	}
	
	private void processValueDomains(CaDSRObjects caDSRObjects, final LoadObjects loadObjects) {
		List<ValueDomain> valueDomains = caDSRObjects.getValueDomains();
		if (valueDomains != null) {
			List<ValueDomain> lookedUpValueDomains = loadValueDomains(valueDomains, new AdminComponentLoaderCallback<ValueDomain>() {

				@Override
				public void doProcess(ValueDomain originalVD, ValueDomain foundVD) {
					if (originalVD != null && foundVD != null
							&& originalVD != foundVD 
							&& foundVD.getPublicId() != null) {
						addCSCSIs(foundVD, foundVD);
						if (originalVD.getPublicId() == null || originalVD.getVersion() == null) {
							foundVD.setPublicId(originalVD.getPublicId());
							foundVD.setVersion(originalVD.getVersion());
							foundVD.setDataType(originalVD.getDataType());
							foundVD.setMaximumLength(originalVD.getMaximumLength());
							foundVD.setValueDomainPermissibleValues(originalVD.getValueDomainPermissibleValues());
							foundVD.setRepresentation(originalVD.getRepresentation());
							foundVD.setConceptualDomain(originalVD.getConceptualDomain());
							foundVD.setConceptDerivationRule(originalVD.getConceptDerivationRule());
							foundVD.setLongName(originalVD.getLongName());
							foundVD.setVdType(originalVD.getVdType());
						}
					}
					else {
						setVMContext(originalVD, loadObjects.getLoadContext());
						ConceptualDomain originalCD = originalVD.getConceptualDomain();
						if (originalCD != null) {
							originalVD.setConceptualDomain(findConceptualDomain(originalCD));
						}
					}
					
					replaceCSCSIs(foundVD);
				}
			});
			caDSRObjects.setValueDomains(lookedUpValueDomains);
		}
	}
	
	private void processDataElements(CaDSRObjects caDSRObjects) {
		List<DataElement> dataElements = caDSRObjects.getDataElements();
		final List<DataElementConcept> deDECs = new ArrayList<DataElementConcept>();
		final List<ObjectClass> deDECOCs = new ArrayList<ObjectClass>();
		final List<Property> deDECProps = new ArrayList<Property>();
		if (dataElements != null) {
			List<DataElement> lookedUpDataElements = loadDataElements(dataElements, new AdminComponentLoaderCallback<DataElement>() {
				
				@Override
				public void doProcess(DataElement originalDE, DataElement foundDE) {
					
					if (originalDE != null && foundDE != null 
							&& originalDE != foundDE 
							&& foundDE.getPublicId() != null) {
						foundDE.removeAlternateNames();
						foundDE.removeDefinitions();
						
						foundDE = addAlternateNames(originalDE, foundDE);
						foundDE = addRefDocs(originalDE, foundDE);
						
						foundDE = addCSCSIs(originalDE, foundDE); // add new classification (if any) to the existing DE
						
						if (foundDE.getDataElementConcept() != null && foundDE.getDataElementConcept().getPublicId() != null) {
							DataElementConcept foundDEC = foundDE.getDataElementConcept();
							if (dataElementConceptCacheById.get(foundDEC.getPublicId()) == null) {
								dataElementConceptCacheById.put(foundDEC.getPublicId(), foundDEC);
								addCSCSIs(originalDE, foundDEC); // add new classification (if any) to the existing DEC
								deDECs.add(foundDEC);
							}
							
							if (foundDEC.getObjectClass() != null && foundDEC.getObjectClass().getPublicId() != null) {
								if (objectClassCacheById.get(foundDEC.getObjectClass().getPublicId()) == null) {
									objectClassCacheById.put(foundDEC.getObjectClass().getPublicId(), foundDEC.getObjectClass());
									addCSCSIs(originalDE, foundDEC.getObjectClass());
									deDECOCs.add(foundDEC.getObjectClass());
								}
							}
							
							if (foundDEC.getProperty() != null && foundDEC.getProperty().getPublicId() != null) {
								if (propertyCacheById.get(foundDEC.getProperty().getPublicId()) == null) {
									propertyCacheById.put(foundDEC.getProperty().getPublicId(), foundDEC.getProperty());
									addCSCSIs(originalDE, foundDEC.getProperty());
									deDECProps.add(foundDEC.getProperty());
								}
							}
						}
					}
					
					replaceCSCSIs(foundDE);
				}
			});
			
			caDSRObjects.setDataElements(lookedUpDataElements);
			addDECs(caDSRObjects, deDECs);
			addObjectClasses(caDSRObjects, deDECOCs);
			addProperties(caDSRObjects, deDECProps);
		}
	}
	
	private List<ObjectClass> loadObjectClasses(List<ObjectClass> createdObjectClasses, AdminComponentLoaderCallback<ObjectClass> callback) {
		List<ObjectClass> lookedUpOCs = new ArrayList<ObjectClass>();
		
		for (ObjectClass createdOC: createdObjectClasses) {
			ObjectClass ocToBeAdded = createdOC;
			List<ObjectClass> foundOCs = findObjectClasses(createdOC);
			if (foundOCs.size() > 0) {
				ObjectClass foundOC = foundOCs.get(0);
				objectClassCacheById.put(foundOC.getPublicId(), foundOC);
				objectClassCache.put(createdOC, foundOC);
				
				ocToBeAdded = foundOC;
			}
			else {
				ocToBeAdded = createdOC;
			}
			
			lookedUpOCs.add(ocToBeAdded);
			callback.doProcess(createdOC, ocToBeAdded);
		}
		return lookedUpOCs;
	}
	
	private List<Property> loadProperties(List<Property> createdProperties, AdminComponentLoaderCallback<Property> callback) {
		List<Property> lookedUpProps = new ArrayList<Property>();
		
		for (Property createdProp: createdProperties) {
			Property propToBeAdded = createdProp;
			List<Property> foundProps = findProperties(createdProp);
			if (foundProps.size() > 0) {
				Property foundProp = foundProps.get(0);
				propertyCacheById.put(foundProp.getPublicId(), foundProp);
				propertyCache.put(createdProp, foundProp);
				propToBeAdded = foundProp;
				
			}
			else {
				propToBeAdded = createdProp;
			}
			
			lookedUpProps.add(propToBeAdded);
			callback.doProcess(createdProp, propToBeAdded);
		}
		return lookedUpProps;
	}
	
	private List<DataElementConcept> loadDataElementConcepts(List<DataElementConcept> createdDataElementConcepts, AdminComponentLoaderCallback<DataElementConcept> postProcessor) {
		List<DataElementConcept> lookedUpDECs = new ArrayList<DataElementConcept>();
		
		for (DataElementConcept createdDEC: createdDataElementConcepts) {
			DataElementConcept decToLoad = null;
			ObjectClass createdOC = createdDEC.getObjectClass();
			Property createdProp = createdDEC.getProperty();
			String publicId = createdDEC.getPublicId();
			Float version = createdDEC.getVersion();
			
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
			
			decToLoad = createdDEC;
			
			if (publicId != null && version != null) {
				DataElementConcept foundDEC = findDataElementConceptById(Integer.parseInt(publicId), version.floatValue());
				if (foundDEC != null && foundDEC.getPublicId() != null) {
					decToLoad = foundDEC;
				}
			}
			else if (createdDEC.getObjectClass() != null && createdDEC.getObjectClass().getId() != null 
					&& createdDEC.getProperty() != null && createdDEC.getProperty().getPublicId() != null) {
				DataElementConcept searchDEC = DomainObjectFactory.newDataElementConcept();
				searchDEC.setObjectClass(createdDEC.getObjectClass());
				searchDEC.setProperty(createdDEC.getProperty());
				
				List<DataElementConcept> foundDECs = findDataElementConcepts(searchDEC);
				
				if (foundDECs != null && foundDECs.size() > 0) {
					DataElementConcept foundDEC = foundDECs.get(0);
					decToLoad = foundDEC;
				}
			}
			
			if (decToLoad != createdDEC) {
				dataElementConceptCacheById.put(decToLoad.getPublicId(), decToLoad);
				dataElementConceptCache.put(createdDEC, decToLoad);
			}
			
			postProcessor.doProcess(createdDEC, decToLoad);
			lookedUpDECs.add(decToLoad);
		}
		
		return lookedUpDECs;
	}
	
	private List<ValueDomain> loadValueDomains(List<ValueDomain> createdValueDomains, AdminComponentLoaderCallback<ValueDomain> callback) {
		List<ValueDomain> lookedUpVDs = new ArrayList<ValueDomain>();
		
		for (ValueDomain createdVD: createdValueDomains) {
			ValueDomain vdToBeAdded = null;
			String publicId = createdVD.getPublicId();
			Float version = createdVD.getVersion();
			
			vdToBeAdded = createdVD;
			
			if (publicId != null && version != null) {
				ValueDomain foundVD = findValueDomainById(Integer.parseInt(publicId), version.floatValue());
				if (foundVD != null && foundVD.getPublicId() != null) {
					vdToBeAdded = foundVD;
				}
			}
			else {
				List<ValueDomain> foundVDs = findValueDomains(createdVD);
				if (foundVDs.size() > 0) {
					ValueDomain foundVD = foundVDs.get(0);
					vdToBeAdded = foundVD;
				}
			}
			
			if (vdToBeAdded != createdVD) {
				valueDomainCacheById.put(vdToBeAdded.getPublicId(), vdToBeAdded);
				valueDomainCache.put(createdVD, vdToBeAdded);
			}
			
			callback.doProcess(createdVD, vdToBeAdded);
			lookedUpVDs.add(vdToBeAdded);
		}
		
		return lookedUpVDs;
	}
	
	private List<DataElement> loadDataElements(List<DataElement> createdDataElements, AdminComponentLoaderCallback<DataElement> postProcessor) {
		List<DataElement> lookedUpDEs = new ArrayList<DataElement>();
		
		for (DataElement createdDE: createdDataElements) {
			DataElement deToBeAdded = null;
			DataElementConcept lookedUpDEC = createdDE.getDataElementConcept();
			ValueDomain lookedUpVD = createdDE.getValueDomain();
			String publicId = createdDE.getPublicId();
			Float version = createdDE.getVersion();
			
			if (lookedUpDEC != null && dataElementConceptCache.get(lookedUpDEC) != null) {
				createdDE.setDataElementConcept(dataElementConceptCache.get(lookedUpDEC));
			}
			
			if (lookedUpVD != null && valueDomainCache.get(lookedUpVD) != null) {
				createdDE.setValueDomain(valueDomainCache.get(lookedUpVD));
			}
			
			deToBeAdded = createdDE;
			
			if (publicId != null && version != null) {
				DataElement foundDE = findDataElementById(Integer.parseInt(publicId), version);
				if (foundDE != null && foundDE.getPublicId() != null) {
					deToBeAdded = foundDE;
				}
			}
			else if (createdDE.getDataElementConcept() != null && createdDE.getDataElementConcept().getId() != null 
					&& createdDE.getValueDomain() != null && createdDE.getValueDomain().getId() != null) {
				DataElement searchableDE = getSearchableDEByDECAndVD(createdDE);
				
				List<DataElement> foundDEs = findDataElements(searchableDE);
				if (foundDEs.size() > 0) {
					DataElement foundDE = foundDEs.get(0);
					
					deToBeAdded = foundDE;
				}
			}
			
			postProcessor.doProcess(createdDE, deToBeAdded);
			
			lookedUpDEs.add(deToBeAdded);
		}
		
		return lookedUpDEs;
	}
	
	private DataElement getSearchableDEByDECAndVD(DataElement originalDE) {
		DataElement de = DomainObjectFactory.newDataElement();
		de.setDataElementConcept(originalDE.getDataElementConcept());
		de.setValueDomain(originalDE.getValueDomain());
		de.setContext(originalDE.getContext());
		de.setDeletedIndicator(originalDE.getDeletedIndicator());
		de.setWorkflowStatus(originalDE.getWorkflowStatus());
		de.setLatestVersionIndicator(originalDE.getLatestVersionIndicator());
		return de;
	}
	
	private ConceptualDomain findConceptualDomain(ConceptualDomain createdCD) {
		List<ConceptualDomain> conceptualDomains = findConceptualDomains(createdCD);
		if (conceptualDomains != null && conceptualDomains.size() > 0) {
			return conceptualDomains.get(0);
		}
		else {
			return DomainObjectFactory.newConceptualDomain();
		}
	}
	
	private void addObjectClasses (CaDSRObjects caDSRObjects, List<ObjectClass> objectClasses) {
		if (objectClasses.size() > 0) {
			if (caDSRObjects.getObjectClasses() != null) {
				caDSRObjects.getObjectClasses().addAll(objectClasses);
			}
			else {
				caDSRObjects.setObjectClasses(objectClasses);
			}
		}
	}
	
	private void addProperties (CaDSRObjects caDSRObjects, List<Property> properties) {
		if (properties.size() > 0) {
			if (caDSRObjects.getProperties() != null) {
				caDSRObjects.getProperties().addAll(properties);
			}
			else {
				caDSRObjects.setProperties(properties);
			}
		}
	}
	
	private void addDECs(CaDSRObjects caDSRObjects, List<DataElementConcept> decs) {
		if (decs.size() > 0) {
			if (caDSRObjects.getDataElementConcepts() != null) {
				caDSRObjects.getDataElementConcepts().addAll(decs);
			}
			else {
				caDSRObjects.setDataElementConcepts(decs);
			}
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
	
	private <T extends AdminComponent> T addAlternateNames(T createdAC, T foundAC) {
		if (createdAC!=null && foundAC!=null && createdAC.getAlternateNames() != null) {
			String foundDELongName = foundAC.getLongName();
			for (AlternateName altName: createdAC.getAlternateNames()) {
				String altLongName = altName.getName();
				if (altLongName!=null && (foundDELongName == null || !foundDELongName.equalsIgnoreCase(altLongName))) {
					foundAC.addAlternateName(altName);
				}
			}
		}
		
		return foundAC;
	}
	
	private <T extends AdminComponent> T addRefDocs(T createdDE, T foundAC) {
		if (createdDE!=null && foundAC!=null && createdDE.getReferenceDocuments() != null) {
			List foundRefDocs = foundAC.getReferenceDocuments();
			if (foundRefDocs == null) {
				foundRefDocs = new ArrayList();
			}
			foundRefDocs.addAll(createdDE.getReferenceDocuments());
		}
		
		return foundAC;
	}
	
	private <T extends AdminComponent> T addCSCSIs(T createdAC, T foundAC) {
		if (createdAC.getAcCsCsis() != null) {
			foundAC.setAcCsCsis(createdAC.getAcCsCsis());
		}
		
		return foundAC;
	}
	
	private interface AdminComponentLoaderCallback<T extends AdminComponent> {
		public void doProcess(T original, T found );
	}

	@Override
	public ConceptualDomain findConceptualDomainById(int publicId,
			double version) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ConceptualDomain> findConceptualDomains(
			ConceptualDomain conceptualDomain) {
		return readDAO.findConceptualDomains(conceptualDomain);
	}
	
	public boolean sourceExists(String sourceName) {
		return readDAO.sourceExists(sourceName);
	}
}
