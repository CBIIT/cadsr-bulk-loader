/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptualDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import java.util.List;

public class ConceptualDomainTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<ConceptualDomain_caDSR11179> isoCDs = iso11179Elements.getConceptualDomains();
		
		for (ConceptualDomain_caDSR11179 isoCD: isoCDs) {
			ConceptualDomain cd = getConceptualDomain(isoCD);
			objRegistry.addConceptualDomain(isoCD.getTagId(), cd);
		}
		return objRegistry;
	}
	
	private ConceptualDomain getConceptualDomain(ConceptualDomain_caDSR11179 isoCD) {
		String cdId = util.getIdentifier(isoCD);
		Float version = util.getIdVersion(isoCD);
		if (version == null || version.equals(new Float(0.0))) {
			version = new Float(1.0);
		}
		String preferredName = util.getPreferredQuestionText(isoCD);
		
		ConceptualDomain cd = DomainObjectFactory.newConceptualDomain();
		cd.setId(cdId);
		cd.setPreferredName(preferredName);
		cd.setVersion(version);
		cd.setWorkflowStatus("RELEASED");
		cd.setPublicId(cdId);
		
		return cd;
	}

}
