/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.ArrayList;
import java.util.List;

public class NonEnumeratedValueDomain_caDSR11179 extends ValueDomain_caDSR11179 {

	private String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<PermissibleValue_ISO11179> getPermissibleValues() {
		return new ArrayList<PermissibleValue_ISO11179>();
	}
}
