package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {

	private final ValidationStatus validationStatus;
	
	private List<Error> errors;
	private List<Fatality> fatalities;
	private List<Warning> warnings;
	
	public ValidationResult(ValidationStatus _validationStatus) {
		this.validationStatus = _validationStatus;
	}
	
	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}

	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public List<Fatality> getFatalities() {
		return fatalities;
	}

	public void setFatalities(List<Fatality> fatalities) {
		this.fatalities = fatalities;
	}

	public List<Warning> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Warning> warnings) {
		this.warnings = warnings;
	}
	
	public void addError(Error error) {
		if (errors == null) {
			errors = new ArrayList<Error>();
		}
		errors.add(error);
	}
	
	public void addFatality(Fatality fatality) {
		if (fatalities == null) {
			fatalities = new ArrayList<Fatality>();
		}
		fatalities.add(fatality);
	}
	
	public void addWarning(Warning warning) {
		if (warnings == null) {
			warnings = new ArrayList<Warning>();
		}
		warnings.add(warning);
	}
	
}
