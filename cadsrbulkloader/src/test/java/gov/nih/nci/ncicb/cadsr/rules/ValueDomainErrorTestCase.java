package gov.nih.nci.ncicb.cadsr.rules;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;

import java.util.List;
import java.util.Properties;

public class ValueDomainErrorTestCase extends MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/rules/vd_handling_errors.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/rules/vd_dec_handling.xls";
	
	public void setUp() {
		try {
			super.setUp();
			FileUtil fileUtil = new FileUtil();
			fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ValueDomainErrorTestCase(String name) {
		super(name, ValueDomainErrorTestCase.class, dataURL);
	}
	
	@Override
	protected void containerSetUp() throws Exception {
		
	}

	@Override
	protected boolean requiresDatabase() {
		return true;
	}

	@Override
	protected boolean runInRealContainer() {
		return false;
	}
	
	public void testReuseVD() {
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		assertNotNull(results);
		
		for (BulkLoadProcessResult result: results) {
			LoadResult loadResult = result.getLoadResult();
			assertNotNull(loadResult);
			assertFalse(loadResult.getMessage(), loadResult.isSuccessful());
			ValidationResult validationResult = loadResult.getValidationResult();
			assertNotNull(validationResult);
			List<ValidationItemResult> itemResults = validationResult.getItemResults();
			assertNotNull(itemResults);
			assertTrue(itemResults.size() == 4);
		}
	}

}
