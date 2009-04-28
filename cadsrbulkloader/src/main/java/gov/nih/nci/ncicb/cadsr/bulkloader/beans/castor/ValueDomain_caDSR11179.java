package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public abstract class ValueDomain_caDSR11179 extends ValueDomain_ISO11179 {

	private ConceptDerivationRule_caDSR11179 conDerivationRule;
	private RepresentationClass_caDSR11179 representationClass;

	public ConceptDerivationRule_caDSR11179 getConDerivationRule() {
		return conDerivationRule;
	}

	public void setConDerivationRule(
			ConceptDerivationRule_caDSR11179 conDerivationRule) {
		this.conDerivationRule = conDerivationRule;
	}
		
	public RepresentationClass_caDSR11179 getRepresentationClass() {
		return representationClass;
	}

	public void setRepresentationClass(
			RepresentationClass_caDSR11179 representationClass) {
		this.representationClass = representationClass;
	}

	public abstract List<PermissibleValue_ISO11179> getPermissibleValues();
}
