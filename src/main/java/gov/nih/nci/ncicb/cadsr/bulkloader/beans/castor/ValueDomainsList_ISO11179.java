package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ValueDomainsList_ISO11179 {

	private List<ValueDomain_caDSR11179> valueDomains = new ArrayList<ValueDomain_caDSR11179>();
	private List<ValueDomain_caDSR11179> enumVDs;
	private List<ValueDomain_caDSR11179> nonEnumVDs;

	public List<ValueDomain_caDSR11179> getValueDomains() {
		return valueDomains;
	}

	public List<ValueDomain_caDSR11179> getEnumVDs() {
		return enumVDs;
	}

	public void setEnumVDs(List<ValueDomain_caDSR11179> enumVDs) {
		valueDomains.removeAll(enumVDs);
		this.enumVDs = enumVDs;
		valueDomains.addAll(enumVDs);
	}

	public List<ValueDomain_caDSR11179> getNonEnumVDs() {
		return nonEnumVDs;
	}

	public void setNonEnumVDs(List<ValueDomain_caDSR11179> nonEnumVDs) {
		valueDomains.removeAll(nonEnumVDs);
		this.nonEnumVDs = nonEnumVDs;
		valueDomains.addAll(nonEnumVDs);
	}
	
	
}
