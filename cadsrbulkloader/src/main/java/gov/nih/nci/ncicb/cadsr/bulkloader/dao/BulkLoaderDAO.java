package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public interface BulkLoaderDAO {

	public void saveElements(CaDSRObjects caDSRObjects) throws BulkLoaderDAORuntimeException;
	public void saveElementsAndDependencies(CaDSRObjects caDSRObjects) throws BulkLoaderDAORuntimeException;
	public BulkLoaderDAO getDependentDAO();
}
