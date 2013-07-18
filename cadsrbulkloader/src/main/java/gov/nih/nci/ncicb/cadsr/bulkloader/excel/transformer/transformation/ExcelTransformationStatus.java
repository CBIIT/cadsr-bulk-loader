/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.transformation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation.TransformerTransformationStatus;

public class ExcelTransformationStatus implements
		TransformerTransformationStatus {

	public static final ExcelTransformationStatus SUCCESS = new ExcelTransformationStatus(0, "Successfully transformed");
	public static final ExcelTransformationStatus FAILURE = new ExcelTransformationStatus(1, "Transformation failed");
	
	private final int code;
	private final String message;
	
	private ExcelTransformationStatus(int _code, String _message){
		code = _code;
		message = _message;
	}
	
	public String getMessage() {
		return message;
	}

	public boolean validationPassed() {
		switch (code % 2) { // all even codes for success, all odds for failure
			case 0: return true;
			default: return false;
		}
	}

}
