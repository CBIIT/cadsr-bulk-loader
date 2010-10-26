package gov.nih.nci.ncicb.cadsr.bulkloader.event;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.PersisterResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;

public class EventRecorderImpl implements EventRecorder {

	private LoadResult result;
	private EventObserver observer;
	
	public EventRecorderImpl() {
		EventObserver parser = new ParserEventObserver();
		EventObserver validator = new ValidatorEventObserver();
		EventObserver persister = new PersisterEventObserver();

		parser.setChild(validator);
		validator.setChild(persister);
		
		observer = parser;
	}
	
	public void preProcessEvent(LoaderEvent _module, Object... _object) {
		observer.preProcess(_module, _object);
	}
	
	public void postProcessEvent(LoaderEvent _module, EventResult _result) {
		observer.postProcess(_module, _result);
	}
	
	public LoadResult getResult() {
		return result;
	}

	interface EventObserver {
		public void setChild(EventObserver _link);
		public void preProcess(LoaderEvent _module, Object... _object);
		public void postProcess(LoaderEvent _module, EventResult _result);
	}
	
	abstract class AbstractEventObserver implements EventObserver {
		protected EventObserver child;
		
		public void setChild(EventObserver _link) {
			child = _link;
		}
		
		public void preProcess(LoaderEvent _module, Object... _object) {
			boolean isProcessed = doPreProcess(_module, _object);
			if (!isProcessed && child != null) {
				child.preProcess(_module, _object);
			}
		}
		
		public void postProcess(LoaderEvent _module, EventResult _result) {
			boolean isProcessed = doPostProcess(_module, _result);
			if (!isProcessed && child != null) {
				child.postProcess(_module, _result);
			}
		}
		
		protected abstract boolean doPreProcess(LoaderEvent _module, Object... _object);
		protected abstract boolean doPostProcess(LoaderEvent _module, EventResult _result);
	}
	
	class ParserEventObserver extends AbstractEventObserver {
		
		public boolean doPreProcess(LoaderEvent _module, Object... _object) {
			if (_module != LoaderEvent.PARSING) {
				return false;
			}
			else if (_object != null && _object.length > 0){
				for (Object obj: _object) {
					if (obj instanceof LoaderInput) {
						result= new LoadResult((LoaderInput)obj);
						break;
					}
				}
			}
			
			return true;
		}
		
		public boolean doPostProcess(LoaderEvent _module, EventResult _result) {
			if (_module != LoaderEvent.PARSING) {
				return false;
			}
			else if (result != null){
				result.setParseResult((ParseResult)_result);
			}
			
			return true;
		}
	}
	
	class ValidatorEventObserver extends AbstractEventObserver {
		
		public boolean doPreProcess(LoaderEvent _module, Object... _object) {
			if (_module != LoaderEvent.VALIDATION) {
				return false;
			}
			return true;
		}
		
		public boolean doPostProcess(LoaderEvent _module, EventResult _result) {
			if (_module != LoaderEvent.VALIDATION) {
				return false;
			}
			else if (result != null){
				result.setValidationResult((ValidationResult)_result);
			}
			
			return true;
		}
	}
	
	class PersisterEventObserver extends AbstractEventObserver {
		
		public boolean doPreProcess(LoaderEvent _event, Object... _object) {
			if (_event != LoaderEvent.PERSISTING) {
				return false;
			}
			
			if (_object != null && _object.length > 0) {
				for (Object obj: _object) {
					if (obj instanceof CaDSRObjects) {
						saveSnapshot(_event, (CaDSRObjects)obj);
						break;
					}
				}
			}
			return true;
		}
		
		public boolean doPostProcess(LoaderEvent _event, EventResult _result) {
			if (_event != LoaderEvent.PERSISTING) {
				return false;
			}
			else if (result != null){
				PersisterResult res = (PersisterResult)_result;
				saveSnapshot(_event, res.getCaDSRObjects());
				result.setPersisterResult((PersisterResult)_result);
			}
			
			return true;
		}
		
		private void saveSnapshot(LoaderEvent _event, CaDSRObjects obj) {
			CaDSRObjects.Memento snapshot = obj.saveToMemento();
			result.addSnapshot(_event, snapshot);
		}
	}	
}
