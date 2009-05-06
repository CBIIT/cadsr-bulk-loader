package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;

public interface Persister {

	public PersisterResult persist(CaDSRObjects cadsrObjects, LoadObjects loadObjects);
}
