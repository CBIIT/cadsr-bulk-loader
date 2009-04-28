package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationFatal;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationWarning;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationImpl implements Validation {

	private List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators;
	private ElementsLists elementsList = ElementsLists.getInstance();
	
	public List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators) {
		this.validators = validators;
	}

	public ValidationResult validate(CaDSRObjects caDSRObjects, boolean validate) {
		if (validate) {
			return validate(caDSRObjects);
		}
		else {
			return getDefaultValidationResult(caDSRObjects);
		}
		
	}
	
	private synchronized ValidationResult validate(CaDSRObjects caDSRObjects) {
		ValidationResult result = new ValidationResult();
		result.setValidationItem(caDSRObjects);
		
		List<ValidationItemResult> itemResults = new ArrayList<ValidationItemResult>();
		result.setItemResults(itemResults);
		
		try {
			loadElements(caDSRObjects);
			
			for (gov.nih.nci.ncicb.cadsr.loader.validator.Validator validator: validators) {
				ValidationItems validationItems = validator.validate();
				processValidationItems(validationItems, itemResults);
			}
			
			unLoadElements(caDSRObjects);
			
			result.setValidationStatus(ValidationStatus.SUCCESS);
		} catch (Exception e) {
			result.setException(e);
			result.setValidationStatus(ValidationStatus.FAILURE);
		}
		
		return result;
	}
	
	private ValidationResult getDefaultValidationResult(CaDSRObjects caDSRObjects) {
		ValidationResult result = new ValidationResult();
		result.setValidationStatus(ValidationStatus.SUCCESS);
		result.setValidationItem(caDSRObjects);
		
		return result;
	}
	
	private void loadElements(CaDSRObjects caDSRObjects) {
		List<? extends AdminComponent> adminComponents = caDSRObjects.getList();
		for (AdminComponent adminComponent: adminComponents) {
			elementsList.addElement(adminComponent);
		}
	}

	private void unLoadElements(CaDSRObjects caDSRObjects) {
		List<? extends AdminComponent> adminComponents = caDSRObjects.getList();
		for (AdminComponent adminComponent: adminComponents) {
			elementsList.removeElement(adminComponent);
		}
	}
	
	private List<ValidationItemResult> processValidationItems(ValidationItems validationItems, List<ValidationItemResult> itemResults) {
		
		Set<ValidationError> validationErrors = validationItems.getErrors();
		Set<ValidationFatal> validationFatals = validationItems.getFatals();
		Set<ValidationWarning> validationWarnings = validationItems.getWarnings();
		
		List<ValidationItem> errors = new ArrayList<ValidationItem>();
		errors.addAll(validationErrors);
		errors.addAll(validationFatals);
		errors.addAll(validationWarnings);
			
		for (ValidationItem error: errors) {
			Object item = error.getRootCause();
			String message = error.getMessage();
			ValidationItemResult lineItemResult = new ValidationItemResult(item, ValidationStatus.FAILURE);
			lineItemResult.setMessage(message);
			
			itemResults.add(lineItemResult);
		}
		
		return itemResults;
	}
	
}
