/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class TerminologicalEntry_ISO11179 {

	private List<LanguageSection_ISO11179> containingEntries;

	public List<LanguageSection_ISO11179> getContainingEntries() {
		return containingEntries;
	}

	public void setContainingEntries(List<LanguageSection_ISO11179> containingEntries) {
		this.containingEntries = containingEntries;
	}
	
	
}
