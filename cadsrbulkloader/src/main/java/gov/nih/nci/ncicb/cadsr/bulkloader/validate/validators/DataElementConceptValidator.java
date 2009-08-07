package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

public class DataElementConceptValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
		List<DataElementConcept> dataElementConcepts = elementsList.getElements(dec);
		for (DataElementConcept dataElementConcept: dataElementConcepts) {
			validateId(dataElementConcept);
			validateDefinitionLength(dataElementConcept);
		}
		
		return validationItems;
	}
	
	private void validateId(DataElementConcept dec) {
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
	
	private void validateDefinitionLength(DataElementConcept dec) {
		if (dec.getPublicId() == null) {
			String ocDef = dec.getObjectClass().getPreferredDefinition();
			String propDef = dec.getProperty().getPreferredDefinition();
			if (ocDef == null) ocDef = "";
			if (propDef == null) propDef = "";
			
			String decDef = ocDef+" "+propDef;
			if (decDef.length() > MAX_DEF_FIELD_SIZE) {
				ValidationError error = new ValidationError("Length of DEC ("+dec.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", dec);
				validationItems.addItem(error);
			}
		}
	}

}
