package gov.nih.nci.ncicb.cadsr.bulkloader;

public enum LoadStatus {

	SUCCESSFUL("The load was completed successfully"),
	SUCCESSFUL_WITH_ERRORS("The load was completed successfully with errors"),
	SUCCESSFUL_WITH_WARNINGS("The load was completed successfully with warnings"),
	
	FAILED_WITH_PARSING_ERROR("There was an error while parsing the file"),
	FAILED_WITH_VALIDATION_ERROR("Data validation failed"),
	FAILED_WITH_PERSISTANCE_ERROR("There was an error while persisting it"),
	FAILED_WITH_UNKNOWN_ERROR("There was an unknown error while processing the file");
	
	private String statusMessage;
	
	private LoadStatus(String msg) {
		this.statusMessage = msg;
	}
	
	public String getStatusMessage() {
		return statusMessage;
	}
}
