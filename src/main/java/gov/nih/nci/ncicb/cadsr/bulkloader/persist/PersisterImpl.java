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
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.List;

public class PersisterImpl implements Persister {

	private BulkLoaderDAOFacade dao;
	
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
			
			if (dataElements != null) {
				List<DataElement> lookedUpDataElements = loadDataElements(dataElements);
				replaceCSCSIs(lookedUpDataElements);
				cadsrObjects.setDataElements(lookedUpDataElements);
			}
			
			if (dataElementConcepts != null) {
				List<DataElementConcept> lookedUpDataElementConcepts = loadDataElementConcepts(dataElementConcepts);
				cadsrObjects.setDataElementConcepts(lookedUpDataElementConcepts);
			}
			
			if (valueDomains != null) {
				List<ValueDomain> lookedUpValueDomains = loadValueDomains(valueDomains);
				cadsrObjects.setValueDomains(lookedUpValueDomains);
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
	
	private List<DataElement> loadDataElements(List<DataElement> createdDataElements) {
		List<DataElement> lookedUpDEs = new ArrayList<DataElement>();
		
		for (DataElement createdDE: createdDataElements) {
			List<DataElement> foundDEs = dao.findDataElements(createdDE);
			if (foundDEs.size() > 0) {
				DataElement foundDE = foundDEs.get(0);
				lookedUpDEs.add(foundDE);
				if (createdDE.getAcCsCsis() != null) {
					foundDE.setAcCsCsis(createdDE.getAcCsCsis());
				}
				if (createdDE.getAlternateNames() != null) {
					for (AlternateName altName: createdDE.getAlternateNames()) {
						foundDE.addAlternateName(altName);
					}
				}
			}
			else {
				lookedUpDEs.add(createdDE);
			}
		}
		
		return lookedUpDEs;
	}
	
	private List<DataElementConcept> loadDataElementConcepts(List<DataElementConcept> createdDataElementConcepts) {
		List<DataElementConcept> lookedUpDECs = new ArrayList<DataElementConcept>();
		
		for (DataElementConcept createdDEC: createdDataElementConcepts) {
			List<DataElementConcept> foundDECs = dao.findDataElementConcepts(createdDEC);
			if (foundDECs.size() > 0) {
				DataElementConcept foundDEC = foundDECs.get(0);
				lookedUpDECs.add(foundDEC);
			}
			else {
				lookedUpDECs.add(createdDEC);
			}
		}
		
		return lookedUpDECs;
	}
	
	private List<ValueDomain> loadValueDomains(List<ValueDomain> createdValueDomains) {
		List<ValueDomain> lookedUpVDs = new ArrayList<ValueDomain>();
		
		for (ValueDomain createdVD: createdValueDomains) {
			List<ValueDomain> foundVDs = dao.findValueDomains(createdVD);
			if (foundVDs.size() > 0) {
				lookedUpVDs.add(foundVDs.get(0));
			}
			else {
				lookedUpVDs.add(createdVD);
			}
		}
		
		return lookedUpVDs;
	}

}
