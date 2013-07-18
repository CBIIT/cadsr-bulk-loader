/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.qa;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;


public class BL_820_TestCase extends AbstractQATestKase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/qa/8_20_3.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/qa/8_20_3.xls";
	
	public BL_820_TestCase(String name) {
		super(name, XML_IP_FILES, dataURL);
	}

	public void testVD() {
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		
		try {
			DataSource ds = super.getDataSource();
			Connection con = ds.getConnection();
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from value_domains");
			assertTrue(compareResultSet(rs, "VALUE_DOMAINS"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
