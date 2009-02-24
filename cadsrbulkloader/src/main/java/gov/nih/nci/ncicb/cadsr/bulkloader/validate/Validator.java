package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;

public interface Validator {

	public ValidationResult validate (CaDSRObjects caDSRObjects);
}
