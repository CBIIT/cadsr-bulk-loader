package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerStatus;

public interface TransformerValidationStatus extends TransformerStatus {

	public static final String FAIL = "Error in validation";
	public static final String WARNING = "Validation passed with warnings";
	public static final String PASS = "Validation passed successfully";

}
