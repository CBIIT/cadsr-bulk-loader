package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriter;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriterImpl;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class CreateNonEnumVDTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/8_18_oneQualifer.xls";
	
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
	
	public void setUp() {
		try {
			//super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public CreateNonEnumVDTestCase() {
		super("CreateNonEnumVDTestCase", CreateNonEnumVDTestCase.class, dataURL);
	}
	
	public void testGF21708() {
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		/*props.put("db.url", "jdbc:oracle:thin:@cbiodb530.nci.nih.gov:1551:DSRQA");
		props.put("db.username", "chenr_qa");
		props.put("db.password", "chenr_qa");*/
		
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
