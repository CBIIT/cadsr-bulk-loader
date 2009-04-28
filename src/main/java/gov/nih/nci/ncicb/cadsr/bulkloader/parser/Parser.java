package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import java.io.File;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 25, 2008
 * @since 
 */

public interface Parser {

	public ParseResult parse(File _xmlFile);
}
