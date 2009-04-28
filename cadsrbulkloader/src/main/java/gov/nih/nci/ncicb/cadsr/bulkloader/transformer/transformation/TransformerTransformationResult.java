package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransformerTransformationResult {

	private ISO11179Elements isoElements;
	private List<TransformerTransformationLineItemResult> lineItemResults;
	private TransformerTransformationStatus status;
	private Exception transformationException;
	
	private AtomicInteger successful = new AtomicInteger();
	private AtomicInteger unsuccessful = new AtomicInteger();
	
	public ISO11179Elements getIsoElements() {
		return isoElements;
	}
	public void setIsoElements(ISO11179Elements isoElements) {
		this.isoElements = isoElements;
	}
	public List<TransformerTransformationLineItemResult> getLineItemResults() {
		return lineItemResults;
	}
	public void setLineItemResults(
			List<TransformerTransformationLineItemResult> lineItemResults) {
		this.lineItemResults = lineItemResults;
	}
	public TransformerTransformationStatus getStatus() {
		return status;
	}
	public void setStatus(TransformerTransformationStatus status) {
		this.status = status;
	}
	public Exception getTransformationException() {
		return transformationException;
	}
	public void setTransformationException(Exception transformationException) {
		this.transformationException = transformationException;
	}
	
	public void addLineItemResult(TransformerTransformationLineItemResult lineItemResult) {
		if (lineItemResults == null) {
			lineItemResults = new ArrayList<TransformerTransformationLineItemResult>();
		}
		
		lineItemResults.add(lineItemResult);
		
		if (lineItemResult.hasErrors()) {
			unsuccessful.incrementAndGet();
		}
		else {
			successful.incrementAndGet();
		}
	}
	
	public boolean hasErrors() {
		if (unsuccessful.intValue() > 0 || transformationException != null) {
			return true;
		}
		else return false;
	}
}
