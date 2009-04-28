package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

public interface TransformerTransformation {

	public TransformerTransformationResult transform(Item marshalledObject);
}
