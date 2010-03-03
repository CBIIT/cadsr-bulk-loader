package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

import java.util.List;


public class ValidationResult {

	private CaDSRObjects validationItem;
	private List<ValidationItemResult> itemResults;
	private ValidationStatus validationStatus;
	private Exception exception;
	private List<AdminComponent> decommissionedItems;
	
	
	public CaDSRObjects getValidationItem() {
		return validationItem;
	}
	public void setValidationItem(CaDSRObjects validationItem) {
		this.validationItem = validationItem;
	}
	public List<ValidationItemResult> getItemResults() {
		return itemResults;
	}
	public void setItemResults(List<ValidationItemResult> itemResults) {
		this.itemResults = itemResults;
	}
	public ValidationStatus getValidationStatus() {
		return validationStatus;
	}
	public void setValidationStatus(ValidationStatus validationStatus) {
		this.validationStatus = validationStatus;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	public boolean isSuccessful() {
		return (validationStatus.isSuccessful() && exception == null);
	}
	public List<AdminComponent> getDecommissionedItems() {
		return decommissionedItems;
	}
	public void setDecommissionedItems(List<AdminComponent> decommissionedItems) {
		this.decommissionedItems = decommissionedItems;
	}
	public boolean hasErrors() {
		for (ValidationItemResult itemResult: itemResults) {
			if (!itemResult.isValid()) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasWarnings() {
		for (ValidationItemResult itemResult: itemResults) {
			if (itemResult.isValid() && itemResult.hasWarnings()) {
				return true;
			}
		}
		
		return false;
	}
}
