/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import java.io.File;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 21, 2008
 * @since 
 */

public interface SchemaValidator {

	/**
	 * Validates against the schema and throws a <code>SchemaValidationException</code> if not valid
	 * 
	 * @param _xmlFile
	 * @throws SchemaValidationException
	 */
	public SchemaValidationResult validate(File _xmlFile);
}
