package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

import java.util.ArrayList;
import java.util.List;

public class TransformerLineItemResult {

	private final List<Item> items;
	private final int recordNumber;
	
	private List<TransformerStatus> statuses;
	private Exception transformerException;
	private String message;
	
	public TransformerLineItemResult(int _recordNumber, List<Item> _items) {
		recordNumber = _recordNumber;
		items = _items;
	}

	public List<TransformerStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<TransformerStatus> statuses) {
		this.statuses = statuses;
	}
	
	public void addStatus(TransformerStatus status) {
		if (statuses == null) {
			statuses = new ArrayList<TransformerStatus>();
		}
		
		statuses.add(status);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Item> getItems() {
		return items;
	}

	public int getRecordNumber() {
		return recordNumber;
	}

	public Exception getTransformerException() {
		return transformerException;
	}

	public void setTransformerException(Exception transformerException) {
		this.transformerException = transformerException;
	}
	
	public boolean hasErrors() {
		if (statuses == null) {
			return false;
		}
		else {
			for (TransformerStatus status: statuses) {
				if (!status.validationPassed()) {
					return true;
				}
			}
			
			return false;
		}
	}
	
	
}
