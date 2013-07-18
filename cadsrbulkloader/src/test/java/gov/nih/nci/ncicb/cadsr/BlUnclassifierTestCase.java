/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.UnloadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.BulkLoaderUnclassifier;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class BlUnclassifierTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/unclassify.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/unclassify.xls";
	
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

	public BlUnclassifierTestCase() {
		super("BlUnclassifierTestCase", BlUnclassifierTestCase.class, dataURL);
	}
	
	public void testUnclassifier() {
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		SpringBeansUtil.getInstance().initialize(props);
		
		CaDSRBulkLoadProcessor blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
		
		BulkLoadProcessResult[] processResults = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		
		BulkLoaderUnclassifier unclassifier = SpringBeansUtil.getInstance().getBulkLoaderUnclassifier();
		UnloadProperties unloadProps = new UnloadProperties();
		unloadProps.setClassificationSchemeName("TEST_Bulk_Loader_0.5");
		unloadProps.setClassificationSchemeItemName("TEST_8_12");
		unloadProps.setCsVersion(1.0);
		unloadProps.setCsiVersion(1.0);
		
		unclassifier.unclassify(unloadProps);
		
		try {
			Connection con = super.getDataSource().getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from DESIGNATIONS");
			
			assertFalse(rs.next());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
