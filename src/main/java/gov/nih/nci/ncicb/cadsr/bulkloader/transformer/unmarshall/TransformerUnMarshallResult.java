package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

public class TransformerUnMarshallResult {

	private Item unMarshalledObject;
	private TransformerUnMarshallerStatus status;
	private Exception unMarshallException;
	
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
	public void setUnMarshallException(Exception unMarshallException) {
		this.unMarshallException = unMarshallException;
	}
	public Exception getUnMarshallException() {
		return unMarshallException;
	}
	
	public boolean hasErrors() {
		if (unMarshallException == null) {
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
