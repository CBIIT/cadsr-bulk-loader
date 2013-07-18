/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public class ReferenceDocumentList_ISO11179 {

	private List<ReferenceDocument_ISO11179> referenceDocuments;

	public List<ReferenceDocument_ISO11179> getReferenceDocuments() {
		return referenceDocuments;
	}

	public void setReferenceDocuments(
			List<ReferenceDocument_ISO11179> referenceDocuments) {
		this.referenceDocuments = referenceDocuments;
	}
	
}
