package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
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
		ValidationItems validationItems = ValidationItems.getInstance();
		DataElement de = DomainObjectFactory.newDataElement();
		List<DataElement> deList = elementsList.getElements(de);
		for (DataElement dataElement: deList) {
			String publicId = dataElement.getPublicId();
			Float version = dataElement.getVersion();
			if (publicId != null && version != null) {
				DataElement deGot = dao.findDataElementById(Integer.parseInt(publicId), version);
				if (deGot.getPublicId() == null) {
					ValidationItem validationItem = new ValidationError("Data Element Id not valid", dataElement);
					validationItems.addItem(validationItem);
				}
			}
		}
		return validationItems;
	}

}
