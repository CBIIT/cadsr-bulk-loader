package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind.ObjectBinder;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.Translator;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.TranslatorResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator.SchemaValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator.SchemaValidator;

import java.io.File;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 25, 2008
 * @since 
 */

public class ParserImpl implements Parser {

	private SchemaValidator schemaValidator;
	private ObjectBinder binder;
	private Translator<CaDSRObjects> translator;
	
	
	public SchemaValidator getSchemaValidator() {
		return schemaValidator;
	}

	public void setSchemaValidator(SchemaValidator schemaValidator) {
		this.schemaValidator = schemaValidator;
	}

	public ObjectBinder getBinder() {
		return binder;
	}

	public void setBinder(ObjectBinder binder) {
		this.binder = binder;
	}

	public Translator<CaDSRObjects> getTranslator() {
		return translator;
	}

	public void setTranslator(Translator<CaDSRObjects> translator) {
		this.translator = translator;
	}

	public ParseResult parse(File _xmlFile) {
		ParseResult result = new ParseResult();
		
		SchemaValidationResult validationResult = schemaValidator.validate(_xmlFile);
		result.setSchemaValidationResult(validationResult);
		
		if (!validationResult.isValid()) {
			result.setStatus(ParseStatus.SCHEMA_VALIDATION_FAILURE);
			
			return result;
		}
		
		try {
			
			ISO11179Elements iso11179Elements = binder.bind(_xmlFile);
			TranslatorResult<CaDSRObjects> translatorResult = translator.translate(iso11179Elements);
			
			result.setTranslatorResult(translatorResult);
			result.setCaDSRObjects(translatorResult.getTranslatedObject());
			
			if (!translatorResult.isSuccessful() || translatorResult.hasErrors()) {
				result.setStatus(ParseStatus.TRANSLATION_FAILURE);
			}
			
		} catch (Exception e) {
			result.setException(e);
			result.setStatus(ParseStatus.TRANSLATION_FAILURE);
			
			return result;
		}
		
		result.setStatus(ParseStatus.SUCCESS);
		return result;
	}
}
