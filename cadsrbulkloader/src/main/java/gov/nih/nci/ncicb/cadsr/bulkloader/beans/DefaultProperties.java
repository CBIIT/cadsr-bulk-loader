package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 20, 2009
 * @since 
 */

public class DefaultProperties {

	private String projectName;
	private String username;
	private float version;

	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public float getVersion() {
		return version;
	}
	public void setVersion(float version) {
		this.version = version;
	}

}
