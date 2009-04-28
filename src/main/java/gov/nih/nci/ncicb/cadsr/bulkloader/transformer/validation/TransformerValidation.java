package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;


public interface TransformerValidation {

	public TransformerValidationResult validate(Item toValidate);
}
