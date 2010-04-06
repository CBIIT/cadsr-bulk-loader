package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.LoaderDefault;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.UserSelections;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.util.Collection;
import java.util.List;

public class UMLLoaderHandler {
	
	private LoaderDefault currentDefault;
	private ElementsLists elements = ElementsLists.getInstance();
	private UMLDefaults defaults = UMLDefaults.getInstance();
	private boolean propertiesLoaded;
	private static LoadObjects loadObjects;

	
	public static LoadObjects getLoadObjects() {
		return loadObjects;
	}

	public synchronized void loadElements(CaDSRObjects caDSRObjects, LoadObjects loadObjects) {
		loadDefaultsIfNotLoaded(loadObjects);
		
		List<? extends AdminComponent> adminComponents = caDSRObjects.getList();
		
		if (adminComponents != null && adminComponents.size() > 0) {
			for (AdminComponent adminComponent: adminComponents) {
				elements.removeElement(adminComponent);
				elements.addElement(adminComponent);
			}
		}
		
		Context loadContext = loadObjects.getLoadContext();
		elements.removeElement(loadContext);
		elements.addElement(loadContext);
		
		ClassificationScheme loadClassScheme = loadObjects.getLoadClassScheme();			
		elements.removeElement(loadClassScheme);
		elements.addElement(loadClassScheme);
		
		loadComponentDefaults(adminComponents);
		
	}
	
	public synchronized void unLoadElements() {
		elements.clear();
	}
	
	public synchronized void loadDefaultsIfNotLoaded(LoadObjects loadObjects) throws BulkLoaderDAORuntimeException{
		
		if (!propertiesLoaded) {
			try {
				loadPropertiesAndSetFlag(loadObjects);
			} catch (PersisterException e) {
				throw new BulkLoaderDAORuntimeException(e);
			}
		}
		
	}
	
	private void loadPropertiesAndSetFlag(LoadObjects _loadObjects) throws PersisterException {
		loadObjects = _loadObjects;
		LoaderDefault loaderDefault = getLoaderDefault(_loadObjects);
		
		if (currentDefault == null || !currentDefault.equals(loaderDefault)) {
			currentDefault = loaderDefault;
			
			defaults.useDefaults(loaderDefault);
			defaults.initWithDB();
			
			UserSelections.getInstance().setProperty("ignore-vd", new Boolean(false));
			UserPreferences userPrefs = SpringBeansUtil.getInstance().getUserPreferences();
			userPrefs.setUsePrivateApi(true);
			
			propertiesLoaded = true;
		}
		
	}
	
	public <T extends AdminComponent> void loadComponentDefaults(Collection<T> adminComps) {
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
	
	public static String getDefaultEVSDefinition() {
		return PropertyAccessor.getProperty("default.evs.definition");
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
			return "DRAFT NEW";
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
