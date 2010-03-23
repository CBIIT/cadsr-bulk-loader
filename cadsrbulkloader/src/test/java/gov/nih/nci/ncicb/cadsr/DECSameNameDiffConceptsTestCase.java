package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;
import gov.nih.nci.ncicb.cadsr.domain.Property;

import java.util.List;
import java.util.Properties;

public class DECSameNameDiffConceptsTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/Retest_Failures_BL_27jan2010_v2.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/Retest_Failures_BL_27jan2010_v2.xls";
	
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
			super.setUp();
			FileUtil fileUtil = new FileUtil();
			fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DECSameNameDiffConceptsTestCase() {
		super("DECSameNameDiffConceptsTestCase", SampleTestCase.class, dataURL);
	}
	
	public void testProcessor() {
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		BulkLoadProcessResult[] processResults = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		assertNotNull(processResults);
		LoadResult loadResult = processResults[0].getLoadResult();
		assertNotNull(loadResult);
		ValidationResult validationResult = loadResult.getValidationResult();
		assertNotNull(validationResult);
		assertTrue(validationResult.hasErrors());
		List<ValidationItemResult> itemResults = validationResult.getItemResults();
		assertNotNull(itemResults);
		assertTrue(itemResults.size() == 1);
		assertFalse(itemResults.get(0).isValid());
		assertTrue(itemResults.get(0).getItem() instanceof Property);
	}
}
