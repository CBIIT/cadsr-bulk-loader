package gov.nih.nci.ncicb.cadsr.load;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Representation;

import java.util.List;
import java.util.Properties;

public class GF30656TestCase extends MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/load/Cord_Blood_Unit_SCTOD_Data_Requirements_rv2.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/load/Cord_Blood_Unit_SCTOD_Data_Requirements_rv2.xls";
	
	public GF30656TestCase(String name, String[] xmlIPFiles, String dataURL) {
		super(name, GF30656TestCase.class, dataURL);
	}
	
	public GF30656TestCase(String name) {
		super(name, GF30656TestCase.class, dataURL);
	}
	
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
			e.printStackTrace();
		}
	}
	
	public void testEmptyRepTerm() {
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		assertNotNull(results);
		assertTrue(results.length == 1);
		assertTrue(results[0].isSuccessful());
		LoadResult loadResult = results[0].getLoadResult();
		assertNotNull(loadResult);
		ParseResult parseResult = loadResult.getParseResult();
		assertNotNull(parseResult);
		CaDSRObjects objs = parseResult.getCaDSRObjects();
		assertNotNull(objs);
		List<Representation> reps = objs.getRepTerms();
		assertNotNull(reps);
		assertTrue(reps.size() == 1);
	}

}
