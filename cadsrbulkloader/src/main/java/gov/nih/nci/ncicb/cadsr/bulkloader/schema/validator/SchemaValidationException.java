/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import gov.nih.nci.ncicb.cadsr.bulkloader.loader.BulkLoaderException;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.ExceptionCode;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 21, 2008
 * @since 
 */

public class SchemaValidationException extends BulkLoaderException {

	private static final long serialVersionUID = -788751498929252874L;

	public SchemaValidationException() {
		super();
	}
	
	public SchemaValidationException(Throwable t) {
		super(t);
	}
	
	public SchemaValidationException(ExceptionCode code, Throwable t) {
		super(t);
		this.code = code;
	}
	
	public SchemaValidationException(String message, Throwable t) {
		super(message, t);
	}
	
	public SchemaValidationException(ExceptionCode code, String message, Throwable t) {
		super(message, t);
		this.code = code;
	}
	
	public SchemaValidationException(String message) {
		super(message);
	}
	
	public SchemaValidationException(ExceptionCode code, String message) {
		super(message);
		this.code = code;
	}
}
