package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.io.File;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandLineProcessor {

	private static Log log = LogFactory.getLog(CommandLineProcessor.class);
	
	public static void main(String[] args) {
		UserInterface userInterface = new CommandLineUserInterfaceImpl();
		UserInput userInput = userInterface.getUserInput();
		
		Properties props = new Properties();
		props.setProperty("db.url", userInput.getDbURL());
		props.setProperty("db.username", userInput.getDbUser());
		props.setProperty("db.password", userInput.getDbPassword());
		
		SpringBeansUtil.getInstance().initialize(props);
		
		String inputDir = userInput.getInputDir();
		String outputDir = inputDir+File.separatorChar+"out";
		CaDSRBulkLoadProcessor processor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		BulkLoadProcessResult[] results = processor.process(inputDir, outputDir, true);
		UIReportWriter reportWriter = new UIReportWriterImpl();
		for (BulkLoadProcessResult result: results) {
			reportWriter.writeReport(result);
		}
		
	}

}
