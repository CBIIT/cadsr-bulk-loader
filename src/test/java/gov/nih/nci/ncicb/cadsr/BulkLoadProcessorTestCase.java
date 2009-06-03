package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriter;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriterImpl;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.ConfigurableApplicationContext;

public class BulkLoadProcessorTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	@Override
	protected void containerSetUp() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean requiresDatabase() {
		return true;
	}

	@Override
	protected boolean runInRealContainer() {
		return false;
	}

	@Override
	protected ConfigurableApplicationContext loadContext(Object arg0)
			throws Exception {
		return null;
	}
	
	public void setUp() {
		try {
		//	super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public BulkLoadProcessorTestCase() {
		super("BulkLoadProcessorTestCase", BulkLoadProcessorTestCase.class, "/gov/nih/nci/ncicb/cadsr/test.xls");
	}
	
	public void testProcessor() {
		Properties props = new Properties();
		props.put("db.url", "jdbc:oracle:thin:@cbiodb530.nci.nih.gov:1521:DSRQA");
		props.put("db.username", "chenr_qa");
		props.put("db.password", "chenr_qa");
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		String inputFileDir = getClasspath()+"gov/nih/nci/ncicb/cadsr";
		String outputFileDir = getClasspath()+"gov/nih/nci/ncicb/cadsr/out";
		
		BulkLoadProcessResult[] processResults = blProcessor.process(inputFileDir, outputFileDir, true);
		UIReportWriter reportWriter = new UIReportWriterImpl();
		
		for (BulkLoadProcessResult processResult: processResults) {
			reportWriter.writeReport(processResult);
		}
		
	}
	
	protected String getClasspath() {
		ClassLoader classLoader = MainTestCase.class.getClassLoader();
		String filePath = classLoader.getResource(".").getPath();
		
		return filePath;
	}
	
	protected File getClasspathFile(String fileName) {
		String classpath = getClasspath();
		File f  = new File(classpath+fileName);
		
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
}