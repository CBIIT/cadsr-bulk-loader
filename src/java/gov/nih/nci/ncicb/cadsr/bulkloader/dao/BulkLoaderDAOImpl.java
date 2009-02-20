package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.ext.ExternalLoader;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 15, 2009
 * @since 
 */

public class BulkLoaderDAOImpl<T extends AdminComponent> implements BulkLoaderDAO {

	private BulkLoaderDAO dependentDAO;
	protected ExternalLoader loader;
	
	private T field;
	
	public BulkLoaderDAO getDependentDAO() {
		return dependentDAO;
	}

	public void setDependentDAO(BulkLoaderDAO dependentDAO) {
		this.dependentDAO = dependentDAO;
	}
	
	public ExternalLoader getLoader() {
		return loader;
	}

	public void setLoader(ExternalLoader loader) {
		this.loader = loader;
	}

	public T getField() {
		return field;
	}

	public void setField(T field) {
		this.field = field;
	}
	
	public void saveElementsAndDependencies(CaDSRObjects caDSRObjects) throws BulkLoaderDAORuntimeException{
		
		try {
			saveDependentElements(caDSRObjects);
			
			saveElements(caDSRObjects);
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}
	
	private void saveDependentElements(CaDSRObjects caDSRObjects) {
		
		if (dependentDAO != null) {
			dependentDAO.saveElementsAndDependencies(caDSRObjects);
		}
	}
	
	public void saveElements(CaDSRObjects caDSRObjects) {
		List<? extends AdminComponent> objectList = caDSRObjects.getList(field);
		loader.save(objectList);
	}
	
}
