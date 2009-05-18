package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Concept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Definition_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Designation_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.LanguageSection_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Organization_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ReferenceDocument_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.TerminologicalEntry_ISO11179;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import java.util.List;

public class ConceptsTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<Concept_caDSR11179> isoConcepts = iso11179Elements.getConcepts();
		for (Concept_caDSR11179 isoConcept: isoConcepts) {
			Concept concept = getCaDSRAPIConcept(isoConcept);
			objRegistry.addConcept(isoConcept.getTagId(), concept);
		}
		
		return objRegistry;
	}
	
	private Concept getCaDSRAPIConcept(Concept_caDSR11179 iso11179Concept) {
		if (iso11179Concept == null) return null;

		Concept concept = DomainObjectFactory.newConcept();
		concept.setPreferredName(iso11179Concept.getCode());
		concept.setLongName(util.getDesignation(iso11179Concept));
		
		concept = addAlternateNamesAndDefinitions(concept, iso11179Concept);
		
		return concept;
	}
	
	private Concept addAlternateNamesAndDefinitions(Concept concept, Concept_caDSR11179 isoConcept) {
		List<TerminologicalEntry_ISO11179> termEntries = isoConcept.getHaving();
		if (termEntries != null) {
			for (TerminologicalEntry_ISO11179 termEntry: termEntries) {
				List<LanguageSection_ISO11179> langSections = termEntry.getContainingEntries();
				for (LanguageSection_ISO11179 langSection: langSections) {
					List<Designation_ISO11179> isoAltNames = langSection.getNamingEntries();
					List<Definition_ISO11179> isoDefs = langSection.getDefiningEntries();
					
					concept = addAlternateNames(concept, isoAltNames);
					concept = addDefinitions(concept, isoDefs);
				}
			}
		}
		return concept;
	}
	
	private Concept addAlternateNames(Concept concept, List<Designation_ISO11179> isoAltNames) {
		for (Designation_ISO11179 isoAltName: isoAltNames) {
			String altNameStr = isoAltName.getName();
			
			AlternateName altName = DomainObjectFactory.newAlternateName();
			altName.setName(altNameStr);
			
			concept.addAlternateName(altName);
		}
		
		return concept;
	}
	
	private Concept addDefinitions(Concept concept, List<Definition_ISO11179> isoDefs) {
		for (Definition_ISO11179 isoDef: isoDefs) {
			String defStr = isoDef.getText();
			
			Definition def = DomainObjectFactory.newDefinition();
			def.setDefinition(defStr);
			
			concept.addDefinition(def);
			if (isoDef.isPreferredDefinition()) {
				concept.setPreferredDefinition(defStr);
				ReferenceDocument_ISO11179 isoRefDoc = isoDef.getSourceReference();
				List<Organization_ISO11179> isoOrgs = isoRefDoc.getProvidedBy();
				String orgName = "";
				for (Organization_ISO11179 isoOrg: isoOrgs) {
					orgName = orgName.equals("")?isoOrg.getName():orgName+"_"+isoOrg.getName();
				}
				concept.setDefinitionSource(orgName);
			}
		}
		
		return concept;
	}

}
