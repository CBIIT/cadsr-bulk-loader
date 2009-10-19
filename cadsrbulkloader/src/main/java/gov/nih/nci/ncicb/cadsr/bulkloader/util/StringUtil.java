package gov.nih.nci.ncicb.cadsr.bulkloader.util;

public class StringUtil {

	private static String specialCharExpn = "[\\x00-\\x08\\x0b-\\x0c\\x0e-\\x1f]";
	
	public static String replaceSpecialCharacters(String str) {
		return str.replaceAll(specialCharExpn, str);
	}
}
