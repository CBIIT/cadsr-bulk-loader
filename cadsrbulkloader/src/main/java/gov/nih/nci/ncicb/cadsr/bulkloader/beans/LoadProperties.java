package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 20, 2009
 * @since 
 */

public class LoadProperties {

	private String contextName;
	private String classificationSchemeName;
	private String classificationSchemeItemName;
	private String loadSource;
	private String defaultConceptualDomain;
	
	public String getContextName() {
		return contextName;
	}
	public void setContextName(String contextName) {
		this.contextName = contextName;
	}
	public String getClassificationSchemeName() {
		return classificationSchemeName;
	}
	public void setClassificationSchemeName(String classificationSchemeName) {
		this.classificationSchemeName = classificationSchemeName;
	}
	public String getClassificationSchemeItemName() {
		return classificationSchemeItemName;
	}
	public void setClassificationSchemeItemName(String classificationSchemeItemName) {
		this.classificationSchemeItemName = classificationSchemeItemName;
	}
	public String getLoadSource() {
		return loadSource;
	}
	public void setLoadSource(String loadSource) {
		this.loadSource = loadSource;
	}
	public String getDefaultConceptualDomain() {
		return defaultConceptualDomain;
	}
	public void setDefaultConceptualDomain(String defaultConceptualDomain) {
		this.defaultConceptualDomain = defaultConceptualDomain;
	}
	
}
