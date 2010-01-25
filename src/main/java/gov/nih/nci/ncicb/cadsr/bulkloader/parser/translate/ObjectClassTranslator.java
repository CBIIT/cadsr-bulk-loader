package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ObjectClass_caDSR11179;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;

import java.util.List;

public class ObjectClassTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<ObjectClass_caDSR11179> isoObjectClasses = iso11179Elements.getObjectClasses();
		for (ObjectClass_caDSR11179 isoObjectClass: isoObjectClasses) {
			ObjectClass objectClass = getCaDSRObjectClass(isoObjectClass, objRegistry);
			objRegistry.addObjectClass(isoObjectClass.getTagId(), objectClass);
		}
		
		return objRegistry;
	}
	
	public ObjectClass getCaDSRObjectClass(ObjectClass_caDSR11179 isoObjClass, CaDSRObjectRegistry objRegistry) {
		ConceptDerivationRule_caDSR11179 conDerRule = isoObjClass.getConceptDerivationRule();
		ConceptDerivationRule cdr = util.getConceptDerivationRule(conDerRule, objRegistry);
		
		List<String> conceptRefs = util.getConceptReferences(conDerRule);
		List<Concept> concepts = util.getConceptsFromRegistry(conceptRefs, objRegistry);
		
		String longName = util.getLongNameFromConcepts(concepts);
		String preferredName = util.getPreferredNameFromConcepts(concepts);
		String preferredDefinition = util.getDefinitionFromConcepts(concepts);
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIList = util.getAdminComponentCSCSI(isoObjClass, objRegistry);
		
		ObjectClass objClass = DomainObjectFactory.newObjectClass();
		objClass.setLongName(longName);
		objClass.setConceptDerivationRule(cdr);
		objClass.setPreferredName(preferredName);
		objClass.setPreferredDefinition(preferredDefinition);
		objClass.setAcCsCsis(acCSCSIList);
		objClass.setOrigin(util.getOrigin(isoObjClass));
		
		return objClass;
	}

}
