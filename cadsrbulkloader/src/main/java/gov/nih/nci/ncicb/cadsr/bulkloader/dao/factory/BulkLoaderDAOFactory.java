package gov.nih.nci.ncicb.cadsr.bulkloader.dao.factory;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;

public interface BulkLoaderDAOFactory {

	public BulkLoaderReadDAO getReadDAO();
	public BulkLoaderWriteDAO getWriteDAO();
}
