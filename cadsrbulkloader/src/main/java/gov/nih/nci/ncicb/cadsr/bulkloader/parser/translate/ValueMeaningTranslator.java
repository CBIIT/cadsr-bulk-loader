package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ComponentConceptList_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ComponentConcept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueMeaning_caDSR11179;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
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
		ConceptDerivationRule cdr = getVMCDR(isoCDR, objRegistry);
		
		String longName = util.getLongName(cdr);
		List<AdminComponentClassSchemeClassSchemeItem> acCsCSI = util.getAdminComponentCSCSI(isoVM, objRegistry);
		
		ValueMeaning valueMeaning = DomainObjectFactory.newValueMeaning();
		valueMeaning.setConceptDerivationRule(cdr);
		valueMeaning.setLongName(longName);
		valueMeaning.setId(longName);
		valueMeaning.setAcCsCsis(acCsCSI);
		valueMeaning.setPreferredDefinition(util.getDefinition(cdr));
		valueMeaning.setOrigin(util.getOrigin(isoVM));
		
		return valueMeaning;
	}
	
	private ConceptDerivationRule getVMCDR(ConceptDerivationRule_caDSR11179 isoCDR, CaDSRObjectRegistry objRegistry) {
		ComponentConceptList_caDSR11179 isoCompConList = isoCDR.getComponentConceptsList();
		List<ComponentConcept_caDSR11179> isoCompCons = isoCompConList.getComponentConcepts();
		
		ConceptDerivationRule cdr = DomainObjectFactory.newConceptDerivationRule();
		List<ComponentConcept> compCons = new ArrayList<ComponentConcept>();
		
		for (ComponentConcept_caDSR11179 isoCompCon: isoCompCons) {
			String conRefId = isoCompCon.getConceptRefId();
			Concept con = objRegistry.getConcept(conRefId);
			ComponentConcept compCon = DomainObjectFactory.newComponentConcept();
			compCon.setConcept(con);
			compCon.setOrder(isoCompCon.getOrder());
			compCon.setConceptDerivationRule(cdr);
			
			compCons.add(compCon);
		}
		
		cdr.setComponentConcepts(compCons);
		return cdr;
	}

}
