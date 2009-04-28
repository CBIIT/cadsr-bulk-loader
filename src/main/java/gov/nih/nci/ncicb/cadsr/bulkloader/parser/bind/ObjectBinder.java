package gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.io.File;

public interface ObjectBinder {

	public ISO11179Elements bind(File _xmlFile) throws Exception;
}
