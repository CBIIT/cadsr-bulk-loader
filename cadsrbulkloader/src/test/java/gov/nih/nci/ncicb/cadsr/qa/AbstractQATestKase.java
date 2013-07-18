/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.qa;

import java.util.Properties;

import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.FileUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;

public abstract class AbstractQATestKase extends MainTestCase {

	String[] XML_IP_FILES = null;
	protected CaDSRBulkLoadProcessor blProcessor;
	
	public AbstractQATestKase(String name, String[] xmlIPFiles, String dataURL) {
		super(name, AbstractQATestKase.class, dataURL);
		XML_IP_FILES = xmlIPFiles;
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

	protected void setUp() throws Exception {
		try {
			super.setUp();
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void init() {
		setupFiles();
		Properties props = new Properties();
		props.put("db.url", getPropertyManager().getUnitDataSourceURL());
		props.put("db.username", getPropertyManager().getUnitDataSourceUser());
		props.put("db.password", getPropertyManager().getUnitDataSourcePassword());
		
		SpringBeansUtil.getInstance().initialize(props);
		
		blProcessor = SpringBeansUtil.getInstance().getBulkLoadProcessor();
	}
	
	private void setupFiles() {
		FileUtil fileUtil = new FileUtil();
		fileUtil.copyFilesToWorkingDir(WORKING_IN_DIR, WORKING_OUT_DIR, XML_IP_FILES);
	}

}
