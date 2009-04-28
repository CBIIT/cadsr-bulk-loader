package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;

public class PersisterResult {

	private PersisterStatus status;
	private Exception exception;
	private CaDSRObjects caDSRObjects;
	
	public PersisterStatus getStatus() {
		return status;
	}
	public void setStatus(PersisterStatus status) {
		this.status = status;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public CaDSRObjects getCaDSRObjects() {
		return caDSRObjects;
	}
	public void setCaDSRObjects(CaDSRObjects caDSRObjects) {
		this.caDSRObjects = caDSRObjects;
	}
	public boolean isSuccessful() {
		if (exception == null && (status == null || status.isSuccessful())) {
			return true;
		}
		
		return false;
	}
}
