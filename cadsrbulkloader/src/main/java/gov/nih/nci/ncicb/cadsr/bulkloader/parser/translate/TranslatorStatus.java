/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;


public class TranslatorStatus {

	public static final TranslatorStatus SUCCESS = new TranslatorStatus(2, "Translation successful");
	
	public static final TranslatorStatus FAILURE = new TranslatorStatus(3, "Translation failed");
	
	private final int code;
	private final String message;
	
	private TranslatorStatus(int _code, String _message) {
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
