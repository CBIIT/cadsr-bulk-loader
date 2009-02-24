package gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLContext;

public class ISO11179SchemaBinder implements ObjectBinder {

	public ISO11179Elements bind(File _xmlFile) {
		try {
			Mapping mapping = new Mapping();
			
			URL url = ISO11179SchemaBinder.class.getClassLoader().getResource("gov/nih/nci/ncicb/cadsr/bulkloader/parser/mapping.xml");
			
			mapping.loadMapping(url);
			
			XMLContext context = new XMLContext();
			context.addMapping(mapping);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setClass(ISO11179Elements.class);
			
			Reader reader = new FileReader(_xmlFile);
			ISO11179Elements elements = (ISO11179Elements)unmarshaller.unmarshal(reader);
			
			return elements;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
