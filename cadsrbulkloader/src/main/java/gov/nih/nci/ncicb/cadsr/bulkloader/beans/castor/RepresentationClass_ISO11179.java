/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;


public class RepresentationClass_ISO11179 extends AdminItem_ISO11179 {

	private ValueDomainRefList_ISO11179 typedBy;

	public ValueDomainRefList_ISO11179 getTypedBy() {
		return typedBy;
	}

	public void setTypedBy(ValueDomainRefList_ISO11179 typedBy) {
		this.typedBy = typedBy;
	}
	
}
