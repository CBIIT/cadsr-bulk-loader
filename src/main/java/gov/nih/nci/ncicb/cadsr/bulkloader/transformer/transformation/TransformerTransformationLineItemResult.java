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
