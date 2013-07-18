/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class LanguageIdentification_ISO11179 {

	private String countryIdentifier;
	private String languageIdentifier;
	
	public String getCountryIdentifier() {
		return countryIdentifier;
	}
	public void setCountryIdentifier(String countryIdentifier) {
		this.countryIdentifier = countryIdentifier;
	}
	public String getLanguageIdentifier() {
		return languageIdentifier;
	}
	public void setLanguageIdentifier(String languageIdentifier) {
		this.languageIdentifier = languageIdentifier;
	}
	
	
}
