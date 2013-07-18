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
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class Property_caDSR11179 extends Property_ISO11179  {

	private ConceptDerivationRule_caDSR11179 conceptDerivationRule;

	public ConceptDerivationRule_caDSR11179 getConceptDerivationRule() {
		return conceptDerivationRule;
	}

	public void setConceptDerivationRule(
			ConceptDerivationRule_caDSR11179 conceptDerivationRule) {
		this.conceptDerivationRule = conceptDerivationRule;
	}
}
