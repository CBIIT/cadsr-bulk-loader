package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class Designation_ISO11179 {

	private String name;
	private String type;
	private boolean preferredDesignation;
	private Definition_ISO11179 specificallyReferencing;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
