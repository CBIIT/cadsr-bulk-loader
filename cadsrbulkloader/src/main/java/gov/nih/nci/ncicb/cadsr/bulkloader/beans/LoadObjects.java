/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.Context;

public class LoadObjects {

	private Context loadContext;
	private ClassificationScheme loadClassScheme;
	private ConceptualDomain loadConceptualDomain;
	
	public Context getLoadContext() {
		return loadContext;
	}
	public void setLoadContext(Context loadContext) {
		this.loadContext = loadContext;
	}
	public ClassificationScheme getLoadClassScheme() {
		return loadClassScheme;
	}
	public void setLoadClassScheme(ClassificationScheme loadClassScheme) {
		this.loadClassScheme = loadClassScheme;
	}
	public ConceptualDomain getLoadConceptualDomain() {
		return loadConceptualDomain;
	}
	public void setLoadConceptualDomain(ConceptualDomain loadConceptualDomain) {
		this.loadConceptualDomain = loadConceptualDomain;
	}
}
