package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall;


public class TransformerMarshallerResult {

	private TransformerMarshallerStatus marshallerStatus;
	private Exception marshallException;

	public TransformerMarshallerStatus getMarshallerStatus() {
		return marshallerStatus;
	}

	public void setUnmarshallerStatus(
			TransformerMarshallerStatus unmarshallerStatus) {
		this.marshallerStatus = unmarshallerStatus;
	}

	public Exception getMarshallException() {
		return marshallException;
	}

	public void setMarshallException(Exception marshallException) {
		this.marshallException = marshallException;
	}
	
	public boolean hasErrors() {
		if (marshallException != null) {
			return true;
		}
		
		return false;
	}
	
	
}
