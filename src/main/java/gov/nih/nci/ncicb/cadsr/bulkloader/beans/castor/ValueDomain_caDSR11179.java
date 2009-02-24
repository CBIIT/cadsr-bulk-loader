package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public abstract class ValueDomain_caDSR11179 extends ValueDomain_ISO11179 {

	private ConceptDerivationRule_caDSR11179 conDerivationRule;

	public ConceptDerivationRule_caDSR11179 getConDerivationRule() {
		return conDerivationRule;
	}

	public void setConDerivationRule(
			ConceptDerivationRule_caDSR11179 conDerivationRule) {
		this.conDerivationRule = conDerivationRule;
	}
	
	
	public abstract List<PermissibleValue_ISO11179> getPermissibleValues();
}
