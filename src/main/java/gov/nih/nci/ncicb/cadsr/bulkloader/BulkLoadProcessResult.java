package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;

public class BulkLoadProcessResult {

	private BulkLoadProcessorStatus processorStatus;
	private TransformerResult transformResult;
	private LoadResult loadResult;
	private Exception exception;
		
	public BulkLoadProcessorStatus getProcessorStatus() {
		return processorStatus;
	}
	public void setProcessorStatus(BulkLoadProcessorStatus processorStatus) {
		this.processorStatus = processorStatus;
	}
	public TransformerResult getTransformResult() {
		return transformResult;
	}
	public void setTransformResult(TransformerResult transformResult) {
		this.transformResult = transformResult;
	}
	public LoadResult getLoadResult() {
		return loadResult;
	}
	public void setLoadResult(LoadResult loadResult) {
		this.loadResult = loadResult;
	}

	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public boolean isSuccessful() {
		if (transformResult == null || loadResult == null) {
			return false;
		}
		if (transformResult.hasErrors() || !loadResult.isSuccessful()) {
			return false;
		}
		
		return true;
	}
	
	public String getMessage() {
		if (isSuccessful()) {
			return "Processed Successfully";
		}
		else {
			StringBuffer message = new StringBuffer();
			message.append("Process FAILED!");
			message.append("\nCause:");
			
			if (transformResult == null) {
				message.append("Error in transformation");
			}
			else if (transformResult.hasErrors()) {
				message.append(transformResult.getStatus().getMessage());
			}
			else if (loadResult == null) {
				message.append("Error in loading");
			}
			else if (!loadResult.isSuccessful()) {
				message.append(loadResult.getMessage());
			}
			else {
				message.append("Unknown Error");
			}
			
			return message.toString();
		}
	}
}
