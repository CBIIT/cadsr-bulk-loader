/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public class ClassifiedBy_ISO11179 {

	private List<ClassificationSchemeItemRef_ISO11179> classifiedBy;

	public List<ClassificationSchemeItemRef_ISO11179> getClassifiedBy() {
		return classifiedBy;
	}

	public void setClassifiedBy(
			List<ClassificationSchemeItemRef_ISO11179> classifiedBy) {
		this.classifiedBy = classifiedBy;
	}
	
}
