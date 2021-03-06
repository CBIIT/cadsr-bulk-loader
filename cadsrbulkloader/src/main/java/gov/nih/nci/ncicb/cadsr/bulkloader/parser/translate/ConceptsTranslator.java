/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Concept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Definition_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Designation_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.LanguageSection_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Organization_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ReferenceDocument_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.TerminologicalEntry_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.UMLLoaderHandler;
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

		String conCode = iso11179Concept.getCode();
		
		Concept concept = daoFacade.findCaDSRConceptByCUI(conCode);
		
		if (concept == null || concept.getPreferredName() == null || concept.getPreferredName().equals("")) {
			concept = daoFacade.findEVSConceptByCUI(conCode, false);
		}
		
		if (concept == null || concept.getPreferredName() == null || concept.getPreferredName().equals("")) {
			concept = DomainObjectFactory.newConcept();
		}
		
		concept.setPreferredName(conCode);
		
		String isoLongName = iso11179Concept.getLongName();
		if ((concept.getLongName() == null || !concept.getLongName().trim().equals("")) && isoLongName != null && !isoLongName.trim().equals("")) {
			concept.setLongName(isoLongName);
		}
		
		concept = addAlternateNamesAndDefinitions(concept, iso11179Concept);
		concept.setDefinitionSource(concept.getDefinitionSource());
		
		String prefDef = concept.getPreferredDefinition();
		if (prefDef == null || prefDef.equals("")) {
			addDefaultDef(concept);
		}
		
		return concept;
	}
	
	private void addDefaultDef(Concept concept) {
		String defaultDefStr = UMLLoaderHandler.getDefaultEVSDefinition();
		
		Definition defaultDef = CaDSRObjectsUtil.createDefinition();
		defaultDef.setDefinition(defaultDefStr);
		concept.addDefinition(defaultDef);
		
		concept.setPreferredDefinition(defaultDefStr);
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
		if (isoDefs != null) {
			for (Definition_ISO11179 isoDef: isoDefs) {
				String defStr = isoDef.getText();
				
				if (!hasDefinition(concept, defStr)) {
					Definition def = DomainObjectFactory.newDefinition();
					def.setDefinition(defStr);
					
					concept.addDefinition(def);
					if (isoDef.isPreferredDefinition()) {
						concept.setPreferredDefinition(defStr);
						ReferenceDocument_ISO11179 isoRefDoc = isoDef.getSourceReference();
						String orgName = "";
						
						if(isoRefDoc != null) {
							List<Organization_ISO11179> isoOrgs = isoRefDoc.getProvidedBy();
							
							for (Organization_ISO11179 isoOrg: isoOrgs) {
								orgName = orgName.equals("")?isoOrg.getName():orgName+"_"+isoOrg.getName();
							}
						}
						
						concept.setDefinitionSource(orgName);
					}
				}
			}
		}
		
		return concept;
	}
	
	private boolean hasDefinition(Concept concept, String defStr) {
		List<Definition> existingDefs = concept.getDefinitions();
		if (existingDefs != null) {
			for (Definition existingDef: existingDefs) {
				if (existingDef.getDefinition().equalsIgnoreCase(defStr)) {
					return true;
				}
			}
		}
		return false;
	}

}
