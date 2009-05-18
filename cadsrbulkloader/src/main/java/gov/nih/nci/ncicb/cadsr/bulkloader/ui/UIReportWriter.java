package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;

import java.io.OutputStream;

public interface UIReportWriter {

	public void writeReport(BulkLoadProcessResult loadResult);
}
