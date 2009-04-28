package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall;

import java.io.File;

public interface TransformerUnMarshaller {

	public TransformerUnMarshallResult read(File inputFile);
}
