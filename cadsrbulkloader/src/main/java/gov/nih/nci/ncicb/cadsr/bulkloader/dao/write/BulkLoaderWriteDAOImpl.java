package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.bulkloader.ext.ExternalLoader;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BulkLoaderWriteDAOImpl implements BulkLoaderWriteDAO {

	protected ExternalLoader loader;
	
	public ExternalLoader getLoader() {
		return loader;
	}

	public void setLoader(ExternalLoader loader) {
		this.loader = loader;
	}

	public void save(AdminComponent adminComponent) {
		List<AdminComponent> adminComponents = new ArrayList<AdminComponent>();
		adminComponents.add(adminComponent);
		
		save(adminComponents);
	}

	public void save(Collection<? extends AdminComponent> adminComponents) {
		loader.save(adminComponents);
	}

}
