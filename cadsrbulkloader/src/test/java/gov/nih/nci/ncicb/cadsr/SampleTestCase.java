/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriter;
import gov.nih.nci.ncicb.cadsr.bulkloader.ui.UIReportWriterImpl;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.util.Properties;

public class SampleTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/8_15_2.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/8_14_2.xls";
	
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
			FileUtil fileUtil = new FileUtil();
			fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SampleTestCase() {
		super("SampleTestCase", SampleTestCase.class, dataURL);
	}
	
	public void testProcessor() {
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		/*props.put("db.url", "jdbc:oracle:thin:@cbdb-s1001.nci.nih.gov:1551:DSRSTG");
		props.put("db.username", "blkldr");
		props.put("db.password", "29K#kd1qA");*/
		
		/*props.put("db.url", "jdbc:oracle:thin:@cbiodb530.nci.nih.gov:1521:DSRQA");
		props.put("db.username", "chenr_qa");
		props.put("db.password", "chenr_qa");*/
		
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		BulkLoadProcessResult[] processResults = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		UIReportWriter writer = new UIReportWriterImpl();
		for(BulkLoadProcessResult result: processResults) {
			writer.writeReport(result);
		}
		
/*		try {
			DataSource ds = super.getDataSource();
			Connection con = ds.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from reference_documents");
			assertTrue(compareResultSet(rs, "REFERENCE_DOCUMENTS"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
}
