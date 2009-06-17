package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class ConceptValidator extends AbstractValidator {

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
