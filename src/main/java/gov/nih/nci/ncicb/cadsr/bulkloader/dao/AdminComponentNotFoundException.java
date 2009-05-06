package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.loader.BulkLoaderException;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.ExceptionCode;

public class AdminComponentNotFoundException extends BulkLoaderException {

	private static final long serialVersionUID = -7895257820362963461L;


	public AdminComponentNotFoundException() {
		super();
	}
	
	public AdminComponentNotFoundException(Throwable t) {
		super(t);
	}
	
	public AdminComponentNotFoundException(ExceptionCode code, Throwable t) {
		super(t);
		this.code = code;
	}
	
	public AdminComponentNotFoundException(String message, Throwable t) {
		super(message, t);
	}
	
	public AdminComponentNotFoundException(ExceptionCode code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}
	
	public AdminComponentNotFoundException(String message) {
		super(message);
	}
	
	public AdminComponentNotFoundException(ExceptionCode code, String message) {
		super(message);
		this.code = code;
	}
}
