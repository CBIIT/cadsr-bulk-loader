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

public class Submission_ISO11179 {

	private Contact_ISO11179 contact;
	private Organization_ISO11179 organization;
	
	public Contact_ISO11179 getContact() {
		return contact;
	}
	public void setContact(Contact_ISO11179 contact) {
		this.contact = contact;
	}
	public Organization_ISO11179 getOrganization() {
		return organization;
	}
	public void setOrganization(Organization_ISO11179 organization) {
		this.organization = organization;
	}
	
	
}
