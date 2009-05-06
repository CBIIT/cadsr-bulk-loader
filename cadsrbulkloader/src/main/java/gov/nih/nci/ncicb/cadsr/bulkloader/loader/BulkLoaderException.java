package gov.nih.nci.ncicb.cadsr.bulkloader.loader;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 21, 2008
 * @since 
 */

public class BulkLoaderException extends Exception {

	private static final long serialVersionUID = 7356408766982636496L;

	protected ExceptionCode code = null;
	
	public BulkLoaderException() {
		super();
	}
	
	public BulkLoaderException(Throwable t) {
		super(t);
	}
	
	public BulkLoaderException(ExceptionCode code, Throwable t) {
		super(t);
		this.code = code;
	}
	
	public BulkLoaderException(String message, Throwable t) {
		super(message, t);
	}
	
	public BulkLoaderException(ExceptionCode code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}
	
	public BulkLoaderException(String message) {
		super(message);
	}
	
	public BulkLoaderException(ExceptionCode code, String message) {
		super(message);
		this.code = code;
	}
	
	
	public ExceptionCode getCode(){return code;}
}
