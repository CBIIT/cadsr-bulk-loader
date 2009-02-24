package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoaderRuntimeException;

public class ValidatorRuntimeException extends BulkLoaderRuntimeException {

	private static final long serialVersionUID = 1L;

	public ValidatorRuntimeException() {
		super();
	}
	
	public ValidatorRuntimeException(Exception e) {
		super(e);
	}
	
	public ValidatorRuntimeException(String message, Exception e) {
		super(message, e);
	}
	
	public ValidatorRuntimeException(String message) {
		super(message);
	}
}
