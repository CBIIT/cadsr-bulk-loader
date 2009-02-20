package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

public enum ValidationStatus {

	PASS("Validation passed"),
	FAIL("Validation failed"),
	PASS_WITH_ERRORS("Validation passed with errors"),
	PASS_WITH_WARNINGS("Validation passed with warnings");
	
	private String validationMessage;
	
	private ValidationStatus(String msg) {
		this.validationMessage = msg;
	}
	
	public String getValidationMessage() {
		return validationMessage;
	}
}
