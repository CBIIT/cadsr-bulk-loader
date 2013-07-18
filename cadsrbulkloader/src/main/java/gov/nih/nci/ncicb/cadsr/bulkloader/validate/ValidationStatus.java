/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.validate;


public class ValidationStatus {

	public static final ValidationStatus SUCCESS = new ValidationStatus(2, "Successfully validated");
	
	public static final ValidationStatus FAILURE = new ValidationStatus(3, "Validation failed");
	public static final ValidationStatus SUCCESS_WITH_WARNINGS = new ValidationStatus(4, "Successfully validated with warnings");
	
	private final int code;
	private final String message;
	
	private ValidationStatus(int _code, String _message) {
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
	
	public boolean hasWarnings() {
		if (code == 4) return true;
		return false;
	}
	
	public String toString() {
		return message;
	}
}
