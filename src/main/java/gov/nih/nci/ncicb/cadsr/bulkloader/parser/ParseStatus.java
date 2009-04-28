package gov.nih.nci.ncicb.cadsr.bulkloader.parser;


public class ParseStatus {

	public static final ParseStatus SUCCESS = new ParseStatus(2, "Successfully Parsed");
	
	public static final ParseStatus FAILURE = new ParseStatus(3, "Parsing failed");
	public static final ParseStatus SCHEMA_VALIDATION_FAILURE = new ParseStatus(5, "The input file could not be validated against the schema");
	public static final ParseStatus TRANSLATION_FAILURE = new ParseStatus(7, "Parsing failed in translation");
	
	private final int code;
	private final String message;
	
	private ParseStatus(int _code, String _message) {
		code = _code;
		message = _message;
	}
	
	public String getMessage() {
		return message;
	}

	public boolean isSuccessful() {
		if (code == 0) return true;
		
		switch (code % 2) {
		case 0: return true;
		default: return false;
		}
	}
	
	public String toString() {
		return message;
	}
}
