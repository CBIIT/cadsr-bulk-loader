package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public class BulkLoaderDAORuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BulkLoaderDAORuntimeException() {
		super();
	}
	
	public BulkLoaderDAORuntimeException(Exception e) {
		super(e);
	}
	
	public BulkLoaderDAORuntimeException(String message) {
		super(message);
	}
	
	public BulkLoaderDAORuntimeException(String message, Exception e) {
		super(message, e);
	}
}
