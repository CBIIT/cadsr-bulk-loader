package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

import java.util.List;


public interface BulkLoaderReadDAO {

	public <T extends AdminComponent> List<T> findAdminComponent(T adminComp);
}
