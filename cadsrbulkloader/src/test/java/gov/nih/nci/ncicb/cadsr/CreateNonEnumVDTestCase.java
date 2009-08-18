package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class CreateNonEnumVDTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/8_18_oneQualifer.xml"};
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
			super.setUp();
			FileUtil fileUtil = new FileUtil();
			fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
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
		
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		boolean compare = false;
		
		try {
			Connection con = super.getDataSource().getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from VALUE_DOMAINS");
			
			compare = compareResultSet(rs, "VALUE_DOMAINS");
			
			assertTrue(compare);
			
			rs = st.executeQuery("select * from DATA_ELEMENTS");
			
			compare = compareResultSet(rs, "DATA_ELEMENTS");
			
			assertTrue(compare);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			compare = false;
		}
		
		assertTrue(compare);
		
	}
}
