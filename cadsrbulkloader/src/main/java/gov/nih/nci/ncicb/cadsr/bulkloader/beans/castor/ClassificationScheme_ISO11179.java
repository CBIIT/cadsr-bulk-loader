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

/**
 * @author mathura2
 *
 */
public class ClassificationScheme_ISO11179 extends AdminItem_ISO11179{

	private String typeName;
	private String name;
	private Float version;
	private List<ClassificationSchemeItemRef_ISO11179> containing;
	
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getVersion() {
		return version;
	}
	public void setVersion(Float version) {
		this.version = version;
	}
	public List<ClassificationSchemeItemRef_ISO11179> getContaining() {
		return containing;
	}
	public void setContaining(List<ClassificationSchemeItemRef_ISO11179> containing) {
		this.containing = containing;
	}
	
}
