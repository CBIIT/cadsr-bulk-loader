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
			String longName = conceptToValidate.getLongName();
			if (longName == null || longName.trim().equals("")) {
				ValidationItem validationItem = new ValidationError("Long name for the Concept ["+cui+"] is null or empty", conceptToValidate);
				validationItems.addItem(validationItem);
			}
			
			Concept foundEVSConcept = dao.findEVSConceptByCUI(cui);
			if (foundEVSConcept.getPreferredName() == null) {
				Concept foundCaDSRConcept = dao.findCaDSRConceptByCUI(cui);
				if (foundCaDSRConcept.getPreferredName() == null) {
					ValidationItem validationItem = new ValidationError("Concept ["+cui+"] does not exist either in EVS or in caDSR", conceptToValidate);
					validationItems.addItem(validationItem);
				}
			}
		}
		
		return validationItems;
	}

}
