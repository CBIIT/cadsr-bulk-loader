/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation;

import java.util.List;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerLineItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

public class TransformerTransformationLineItemResult extends
		TransformerLineItemResult {

	public TransformerTransformationLineItemResult(int _recordNumber, List<Item> _items) {
		super(_recordNumber, _items);
	}
}
