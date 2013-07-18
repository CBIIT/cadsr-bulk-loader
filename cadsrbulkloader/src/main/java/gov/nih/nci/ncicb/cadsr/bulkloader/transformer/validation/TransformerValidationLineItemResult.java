/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerLineItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

import java.util.List;

public class TransformerValidationLineItemResult extends TransformerLineItemResult{

	public TransformerValidationLineItemResult(int _recordNumber, List<Item> _items) {
		super(_recordNumber, _items);
	}
	
}
