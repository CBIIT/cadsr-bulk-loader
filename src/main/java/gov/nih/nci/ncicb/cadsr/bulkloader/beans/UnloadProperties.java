package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

public class UnloadProperties {

	private String classificationSchemeName;
	private double csVersion;
	private String classificationSchemeItemName;
	private double csiVersion;
	
	public String getClassificationSchemeName() {
		return classificationSchemeName;
	}
	public void setClassificationSchemeName(String classificationSchemeName) {
		this.classificationSchemeName = classificationSchemeName;
	}
	public double getCsVersion() {
		return csVersion;
	}
	public void setCsVersion(double csVersion) {
		this.csVersion = csVersion;
	}
	public String getClassificationSchemeItemName() {
		return classificationSchemeItemName;
	}
	public void setClassificationSchemeItemName(String classificationSchemeItemName) {
		this.classificationSchemeItemName = classificationSchemeItemName;
	}
	public double getCsiVersion() {
		return csiVersion;
	}
	public void setCsiVersion(double csiVersion) {
		this.csiVersion = csiVersion;
	}
	
}
