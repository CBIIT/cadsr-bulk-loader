package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.Date;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class PermissibleValue_ISO11179 {

	private Date beginDate;
	private Date endDate;
	private String value;
	private String valueMeaningRefId;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getValueMeaningRefId() {
		return valueMeaningRefId;
	}
	public void setValueMeaningRefId(String valueMeaningRefId) {
		this.valueMeaningRefId = valueMeaningRefId;
	}
	
}
