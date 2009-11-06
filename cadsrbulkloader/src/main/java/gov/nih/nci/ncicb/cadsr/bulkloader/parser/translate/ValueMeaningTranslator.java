package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueMeaning_caDSR11179;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.List;

public class ValueMeaningTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<ValueMeaning_caDSR11179> isoVMs = iso11179Elements.getValueMeanings();
		for (ValueMeaning_caDSR11179 isoVM: isoVMs) {
			ValueMeaning vm = getValueMeaning(isoVM, objRegistry);
			objRegistry.addValueMeaning(isoVM.getTagId(), vm);
		}
		
		return objRegistry;
	}
	
	private ValueMeaning getValueMeaning(ValueMeaning_caDSR11179 isoVM, CaDSRObjectRegistry objRegistry) {
		ConceptDerivationRule_caDSR11179 isoCDR = isoVM.getConceptDerivationRule();
		ConceptDerivationRule cdr = util.getConceptDerivationRule(isoCDR, objRegistry);
		String longName = util.getLongName(cdr);
		List<AdminComponentClassSchemeClassSchemeItem> acCsCSI = util.getAdminComponentCSCSI(isoVM, objRegistry);
		
		ValueMeaning valueMeaning = DomainObjectFactory.newValueMeaning();
		valueMeaning.setConceptDerivationRule(cdr);
		valueMeaning.setLongName(longName);
		valueMeaning.setId(longName);
		valueMeaning.setAcCsCsis(acCsCSI);
		valueMeaning.setPreferredDefinition(util.getDefinition(cdr));
		
		return valueMeaning;
	}

}
