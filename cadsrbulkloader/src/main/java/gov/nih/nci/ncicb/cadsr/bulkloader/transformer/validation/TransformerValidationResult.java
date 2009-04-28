package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation;

import java.util.ArrayList;
import java.util.List;

public class TransformerValidationResult {

	private TransformerValidationStatus status;
	private List<TransformerValidationLineItemResult> lineItemResults;
	private Exception validationException;
	
	public TransformerValidationStatus getStatus() {
		return status;
	}
	public void setStatus(TransformerValidationStatus status) {
		this.status = status;
	}
	public List<TransformerValidationLineItemResult> getLineItemResults() {
		return lineItemResults;
	}
	public void setLineItemResults(
			List<TransformerValidationLineItemResult> lineItemResults) {
		this.lineItemResults = lineItemResults;
	}
	public Exception getValidationException() {
		return validationException;
	}
	public void setValidationException(Exception _validationException) {
		this.validationException = _validationException;
		validationException.setStackTrace(_validationException.getStackTrace());
	}
	
	public void addLineItemResult(TransformerValidationLineItemResult lineItemResult) {
		if (lineItemResults == null) {
			lineItemResults = new ArrayList<TransformerValidationLineItemResult>();
		}
		
		lineItemResults.add(lineItemResult);
	}
	
	public boolean hasErrors() {
		if (lineItemResults == null) {
			return false;
		}
		else {
			for (TransformerValidationLineItemResult result: lineItemResults) {
				if (result.hasErrors()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
