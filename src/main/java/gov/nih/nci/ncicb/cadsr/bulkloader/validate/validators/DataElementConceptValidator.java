package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.NonPersistentObject;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.UMLLoaderHandler;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationWarning;

import java.util.List;

public class DataElementConceptValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
		List<DataElementConcept> dataElementConcepts = elementsList.getElements(dec);
		for (DataElementConcept dataElementConcept: dataElementConcepts) {
			validateId(dataElementConcept);
			validateDefinitionLength(dataElementConcept);
			validateRetiredDataElementConcepts(dataElementConcept);
		}
		
		return validationItems;
	}
	
	private void validateId(DataElementConcept dec) {
		String publicId = dec.getPublicId();
		Float version = dec.getVersion();
		
		if (publicId != null && version != null) {
			DataElementConcept searchDEC = DomainObjectFactory.newDataElementConcept();
			searchDEC.setPublicId(publicId);
			searchDEC.setVersion(version);
			
			List<DataElementConcept> foundDECs = dao.findDataElementConcepts(searchDEC);
			
			if (foundDECs == null || foundDECs.size() == 0) {
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
	
	private void validateRetiredDataElementConcepts(DataElementConcept dataElementConcept) {
		
		DataElementConcept searchDEC = getSearchAC(dataElementConcept, DomainObjectFactory.newDataElementConcept());
		List<DataElementConcept> foundDECs = dao.findDataElementConcepts(searchDEC);
		
		if (foundDECs != null) {
			for (DataElementConcept foundDEC: foundDECs) {
				String foundOCWFStatus = foundDEC.getWorkflowStatus();
				if (foundOCWFStatus.contains("RETIRED")) {
					ValidationItem error = new ValidationError("The Data Element Concept to be created ["+dataElementConcept.getPreferredName()+"] already exists but is retired. Please correct this and reload", dataElementConcept);
					validationItems.addItem(error);
				}
			}
		}
	}
	
	

}
