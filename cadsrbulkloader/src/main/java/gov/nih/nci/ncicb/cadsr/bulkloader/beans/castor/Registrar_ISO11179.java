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

public class Registrar_ISO11179 {

	private Contact_ISO11179 registrarContact;
	private String registrarIdentifier;
	
	public Contact_ISO11179 getRegistrarContact() {
		return registrarContact;
	}
	public void setRegistrarContact(Contact_ISO11179 registrarContact) {
		this.registrarContact = registrarContact;
	}
	public String getRegistrarIdentifier() {
		return registrarIdentifier;
	}
	public void setRegistrarIdentifier(String registrarIdentifier) {
		this.registrarIdentifier = registrarIdentifier;
	}
	
	
}
