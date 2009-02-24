package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class RegistrationAuthorityIdentifier_ISO11179 {

	private String internationalCodeDesignator;
	private String opiSource;
	private String organizationIdentifier;
	private String organizationPartIdentifier;
	public String getInternationalCodeDesignator() {
		return internationalCodeDesignator;
	}
	public void setInternationalCodeDesignator(String internationalCodeDesignator) {
		this.internationalCodeDesignator = internationalCodeDesignator;
	}
	public String getOpiSource() {
		return opiSource;
	}
	public void setOpiSource(String opiSource) {
		this.opiSource = opiSource;
	}
	public String getOrganizationIdentifier() {
		return organizationIdentifier;
	}
	public void setOrganizationIdentifier(String organizationIdentifier) {
		this.organizationIdentifier = organizationIdentifier;
	}
	public String getOrganizationPartIdentifier() {
		return organizationPartIdentifier;
	}
	public void setOrganizationPartIdentifier(String organizationPartIdentifier) {
		this.organizationPartIdentifier = organizationPartIdentifier;
	}
	
	
}
