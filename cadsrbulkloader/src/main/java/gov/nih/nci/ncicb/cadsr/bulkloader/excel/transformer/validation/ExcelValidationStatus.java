package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationStatus;

public class ExcelValidationStatus implements TransformerValidationStatus {

	public static final ExcelValidationStatus PASSED = new ExcelValidationStatus(1, PASS);
	public static final ExcelValidationStatus PASSED_WITH_WARNING = new ExcelValidationStatus(2, WARNING);
	
	public static final ExcelValidationStatus FAILED = new ExcelValidationStatus(-1, FAIL);
	
	public static final ExcelValidationStatus FAILED_UNKNOWN = new ExcelValidationStatus(-2,"Validation failed due to an unknown error");
	
	public static final ExcelValidationStatus CDEID_AND_DATA_PRESENT = new ExcelValidationStatus(-3, "Validation failed because the CDE ID AND data to create CDE are present");
	public static final ExcelValidationStatus CDEID_AND_DATA_NOT_PRESENT = new ExcelValidationStatus(-4,"Validation failed because the CDE ID AND data to create CDE are not present");
	
	public static final ExcelValidationStatus DECID_AND_DATA_PRESENT = new ExcelValidationStatus(-5,"Validation failed because the DEC ID AND data to create DEC are present");
	public static final ExcelValidationStatus DECID_AND_DATA_NOT_PRESENT = new ExcelValidationStatus(-6, "Validation failed because the DEC ID AND data to create DEC are not present");
	
	public static final ExcelValidationStatus VDID_AND_DATA_PRESENT = new ExcelValidationStatus(-7, "Validation failed because the VD ID AND data to create VD are present");
	public static final ExcelValidationStatus VDID_AND_DATA_NOT_PRESENT = new ExcelValidationStatus(-8, "Validation failed because the VD ID AND data to create VD are not present");
	
	public static final ExcelValidationStatus INVALID_CDEID = new ExcelValidationStatus(-9, "The CDE Id entered is invalid");
	public static final ExcelValidationStatus INVALID_DECID = new ExcelValidationStatus(-10, "The DEC Id entered is invalid");
	public static final ExcelValidationStatus INVALID_VDID = new ExcelValidationStatus(-11, "The VD Id entered is invalid");
	public static final ExcelValidationStatus INVALID_DEC_CD_ID = new ExcelValidationStatus(-12, "The DEC CD Id entered is invalid");
	public static final ExcelValidationStatus INVALID_VD_CD_ID = new ExcelValidationStatus(-13, "The VD CD Id entered is invalid");
	public static final ExcelValidationStatus DUPLICATE_VM_CONCEPT = new ExcelValidationStatus(-14, "Duplicate VM concepts found for the same VD");
	
	public static final ExcelValidationStatus INVALID_CONCEPT_ID = new ExcelValidationStatus(-15, "The Concept Id entered is invalid");
	public static final ExcelValidationStatus INVALID_QUAL_CONCEPT_ID = new ExcelValidationStatus(-16, "The Qualifier Concept Ids entered are invalid");
	public static final ExcelValidationStatus INVALID_PRIM_CONCEPT_ID = new ExcelValidationStatus(-17, "The Primary Concept Id entered is invalid");
	
	public static final ExcelValidationStatus INVALID_ENUMERATED = new ExcelValidationStatus(-18, "The data entered in the 'Enumerated' field is invalid");
	
	public static final ExcelValidationStatus BLANK_FORM_NAME = new ExcelValidationStatus(-19, "Form Name cannot be blank");
	public static final ExcelValidationStatus BLANK_CONTEXT_NAME = new ExcelValidationStatus(-20, "Context Name cannot be blank");
	public static final ExcelValidationStatus BLANK_CS = new ExcelValidationStatus(-21, "Classification Scheme cannot be blank");
	public static final ExcelValidationStatus BLANK_CSI = new ExcelValidationStatus(-22, "Classification Scheme Item cannot be blank");
	public static final ExcelValidationStatus BLANK_SOURCE = new ExcelValidationStatus(-23, "Source cannot be blank");
	public static final ExcelValidationStatus PV_NO_VM = new ExcelValidationStatus(-24, "The Permissible Value entered does not have any Value Meaning concepts");
	public static final ExcelValidationStatus INVALID_MAX_LENGTH = new ExcelValidationStatus(-25, "The VD Max Length entered is not a number");
	
	private int errorCode;
	private String message;
	
	private ExcelValidationStatus(int _errorCode, String _message) {
		errorCode = _errorCode;
		message = _message;
	}
	
	public static ExcelValidationStatus generateErrorStatus(String _errorMessage) {
		return new ExcelValidationStatus(-1000, _errorMessage);
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean validationPassed() {
		if (errorCode < 0) {
			return false;
		}
		else {
			return true;
		}
	}

}
