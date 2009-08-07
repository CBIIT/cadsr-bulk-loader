package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.Validator;

public abstract class AbstractValidator implements Validator {

	protected static final int MAX_DEF_FIELD_SIZE = 2000;
	
	protected ElementsLists elementsList = ElementsLists.getInstance();
	protected ValidationItems validationItems = ValidationItems.getInstance();
	
	protected BulkLoaderDAOFacade dao;
	
	
	public BulkLoaderDAOFacade getDao() {
		return dao;
	}


	public void setDao(BulkLoaderDAOFacade dao) {
		this.dao = dao;
	}


	@Override
	public void addProgressListener(ProgressListener l) {
		// TODO Auto-generated method stub

	}

}
