/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.event.EventResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.TranslatorResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator.SchemaValidationResult;

public class ParseResult implements EventResult{

	private CaDSRObjects caDSRObjects;
	private ParseStatus status;
	private Exception exception;
	private String message;
	private SchemaValidationResult schemaValidationResult;
	private TranslatorResult<CaDSRObjects> translatorResult;
	
	public CaDSRObjects getCaDSRObjects() {
		return caDSRObjects;
	}
	public void setCaDSRObjects(CaDSRObjects caDSRObjects) {
		this.caDSRObjects = caDSRObjects;
	}
	public ParseStatus getStatus() {
		return status;
	}
	public void setStatus(ParseStatus status) {
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
	public SchemaValidationResult getSchemaValidationResult() {
		return schemaValidationResult;
	}
	public void setSchemaValidationResult(
			SchemaValidationResult schemaValidationResult) {
		this.schemaValidationResult = schemaValidationResult;
	}
	public TranslatorResult<CaDSRObjects> getTranslatorResult() {
		return translatorResult;
	}
	public void setTranslatorResult(TranslatorResult<CaDSRObjects> translatorResult) {
		this.translatorResult = translatorResult;
	}
	
	public boolean isSuccessful() {
		if (exception == null && isSchemaValidationSuccessful() && isTranslationSuccessful() && status != null && status.isSuccessful()) {
			return true;
		}
		return false;
	}
	
	private boolean isSchemaValidationSuccessful() {
		if (schemaValidationResult == null || !schemaValidationResult.isValid()) {
			return false;
		}
		return true;
	}
	
	private boolean isTranslationSuccessful() {
		if (translatorResult == null || !translatorResult.isSuccessful()) {
			return false;
		}
		return true;
	}
	
	public boolean hasErrors() {
		if (isSuccessful()) {
			if (schemaValidationResult.isValid() && !translatorResult.hasErrors()) {
				return false;
			}
		}
		
		return true;
	}
}
