/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.rules;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

public class ValueDomainNoErrorTestCase extends MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/rules/vd_handling.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/rules/vd_handling.xls";
	
	public void setUp() {
		try {
			super.setUp();
			FileUtil fileUtil = new FileUtil();
			fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public ValueDomainNoErrorTestCase(String name) {
		super(name, ValueDomainNoErrorTestCase.class, dataURL);
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
			assertTrue(loadResult.getMessage(), loadResult.isSuccessful());
		}
		
		try {
			DataSource ds = super.getDataSource();
			Connection con = ds.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from value_domains");
			assertTrue(compareResultSet(rs, "VALUE_DOMAINS"));
			
			rs = st.executeQuery("select * from data_elements");
			assertTrue(compareResultSet(rs, "DATA_ELEMENTS"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
