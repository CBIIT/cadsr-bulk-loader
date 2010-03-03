package gov.nih.nci.ncicb.cadsr.bulkloader.validate;


public class ValidationItemResult {

	private final Object item;
	private final ValidationStatus status;
	private Exception exception;
	private String message;
	
	public ValidationItemResult(Object _item, ValidationStatus _status) {
		item = _item;
		status = _status;
	}
	
	public Object getItem() {
		return item;
	}
	public ValidationStatus getStatus() {
		return status;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean isValid() {
		return status.isSuccessful();
	}
	
	public boolean hasWarnings() {
		return status.hasWarnings();
	}
}
