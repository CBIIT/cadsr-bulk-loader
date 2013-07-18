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

public class LanguageSection_ISO11179 {

	private LanguageIdentification_ISO11179 identifier;
	private List<Designation_ISO11179> namingEntries;
	private List<Definition_ISO11179> definingEntries;
	
	public LanguageIdentification_ISO11179 getIdentifier() {
		return identifier;
	}
	public void setIdentifier(LanguageIdentification_ISO11179 identifier) {
		this.identifier = identifier;
	}
	public List<Designation_ISO11179> getNamingEntries() {
		return namingEntries;
	}
	public void setNamingEntries(List<Designation_ISO11179> namingEntries) {
		this.namingEntries = namingEntries;
	}
	public List<Definition_ISO11179> getDefiningEntries() {
		return definingEntries;
	}
	public void setDefiningEntries(List<Definition_ISO11179> definingEntries) {
		this.definingEntries = definingEntries;
	}
	
	
}
