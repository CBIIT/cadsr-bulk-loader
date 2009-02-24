package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoaderException;
import gov.nih.nci.ncicb.cadsr.bulkloader.ExceptionCode;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 25, 2008
 * @since 
 */

public class ParsingException extends BulkLoaderException {

	private static final long serialVersionUID = 4515990379880148397L;

	public ParsingException() {
		super();
	}
	
	public ParsingException(Throwable t) {
		super(t);
	}
	
	public ParsingException(ExceptionCode code, Throwable t) {
		super(t);
		this.code = code;
	}
	
	public ParsingException(String message, Throwable t) {
		super(message, t);
	}
	
	public ParsingException(ExceptionCode code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}
	
	public ParsingException(String message) {
		super(message);
	}
	
	public ParsingException(ExceptionCode code, String message) {
		super(message);
		this.code = code;
	}
}
