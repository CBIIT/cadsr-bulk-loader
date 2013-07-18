/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public class DataElementConceptRelationship_ISO11179 {

	private String relationshipTypeDescription;
	private List<DataElementConceptRef_ISO11179> relatedTo;
	
	public String getRelationshipTypeDescription() {
		return relationshipTypeDescription;
	}
	public void setRelationshipTypeDescription(String relationshipTypeDescription) {
		this.relationshipTypeDescription = relationshipTypeDescription;
	}
	public List<DataElementConceptRef_ISO11179> getRelatedTo() {
		return relatedTo;
	}
	public void setRelatedTo(List<DataElementConceptRef_ISO11179> relatedTo) {
		this.relatedTo = relatedTo;
	}
	
}
