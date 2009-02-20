package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import java.io.File;

public interface Transformer {

	public File transform (File inputFile);
}
