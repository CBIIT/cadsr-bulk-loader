package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

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

	private List<String> relatedDECsRefIds;

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

	public List<String> getRelatedDECsRefIds() {
		return relatedDECsRefIds;
	}

	public void setRelatedDECsRefIds(List<String> relatedDECsRefIds) {
		this.relatedDECsRefIds = relatedDECsRefIds;
	}
	
	
}
