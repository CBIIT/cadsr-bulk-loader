package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import java.io.Serializable;

public class UserInput implements Serializable {

	private static final long serialVersionUID = -3992364382651237904L;
	
	private String dbURL;
	private String dbUser;
	private transient String dbPassword;
	private String inputDir;
	
	public String getDbURL() {
		return dbURL;
	}
	public void setDbURL(String dbURL) {
		this.dbURL = dbURL;
	}
	public String getDbUser() {
		return dbUser;
	}
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public void setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
	}
	public String getInputDir() {
		return inputDir;
	}
	public void setInputDir(String inputDir) {
		this.inputDir = inputDir;
	}
		
}
