package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.io.File;

public interface TransformerMarshaller {

	public TransformerMarshallerResult marshall(ISO11179Elements isoElements, File outputFile);
}
