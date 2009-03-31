package gov.nih.nci.ncicb.cadsr.bulkloader.dao.factory;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAOImpl;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAOImpl;

public class BulkLoaderDAOFactoryImpl implements BulkLoaderDAOFactory {

	public BulkLoaderReadDAO getReadDAO() {
		return new BulkLoaderReadDAOImpl();
	}
	
	public BulkLoaderWriteDAO getWriteDAO() {
		return new BulkLoaderWriteDAOImpl();
	}

}
