/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import java.io.Serializable;

public class UserInput implements Serializable {

	private static final long serialVersionUID = -3992364382651237904L;
	
	private String dbURL;
	private String dbUser;
	private transient String dbPassword;
	private String inputDir;
	
	private String classificationSchemeName;
	private String classificationSchemeItemName;
	private String classificationSchemeVersion;
	private String classificationSchemeItemVersion;
	
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
	public String getClassificationSchemeVersion() {
		return classificationSchemeVersion;
	}
	public void setClassificationSchemeVersion(String classificationSchemeVersion) {
		this.classificationSchemeVersion = classificationSchemeVersion;
	}
	public String getClassificationSchemeItemVersion() {
		return classificationSchemeItemVersion;
	}
	public void setClassificationSchemeItemVersion(
			String classificationSchemeItemVersion) {
		this.classificationSchemeItemVersion = classificationSchemeItemVersion;
	}
}
