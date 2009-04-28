package gov.nih.nci.ncicb.cadsr.bulkloader.result;

public abstract class LineItemResult {

	private Object item;
	private Throwable exception;
	
	protected abstract boolean isValid();
}
