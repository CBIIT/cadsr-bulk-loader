package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

public class TranslatorLineItemResult {

	private final Object item;
	private final TranslatorStatus status;
	private Exception exception;
	private String message;
	
	public TranslatorLineItemResult(Object _item, TranslatorStatus _status) {
		item = _item;
		status = _status;
	}
	
	public Object getItem() {
		return item;
	}
	
	public TranslatorStatus getStatus() {
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
		if (exception == null && (status == null || status.isSuccessful())) {
			return true;
		}
		
		return false;
	}
}
