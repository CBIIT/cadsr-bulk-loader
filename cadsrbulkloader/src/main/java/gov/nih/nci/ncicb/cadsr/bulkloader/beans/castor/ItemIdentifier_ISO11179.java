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

public class ItemIdentifier_ISO11179 {

	private RegistrationAuthorityIdentifier_ISO11179 itemRegistrationAuthorityIdentifier;
	private String dataIdentifier;
	private String version;
	
	
	public RegistrationAuthorityIdentifier_ISO11179 getItemRegistrationAuthorityIdentifier() {
		return itemRegistrationAuthorityIdentifier;
	}
	public void setItemRegistrationAuthorityIdentifier(
			RegistrationAuthorityIdentifier_ISO11179 itemRegistrationAuthorityIdentifier) {
		this.itemRegistrationAuthorityIdentifier = itemRegistrationAuthorityIdentifier;
	}
	public String getDataIdentifier() {
		return dataIdentifier;
	}
	public void setDataIdentifier(String dataIdentifier) {
		this.dataIdentifier = dataIdentifier;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
