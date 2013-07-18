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

public class DataElementDerivation_ISO11179 {

	private DataElementRef_ISO11179 dataElementRef;
	private DerivationRule_ISO11179 derivationRule;
	
	public DataElementRef_ISO11179 getDataElementRef() {
		return dataElementRef;
	}
	public void setDataElementRef(DataElementRef_ISO11179 dataElementRef) {
		this.dataElementRef = dataElementRef;
	}
	public DerivationRule_ISO11179 getDerivationRule() {
		return derivationRule;
	}
	public void setDerivationRule(DerivationRule_ISO11179 derivationRule) {
		this.derivationRule = derivationRule;
	}
	
	
}
