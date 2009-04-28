package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public abstract class ValueDomain_ISO11179 extends AdminItem_ISO11179 {

	private Datatype_ISO11179 datatype;
	private String format;
	private int maxCharacters;
	private String unitOfMeasure;
	private String conceptualDomainRefId;
	
	public Datatype_ISO11179 getDatatype() {
		return datatype;
	}
	public void setDatatype(Datatype_ISO11179 datatype) {
		this.datatype = datatype;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getMaxCharacters() {
		return maxCharacters;
	}
	public void setMaxCharacters(int maxCharacters) {
		this.maxCharacters = maxCharacters;
	}
	public String getUnitOfMeasure() {
		return unitOfMeasure;
	}
	public void setUnitOfMeasure(String unitOfMeasure) {
		this.unitOfMeasure = unitOfMeasure;
	}
	public String getConceptualDomainRefId() {
		return conceptualDomainRefId;
	}
	public void setConceptualDomainRefId(String conceptualDomainRefId) {
		this.conceptualDomainRefId = conceptualDomainRefId;
	}

}
