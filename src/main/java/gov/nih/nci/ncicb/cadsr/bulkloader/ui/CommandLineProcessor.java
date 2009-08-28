package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.UnloadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.BulkLoaderUnclassifier;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.io.File;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CommandLineProcessor {

	private static Log log = LogFactory.getLog(CommandLineProcessor.class);
	
	private static UserInterface userInterface = new CommandLineUserInterfaceImpl();
	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			runLoad();	
		}
		else if (args[0].equalsIgnoreCase("unload")) {
			runUnLoad();
		}
	}
	
	private static void runLoad() {
		UserInput userInput = userInterface.getLoadUserInput();
		
		initializeSpringCtx(userInput);
		
		String inputDir = userInput.getInputDir();
		String outputDir = inputDir+File.separatorChar+"out";
		CaDSRBulkLoadProcessor processor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		BulkLoadProcessResult[] results = processor.process(inputDir, outputDir, true);
		UIReportWriter reportWriter = new UIReportWriterImpl();
		for (BulkLoadProcessResult result: results) {
			reportWriter.writeReport(result);
		}
	}
	
	private static void runUnLoad() {
		UserInput userInput = userInterface.getUnloadUserInput();
		
		initializeSpringCtx(userInput);
		
		UnloadProperties unloadProps = getUnloadProperties(userInput);
		BulkLoaderUnclassifier unclassifier = SpringBeansUtil.getInstance().getBulkLoaderUnclassifier();
		unclassifier.unclassify(unloadProps);
	}
	
	private static void initializeSpringCtx(UserInput userInput) {
		Properties props = new Properties();
		props.setProperty("db.url", userInput.getDbURL());
		props.setProperty("db.username", userInput.getDbUser());
		props.setProperty("db.password", userInput.getDbPassword());
		
		SpringBeansUtil.getInstance().initialize(props);
	}
	
	private static UnloadProperties getUnloadProperties (UserInput userInput) {
		UnloadProperties unloadProps = new UnloadProperties();
		
		unloadProps.setClassificationSchemeName(userInput.getClassificationSchemeName());
		unloadProps.setClassificationSchemeItemName(userInput.getClassificationSchemeItemName());
		double csVersion = 0.0;
		double csiVersion = 0.0;
		
		try {
			csVersion = Double.parseDouble(userInput.getClassificationSchemeVersion());
		} catch (Exception e) {
			log.warn("The CS Version supplied ["+userInput.getClassificationSchemeVersion()+"] is not in the correct format. Assuming default.");
		}
		
		try {
			csiVersion = Double.parseDouble(userInput.getClassificationSchemeItemVersion());
		} catch (Exception e) {
			log.warn("The CSI Version supplied ["+userInput.getClassificationSchemeItemVersion()+"] is not in the correct format. Assuming default.");
		}
		
		unloadProps.setCsVersion(csVersion);
		unloadProps.setCsiVersion(csiVersion);
		
		return unloadProps;
		
	}

}
