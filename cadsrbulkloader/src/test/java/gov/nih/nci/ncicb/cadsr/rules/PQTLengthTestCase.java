package gov.nih.nci.ncicb.cadsr.rules;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.PersisterResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.util.Properties;

public class PQTLengthTestCase extends MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/rules/PQTLengthTest.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/rules/PQTLengthTest.xls";
	
	public void setUp() {
		try {
			super.setUp();
			FileUtil fileUtil = new FileUtil();
			fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public PQTLengthTestCase(String name) {
		super(name, PQTLengthTestCase.class, dataURL);
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
		assertTrue(results[0].isSuccessful());
		LoadResult loadResult = results[0].getLoadResult();
		assertNotNull(loadResult);
		PersisterResult persisterResult = loadResult.getPersisterResult();
		assertNotNull(persisterResult);
		assertNull(persisterResult.getException());
	}

}
