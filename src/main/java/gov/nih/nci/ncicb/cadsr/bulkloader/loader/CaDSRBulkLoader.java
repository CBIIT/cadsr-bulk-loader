package gov.nih.nci.ncicb.cadsr.bulkloader.loader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;

public interface CaDSRBulkLoader {

	public LoadResult load(LoaderInput input, LoadProperties loadProperties);
}
