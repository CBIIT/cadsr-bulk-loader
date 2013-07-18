/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall;

import java.io.File;
import java.io.InputStream;

public interface TransformerUnMarshaller {

	public TransformerUnMarshallResult read(File inputFile);
	public TransformerUnMarshallResult read(InputStream is);
}
