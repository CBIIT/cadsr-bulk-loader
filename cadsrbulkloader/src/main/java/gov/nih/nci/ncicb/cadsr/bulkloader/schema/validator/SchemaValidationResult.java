/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import java.io.File;

public class SchemaValidationResult {

	private SchemaValidationStatus status;
	private Exception exception;
	private File inputFile;
	private String message;
		
	public SchemaValidationStatus getStatus() {
		return status;
	}
	public void setStatus(SchemaValidationStatus status) {
		this.status = status;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public File getInputFile() {
		return inputFile;
	}
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
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
