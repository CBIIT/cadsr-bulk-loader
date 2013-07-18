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
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class DataElement_ISO11179 extends AdminItem_ISO11179{

	private String id;
	private String decRefId;
	private String vdRefId;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDecRefId() {
		return decRefId;
	}
	public void setDecRefId(String decRefId) {
		this.decRefId = decRefId;
	}
	public String getVdRefId() {
		return vdRefId;
	}
	public void setVdRefId(String vdRefId) {
		this.vdRefId = vdRefId;
	}

	
}
