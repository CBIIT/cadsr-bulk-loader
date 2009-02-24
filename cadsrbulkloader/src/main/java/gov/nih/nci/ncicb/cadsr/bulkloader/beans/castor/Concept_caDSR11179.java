package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public class Concept_caDSR11179 extends AdminItem_ISO11179{

	private String code;
	private List<Definition_ISO11179> definitions;
	
	public String getCode() {
		return code;
	}
	public void setCode(String id) {
		this.code = id;
	}
	public List<Definition_ISO11179> getDefinitions() {
		return definitions;
	}
	public void setDefinitions(List<Definition_ISO11179> definitions) {
		this.definitions = definitions;
	}
	
}
