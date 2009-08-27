package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

public class PropertyValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		List<Property> properties = elementsList.getElements(DomainObjectFactory.newProperty());
		for (Property property: properties) {
			validateDefinitionLength(property);
			validateRetiredProperty(property);
		}
		
		return validationItems;
	}
	
	private void validateDefinitionLength(Property property) {
		if (property.getPublicId() == null) {
			String propDef = property.getPreferredDefinition();
			if (propDef != null && propDef.length() > MAX_DEF_FIELD_SIZE) {
				ValidationError error = new ValidationError("Length of Property ("+property.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", property);
				validationItems.addItem(error);
			}
		}
	}
	
	private void validateRetiredProperty(Property property) {
		Property searchProp = getSearchAC(property, DomainObjectFactory.newProperty());
		
		List<Property> foundProps = dao.findProperties(searchProp);
		
		if (foundProps != null) {
			for (Property foundDEC: foundProps) {
				String foundOCWFStatus = foundDEC.getWorkflowStatus();
				if (foundOCWFStatus.contains("RETIRED")) {
					ValidationItem error = new ValidationError("The Property to be created ["+property.getPreferredName()+"] already exists but is retired. Please correct this and reload", property);
					validationItems.addItem(error);
				}
			}
		}
	}

}
