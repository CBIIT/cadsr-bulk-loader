/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

import java.io.File;

public class LoaderInput {

	private File fileToLoad;
	private boolean validate;
	
	public File getFileToLoad() {
		return fileToLoad;
	}
	public void setFileToLoad(File fileToLoad) {
		this.fileToLoad = fileToLoad;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
}
