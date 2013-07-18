/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.marshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshaller;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshallerResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Marshaller;

public class ExcelMarshaller implements TransformerMarshaller {

	private static final String ISO11179_MAPPING_FILE = "gov/nih/nci/ncicb/cadsr/bulkloader/parser/mapping.xml";
	
	public TransformerMarshallerResult marshall(ISO11179Elements isoElements,
			File outputFile) {
		
		TransformerMarshallerResult result = new TransformerMarshallerResult();
		
		try {
			Writer writer = new FileWriter(outputFile);
			
			Marshaller marshaller = get11179SchemaMarshaller();
			marshaller.setWriter(writer);
			marshaller.marshal(isoElements);
			
			result.setUnmarshallerStatus(ExcelMarshallerStatus.SUCCESS);
			
		} catch (Exception e) {
			result.setUnmarshallerStatus(ExcelMarshallerStatus.FAILURE);
			result.setMarshallException(e);
		}
		return result;
	}
	
	private Marshaller get11179SchemaMarshaller() throws MappingException, IOException {
		Mapping mapping = get11179Mapping();
		Marshaller marshaller = getMarshaller(mapping);
		
		return marshaller;
	}
	
	private Mapping get11179Mapping() throws IOException, MappingException {
		return getMapping(ISO11179_MAPPING_FILE);
	}
	
	private Marshaller getMarshaller(Mapping mapping) throws MappingException {
		Marshaller marshaller = new Marshaller();
		marshaller.setMapping(mapping);
		marshaller.setNamespaceMapping("iso11179", "http://www.cancergrid.org/schema/ISO11179");
		marshaller.setNamespaceMapping("caDSR", "http://www.ncicb.nih.gov/caDSR/schema/ISO11179");
		marshaller.setSchemaLocation("http://www.cancergrid.org/schema/ISO11179 gov/nih/nci/ncicb/cadsr/bulkloader/schema/iso11179_updated.xsd " +
										"http://www.ncicb.nih.gov/caDSR/schema/ISO11179 gov/nih/nci/ncicb/cadsr/bulkloader/schema/cadsr_iso11179.xsd");
		
		return marshaller;
	}
	
	private Mapping getMapping(String mappingFile) throws IOException, MappingException {
		ClassLoader cl = ExcelMarshaller.class.getClassLoader();
		URL url = cl.getResource(mappingFile);
		
		Mapping mapping = new Mapping();
		mapping.loadMapping(url);
		
		return mapping;
	}

}
