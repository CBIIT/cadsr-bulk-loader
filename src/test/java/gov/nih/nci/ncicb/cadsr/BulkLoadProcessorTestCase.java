package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriter;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriterImpl;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

public class BulkLoadProcessorTestCase extends MainTestCase {

	public void testProcessor() {
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		String inputFileDir = getClasspath()+"gov/nih/nci/ncicb/cadsr";
		String outputFileDir = getClasspath()+"gov/nih/nci/ncicb/cadsr/out";
		
		BulkLoadProcessResult[] processResults = blProcessor.process(inputFileDir, outputFileDir, true);
		UIReportWriter reportWriter = new UIReportWriterImpl();
		
		for (BulkLoadProcessResult processResult: processResults) {
			reportWriter.writeReport(processResult);
		}
		
	}
}
