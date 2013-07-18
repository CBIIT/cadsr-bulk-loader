/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class AdminItem_ISO11179 {

	private String tagId;
	
	
	private AdminRecord_ISO11179 adminRecord;
	private RegistrationAuthority_ISO11179 registeredBy;
	private List<ReferenceDocument_ISO11179> describedBy;
	private ClassifiedBy_ISO11179 classifiedBy;
	private Submission_ISO11179 submittedBy;
	private Stewardship_ISO11179 administeredBy;
	private List<TerminologicalEntry_ISO11179> having;
	private String longName;
	
	public String getTagId() {
		return tagId;
	}
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}
	public AdminRecord_ISO11179 getAdminRecord() {
		return adminRecord;
	}
	public void setAdminRecord(AdminRecord_ISO11179 adminRecord) {
		this.adminRecord = adminRecord;
	}
	public RegistrationAuthority_ISO11179 getRegisteredBy() {
		return registeredBy;
	}
	public void setRegisteredBy(RegistrationAuthority_ISO11179 registeredBy) {
		this.registeredBy = registeredBy;
	}
	public List<ReferenceDocument_ISO11179> getDescribedBy() {
		return describedBy;
	}
	public void setDescribedBy(List<ReferenceDocument_ISO11179> describedBy) {
		this.describedBy = describedBy;
	}
	public ClassifiedBy_ISO11179 getClassifiedBy() {
		return classifiedBy;
	}
	public void setClassifiedBy(
			ClassifiedBy_ISO11179 classifiedBy) {
		this.classifiedBy = classifiedBy;
	}
	public Submission_ISO11179 getSubmittedBy() {
		return submittedBy;
	}
	public void setSubmittedBy(Submission_ISO11179 submittedBy) {
		this.submittedBy = submittedBy;
	}
	public Stewardship_ISO11179 getAdministeredBy() {
		return administeredBy;
	}
	public void setAdministeredBy(Stewardship_ISO11179 administeredBy) {
		this.administeredBy = administeredBy;
	}
	public List<TerminologicalEntry_ISO11179> getHaving() {
		return having;
	}
	public void setHaving(List<TerminologicalEntry_ISO11179> having) {
		this.having = having;
	}
	public String getLongName() {
		return longName;
	}
	public void setLongName(String longName) {
		this.longName = longName;
	}

	
}
