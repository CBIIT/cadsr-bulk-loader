package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

public class TransformerUnMarshallResult {

	private Item unMarshalledObject;
	private TransformerUnMarshallerStatus status;
	private Exception marshallException;
	
	public Item getUnMarshalledObject() {
		return unMarshalledObject;
	}
	public void setUnMarshalledObject(Item unMarshalledObject) {
		this.unMarshalledObject = unMarshalledObject;
	}
	public TransformerUnMarshallerStatus getStatus() {
		return status;
	}
	public void setStatus(TransformerUnMarshallerStatus status) {
		this.status = status;
	}
	public void setMarshallException(Exception marshallException) {
		this.marshallException = marshallException;
	}
	public Exception getMarshallException() {
		return marshallException;
	}
	
	public boolean hasErrors() {
		if (marshallException == null) {
			if (status != null) {
				if (status.validationPassed()) {
					return false;
				}
				else {
					return true;
				}
			}
			else {
				return false;
			}
		}
		else {
			return true;
		}
	}
}
