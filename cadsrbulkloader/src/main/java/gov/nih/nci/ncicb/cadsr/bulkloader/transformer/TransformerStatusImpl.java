/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;


public class TransformerStatusImpl implements TransformerStatus {

	public static final TransformerStatus PASSED = new TransformerStatusImpl(0, "Transformation Successful");
	public static final TransformerStatus PARTIALLY_PASSED = new TransformerStatusImpl(1, "Transformation Partially Successful");
	public static final TransformerStatus FAILED = new TransformerStatusImpl(2, "Transformation Failed");
	
	private int errorCode;
	private String message;
	
	private TransformerStatusImpl(int _errorCode, String _message) {
		errorCode = _errorCode;
		message = _message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public boolean validationPassed() {
		switch (errorCode) {
			case 0: 
			case 1:return true;
			case 2:return false;
			default: return false;
		}
	}

}
