package gov.nih.nci.ncicb.cadsr.bulkloader;

public class BulkLoaderRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BulkLoaderRuntimeException() {
		super();
	}
	
	public BulkLoaderRuntimeException(Exception e) {
		super(e);
		super.setStackTrace(e.getStackTrace());
	}
	
	public BulkLoaderRuntimeException(String message, Exception e) {
		super(message, e);
		super.setStackTrace(e.getStackTrace());
	}
	
	public BulkLoaderRuntimeException(String message) {
		super(message);
	}
	
}
