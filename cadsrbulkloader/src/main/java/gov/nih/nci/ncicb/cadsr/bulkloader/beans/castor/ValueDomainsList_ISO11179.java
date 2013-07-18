/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ValueDomainsList_ISO11179 {

	private List<ValueDomain_caDSR11179> enumVDs;
	private List<ValueDomain_caDSR11179> nonEnumVDs;

	public List<ValueDomain_caDSR11179> getValueDomains() {
		List<ValueDomain_caDSR11179> valueDomains = new ArrayList<ValueDomain_caDSR11179>();
		if (enumVDs != null) {
			valueDomains.addAll(enumVDs);
		}
		if (nonEnumVDs != null) {
			valueDomains.addAll(nonEnumVDs);
		}
		return valueDomains;
	}

	public List<ValueDomain_caDSR11179> getEnumVDs() {
		return enumVDs;
	}

	public void setEnumVDs(List<ValueDomain_caDSR11179> enumVDs) {
		this.enumVDs = enumVDs;
	}

	public List<ValueDomain_caDSR11179> getNonEnumVDs() {
		return nonEnumVDs;
	}

	public void setNonEnumVDs(List<ValueDomain_caDSR11179> nonEnumVDs) {
		this.nonEnumVDs = nonEnumVDs;
	}
	
	
}
