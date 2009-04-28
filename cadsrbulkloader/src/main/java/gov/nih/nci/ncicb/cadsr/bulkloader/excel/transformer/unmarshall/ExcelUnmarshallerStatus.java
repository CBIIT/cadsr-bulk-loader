package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.unmarshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallerStatus;

public class ExcelUnmarshallerStatus implements TransformerUnMarshallerStatus {

	public static final ExcelUnmarshallerStatus SUCCESS = new ExcelUnmarshallerStatus(0, "Successfully unmarshalled");
	public static final ExcelUnmarshallerStatus FAILURE = new ExcelUnmarshallerStatus(1, "Unmarshalling failed");
	
	private final int code;
	private final String message;
	
	private ExcelUnmarshallerStatus(int _code, String _message){
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
