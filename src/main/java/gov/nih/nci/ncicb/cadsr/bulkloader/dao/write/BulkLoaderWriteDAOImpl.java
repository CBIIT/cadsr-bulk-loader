package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.LoaderDefault;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.persister.Persister;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class BulkLoaderWriteDAOImpl implements BulkLoaderWriteDAO {

	private final String PROPERTIES_FILE_NAME = "/bulkloader.properties";

	private final String CONCEPT_PERSISTER_REF = "concept";
	private final String OC_PERSISTER_REF = "objectClass";
	private final String PROP_PERSISTER_REF = "property";
	private final String DEC_PERSISTER_REF = "dataElementConcept";
	private final String VD_PERSISTER_REF = "valueDomain";
	private final String DE_PERSISTER_REF = "dataElement";
	
	private ElementsLists elements = ElementsLists.getInstance();
	private UMLDefaults defaults = UMLDefaults.getInstance();
	private boolean propertiesLoaded;

	private HashMap<String, Persister> persisterMap;
	
	public HashMap<String, Persister> getPersisterMap() {
		return persisterMap;
	}

	public void setPersisterMap(HashMap<String, Persister> persisterMap) {
		this.persisterMap = persisterMap;
	}
	
	public void saveConcepts(Collection<Concept> adminComponents) {
		Persister conceptPersister = persisterMap.get(CONCEPT_PERSISTER_REF);
		save(adminComponents, conceptPersister);
	}

	public void saveDataElementConcepts(Collection<DataElementConcept> adminComponents) {
		Persister conceptPersister = persisterMap.get(DEC_PERSISTER_REF);
		save(adminComponents, conceptPersister);
	}

	public void saveDataElements(Collection<DataElement> adminComponents) {
		Persister conceptPersister = persisterMap.get(DE_PERSISTER_REF);
		save(adminComponents, conceptPersister);
	}

	public void saveObjectClasses(Collection<ObjectClass> adminComponents) {
		Persister conceptPersister = persisterMap.get(OC_PERSISTER_REF);
		save(adminComponents, conceptPersister);
	}

	public void saveProperties(Collection<Property> adminComponents) {
		Persister conceptPersister = persisterMap.get(PROP_PERSISTER_REF);
		save(adminComponents, conceptPersister);
	}

	public void saveValueDomains(Collection<ValueDomain> adminComponents) {
		Persister conceptPersister = persisterMap.get(VD_PERSISTER_REF);
		save(adminComponents, conceptPersister);
	}
	
	public void save(Collection<? extends AdminComponent> adminComponents, Persister persister) throws BulkLoaderDAORuntimeException{
		
		try {
			loadDefaultsIfNotLoaded();
			
			addElementsToSave(adminComponents);
			
			loadComponentDefaults(adminComponents);
			
			persister.persist();
		} catch (PersisterException e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}
	
	private synchronized void loadDefaultsIfNotLoaded() throws BulkLoaderDAORuntimeException{
		
		if (!propertiesLoaded) {
			try {
				loadPropertiesAndSetFlag();
			} catch (PersisterException e) {
				throw new BulkLoaderDAORuntimeException(e);
			}
		}
		
	}
	
	private void loadPropertiesAndSetFlag() throws PersisterException {
		LoaderDefault loaderDefault = getLoaderDefault();
		defaults.updateDefaults(loaderDefault);
		//defaults.initParams(PROPERTIES_FILE_NAME);
		defaults.initWithDB();
		propertiesLoaded = true;
	}
	
	private void addElementsToSave(Collection<? extends AdminComponent> adminComponents) {
		for (Object adminComponent: adminComponents) {
			elements.addElement(adminComponent);
		}
	}
	
	private <T extends AdminComponent> void loadComponentDefaults(Collection<T> adminComps) {
		for (AdminComponent adminComp: adminComps) {
			loadAdminComponentCSCSI(adminComp);
		}
	}
	
	private void loadAdminComponentCSCSI(AdminComponent adminComp) {
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIs = adminComp.getAcCsCsis();
		if (acCSCSIs != null) {
			for (AdminComponentClassSchemeClassSchemeItem acCSCSI: acCSCSIs) {
				loadCSCSIAsDefaults(acCSCSI);
			}
		}
	}
	
	private void loadCSCSIAsDefaults(AdminComponentClassSchemeClassSchemeItem acCSCSI) {
		ClassSchemeClassSchemeItem csCSI = acCSCSI.getCsCsi();
		ClassificationSchemeItem csi = csCSI.getCsi();
		String csiLongName = csi.getLongName();
		defaults.getPackageCsCsis().put(csiLongName, csCSI);
	}
	
	private LoaderDefault getLoaderDefault() {
		
		LoaderDefault loaderDefault = new LoaderDefault() {

			public String getCdContextName() {
				return "caBIG";
			}
			public String getCdName() {
				return "CABIG";
			}
			public String getContextName() {
				return "NHLBI";
			}
			public String getId() {
				// TODO Auto-generated method stub
				return null;
			}
			public String getPackageFilter() {
				// TODO Auto-generated method stub
				return null;
			}
			public String getProjectDescription() {
				return "caDSR Bulk Loader";
			}
			public String getProjectLongName() {
				return "caDSR Bulk Loader";
			}
			public String getProjectName() {
				return "caDSR Bulk Loader";
			}
			public Float getProjectVersion() {
				return new Float(0.5);
			}
			public Float getVersion() {
				return new Float(3.0);
			}
			public String getWorkflowStatus() {
				return "RELEASED";
			}
			public void setCdContextName(String newCdContextName) {
				// TODO Auto-generated method stub
				
			}
			public void setCdName(String newCdName) {
				// TODO Auto-generated method stub
				
			}
			public void setContextName(String newContextName) {
				// TODO Auto-generated method stub
				
			}
			public void setId(String newId) {
				// TODO Auto-generated method stub
				
			}
			public void setPackageFilter(String newPackageFilter) {
				// TODO Auto-generated method stub
				
			}
			public void setProjectDescription(String newProjectDescription) {
				// TODO Auto-generated method stub
				
			}
			public void setProjectLongName(String newProjectLongName) {
				// TODO Auto-generated method stub
				
			}
			public void setProjectName(String newProjectName) {
				// TODO Auto-generated method stub
				
			}
			public void setProjectVersion(Float newVersion) {
				// TODO Auto-generated method stub
				
			}
			public void setVersion(Float newVersion) {
				// TODO Auto-generated method stub
				
			}
			public void setWorkflowStatus(String newWorkflowStatus) {
				// TODO Auto-generated method stub
				
			}
		};
		
		return loaderDefault;
	}
	
}
