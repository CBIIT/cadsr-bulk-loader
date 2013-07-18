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
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ObjectClassList_ISO11179 {

	private List<ObjectClass_caDSR11179> objectClasses;

	public List<ObjectClass_caDSR11179> getObjectClasses() {
		return objectClasses;
	}

	public void setObjectClasses(List<ObjectClass_caDSR11179> objectClasses) {
		this.objectClasses = objectClasses;
	}
	
	
}
