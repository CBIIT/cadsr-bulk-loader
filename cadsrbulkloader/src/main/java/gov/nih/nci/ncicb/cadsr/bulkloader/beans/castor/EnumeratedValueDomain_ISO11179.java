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
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class EnumeratedValueDomain_ISO11179 extends ValueDomain_ISO11179 {

	private List<PermissibleValue_ISO11179> permissibleValues;

	public List<PermissibleValue_ISO11179> getPermissibleValues() {
		return permissibleValues;
	}

	public void setPermissibleValues(
			List<PermissibleValue_ISO11179> permissibleValues) {
		this.permissibleValues = permissibleValues;
	}
	
	
}
