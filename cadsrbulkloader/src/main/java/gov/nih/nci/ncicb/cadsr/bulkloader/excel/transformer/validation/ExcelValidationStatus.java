package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationStatus;

public class ExcelValidationStatus implements TransformerValidationStatus {

	public static final ExcelValidationStatus PASSED = new ExcelValidationStatus(2, PASS);
	public static final ExcelValidationStatus PASSED_WITH_WARNING = new ExcelValidationStatus(4, WARNING);
	
	public static final ExcelValidationStatus FAILED = new ExcelValidationStatus(1, FAIL);
	
	public static final ExcelValidationStatus FAILED_UNKNOWN = new ExcelValidationStatus(3,"Validation failed due to an unknown error");
	
	public static final ExcelValidationStatus CDEID_AND_DATA_PRESENT = new ExcelValidationStatus(5, "Validation failed because the CDE ID AND data to create CDE are present");
	public static final ExcelValidationStatus CDEID_AND_DATA_NOT_PRESENT = new ExcelValidationStatus(7,"Validation failed because the CDE ID AND data to create CDE are not present");
	
	public static final ExcelValidationStatus DECID_AND_DATA_PRESENT = new ExcelValidationStatus(9,"Validation failed because the DEC ID AND data to create DEC are present");
	public static final ExcelValidationStatus DECID_AND_DATA_NOT_PRESENT = new ExcelValidationStatus(11, "Validation failed because the DEC ID AND data to create DEC are not present");
	
	public static final ExcelValidationStatus VDID_AND_DATA_PRESENT = new ExcelValidationStatus(13, "Validation failed because the VD ID AND data to create VD are present");
	public static final ExcelValidationStatus VDID_AND_DATA_NOT_PRESENT = new ExcelValidationStatus(15, "Validation failed because the VD ID AND data to create VD are not present");
	
	public static final ExcelValidationStatus INVALID_CDEID = new ExcelValidationStatus(17, "The CDE Id entered is invalid");
	public static final ExcelValidationStatus INVALID_DECID = new ExcelValidationStatus(19, "The DEC Id entered is invalid");
	public static final ExcelValidationStatus INVALID_VDID = new ExcelValidationStatus(21, "The VD Id entered is invalid");
	public static final ExcelValidationStatus INVALID_DEC_CD_ID = new ExcelValidationStatus(23, "The DEC CD Id entered is invalid");
	public static final ExcelValidationStatus INVALID_VD_CD_ID = new ExcelValidationStatus(25, "The VD CD Id entered is invalid");
	public static final ExcelValidationStatus DUPLICATE_VM_CONCEPT = new ExcelValidationStatus(25, "Duplicate VM concepts found for the same VD");
	
	public static final ExcelValidationStatus INVALID_CONCEPT_ID = new ExcelValidationStatus(27, "The Concept Id entered is invalid");
	public static final ExcelValidationStatus INVALID_QUAL_CONCEPT_ID = new ExcelValidationStatus(29, "The Qualifier Concept Ids entered are invalid");
	public static final ExcelValidationStatus INVALID_PRIM_CONCEPT_ID = new ExcelValidationStatus(31, "The Primary Concept Id entered is invalid");
	
	public static final ExcelValidationStatus INVALID_ENUMERATED = new ExcelValidationStatus(33, "The data entered in the 'Enumerated' field is invalid");
	
	public static final ExcelValidationStatus BLANK_FORM_NAME = new ExcelValidationStatus(35, "Form Name cannot be blank");
	public static final ExcelValidationStatus BLANK_CONTEXT_NAME = new ExcelValidationStatus(37, "Context Name cannot be blank");
	public static final ExcelValidationStatus BLANK_CS = new ExcelValidationStatus(39, "Classification Scheme cannot be blank");
	public static final ExcelValidationStatus BLANK_CSI = new ExcelValidationStatus(41, "Classification Scheme Item cannot be blank");
	public static final ExcelValidationStatus BLANK_SOURCE = new ExcelValidationStatus(43, "Source cannot be blank");
	public static final ExcelValidationStatus PV_NO_VM = new ExcelValidationStatus(45, "The Permissible Value entered does not have any Value Meaning concepts");
	public static final ExcelValidationStatus INVALID_MAX_LENGTH = new ExcelValidationStatus(47, "The VD Max Length entered is not a number");
	
	private int errorCode;
	private String message;
	
	private ExcelValidationStatus(int _errorCode, String _message) {
		errorCode = _errorCode;
		message = _message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean validationPassed() {
		switch (errorCode % 2) { // all even codes for success, all odds for failure
			case 0: return true;
			default: return false;
		}
	}

}
