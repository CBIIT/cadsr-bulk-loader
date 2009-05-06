package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;

public class BulkLoadProcessResult {

	private TransformerResult transformResult;
	private LoadResult loadResult;
	
	public TransformerResult getTransformResult() {
		return transformResult;
	}
	public void setTransformResult(TransformerResult transformResult) {
		this.transformResult = transformResult;
	}
	public LoadResult getLoadResult() {
		return loadResult;
	}
	public void setLoadResult(LoadResult loadResult) {
		this.loadResult = loadResult;
	}

	public boolean isSuccessful() {
		if (transformResult == null || loadResult == null) {
			return false;
		}
		if (transformResult.hasErrors() || !loadResult.isSuccessful()) {
			return false;
		}
		
		return true;
	}
}
