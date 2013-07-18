/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.validation.ExcelValidationStatus;
import junit.framework.TestCase;

public class ExcelValidatorTestCase extends TestCase {

	public void testValidationStatus() {
		ExcelValidationStatus status = ExcelValidationStatus.PV_TOO_LONG("testPV", "255");
		
		assertEquals(status.getMessage(), "The permissible value [testPV] is longer than the max possible length of [255]");
	}
}
