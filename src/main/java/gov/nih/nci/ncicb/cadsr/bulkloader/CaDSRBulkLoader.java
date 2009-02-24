package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParserRuntimeException;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationStatus;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.Validator;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidatorRuntimeException;

import java.io.File;

public class CaDSRBulkLoader {

	private Parser parser;
	private Validator validator;
	private BulkLoaderDAO dao;
	
	private CaDSRObjects caDSRObjects;
	private ValidationResult validationResult;
	
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	public Validator getValidator() {
		return validator;
	}
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	public BulkLoaderDAO getDao() {
		return dao;
	}
	public void setDao(BulkLoaderDAO dao) {
		this.dao = dao;
	}
	
	public LoadResult load(File _xmlFile) {
		try {
			caDSRObjects = parser.parse(_xmlFile);
			if (validateParsedObjects()) {
				dao.saveElementsAndDependencies(caDSRObjects);
			}
			return getLoadResult();
		} catch (BulkLoaderRuntimeException e) {
			return getLoadResult(e);
		}
	}
	
	private boolean validateParsedObjects() {
		String validateFlag = System.getProperty("validate");
		if (validateFlag != null && validateFlag.equalsIgnoreCase("true")) {
			validationResult = validator.validate(caDSRObjects);
			return isValidationResultValid();
		}
		
		return true;
	}
	
	private boolean isValidationResultValid() {
		ValidationStatus validationStatus = validationResult.getValidationStatus();
		if (validationStatus.equals(ValidationStatus.PASS)	
				|| validationStatus.equals(ValidationStatus.PASS_WITH_WARNINGS)
				|| validationStatus.equals(ValidationStatus.PASS_WITH_ERRORS)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private LoadResult getLoadResult() {
		LoadResult result = null;
		
		if (validationResult != null) {
			ValidationStatus validationStatus = validationResult.getValidationStatus();
			
			if(validationStatus.equals(ValidationStatus.FAIL)) {
				result = new LoadResult(LoadStatus.FAILED_WITH_VALIDATION_ERROR);
			}
			else if(validationStatus.equals(ValidationStatus.PASS_WITH_WARNINGS)) {
				result = new LoadResult(LoadStatus.SUCCESSFUL_WITH_WARNINGS);
			}
			else if (validationStatus.equals(ValidationStatus.PASS_WITH_ERRORS)) {
				result = new LoadResult(LoadStatus.SUCCESSFUL_WITH_ERRORS);
			}
		}
		
		result = new LoadResult(LoadStatus.SUCCESSFUL);
		
		result.setValidationResult(validationResult);
		
		return result;
	}
	
	private LoadResult getLoadResult(BulkLoaderRuntimeException blre) {
		if (blre instanceof ParserRuntimeException) {
			return getLoadResult((ParserRuntimeException) blre);
		}
		else if (blre instanceof ValidatorRuntimeException) {
			return getLoadResult((ValidatorRuntimeException) blre);
		}
		else if (blre instanceof BulkLoaderDAORuntimeException) {
			return getLoadResult((BulkLoaderDAORuntimeException) blre);
		}
		else {
			LoadResult result = new LoadResult(LoadStatus.FAILED_WITH_UNKNOWN_ERROR);
			result.setExceptionTrace(blre);
			
			return result;
		}
	}
	
	private LoadResult getLoadResult(BulkLoaderDAORuntimeException bldre) {
		LoadResult result = new LoadResult(LoadStatus.FAILED_WITH_PERSISTANCE_ERROR);
		result.setExceptionTrace(bldre);
		
		return result;
	}
	
	private LoadResult getLoadResult(ParserRuntimeException pe) {
		LoadResult result = new LoadResult(LoadStatus.FAILED_WITH_PARSING_ERROR);
		result.setExceptionTrace(pe);
		
		return result;
	}
	
	private LoadResult getLoadResult(ValidatorRuntimeException ve) {
		LoadResult result = new LoadResult(LoadStatus.FAILED_WITH_VALIDATION_ERROR);
		result.setExceptionTrace(ve);
		
		return result;
	}
}
