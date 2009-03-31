package gov.nih.nci.ncicb.cadsr.bulkloader.ext;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

import java.util.Collection;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 20, 2009
 * @since 
 */

public interface ExternalLoader {

	public void save(Collection<? extends AdminComponent> adminComponents) throws BulkLoaderDAORuntimeException;
}
