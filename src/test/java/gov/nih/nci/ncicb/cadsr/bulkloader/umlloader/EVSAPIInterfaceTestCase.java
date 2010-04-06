package gov.nih.nci.ncicb.cadsr.bulkloader.umlloader;

import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;

public class EVSAPIInterfaceTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private static String[] XML_IP_FILES = {"/gov/nih/nci/ncicb/cadsr/8_9_3_QualiferMultipleConcepts_RemoveLongName_RemoveC16007_C16443.xml"};
//	private static String dataURL = "/gov/nih/nci/ncicb/cadsr/8_27_1_2.xls";
	
	 private EvsModule evsModule = new EvsModule("NCI Thesaurus");
	 
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
			//FileUtil fileUtil = new FileUtil();
			//fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public EVSAPIInterfaceTestCase() {
		super("EVSAPIInterfaceTestCase");
	}
	
	public void testProcessor() {
		EvsResult evsResult = evsModule.findByConceptCode("C17998", false);
		assertNotNull(evsResult);
	}
}
