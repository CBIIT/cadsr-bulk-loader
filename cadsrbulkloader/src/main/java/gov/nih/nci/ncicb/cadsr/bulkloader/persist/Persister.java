package gov.nih.nci.ncicb.cadsr.bulkloader.persist;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;

public interface Persister {

	public PersisterResult persist(CaDSRObjects cadsrObjects);
}
