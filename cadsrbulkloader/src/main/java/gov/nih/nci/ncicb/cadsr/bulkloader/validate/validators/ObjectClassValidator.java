/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class ObjectClassValidator extends AbstractValidator {

	
	@Override
	public ValidationItems validate() {
		ValidationItems validationItems = ValidationItems.getInstance();
		List<ObjectClass> objectClasses = elementsList.getElements(DomainObjectFactory.newObjectClass());
		
		for (ObjectClass objectClass: objectClasses) {
			validateOCExistsByName(objectClass);
			validateDefinitionLength(objectClass);
			validateRetiredObjectClasses(objectClass);
		}
		
		return validationItems;
	}
	
	private void validateOCExistsByName(ObjectClass objectClass) {
		if (objectClass.getId() == null) {
			
			// make sure this will not throw a unique key violation (OC_UK) when we try to create it
			ObjectClass searchOC = DomainObjectFactory.newObjectClass();
			searchOC.setPreferredName(objectClass.getPreferredName());
			searchOC.setVersion(objectClass.getVersion());
			searchOC.setContext(objectClass.getContext());
			
			List<ObjectClass> ocs = dao.findObjectClassesByName(searchOC);
			if (ocs != null && ocs.size() > 0) {
				ValidationError error = new ValidationError("The Object Class ["+objectClass.getLongName()+"] already exists with a different set of concepts", objectClass);
				validationItems.addItem(error);
			}
		}
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
		
		if (objectClass != null 
				&& objectClass.getPublicId()==null 
				&& objectClass.getConceptDerivationRule() != null
				&& objectClass.getConceptDerivationRule().getComponentConcepts() != null
				&& objectClass.getConceptDerivationRule().getComponentConcepts().size() > 0) {
			ObjectClass searchOC = getSearchAC(objectClass, DomainObjectFactory.newObjectClass());
			searchOC.setConceptDerivationRule(objectClass.getConceptDerivationRule());
			
			List<ObjectClass> foundOCs = dao.findObjectClasses(searchOC);
			
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

}
