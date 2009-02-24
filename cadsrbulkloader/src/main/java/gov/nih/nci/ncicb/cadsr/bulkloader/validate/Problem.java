package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

public abstract class Problem {

	private String message;
	private Object trace;
	
	protected Problem(String message, Object trace) {
		this.message = message;
		this.trace = trace;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getTrace() {
		return trace;
	}
	public void setTrace(Object trace) {
		this.trace = trace;
	}
	
}
