package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;

public interface Transformer {

	public TransformerResult transform (TransformerInputParams _inputParams) throws TransformerException;
}
