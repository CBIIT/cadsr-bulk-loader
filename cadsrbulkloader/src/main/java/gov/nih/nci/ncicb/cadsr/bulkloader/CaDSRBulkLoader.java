package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.Persister;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.PersisterResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.Validation;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;

public class CaDSRBulkLoader {

	private Parser parser;
	private Validation validator;
	private Persister persister;
	
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	public Validation getValidator() {
		return validator;
	}
	public void setValidator(Validation validator) {
		this.validator = validator;
	}
	
	public Persister getPersister() {
		return persister;
	}
	public void setPersister(Persister persister) {
		this.persister = persister;
	}
	public LoadResult load(LoaderInput input) {
		LoadResult result = new LoadResult(input);
		
		ParseResult parseResult = parser.parse(input.getFileToLoad());
		result.setParseResult(parseResult);
		if (!parseResult.hasErrors()) {
			CaDSRObjects caDSRObjects = parseResult.getCaDSRObjects();
			ValidationResult validationResult = validator.validate(caDSRObjects, input.isValidate());
			result.setValidationResult(validationResult);
			if (validationResult.isSuccessful() && !validationResult.hasErrors()) {
				PersisterResult persisterResult = persister.persist(caDSRObjects);
				result.setPersisterResult(persisterResult);
			}
			else {
				result.setLoadStatus(LoadStatus.FAILED_WITH_VALIDATION_ERROR);
			}
		}
		else {
			result.setLoadStatus(LoadStatus.FAILED_WITH_PARSING_ERROR);
		}
		
		if (result.isSuccessful()) {
			result.setLoadStatus(LoadStatus.SUCCESSFUL);
		}
		return result;
	}
}
