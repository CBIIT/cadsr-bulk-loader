package gov.nih.nci.ncicb.cadsr.bulkloader.ext;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.persister.Persister;
import gov.nih.nci.ncicb.cadsr.loader.persister.PersisterException;

import java.util.Collection;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 20, 2009
 * @since 
 */

public class ExternalLoaderImpl implements ExternalLoader{

	private final String PROPERTIES_FILE_NAME = "/bulkloader.properties";
	
	private Persister persister;

	private ElementsLists elements = ElementsLists.getInstance();
	private UMLDefaults defaults = UMLDefaults.getInstance();
	private boolean propertiesLoaded;
	
	
	public Persister getPersister() {
		return persister;
	}

	public void setPersister(Persister persister) {
		this.persister = persister;
	}
	
	public void save(Collection<? extends AdminComponent> adminComponents) throws BulkLoaderDAORuntimeException{
		
		loadDefaultsIfNotLoaded();
		
		addElementsToSave(adminComponents);
		
		loadComponentDefaults(adminComponents);
		
		persist();
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
		defaults.initParams(PROPERTIES_FILE_NAME);
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
	
	private void persist() {
		try {
			persister.persist();
		} catch (PersisterException e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}

}
