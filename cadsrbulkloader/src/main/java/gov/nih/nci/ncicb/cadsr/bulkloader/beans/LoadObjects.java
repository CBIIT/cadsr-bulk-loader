package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.Context;

public class LoadObjects {

	private Context loadContext;
	private ClassificationScheme loadClassScheme;
	
	public Context getLoadContext() {
		return loadContext;
	}
	public void setLoadContext(Context loadContext) {
		this.loadContext = loadContext;
	}
	public ClassificationScheme getLoadClassScheme() {
		return loadClassScheme;
	}
	public void setLoadClassScheme(ClassificationScheme loadClassScheme) {
		this.loadClassScheme = loadClassScheme;
	}
}
