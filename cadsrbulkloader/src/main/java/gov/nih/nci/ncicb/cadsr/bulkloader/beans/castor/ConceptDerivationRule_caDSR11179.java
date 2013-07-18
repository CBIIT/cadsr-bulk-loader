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

public class ConceptDerivationRule_caDSR11179 extends DerivationRule_ISO11179{

	private ComponentConceptList_caDSR11179 componentConceptsList;

	public ComponentConceptList_caDSR11179 getComponentConceptsList() {
		return componentConceptsList;
	}

	public void setComponentConceptsList(
			ComponentConceptList_caDSR11179 componentConceptsList) {
		this.componentConceptsList = componentConceptsList;
	}
	
	
}
