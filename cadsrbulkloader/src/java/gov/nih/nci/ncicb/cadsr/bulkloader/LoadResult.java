package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;

public class LoadResult {

	private final LoadStatus loadStatus;
	private Throwable exceptionTrace;
	private ValidationResult validationResult;
	
	public LoadResult(LoadStatus _loadStatus) {
		this.loadStatus = _loadStatus;
	}
	
	public LoadStatus getLoadStatus() {
		return loadStatus;
	}

	public Throwable getExceptionTrace() {
		return exceptionTrace;
	}

	public void setExceptionTrace(Throwable exceptionTrace) {
		this.exceptionTrace = exceptionTrace;
	}

	public ValidationResult getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(ValidationResult validationResult) {
		this.validationResult = validationResult;
	}
	
}
