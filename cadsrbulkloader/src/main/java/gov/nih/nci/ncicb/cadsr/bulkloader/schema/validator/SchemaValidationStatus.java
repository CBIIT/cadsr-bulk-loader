/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;



public class SchemaValidationStatus {

	public static final SchemaValidationStatus SUCCESS = new SchemaValidationStatus(2, "Successfully validated file against schema");
	
	public static final SchemaValidationStatus FAILURE = new SchemaValidationStatus(3, "Validation against schema failed");
	public static final SchemaValidationStatus FILE_READ_FAILURE = new SchemaValidationStatus(5, "The input file could not be read");
	
	private final int code;
	private final String message;
	
	private SchemaValidationStatus(int _code, String _message) {
		code = _code;
		message = _message;
	}
	
	public String getMessage() {
		return message;
	}

	public boolean isSuccessful() {
		if (code == 0) return true;
		
		switch (code % 2) {
			case 0: return true;
			default: return false;
		}
	}
	
	public String toString() {
		return message;
	}
}
