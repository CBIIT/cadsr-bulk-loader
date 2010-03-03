package gov.nih.nci.ncicb.cadsr.bulkloader.loader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.PersisterResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;

public class LoadResult {

	private final LoaderInput input;
	private ParseResult parseResult;
	private ValidationResult validationResult;
	private PersisterResult persisterResult;
	
	private LoadStatus loadStatus;
	private Throwable exceptionTrace;
	private String  message;
	
	public LoadResult(LoaderInput _input) {
		input = _input;
	}
	
	public LoaderInput getInput() {
		return input;
	}

	public ParseResult getParseResult() {
		return parseResult;
	}

	public void setParseResult(ParseResult parseResult) {
		this.parseResult = parseResult;
	}

	public PersisterResult getPersisterResult() {
		return persisterResult;
	}

	public void setPersisterResult(PersisterResult persisterResult) {
		this.persisterResult = persisterResult;
	}

	public String getMessage() {
		return message;
	}

	public LoadStatus getLoadStatus() {
		if (loadStatus == null) {
			if (isSuccessful()) {
				if (hasWarnings()) {
					loadStatus = LoadStatus.SUCCESSFUL_WITH_WARNINGS;
				}
				else {
					loadStatus = LoadStatus.SUCCESSFUL;
				}
			}
			else {
				if (!parseResult.isSuccessful() || parseResult.hasErrors()) {
					loadStatus = LoadStatus.FAILED_WITH_PARSING_ERROR;
				}
				else if (!validationResult.isSuccessful() || validationResult.hasErrors()) {
					loadStatus = LoadStatus.FAILED_WITH_VALIDATION_ERROR;
				}
				else if (!persisterResult.isSuccessful()) {
					loadStatus = LoadStatus.FAILED_WITH_PERSISTANCE_ERROR;
				}
				else {
					loadStatus = LoadStatus.FAILED_WITH_UNKNOWN_ERROR;
				}
			}
		}
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
	
	public boolean isSuccessful() {
		if (parseResult == null || validationResult == null 
				|| persisterResult == null || exceptionTrace!=null) {
			return false;
		}
		if (!parseResult.isSuccessful() || !validationResult.isSuccessful() 
				|| !persisterResult.isSuccessful()) {
			return false;
		}
		
		return true;
	}
	
	public boolean hasWarnings() {
		if (validationResult != null && validationResult.hasWarnings()) {
			return true;
		}
		
		return false;
	}
}
