/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

public class UnitTestsUtil {

	public static Context getDefaultContext() {
		Context ctx = DomainObjectFactory.newContext();
		ctx.setName("NHLBI");
		
		return ctx;
	}
	
	public static ClassificationScheme getDefaultClassificationScheme() {
		ClassificationScheme classScheme = DomainObjectFactory.newClassificationScheme();
		classScheme.setPreferredName("NMDP: CDEs to be Reviewed");
		classScheme.setLongName("NMDP: CDEs to review");
		classScheme.setPreferredDefinition("NMDP CDEs to be reviewed");
		
		return classScheme;
	}
	
	public static LoadProperties getDefaultLoadProperties() {
		
		LoadProperties loadProperties = new LoadProperties();
		loadProperties.setContextName("NHLBI");
		loadProperties.setClassificationSchemeName("NMDP: CDEs to be Reviewed");
		loadProperties.setClassificationSchemeItemName("2100: 100 Days Post-HSCT Data");
		
		return loadProperties;
	}
	
	public static LoadObjects getDefaultLoadObjects() {
		LoadObjects loadObjects = new LoadObjects();
		
		loadObjects.setLoadContext(getDefaultContext());
		loadObjects.setLoadClassScheme(getDefaultClassificationScheme());
		
		return loadObjects;
	}
}
