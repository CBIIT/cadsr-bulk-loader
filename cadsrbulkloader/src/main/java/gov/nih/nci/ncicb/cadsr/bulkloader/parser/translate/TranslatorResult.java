/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.util.List;

public class TranslatorResult<T> {

	private ISO11179Elements iso11179Elements;
	private T translatedObject;
	private TranslatorStatus status;
	private Exception exception;
	private String message;
	private List<TranslatorLineItemResult> lineItemResults;
	
	public ISO11179Elements getIso11179Elements() {
		return iso11179Elements;
	}
	public void setIso11179Elements(ISO11179Elements iso11179Elements) {
		this.iso11179Elements = iso11179Elements;
	}
	public T getTranslatedObject() {
		return translatedObject;
	}
	public void setTranslatedObject(T translatedObject) {
		this.translatedObject = translatedObject;
	}
	public TranslatorStatus getStatus() {
		return status;
	}
	public void setStatus(TranslatorStatus status) {
		this.status = status;
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
	public List<TranslatorLineItemResult> getLineItemResults() {
		return lineItemResults;
	}
	public void setLineItemResults(List<TranslatorLineItemResult> lineItemResults) {
		this.lineItemResults = lineItemResults;
	}
	
	public boolean isSuccessful() {
		if (exception == null && (status == null || status.isSuccessful())) {
			return true;
		}
		
		return false;
	}
	
	public boolean hasErrors() {
		if (lineItemResults != null) {
			for (TranslatorLineItemResult itemResult: lineItemResults) {
				if (!itemResult.isValid()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
