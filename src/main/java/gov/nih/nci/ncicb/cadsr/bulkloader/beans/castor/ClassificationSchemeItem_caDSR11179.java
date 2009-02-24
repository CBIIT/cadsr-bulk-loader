package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class ClassificationSchemeItem_caDSR11179 extends
		AdminItem_ISO11179 {

	private String csiName;
	private String csiValue;
	
	public String getCsiName() {
		return csiName;
	}
	public void setCsiName(String csiName) {
		this.csiName = csiName;
	}
	public String getCsiValue() {
		return csiValue;
	}
	public void setCsiValue(String csiValue) {
		this.csiValue = csiValue;
	}
}
