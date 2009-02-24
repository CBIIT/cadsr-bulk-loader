package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public abstract class ConceptualDomain_ISO11179 extends AdminItem_ISO11179 {

	private String dimensionality;
	private ConceptualDomainRelationship_ISO11179 relatedTo;
	
	public String getDimensionality() {
		return dimensionality;
	}
	public void setDimensionality(String dimensionality) {
		this.dimensionality = dimensionality;
	}
	public ConceptualDomainRelationship_ISO11179 getRelatedTo() {
		return relatedTo;
	}
	public void setRelatedTo(ConceptualDomainRelationship_ISO11179 relatedTo) {
		this.relatedTo = relatedTo;
	}
	
}
