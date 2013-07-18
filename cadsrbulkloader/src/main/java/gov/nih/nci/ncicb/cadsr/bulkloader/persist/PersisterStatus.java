/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

public class PersisterStatus {

	public static final PersisterStatus SUCCESS = new PersisterStatus(2, "Successfully Persisted");
	
	public static final PersisterStatus FAILURE = new PersisterStatus(3, "Persistence failed");
	
	private final int code;
	private final String message;
	
	private PersisterStatus(int _code, String _message) {
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
