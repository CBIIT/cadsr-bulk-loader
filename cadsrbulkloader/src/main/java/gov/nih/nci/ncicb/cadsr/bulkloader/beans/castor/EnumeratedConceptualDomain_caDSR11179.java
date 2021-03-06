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

public class EnumeratedConceptualDomain_caDSR11179 extends
						ConceptualDomain_caDSR11179{

	private List<ValueMeaningRef_ISO11179> valueMeaningRefIds;

	public List<ValueMeaningRef_ISO11179> getValueMeaningRefIds() {
		return valueMeaningRefIds;
	}

	public void setValueMeaningRefIds(
			List<ValueMeaningRef_ISO11179> valueMeaningRefIds) {
		this.valueMeaningRefIds = valueMeaningRefIds;
	}
	
	
}
