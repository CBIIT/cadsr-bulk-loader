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
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class Definition_ISO11179 {

	private String text;
	private String type;
	private boolean preferredDefinition;
	private ReferenceDocument_ISO11179 sourceReference;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isPreferredDefinition() {
		return preferredDefinition;
	}
	public void setPreferredDefinition(boolean definition) {
		this.preferredDefinition = definition;
	}
	public ReferenceDocument_ISO11179 getSourceReference() {
		return sourceReference;
	}
	public void setSourceReference(ReferenceDocument_ISO11179 sourceReference) {
		this.sourceReference = sourceReference;
	}
	
	
}
