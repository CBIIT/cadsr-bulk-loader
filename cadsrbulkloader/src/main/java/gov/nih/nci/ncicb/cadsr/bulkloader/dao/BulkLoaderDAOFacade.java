/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.read.BulkLoaderReadDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.write.BulkLoaderWriteDAO;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

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
	public Concept findEVSConceptByCUI(String cui, boolean includeRetired);
	public CaDSRObjects loadFromCaDSR(CaDSRObjects caDSRObjects, LoadObjects loadObjects);
	public void clearCache();
}
