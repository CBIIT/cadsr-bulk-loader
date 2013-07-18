/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader;


public class BulkLoadProcessorStatus {

public static final BulkLoadProcessorStatus SUCCESS = new BulkLoadProcessorStatus(2, "Successfully processed");
	
	public static final BulkLoadProcessorStatus FAILURE = new BulkLoadProcessorStatus(3, "Processing failed");
	public static final BulkLoadProcessorStatus OP_FILE_CREATION_FAILURE = new BulkLoadProcessorStatus(5, "The transformed file could not be created");
	public static final BulkLoadProcessorStatus TRANSFORM_FAILURE = new BulkLoadProcessorStatus(7, "Transformation failed");
	public static final BulkLoadProcessorStatus LOAD_FAILURE = new BulkLoadProcessorStatus(9, "Loading failed");
	
	private final int code;
	private final String message;
	
	private BulkLoadProcessorStatus(int _code, String _message) {
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
