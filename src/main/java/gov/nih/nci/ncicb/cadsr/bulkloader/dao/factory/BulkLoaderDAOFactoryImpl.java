package gov.nih.nci.ncicb.cadsr.bulkloader.dao.factory;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;

public class BulkLoaderDAOFactoryImpl implements BulkLoaderDAOFactory {

	private BulkLoaderReadDAO readDAO;
	private BulkLoaderWriteDAO writeDAO;
	
	public void setReadDAO(BulkLoaderReadDAO readDAO) {
		this.readDAO = readDAO;
	}

	public void setWriteDAO(BulkLoaderWriteDAO writeDAO) {
		this.writeDAO = writeDAO;
	}

	public BulkLoaderReadDAO getReadDAO() {
		return readDAO;
	}
	
	public BulkLoaderWriteDAO getWriteDAO() {
		return writeDAO;
	}

}
