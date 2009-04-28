package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation;

public class TransformerValidationException extends Exception{

	private static final long serialVersionUID = 6839181061584154889L;

	public TransformerValidationException() {
		super();
	}
	
	public TransformerValidationException(Exception e) {
		super(e);
		super.setStackTrace(e.getStackTrace());
	}
	
	public TransformerValidationException(String message, Exception e) {
		super(message, e);
		super.setStackTrace(e.getStackTrace());
	}
	
	public TransformerValidationException(String message) {
		super(message);
	}
}
