package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public interface BulkLoaderDAOFacade extends BulkLoaderReadDAO, BulkLoaderWriteDAO{
	public ClassificationScheme getClassificationScheme(ClassificationScheme classScheme);
	public ClassificationScheme getClassificationScheme(String csName);
	public List<ClassSchemeClassSchemeItem> getClassSchemeClassSchemeItems(String csName);
}
