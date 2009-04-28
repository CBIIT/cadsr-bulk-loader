package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall;


public class TransformerMarshallerResult {

	private TransformerMarshallerStatus unmarshallerStatus;
	private Exception unmarshallException;

	public TransformerMarshallerStatus getUnmarshallerStatus() {
		return unmarshallerStatus;
	}

	public void setUnmarshallerStatus(
			TransformerMarshallerStatus unmarshallerStatus) {
		this.unmarshallerStatus = unmarshallerStatus;
	}

	public Exception getUnmarshallException() {
		return unmarshallException;
	}

	public void setUnmarshallException(Exception unmarshallException) {
		this.unmarshallException = unmarshallException;
	}
	
	public boolean hasErrors() {
		if (unmarshallException != null) {
			return true;
		}
		
		return false;
	}
	
	
}
