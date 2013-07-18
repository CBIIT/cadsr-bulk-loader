/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

public class NonPersistentObject {

	private final AdminComponent actualObject;
	
	public NonPersistentObject(AdminComponent actualObject) {
		this.actualObject = actualObject;
	}

	public AdminComponent getActualObject() {
		return actualObject;
	}
	
}
