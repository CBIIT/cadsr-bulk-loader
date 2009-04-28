package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

public class TransformerException extends RuntimeException {

	private static final long serialVersionUID = -3728481018979385744L;

	public TransformerException() {
		super();
	}
	
	public TransformerException(Exception e) {
		super(e);
		super.setStackTrace(e.getStackTrace());
	}
	
	public TransformerException(String message, Exception e) {
		super(message, e);
		super.setStackTrace(e.getStackTrace());
	}
	
	public TransformerException(String message) {
		super(message);
	}
}
