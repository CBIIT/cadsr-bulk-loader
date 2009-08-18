package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class ConceptValidator extends AbstractValidator {

	public ValidationItems validate() {
		Concept con = CaDSRObjectsUtil.createConcept();
		List<Concept> concepts = elementsList.getElements(con);
		for (Concept concept: concepts) {
			validateExistence(concept);
			validateLongName(concept);
			validateDefinitions(concept);
		}
		
		return validationItems;
	}
	
	private void validateExistence(Concept concept) {
		String cui = concept.getPreferredName();
		if (concept.getId() == null) {
			Concept evsCon = dao.findEVSConceptByCUI(cui, false);
			if (evsCon.getPreferredName() == null || evsCon.getPreferredName().equals("")) {
				ValidationItem validationItem = new ValidationError("Concept ["+cui+"] does not exist either in EVS or in caDSR or has been retired. Please create it in caDSR manually", concept);
				validationItems.addItem(validationItem);
			}
		}
	}
	
	private void validateLongName(Concept concept) {
		String cui = concept.getPreferredName();
		String longName = concept.getLongName();
		if (longName == null || longName.trim().equals("")) {
			ValidationItem validationItem = new ValidationError("Long name for the Concept ["+cui+"] is null or empty", concept);
			validationItems.addItem(validationItem);
		}
	}
	
	private void validateDefinitions(Concept concept) {
		String cui = concept.getPreferredName();
		List<Definition> defs = concept.getDefinitions();
		if (defs == null || defs.size() < 1) {
			ValidationItem validationItem = new ValidationError("Concept ["+cui+"] does not have any definitions", concept);
			validationItems.addItem(validationItem);
		}
	}

}
