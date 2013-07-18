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

public class DataElementConcept_ISO11179 extends AdminItem_ISO11179{

	private String id;
	private String conceptualDomainRefId;

	private String objectClassRefId;
	private String propertyRefId;

	private DataElementConceptRelationship_ISO11179 decRelationship;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getConceptualDomainRefId() {
		return conceptualDomainRefId;
	}

	public void setConceptualDomainRefId(String conceptualDomainRefId) {
		this.conceptualDomainRefId = conceptualDomainRefId;
	}

	public String getObjectClassRefId() {
		return objectClassRefId;
	}

	public void setObjectClassRefId(String objectClassRefId) {
		this.objectClassRefId = objectClassRefId;
	}

	public String getPropertyRefId() {
		return propertyRefId;
	}

	public void setPropertyRefId(String propertyRefId) {
		this.propertyRefId = propertyRefId;
	}

	public DataElementConceptRelationship_ISO11179 getDecRelationship() {
		return decRelationship;
	}

	public void setDecRelationship(
			DataElementConceptRelationship_ISO11179 decRelationship) {
		this.decRelationship = decRelationship;
	}

	
}
