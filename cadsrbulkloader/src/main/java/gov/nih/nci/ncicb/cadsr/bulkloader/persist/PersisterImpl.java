package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PersisterImpl implements Persister {

	private BulkLoaderDAOFacade dao;
	
	private HashMap<String, DataElementConcept> lookedUpDECsCache = new HashMap<String, DataElementConcept>();
	private HashMap<String, ValueDomain> lookedUpVDsCache = new HashMap<String, ValueDomain>();
	
	public BulkLoaderDAOFacade getDao() {
		return dao;
	}

	public void setDao(BulkLoaderDAOFacade dao) {
		this.dao = dao;
	}

	public PersisterResult persist(CaDSRObjects cadsrObjects, LoadObjects loadObjects) {
		PersisterResult result = new PersisterResult();
		result.setCaDSRObjects(cadsrObjects);
		
		try {

			List<DataElement> dataElements = cadsrObjects.getDataElements();
			List<DataElementConcept> dataElementConcepts = cadsrObjects.getDataElementConcepts();
			List<ValueDomain> valueDomains = cadsrObjects.getValueDomains();
						
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
			
			dao.save(cadsrObjects, loadObjects);
			
			result.setStatus(PersisterStatus.SUCCESS);
			
		} catch (Exception e) {
			result.setException(e);
			result.setStatus(PersisterStatus.FAILURE);
		}
		
		return result;
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
			
			ClassificationScheme retrievedCS = dao.getClassificationScheme(classScheme);
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
	
	private List<DataElementConcept> loadDataElementConcepts(List<DataElementConcept> createdDataElementConcepts) {
		List<DataElementConcept> lookedUpDECs = new ArrayList<DataElementConcept>();
		
		for (DataElementConcept createdDEC: createdDataElementConcepts) {
			List<DataElementConcept> foundDECs = dao.findDataElementConcepts(createdDEC);
			if (foundDECs.size() > 0) {
				DataElementConcept foundDEC = foundDECs.get(0);
				lookedUpDECsCache.put(foundDEC.getPublicId(), foundDEC);
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
			List<ValueDomain> foundVDs = dao.findValueDomains(createdVD);
			if (foundVDs.size() > 0) {
				ValueDomain foundVD = foundVDs.get(0);
				lookedUpVDsCache.put(foundVD.getPublicId(), foundVD);
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
			List<DataElement> foundDEs = dao.findDataElements(createdDE);
			if (foundDEs.size() > 0) {
				DataElement foundDE = foundDEs.get(0);
				foundDE.removeAlternateNames();
				foundDE.removeDefinitions();
				
				lookedUpDEs.add(foundDE);
				if (createdDE.getAcCsCsis() != null) {
					foundDE.setAcCsCsis(createdDE.getAcCsCsis());
				}
				if (createdDE.getAlternateNames() != null) {
					String foundDELongName = foundDE.getLongName();
					for (AlternateName altName: createdDE.getAlternateNames()) {
						String altLongName = altName.getName();
						if (altLongName!=null && !foundDELongName.equalsIgnoreCase(altLongName)) {
							foundDE.addAlternateName(altName);
						}
					}
				}
			}
			else {
				DataElementConcept dec = createdDE.getDataElementConcept();
				ValueDomain vd = createdDE.getValueDomain();
				
				if (dec != null) {
					String decPublicId = dec.getPublicId();
					DataElementConcept lookedUpDEC = lookedUpDECsCache.get(decPublicId);
					if (lookedUpDEC != null) {
						createdDE.setDataElementConcept(lookedUpDEC);
					}
				}
				
				if (vd != null) {
					String vdPublicId = vd.getPublicId();
					ValueDomain lookedUpVD = lookedUpVDsCache.get(vdPublicId);
					if (lookedUpVD != null) {
						createdDE.setValueDomain(lookedUpVD);
					}
				}
				
				lookedUpDEs.add(createdDE);
			}
		}
		
		return lookedUpDEs;
	}

}
