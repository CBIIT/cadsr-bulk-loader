package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.util;

public class ExcelTransformerUtil {

	private static final String COMPOSITE_STRING_CONCAT = ":";
	
	public static String getPublicIdFromCompositeString(String compositeString) {
		if (compositeString == null) return null;
		else {
			return compositeString.split(COMPOSITE_STRING_CONCAT)[0];
		}
	}
}
