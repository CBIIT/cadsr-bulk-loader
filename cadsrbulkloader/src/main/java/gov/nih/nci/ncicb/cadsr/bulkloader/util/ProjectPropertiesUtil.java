/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class ProjectPropertiesUtil {

	public static final String VALIDATION_STOP_ON_ERROR = "validation.stop.on.error";
	public static final String VALIDATION_STOP_ON_FATALITY = "validation.stop.on.fatality";
	public static final String VALIDATION_STOP_ON_WARNING = "validation.stop.on.warning";
	
	public static final String DEFAULT_ALT_NAME_PROP = "default.altname.type";
	
	private static final String PROPERTIES_FILE_STR = "/bulkloader.properties";
	private static ClassLoader classLoader = ProjectPropertiesUtil.class.getClassLoader();
	private static Properties props = new Properties();
	
	static {
		loadProperties();
	}
	
	public static File getClasspathFile(String filePathStr) {
		URL propsFileURL = classLoader.getResource(PROPERTIES_FILE_STR);
		String filePath = propsFileURL.getPath();
		
		File f = new File(filePath);
		
		return f;
	}
	
	private static void loadProperties() {
		try {
			InputStream fis = ProjectPropertiesUtil.class.getResourceAsStream(PROPERTIES_FILE_STR);
			props.load(fis);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getValidationStopOnError() {
		return props.getProperty(VALIDATION_STOP_ON_ERROR);
	}
	
	public String getValidationStopOnFatality() {
		return props.getProperty(VALIDATION_STOP_ON_FATALITY);
	}
	
	public String getValidationStopOnWarning() {
		return props.getProperty(VALIDATION_STOP_ON_WARNING);
	}
	
	public static String getDefaultAlternateNameType() {
		return props.getProperty(DEFAULT_ALT_NAME_PROP);
	}
}
