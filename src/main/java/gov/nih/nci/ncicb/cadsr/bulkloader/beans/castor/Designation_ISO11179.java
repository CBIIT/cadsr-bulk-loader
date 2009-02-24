package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class Designation_ISO11179 {

	private String name;
	private boolean preferredDesignation;
	private Definition_ISO11179 specificallyReferencing;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isPreferredDesignation() {
		return preferredDesignation;
	}
	public void setPreferredDesignation(boolean preferredDesignation) {
		this.preferredDesignation = preferredDesignation;
	}
	public Definition_ISO11179 getSpecificallyReferencing() {
		return specificallyReferencing;
	}
	public void setSpecificallyReferencing(
			Definition_ISO11179 specificallyReferencing) {
		this.specificallyReferencing = specificallyReferencing;
	}
	
	
}
