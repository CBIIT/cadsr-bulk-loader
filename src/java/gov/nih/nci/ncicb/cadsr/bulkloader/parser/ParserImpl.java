package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind.ObjectBinder;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.Translator;

import java.io.File;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 25, 2008
 * @since 
 */

public class ParserImpl implements Parser {

	private ObjectBinder binder;
	private Translator<CaDSRObjects> translator;
	
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

	public CaDSRObjects parse(File _xmlFile) {
		try {
			ISO11179Elements iso11179Elements = binder.bind(_xmlFile);
			CaDSRObjects caDSRObjects = translator.translate(iso11179Elements);
			
			return caDSRObjects;
		} catch (Exception e) {
			throw new ParserRuntimeException(e);
		}
	}
}
