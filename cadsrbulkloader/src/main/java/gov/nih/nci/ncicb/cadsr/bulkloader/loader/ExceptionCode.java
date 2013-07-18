/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.loader;


/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 21, 2008
 * @since 
 */

public enum ExceptionCode {

	UNKNOWN("DSR0001","An unknown error has occurred"),
	IO_EXCEPTION("DSR1001", "An IO Exception has occurred"),
	FILE_NOT_FOUND("DSR1002", "The file {?} could not be found"),
	FILE_NOT_READ("DSR1003", "The file {?} could not be read"),
	FILE_NOT_WRITE("DSR1004", "The file {?} could not be read"),
	MALFORMED_SCHEMA_FILE("DSR2001", "The schema file {?} is malformed"),
	MALFORMED_XML_FILE("DSR2002", "The xml file {?} is malformed"),
	INVALID_XML_FILE("DSR2002", "The xml file {?} does not conform to the schema");
	
	private final String code;
	private final String message;
	
	ExceptionCode(String _code, String _message) {
		code = _code;
		message = _message;
	}
	public String code(){return code;}
	public String message() {return message; }
	public String toString() {return code+": "+message;}
}
