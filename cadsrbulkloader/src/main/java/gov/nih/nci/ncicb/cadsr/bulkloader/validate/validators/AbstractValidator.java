package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
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
	
	protected synchronized <T extends AdminComponent> T getSearchAC(T from, T to) {
				
		to.setWorkflowStatus(AdminComponent.WF_STATUS_ALL);
		to.setPreferredName(from.getPreferredName());
		to.setContext(from.getContext());
		to.setVersion(from.getVersion());
		
		return to;
	}

}
