/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TransformerTransformationResult {

	private ISO11179Elements isoElements;
	private LoadProperties loadProperties;
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
	public LoadProperties getLoadProperties() {
		return loadProperties;
	}
	public void setLoadProperties(LoadProperties loadProperties) {
		this.loadProperties = loadProperties;
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
