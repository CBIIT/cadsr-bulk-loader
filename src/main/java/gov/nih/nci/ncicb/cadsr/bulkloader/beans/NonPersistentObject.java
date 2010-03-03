package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;

public class NonPersistentObject {

	private final AdminComponent actualObject;
	
	public NonPersistentObject(AdminComponent actualObject) {
		this.actualObject = actualObject;
	}

	public AdminComponent getActualObject() {
		return actualObject;
	}
	
}
