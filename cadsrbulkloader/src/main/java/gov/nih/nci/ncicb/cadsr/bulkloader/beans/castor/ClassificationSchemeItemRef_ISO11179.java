/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class ClassificationSchemeItemRef_ISO11179 {

	private String csiRefId;
	private String csRefId;

	public String getCsiRefId() {
		return csiRefId;
	}

	public void setCsiRefId(String csiRefId) {
		this.csiRefId = csiRefId;
	}

	public String getCsRefId() {
		return csRefId;
	}

	public void setCsRefId(String csRefId) {
		this.csRefId = csRefId;
	}
	
	
}
