package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class RetiredElementsValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		ValidationItems validationItems = validateObjectClasses();
		
		return validationItems;
	}
	
	private ValidationItems validateObjectClasses() {
		ValidationItems validationItems = ValidationItems.getInstance();
		List<ObjectClass> objectClasses = elementsList.getElements(DomainObjectFactory.newObjectClass());
		
		ObjectClass testOC = DomainObjectFactory.newObjectClass();
		testOC.setWorkflowStatus(AdminComponent.WF_STATUS_ALL);
		
		for (ObjectClass oc: objectClasses) {
			testOC.setPreferredName(oc.getPreferredName());
			
			List<ObjectClass> foundOCs = dao.findObjectClasses(testOC);
			
			if (foundOCs != null) {
				for (ObjectClass foundOC: foundOCs) {
					String foundOCWFStatus = foundOC.getWorkflowStatus();
					if (foundOCWFStatus.contains("RETIRED")) {
						ValidationItem error = new ValidationError("An object class to be created ["+oc.getPreferredName()+"] already exists but is retired. Please correct this and reload", oc);
						validationItems.addItem(error);
					}
				}
			}
		}
		
		return validationItems;
	}

}
