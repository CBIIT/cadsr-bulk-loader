package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;

public interface Validation {

	public ValidationResult validate (CaDSRObjects caDSRObjects, LoadProperties loadProperties);
}
