package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BulkLoaderWriteDAOImpl implements BulkLoaderWriteDAO {
	
	private LoaderDefault currentDefault;
	
	private ElementsLists elements = ElementsLists.getInstance();
	private UMLDefaults defaults = UMLDefaults.getInstance();
	private boolean propertiesLoaded;

	private List<Persister> persisters;
	
	public List<Persister> getPersisters() {
		return persisters;
	}

	public void setPersisters(List<Persister> persisters) {
		this.persisters = persisters;
	}

	public synchronized void save(CaDSRObjects cadsrObjects, LoadObjects loadObjects) {
		try {
			loadDefaultsIfNotLoaded(loadObjects);
			loadElementsList(cadsrObjects);
			
			for (Persister persister: persisters) {
				persister.persist();
			}
			
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
		finally {
			clearElementsList(cadsrObjects);
		}
	}
	
	private void loadElementsList(CaDSRObjects cadsrObjects) {
		List<AdminComponent> elementsList = new ArrayList<AdminComponent>();
		
		List<Concept> concepts = cadsrObjects.getConcepts();
		List<ObjectClass> objectClasses = cadsrObjects.getObjectClasses();
		List<Property> properties = cadsrObjects.getProperties();
		List<DataElementConcept> dataElementConcepts = cadsrObjects.getDataElementConcepts();
		List<ValueDomain> valueDomains = cadsrObjects.getValueDomains();
		List<DataElement> dataElements = cadsrObjects.getDataElements();
		
		if (concepts != null) {
			elementsList.addAll(concepts);
		}
		if (objectClasses != null) {
			elementsList.addAll(objectClasses);
		}
		if (properties != null) {
			elementsList.addAll(properties);
		}
		if (dataElementConcepts != null) {
			elementsList.addAll(dataElementConcepts);
		}
		if (valueDomains != null) {
			elementsList.addAll(valueDomains);
		}
		if (dataElements != null) {
			elementsList.addAll(dataElements);
		}
		
		loadComponentDefaults(elementsList);
		
		for (AdminComponent adminComp: elementsList) {
			elements.addElement(adminComp);
		}
	}
	
	private void clearElementsList(CaDSRObjects cadsrObjects) {
		List<Concept> concepts = elements.getElements(CaDSRObjectsUtil.createConcept());
		List<ObjectClass> objectClasses = elements.getElements(CaDSRObjectsUtil.createObjectClass());
		List<Property> properties = elements.getElements(CaDSRObjectsUtil.createProperty());
		List<DataElementConcept> dataElementConcept = elements.getElements(CaDSRObjectsUtil.createDataElementConcept());
		List<ValueDomain> valueDomains = elements.getElements(CaDSRObjectsUtil.createValueDomain());
		List<DataElement> dataElements = elements.getElements(CaDSRObjectsUtil.createDataElement());
		
		List<AdminComponent> elementsList = new ArrayList<AdminComponent>();
		if (concepts != null) elementsList.addAll(concepts);
		if (objectClasses != null) elementsList.addAll(objectClasses);
		if (properties != null) elementsList.addAll(properties);
		if (dataElementConcept != null) elementsList.addAll(dataElementConcept);
		if (valueDomains != null) elementsList.addAll(valueDomains);
		if (dataElements != null) elementsList.addAll(dataElements);
		
		for (AdminComponent adminComp: elementsList) {
			elements.removeElement(adminComp);
		}
	}
	
	
	
	private synchronized void loadDefaultsIfNotLoaded(LoadObjects loadObjects) throws BulkLoaderDAORuntimeException{
		
		if (!propertiesLoaded) {
			try {
				loadPropertiesAndSetFlag(loadObjects);
			} catch (PersisterException e) {
				throw new BulkLoaderDAORuntimeException(e);
			}
		}
		
	}
	
	private void loadPropertiesAndSetFlag(LoadObjects loadObjects) throws PersisterException {
		LoaderDefault loaderDefault = getLoaderDefault(loadObjects);
		
		if (currentDefault == null || !currentDefault.equals(loaderDefault)) {
			currentDefault = loaderDefault;
			
			defaults.useDefaults(loaderDefault);
			defaults.initWithDB();
			propertiesLoaded = true;
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
	
	private LoaderDefault getLoaderDefault(LoadObjects loadObjects) {
		
		LoaderDefault loaderDefault = new LoaderDefaultImpl();
		loaderDefault.setContextName(loadObjects.getLoadContext().getName());
		
		ClassificationScheme loadClassScheme = loadObjects.getLoadClassScheme();
		loaderDefault.setProjectLongName(loadClassScheme.getLongName());
		loaderDefault.setProjectName(loadClassScheme.getPreferredName());
		loaderDefault.setProjectDescription(loadClassScheme.getPreferredDefinition());
		
		return loaderDefault;
	}
	
	private class LoaderDefaultImpl implements LoaderDefault {
		private String contextName;
		private String projectDescription;
		private String projectLongName;
		private String projectName;
		
		public String getCdContextName() {
			return "caBIG";
		}
		public String getCdName() {
			return "CABIG";
		}
		public String getContextName() {
			if (contextName == null) {
				return "";
			}
			else {
				return contextName;
			}
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
			if (projectDescription == null) {
				return "";
			}
			else {
				return projectDescription;
			}
		}
		public String getProjectLongName() {
			if (projectLongName == null) {
				return "";
			}
			else {
				return projectLongName;
			}
		}
		public String getProjectName() {
			if (projectName == null) {
				return "";
			}
			else {
				return projectName;
			}
		}
		public Float getProjectVersion() {
			return new Float(1.0);
		}
		public Float getVersion() {
			return new Float(1.0);
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
			contextName = newContextName;
		}
		public void setId(String newId) {
			// TODO Auto-generated method stub
			
		}
		public void setPackageFilter(String newPackageFilter) {
			// TODO Auto-generated method stub
			
		}
		public void setProjectDescription(String newProjectDescription) {
			projectDescription = newProjectDescription;
			
		}
		public void setProjectLongName(String newProjectLongName) {
			projectLongName = newProjectLongName;
		}
		public void setProjectName(String newProjectName) {
			projectName = newProjectName;
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
		
		public boolean equals(Object o) {
			if (!(o instanceof LoaderDefault)) {
				return false;
			}
			LoaderDefault toCheck = (LoaderDefault)o;
			if (toCheck.getContextName().equals(getContextName()) 
					&& toCheck.getProjectDescription().equals(getProjectDescription())
					&& toCheck.getProjectLongName().equals(getProjectDescription())
					&& toCheck.getProjectName().equals(getProjectName())
					&& toCheck.getProjectVersion().equals(getProjectVersion())
					&& toCheck.getWorkflowStatus().equals(getWorkflowStatus())) {
				return true;
			}
			return false;
		}
	}
}
