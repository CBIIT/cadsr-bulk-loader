package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall;

import java.io.File;
import java.io.InputStream;

public interface TransformerUnMarshaller {

	public TransformerUnMarshallResult read(File inputFile);
	public TransformerUnMarshallResult read(InputStream is);
}
