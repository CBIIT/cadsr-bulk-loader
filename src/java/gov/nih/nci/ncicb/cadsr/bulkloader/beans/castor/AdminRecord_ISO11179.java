package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.Date;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class AdminRecord_ISO11179 {

	private ItemIdentifier_ISO11179 identifier;
	private String registrationStatus;
	private String administrativeStatus;
	private String administrativeNote;
	private String changeDescription;
	private Date creationDate;
	private Date effectiveDate;
	private String explanatoryComment;
	private Date lastChangeDate;
	private String origin;
	private String unresolvedIssue;
	private Date untilDate;
	
	
	public ItemIdentifier_ISO11179 getIdentifier() {
		return identifier;
	}
	public void setIdentifier(ItemIdentifier_ISO11179 identifier) {
		this.identifier = identifier;
	}
	public String getRegistrationStatus() {
		return registrationStatus;
	}
	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
	public String getAdministrativeStatus() {
		return administrativeStatus;
	}
	public void setAdministrativeStatus(String administrativeStatus) {
		this.administrativeStatus = administrativeStatus;
	}
	public String getAdministrativeNote() {
		return administrativeNote;
	}
	public void setAdministrativeNote(String administrativeNote) {
		this.administrativeNote = administrativeNote;
	}
	public String getChangeDescription() {
		return changeDescription;
	}
	public void setChangeDescription(String changeDescription) {
		this.changeDescription = changeDescription;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExplanatoryComment() {
		return explanatoryComment;
	}
	public void setExplanatoryComment(String explanatoryComment) {
		this.explanatoryComment = explanatoryComment;
	}
	public Date getLastChangeDate() {
		return lastChangeDate;
	}
	public void setLastChangeDate(Date lastChangeDate) {
		this.lastChangeDate = lastChangeDate;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getUnresolvedIssue() {
		return unresolvedIssue;
	}
	public void setUnresolvedIssue(String unresolvedIssue) {
		this.unresolvedIssue = unresolvedIssue;
	}
	public Date getUntilDate() {
		return untilDate;
	}
	public void setUntilDate(Date untilDate) {
		this.untilDate = untilDate;
	}
	
	
}
