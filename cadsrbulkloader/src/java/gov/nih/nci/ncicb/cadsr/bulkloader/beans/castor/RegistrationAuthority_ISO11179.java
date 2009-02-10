package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class RegistrationAuthority_ISO11179 extends Organization_ISO11179 {

	private RegistrationAuthorityIdentifier_ISO11179 identifier;	
	private List<LanguageIdentification_ISO11179> documentationLanguageIdentifier; 
	
	private List<Registrar_ISO11179> representedBy;
	
	
	public RegistrationAuthorityIdentifier_ISO11179 getIdentifier() {
		return identifier;
	}
	public void setIdentifier(RegistrationAuthorityIdentifier_ISO11179 identifier) {
		this.identifier = identifier;
	}
	
	public List<LanguageIdentification_ISO11179> getDocumentationLanguageIdentifier() {
		return documentationLanguageIdentifier;
	}
	public void setDocumentationLanguageIdentifier(
			List<LanguageIdentification_ISO11179> documentationLanguageIdentifier) {
		this.documentationLanguageIdentifier = documentationLanguageIdentifier;
	}
	public List<Registrar_ISO11179> getRepresentedBy() {
		return representedBy;
	}
	public void setRepresentedBy(List<Registrar_ISO11179> representedBy) {
		this.representedBy = representedBy;
	}
		
	
}
