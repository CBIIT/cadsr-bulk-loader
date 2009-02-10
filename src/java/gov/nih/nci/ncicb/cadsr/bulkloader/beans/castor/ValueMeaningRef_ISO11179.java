package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class ValueMeaningRef_ISO11179 {

	private String valueMeaningTagRefId;
	private int order;
	
	public String getValueMeaningTagRefId() {
		return valueMeaningTagRefId;
	}
	public void setValueMeaningTagRefId(String valueMeaningRefId) {
		this.valueMeaningTagRefId = valueMeaningRefId;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	
}
