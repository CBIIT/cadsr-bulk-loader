package gov.nih.nci.ncicb.cadsr.qa;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class BL_85_TestCase extends AbstractQATestKase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/qa/85_1.xml"};
	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/qa/85.xls";
	
	public BL_85_TestCase(String name) {
		super(name, XML_IP_FILES, dataURL);
	}

	public void test85_1() {
		BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
		assertNotNull(results);
		assertTrue(results.length == 1);
		for (BulkLoadProcessResult result: results) {
			TransformerResult transformResult = result.getTransformResult();
			assertNotNull(transformResult);
			String fileName = transformResult.getInputParams().getInputFile().getName();
			
			assertTrue(fileName.equals("85_1.xml"));
			
			try {
				Connection con = getDataSource().getConnection();
				Statement st = con.createStatement();
				
				ResultSet rs = st.executeQuery("select * from DESIGNATIONS");
				boolean cmp = compareResultSet(rs, "DESIGNATIONS-1");
				assertTrue(cmp);
				
				rs = st.executeQuery("select * from REFERENCE_DOCUMENTS");
				cmp = compareResultSet(rs, "REFERENCE_DOCUMENTS-1");
				assertTrue(cmp);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		}
	}
	
	public void test85_2() {
		try {
			XML_IP_FILES = new String[]{"/gov/nih/nci/ncicb/cadsr/qa/85_2.xml"};
			BL_85_TestCase tc = new BL_85_TestCase("");
			tc.setUp();
			
			BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
			
			assertNotNull(results);
			assertTrue(results.length == 1);
			
			BulkLoadProcessResult result = results[0];
			TransformerResult transformResult = result.getTransformResult();
			assertNotNull(transformResult);
			String fileName = transformResult.getInputParams().getInputFile().getName();
			
			assertTrue(fileName.equals("85_2.xml"));
			
			try {
				Connection con = getDataSource().getConnection();
				Statement st = con.createStatement();
				
				ResultSet rs = st.executeQuery("select * from DESIGNATIONS");
				boolean cmp = compareResultSet(rs, "DESIGNATIONS-2");
				assertTrue(cmp);
				
				rs = st.executeQuery("select * from REFERENCE_DOCUMENTS");
				cmp = compareResultSet(rs, "REFERENCE_DOCUMENTS-2");
				assertTrue(cmp);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void test85_3() {
		try {
			XML_IP_FILES = new String[]{"/gov/nih/nci/ncicb/cadsr/qa/85_3.xml"};
			BL_85_TestCase tc = new BL_85_TestCase("");
			tc.setUp();
			
			BulkLoadProcessResult[] results = blProcessor.process(WORKING_IN_DIR, WORKING_OUT_DIR, true);
			
			assertNotNull(results);
			assertTrue(results.length == 1);
			
			BulkLoadProcessResult result = results[0];
			TransformerResult transformResult = result.getTransformResult();
			assertNotNull(transformResult);
			String fileName = transformResult.getInputParams().getInputFile().getName();
			
			assertTrue(fileName.equals("85_3.xml"));
			
			try {
				Connection con = getDataSource().getConnection();
				Statement st = con.createStatement();
				
				ResultSet rs = st.executeQuery("select * from DESIGNATIONS");
				boolean cmp = compareResultSet(rs, "DESIGNATIONS-3");
				assertTrue(cmp);
				
				rs = st.executeQuery("select * from REFERENCE_DOCUMENTS");
				cmp = compareResultSet(rs, "REFERENCE_DOCUMENTS-1");
				assertTrue(cmp);
				
				rs = st.executeQuery("select * from AC_CSI");
				cmp = compareResultSet(rs, "AC_CSI");
				assertTrue(cmp);
			} catch (Exception e) {
				fail(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
