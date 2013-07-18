/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 21, 2009
 * @since 
 */

public interface Translator<T> {

	public TranslatorResult<T> translate(ISO11179Elements iso11179Elements);
}
