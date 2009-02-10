package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;

import java.io.File;

public class CaDSRBulkLoader {

	private Parser parser;
	private BulkLoaderDAO dao;
	
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	public BulkLoaderDAO getDao() {
		return dao;
	}
	public void setDao(BulkLoaderDAO dao) {
		this.dao = dao;
	}
	
	public void load(File _xmlFile) {
		CaDSRObjects caDSRObjects = parser.parse(_xmlFile);
		dao.saveElementsAndDependencies(caDSRObjects);
	}
}
