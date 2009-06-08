package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.UMLLoaderHandler;
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
	
	public void setValidators(List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators) {
		this.validators = validators;
	}
	public List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> getValidators() {
		return validators;
	}
	
	public synchronized ValidationResult validate(CaDSRObjects caDSRObjects, LoadObjects loadObjects) {
		ValidationResult result = new ValidationResult();
		result.setValidationItem(caDSRObjects);
		
		List<ValidationItemResult> itemResults = new ArrayList<ValidationItemResult>();
		result.setItemResults(itemResults);
		
		UMLLoaderHandler handler = new UMLLoaderHandler();
		
		try {
			handler.loadElements(caDSRObjects, loadObjects);
			
			for (gov.nih.nci.ncicb.cadsr.loader.validator.Validator validator: validators) {
				ValidationItems validationItems = validator.validate();
				processValidationItems(validationItems, itemResults);
				clearValidationItems(validationItems);
			}
			
			handler.unLoadElements(caDSRObjects, loadObjects);
			
			result.setValidationStatus(ValidationStatus.SUCCESS);
		} catch (Exception e) {
			result.setException(e);
			result.setValidationStatus(ValidationStatus.FAILURE);
		}
		
		return result;
	}
	
	
	
	private List<ValidationItemResult> processValidationItems(ValidationItems validationItems, List<ValidationItemResult> itemResults) {
		
		Set<ValidationError> validationErrors = validationItems.getErrors();
		Set<ValidationFatal> validationFatals = validationItems.getFatals();
		Set<ValidationWarning> validationWarnings = validationItems.getWarnings();
		
		List<ValidationItem> errors = new ArrayList<ValidationItem>();
		errors.addAll(validationErrors);
		errors.addAll(validationFatals);
		//errors.addAll(validationWarnings);
			
		for (ValidationItem error: errors) {
			Object item = error.getRootCause();
			String message = error.getMessage();
			ValidationItemResult lineItemResult = new ValidationItemResult(item, ValidationStatus.FAILURE);
			lineItemResult.setMessage(message);
			
			itemResults.add(lineItemResult);
		}
		
		return itemResults;
	}
	
	private void clearValidationItems(ValidationItems validationItems){
		validationItems.getErrors().clear();
		validationItems.getFatals().clear();
		validationItems.getWarnings().clear();
	}
	
}
