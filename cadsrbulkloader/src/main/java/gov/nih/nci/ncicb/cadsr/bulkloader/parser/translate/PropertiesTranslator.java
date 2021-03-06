/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Property_caDSR11179;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.Property;

import java.util.List;

public class PropertiesTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<Property_caDSR11179> isoProperties = iso11179Elements.getProperties();
		for (Property_caDSR11179 isoProperty: isoProperties) {
			Property property = getCaDSRProperty(isoProperty, objRegistry);
			
			objRegistry.addProperty(isoProperty.getTagId(), property);
		}
		
		return objRegistry;
	}
	
	public Property getCaDSRProperty(Property_caDSR11179 caDSRProperty, CaDSRObjectRegistry objRegistry) {
		ConceptDerivationRule_caDSR11179 conDerRule = caDSRProperty.getConceptDerivationRule();
		
		ConceptDerivationRule cdr = util.getConceptDerivationRule(conDerRule, objRegistry);
		List<String> conceptRefs = util.getConceptReferences(conDerRule);
		List<Concept> concepts = util.getConceptsFromRegistry(conceptRefs, objRegistry);
		
		String longName = util.getLongNameFromConcepts(concepts);
		String preferredName = util.getPreferredNameFromConcepts(concepts);
		String preferredDefinition = util.getPreferredDefinitionFromConcepts(concepts);
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIList = util.getAdminComponentCSCSI(caDSRProperty, objRegistry);
		
		Property property = DomainObjectFactory.newProperty();
		property.setConceptDerivationRule(cdr);
		property.setPreferredName(preferredName);
		property.setLongName(longName);
		property.setPreferredDefinition(preferredDefinition);
		property.setAcCsCsis(acCSCSIList);
		property.setOrigin(util.getOrigin(caDSRProperty));
		
		return property;
	}

}
