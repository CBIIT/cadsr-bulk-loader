package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

public class ObjectClassValidator extends AbstractValidator {

	private static ObjectClass testOC = DomainObjectFactory.newObjectClass();
	
	static {
		testOC.setWorkflowStatus(AdminComponent.WF_STATUS_ALL);
	}
	
	@Override
	public ValidationItems validate() {
		ValidationItems validationItems = ValidationItems.getInstance();
		List<ObjectClass> objectClasses = elementsList.getElements(DomainObjectFactory.newObjectClass());
		
		for (ObjectClass objectClass: objectClasses) {
			validateDefinitionLength(objectClass);
			validateRetiredObjectClasses(objectClass);
		}
		
		return validationItems;
	}
	
	private void validateDefinitionLength(ObjectClass objectClass) {
		if (objectClass != null && objectClass.getPublicId() == null) {
			String objDef = objectClass.getPreferredDefinition();
			if (objDef!=null && objDef.length() > MAX_DEF_FIELD_SIZE) {
				ValidationError error = new ValidationError("Length of Object Class ("+objectClass.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", objectClass);
				validationItems.addItem(error);
			}
		}
	}
	
	private void validateRetiredObjectClasses(ObjectClass objectClass) {
		testOC.setPreferredName(objectClass.getPreferredName());
		
		List<ObjectClass> foundOCs = dao.findObjectClasses(testOC);
		
		if (foundOCs != null) {
			for (ObjectClass foundOC: foundOCs) {
				String foundOCWFStatus = foundOC.getWorkflowStatus();
				if (foundOCWFStatus.contains("RETIRED")) {
					ValidationItem error = new ValidationError("An object class to be created ["+objectClass.getPreferredName()+"] already exists but is retired. Please correct this and reload", objectClass);
					validationItems.addItem(error);
				}
			}
		}
	}

}
