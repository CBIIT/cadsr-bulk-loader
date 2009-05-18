package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.Validator;

import java.util.List;

public class IdValidator implements Validator {

	private ElementsLists elementsList = ElementsLists.getInstance();
	private BulkLoaderDAOFacade dao;
	
	public BulkLoaderDAOFacade getDao() {
		return dao;
	}

	public void setDao(BulkLoaderDAOFacade dao) {
		this.dao = dao;
	}

	public void addProgressListener(ProgressListener arg0) {
		// TODO Auto-generated method stub

	}
	
	public ValidationItems validate() {
		ValidationItems validItems = validateDE();
		validItems = validateDEC();
		validItems = validateVD();
		
		return validItems;
	}

	private ValidationItems validateDE() {
		ValidationItems validationItems = ValidationItems.getInstance();
		DataElement de = DomainObjectFactory.newDataElement();
		List<DataElement> deList = elementsList.getElements(de);
		for (DataElement dataElement: deList) {
			String publicId = dataElement.getPublicId();
			Float version = dataElement.getVersion();
			if (publicId != null && version != null) {
				DataElement deGot = dao.findDataElementById(Integer.parseInt(publicId), version);
				if (deGot.getPublicId() == null) {
					ValidationItem validationItem = new ValidationError("Data Element Id ["+publicId+"v"+version+"] not valid", dataElement);
					validationItems.addItem(validationItem);
				}
			}
		}
		return validationItems;
	}
	
	private ValidationItems validateDEC() {
		ValidationItems validationItems = ValidationItems.getInstance();
		DataElementConcept newDEC = DomainObjectFactory.newDataElementConcept();
		List<DataElementConcept> decList = elementsList.getElements(newDEC);
		for (DataElementConcept dec: decList) {
			String publicId = dec.getPublicId();
			Float version = dec.getVersion();
			
			if (publicId != null && version != null) {
				DataElementConcept decGot = dao.findDataElementConceptById(Integer.parseInt(publicId), new Double(version).doubleValue());
				if (decGot.getPublicId() == null) {
					ValidationItem validationItem = new ValidationError("Data Element Concept Id ["+publicId+"v"+version+"] not valid", dec);
					validationItems.addItem(validationItem);
				}
			}
		}
		
		return validationItems;
	}
	
	private ValidationItems validateVD() {
		ValidationItems validationItems = ValidationItems.getInstance();
		ValueDomain newVd = DomainObjectFactory.newValueDomain();
		List<ValueDomain> vdList = elementsList.getElements(newVd);
		for (ValueDomain vd: vdList) {
			String publicId = vd.getPublicId();
			Float version = vd.getVersion();
			
			if (publicId != null && version != null) {
				ValueDomain vdGot = dao.findValueDomainsById(Integer.parseInt(publicId), new Double(version).doubleValue());
				if (vdGot.getPublicId() == null) {
					ValidationItem validationItem = new ValidationError("Value Domain Id ["+publicId+"v"+version+"] not valid", vd);
					validationItems.addItem(validationItem);
				}
			}
		}
		
		return validationItems;
	}

}
