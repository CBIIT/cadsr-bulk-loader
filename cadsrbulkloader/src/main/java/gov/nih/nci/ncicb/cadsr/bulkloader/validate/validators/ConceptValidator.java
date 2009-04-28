package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.Validator;

import java.util.List;

public class ConceptValidator implements Validator {

	private ElementsLists elementsList = ElementsLists.getInstance();
	private BulkLoaderDAOFacade dao;
	
	public BulkLoaderDAOFacade getDao() {
		return dao;
	}

	public void setDao(BulkLoaderDAOFacade dao) {
		this.dao = dao;
	}

	public void addProgressListener(ProgressListener l) {
		// TODO Auto-generated method stub

	}

	public ValidationItems validate() {
		ValidationItems validationItems = ValidationItems.getInstance();
		Concept concept = CaDSRObjectsUtil.createConcept();
		List<Concept> conceptsToValidate = elementsList.getElements(concept);
		for (Concept conceptToValidate: conceptsToValidate) {
			String cui = conceptToValidate.getPreferredName();
			Concept foundConcept = dao.findConceptByCUI(cui);
			if (foundConcept.getPreferredName() == null) {
				ValidationItem validationItem = new ValidationError("Concept ["+cui+"] does not exist in EVS", conceptToValidate);
				validationItems.addItem(validationItem);
			}
		}
		
		return validationItems;
	}

}
