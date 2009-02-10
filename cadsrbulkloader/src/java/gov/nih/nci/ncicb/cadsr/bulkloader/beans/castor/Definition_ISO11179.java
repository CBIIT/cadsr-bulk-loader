package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 3, 2008
 * @since 
 */

public class Definition_ISO11179 {

	private String text;
	private boolean preferredDefinition;
	private ReferenceDocument_ISO11179 sourceReference;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
