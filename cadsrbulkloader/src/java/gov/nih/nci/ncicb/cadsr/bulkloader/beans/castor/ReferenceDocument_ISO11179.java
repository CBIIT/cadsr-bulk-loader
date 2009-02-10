package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class ReferenceDocument_ISO11179 {

	private String identifier;
	private String description;
	private List<LanguageIdentification_ISO11179> languageIdentifiers;
	private String title;
	private List<Organization_ISO11179> providedBy;
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<LanguageIdentification_ISO11179> getLanguageIdentifier() {
		return languageIdentifiers;
	}
	public void setLanguageIdentifier(
			List<LanguageIdentification_ISO11179> languageIdentifier) {
		this.languageIdentifiers = languageIdentifier;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<Organization_ISO11179> getProvidedBy() {
		return providedBy;
	}
	public void setProvidedBy(List<Organization_ISO11179> providedBy) {
		this.providedBy = providedBy;
	}
	
	
}
