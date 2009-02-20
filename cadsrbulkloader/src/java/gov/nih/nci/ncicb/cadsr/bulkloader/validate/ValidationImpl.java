package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationFatal;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationWarning;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ValidationImpl implements Validator {

	private List<Error> errorsList = new ArrayList<Error>();
	private List<Fatality> fatalitiesList = new ArrayList<Fatality>();
	private List<Warning> warningsList = new ArrayList<Warning>();
	
	private List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators;
	private ElementsLists elementsList = ElementsLists.getInstance();
	
	public List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> getValidators() {
		return validators;
	}

	public void setValidators(List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators) {
		this.validators = validators;
	}

	public ValidationResult validate(CaDSRObjects caDSRObjects) {
		try {
			loadElements(caDSRObjects);
			
			for (gov.nih.nci.ncicb.cadsr.loader.validator.Validator validator: validators) {
				ValidationItems validationItems = validator.validate();
				processValidationItems(validationItems);
			}
			
			unLoadElements(caDSRObjects);
			
			ValidationResult result = getValidationResult();
			return result;
		} catch (Exception e) {
			throw new ValidatorRuntimeException(e);
		}
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
	
	private void processValidationItems(ValidationItems validationItems) {
		
		Set<ValidationError> validationErrors = validationItems.getErrors();
		Set<ValidationFatal> validationFatals = validationItems.getFatals();
		Set<ValidationWarning> validationWarnings = validationItems.getWarnings();
		
		errorsList.addAll(getErrors(validationErrors));
		fatalitiesList.addAll(getFatalities(validationFatals));
		warningsList.addAll(getWarnings(validationWarnings));
		
	}
	
	private List<Error> getErrors(Set<ValidationError> validationErrors) {
		List<Error> errors = new ArrayList<Error>();
		if (validationErrors != null) {
			Iterator<ValidationError> validationErrorIter = validationErrors.iterator();
			while (validationErrorIter.hasNext()) {
				ValidationError validationError = validationErrorIter.next();
				String message = validationError.getMessage();
				Object trace = validationError.getRootCause();
				
				Error error = new Error(message, trace);
				errors.add(error);
			}
		}
		
		return errors;
	}
	
	private List<Fatality> getFatalities(Set<ValidationFatal> validationFatals) {
		List<Fatality> fatalities = new ArrayList<Fatality>();
		if (validationFatals != null) {
			Iterator<ValidationFatal> validationFatalsIter = validationFatals.iterator();
			while (validationFatalsIter.hasNext()) {
				ValidationFatal validationFatal = validationFatalsIter.next();
				String message = validationFatal.getMessage();
				Object trace = validationFatal.getRootCause();
				
				Fatality fatality = new Fatality(message, trace);
				fatalities.add(fatality);
			}
		}
		
		return fatalities;
	}
	
	private List<Warning> getWarnings(Set<ValidationWarning> validationWarnings) {
		List<Warning> warnings = new ArrayList<Warning>();
		if (validationWarnings != null) {
			Iterator<ValidationWarning> validationWarningsIter = validationWarnings.iterator();
			while (validationWarningsIter.hasNext()) {
				ValidationWarning validationWarning = validationWarningsIter.next();
				String message = validationWarning.getMessage();
				Object trace = validationWarning.getRootCause();
				
				Warning warning = new Warning(message, trace);
				warnings.add(warning);
			}
		}
		
		return warnings;
	}
	
	private ValidationResult getValidationResult() {
		ValidationResult result;
		
		if (errorsList.size() > 0 || fatalitiesList.size() > 0) {
			result = new ValidationResult(ValidationStatus.FAIL);
		}
		else if (warningsList.size() > 0) {
			result = new ValidationResult(ValidationStatus.PASS_WITH_WARNINGS);
		}
		else {
			result = new ValidationResult(ValidationStatus.PASS);
		}
		
		result.setErrors(errorsList);
		result.setFatalities(fatalitiesList);
		result.setWarnings(warningsList);
		
		return result;
	}
}
