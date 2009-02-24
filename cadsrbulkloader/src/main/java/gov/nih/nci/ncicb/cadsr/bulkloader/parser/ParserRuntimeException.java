package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoaderRuntimeException;

public class ParserRuntimeException extends BulkLoaderRuntimeException {

	private static final long serialVersionUID = 1L;

	public ParserRuntimeException() {
		super();
	}
	
	public ParserRuntimeException(Exception e) {
		super(e);
	}
	
	public ParserRuntimeException(String message, Exception e) {
		super(message, e);
	}
	
	public ParserRuntimeException(String message) {
		super(message);
	}
}
