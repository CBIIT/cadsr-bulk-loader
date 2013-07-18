/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public class EnumeratedValueDomain_caDSR11179 extends ValueDomain_caDSR11179 {

	private PermissibleValuesList_ISO11179 pvList = new PermissibleValuesList_ISO11179();

	public List<PermissibleValue_ISO11179> getPermissibleValues() {
		return pvList.getPermissibleValues();
	}

	public void setPermissibleValues(
			List<PermissibleValue_ISO11179> permissibleValues) {
		pvList.setPermissibleValues(permissibleValues);
	}

	public PermissibleValuesList_ISO11179 getPvList() {
		return pvList;
	}

	public void setPvList(PermissibleValuesList_ISO11179 pvList) {
		this.pvList = pvList;
	}
	
}
