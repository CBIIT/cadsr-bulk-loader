/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.result;

import java.util.List;

public abstract class ProcessResult {

	private List<LineItemResult> itemResults;
	private Throwable exception;
	private String message;
	
	public abstract boolean isSuccessful();
	public abstract boolean hasErrors();
}
