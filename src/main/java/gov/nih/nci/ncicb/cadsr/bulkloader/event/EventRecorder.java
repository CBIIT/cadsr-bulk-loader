package gov.nih.nci.ncicb.cadsr.bulkloader.event;

import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;


public interface EventRecorder {

	public void preProcessEvent(LoaderEvent _module, Object... _object);
	public void postProcessEvent(LoaderEvent _module, EventResult _result);
	public LoadResult getResult();
}
