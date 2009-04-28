package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.LexEVSDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public interface BulkLoaderDAOFacade extends BulkLoaderReadDAO, BulkLoaderWriteDAO{
	
}
