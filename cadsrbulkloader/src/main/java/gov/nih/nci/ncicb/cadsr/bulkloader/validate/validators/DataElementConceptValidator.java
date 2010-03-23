package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationWarning;

import java.util.ArrayList;
import java.util.List;

public class DataElementConceptValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
		List<DataElementConcept> dataElementConcepts = elementsList.getElements(dec);
		for (DataElementConcept dataElementConcept: dataElementConcepts) {
			List<DataElementConcept> matchedDECs = findDECsById(dataElementConcept);
			validateId(dataElementConcept, matchedDECs);
			if (matchedDECs == null) {
				matchedDECs = findDECs(dataElementConcept);
				validateCD(dataElementConcept, matchedDECs);
			}
			validateDefinitionLength(dataElementConcept);
			validateRetiredDataElementConcepts(dataElementConcept);
		}
		
		return validationItems;
	}
	
	private List<DataElementConcept> findDECsById(DataElementConcept dec) {
		List<DataElementConcept> foundDECs = null;
		String publicId = dec.getPublicId();
		Float version = dec.getVersion();
		
		if (publicId != null && version != null) {
			DataElementConcept searchDEC = DomainObjectFactory.newDataElementConcept();
			searchDEC.setPublicId(publicId);
			searchDEC.setVersion(version);
			
			foundDECs = dao.findDataElementConcepts(searchDEC);
		}
		
		return foundDECs;
	}
	
	private List<DataElementConcept> findDECs(DataElementConcept dec) {
		DataElementConcept searchDEC = DomainObjectFactory.newDataElementConcept();
		searchDEC.setObjectClass(dec.getObjectClass());
		searchDEC.setProperty(dec.getProperty());
		List<DataElementConcept> foundDECs = dao.findDataElementConcepts(searchDEC);
		return foundDECs;
	}
	
	private void validateId(DataElementConcept toValidate, List<DataElementConcept> matches) {
		if ((toValidate.getPublicId() != null && toValidate.getVersion() != null) && (matches == null || matches.size() == 0)) {
			ValidationItem validationItem = new ValidationError("Data Element Concept Id ["+toValidate.getPublicId()+"v"+toValidate.getVersion()+"] not valid", toValidate);
			validationItems.addItem(validationItem);
		}
	}
	
	private void validateCD(DataElementConcept toValidate, List<DataElementConcept> matches) {
		ConceptualDomain cd = toValidate.getConceptualDomain();
		if (cd != null && cd.getPublicId() != null && matches != null && matches.size() > 0) {
			ConceptualDomain matchCD = matches.get(0).getConceptualDomain();
			if (matchCD != null && matchCD.getPublicId() != null && !matchCD.getPublicId().equals(cd.getPublicId())) {
				ValidationItem validationItem = new ValidationWarning("Given CD ["+cd.getPublicId()+"v"+cd.getVersion()+"] for the DEC ["+toValidate.getLongName()+"] does not match with the CD ["+matchCD.getPublicId()+"v"+matchCD.getVersion()+"] for the DEC in caDSR", toValidate);
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
		
		if (dataElementConcept != null 
				&& dataElementConcept.getObjectClass() != null
				&& dataElementConcept.getObjectClass().getId() != null 
				&& dataElementConcept.getProperty() != null
				&& dataElementConcept.getProperty().getId() != null) {
			
			DataElementConcept searchDEC = getSearchAC(dataElementConcept, DomainObjectFactory.newDataElementConcept());
			searchDEC.setObjectClass(dataElementConcept.getObjectClass());
			searchDEC.setProperty(dataElementConcept.getProperty());
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
	
	

}
