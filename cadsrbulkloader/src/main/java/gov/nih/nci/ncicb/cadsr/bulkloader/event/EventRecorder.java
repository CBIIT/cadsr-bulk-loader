/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.event;

import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;


public interface EventRecorder {

	public void preProcessEvent(LoaderEvent _module, Object... _object);
	public void postProcessEvent(LoaderEvent _module, EventResult _result);
	public LoadResult getResult();
}
