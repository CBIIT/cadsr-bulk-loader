package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

import java.util.Collection;


public interface BulkLoaderWriteDAO {

	public void save(AdminComponent adminComponent);
	public void save(Collection<? extends AdminComponent> adminComponents);
}
