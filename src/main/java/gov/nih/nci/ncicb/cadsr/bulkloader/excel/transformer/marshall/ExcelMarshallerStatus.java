package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.marshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshallerStatus;

public class ExcelMarshallerStatus implements TransformerMarshallerStatus {

	public static final ExcelMarshallerStatus SUCCESS = new ExcelMarshallerStatus(0, "Successfully marshalled");
	public static final ExcelMarshallerStatus FAILURE = new ExcelMarshallerStatus(1, "Marshalling failed");
	
	private final int code;
	private final String message;
	
	private ExcelMarshallerStatus(int _code, String _message){
		code = _code;
		message = _message;
	}
	
	public String getMessage() {
		return message;
	}

	public boolean validationPassed() {
		switch (code % 2) { // all even codes for success, all odds for failure
			case 0: return true;
			default: return false;
		}
	}

}
