package gov.nih.nci.ncicb.cadsr.bulkloader.dao.write;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.UMLLoaderHandler;
import gov.nih.nci.ncicb.cadsr.loader.persister.Persister;

import java.util.List;

public class BulkLoaderWriteDAOImpl implements BulkLoaderWriteDAO {
	

	private List<Persister> persisters;
	
	public List<Persister> getPersisters() {
		return persisters;
	}

	public void setPersisters(List<Persister> persisters) {
		this.persisters = persisters;
	}

	public synchronized void save(CaDSRObjects cadsrObjects, LoadObjects loadObjects) {
		UMLLoaderHandler handler = new UMLLoaderHandler();
		try {
			handler.loadDefaultsIfNotLoaded(loadObjects);
			handler.loadElements(cadsrObjects, loadObjects);
			
			for (Persister persister: persisters) {
				persister.persist();
			}
			
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
		finally {
			handler.unLoadElements(cadsrObjects, loadObjects);
		}
	}
	
	
}
