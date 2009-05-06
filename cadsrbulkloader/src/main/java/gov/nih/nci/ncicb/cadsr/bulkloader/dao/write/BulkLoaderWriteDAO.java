package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;


public interface BulkLoaderWriteDAO {

	public void save(CaDSRObjects caDSRObjects, LoadObjects loadObjects);
}
