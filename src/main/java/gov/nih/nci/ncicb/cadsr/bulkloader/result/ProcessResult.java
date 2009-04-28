package gov.nih.nci.ncicb.cadsr.bulkloader.result;

import java.util.List;

public abstract class ProcessResult {

	private List<LineItemResult> itemResults;
	private Throwable exception;
	private String message;
	
	public abstract boolean isSuccessful();
	public abstract boolean hasErrors();
}
