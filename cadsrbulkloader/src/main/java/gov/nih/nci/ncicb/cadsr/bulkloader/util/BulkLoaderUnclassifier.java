package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BulkLoaderUnclassifier {

	private DataSource dataSource;
	private static Log log = LogFactory.getLog(BulkLoaderUnclassifier.class.getName());
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void unclassify(LoadProperties loadProperties) {
		
	}
	
}
