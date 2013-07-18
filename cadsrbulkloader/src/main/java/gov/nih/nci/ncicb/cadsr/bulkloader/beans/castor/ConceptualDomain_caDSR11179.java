/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

public abstract class ConceptualDomain_caDSR11179 extends ConceptualDomain_ISO11179 {

	private ConceptDerivationRule_caDSR11179 conceptDerivationRule;

	public ConceptDerivationRule_caDSR11179 getConceptDerivationRule() {
		return conceptDerivationRule;
	}

	public void setConceptDerivationRule(
			ConceptDerivationRule_caDSR11179 conceptDerivationRule) {
		this.conceptDerivationRule = conceptDerivationRule;
	}
	
}
