package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.List;

public class EnumeratedValueDomain_caDSR11179 extends ValueDomain_caDSR11179 {

	private List<PermissibleValue_ISO11179> permissibleValues;

	public List<PermissibleValue_ISO11179> getPermissibleValues() {
		return permissibleValues;
	}

	public void setPermissibleValues(
			List<PermissibleValue_ISO11179> permissibleValues) {
		this.permissibleValues = permissibleValues;
	}
	
	public Datatype_ISO11179 getDatatype() {
		return super.getDatatype();
	}
	public void setDatatype(Datatype_ISO11179 datatype) {
		super.setDatatype(datatype);
	}
}
