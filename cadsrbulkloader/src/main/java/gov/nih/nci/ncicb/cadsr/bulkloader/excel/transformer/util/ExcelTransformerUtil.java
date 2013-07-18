/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.util;

public class ExcelTransformerUtil {

	private static final String COMPOSITE_STRING_CONCAT = ":";
	
	public static String getPublicIdFromCompositeString(String compositeString) {
		if (compositeString == null) return null;
		else {
			return compositeString.split(COMPOSITE_STRING_CONCAT)[0];
		}
	}
}
