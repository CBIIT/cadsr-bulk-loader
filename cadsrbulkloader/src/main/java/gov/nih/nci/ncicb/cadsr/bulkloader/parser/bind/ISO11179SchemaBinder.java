package gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLContext;

public class ISO11179SchemaBinder implements ObjectBinder {

	public ISO11179Elements bind(File _xmlFile) throws Exception {
		Mapping mapping = new Mapping();
		
		URL url = ISO11179SchemaBinder.class.getClassLoader().getResource("gov/nih/nci/ncicb/cadsr/bulkloader/parser/mapping.xml");
		
		mapping.loadMapping(url);
		
		XMLContext context = new XMLContext();
		context.addMapping(mapping);
		
		Unmarshaller unmarshaller = new Unmarshaller(mapping); //context.createUnmarshaller();
		unmarshaller.setClass(ISO11179Elements.class);
		unmarshaller.setClearCollections(true);
		
		Reader reader = new FileReader(_xmlFile);
		ISO11179Elements elements = (ISO11179Elements)unmarshaller.unmarshal(reader);
		
		return elements;
	}

}
