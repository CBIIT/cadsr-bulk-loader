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

	private List<ConceptualDomain_caDSR11179> conceptualDomains = new ArrayList<ConceptualDomain_caDSR11179>();
	private List<EnumeratedConceptualDomain_caDSR11179> enumCDs;
	private List<NonEnumeratedConceptualDomain_caDSR11179> nonEnumCDs;

	public List<ConceptualDomain_caDSR11179> getConceptualDomains() {
		return conceptualDomains;
	}

	public List<EnumeratedConceptualDomain_caDSR11179> getEnumCDs() {
		return enumCDs;
	}

	public void setEnumCDs(List<EnumeratedConceptualDomain_caDSR11179> enumCDs) {
		conceptualDomains.removeAll(enumCDs);
		this.enumCDs = enumCDs;
		conceptualDomains.addAll(enumCDs);
	}

	public List<NonEnumeratedConceptualDomain_caDSR11179> getNonEnumCDs() {
		return nonEnumCDs;
	}

	public void setNonEnumCDs(List<NonEnumeratedConceptualDomain_caDSR11179> nonEnumCDs) {
		conceptualDomains.removeAll(nonEnumCDs);
		this.nonEnumCDs = nonEnumCDs;
		conceptualDomains.addAll(nonEnumCDs);
	}
	
	
}
