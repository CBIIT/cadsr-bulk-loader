package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ConceptualDomainList_ISO11179 {

	private List<EnumeratedConceptualDomain_caDSR11179> enumCDs;
	private List<NonEnumeratedConceptualDomain_caDSR11179> nonEnumCDs;

	public List<ConceptualDomain_caDSR11179> getConceptualDomains() {
		List<ConceptualDomain_caDSR11179> conceptualDomains = new ArrayList<ConceptualDomain_caDSR11179>();
		if (enumCDs != null) {
			conceptualDomains.addAll(enumCDs);
		}
		if (nonEnumCDs != null) {
			conceptualDomains.addAll(nonEnumCDs);
		}
		return conceptualDomains;
	}

	public List<EnumeratedConceptualDomain_caDSR11179> getEnumCDs() {
		return enumCDs;
	}

	public void setEnumCDs(List<EnumeratedConceptualDomain_caDSR11179> enumCDs) {
		this.enumCDs = enumCDs;
	}

	public List<NonEnumeratedConceptualDomain_caDSR11179> getNonEnumCDs() {
		return nonEnumCDs;
	}

	public void setNonEnumCDs(List<NonEnumeratedConceptualDomain_caDSR11179> nonEnumCDs) {
		this.nonEnumCDs = nonEnumCDs;
	}
	
	
}
