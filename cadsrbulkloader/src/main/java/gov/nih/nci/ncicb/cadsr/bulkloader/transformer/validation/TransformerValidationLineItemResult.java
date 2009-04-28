package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerLineItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

import java.util.List;

public class TransformerValidationLineItemResult extends TransformerLineItemResult{

	public TransformerValidationLineItemResult(int _recordNumber, List<Item> _items) {
		super(_recordNumber, _items);
	}
	
}
