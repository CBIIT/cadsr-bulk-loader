/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Nov 21, 2008
 * @since 
 */

public class SchemaValidatorImpl implements SchemaValidator {

	private final String xsdFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/cadsr_iso11179.xsd";
	
	private static Log log = LogFactory.getLog(SchemaValidatorImpl.class);
	private ClassLoader classLoader = SchemaValidatorImpl.class.getClassLoader();

	public SchemaValidationResult validate(File _xmlFile) {
		SchemaValidationResult result = new SchemaValidationResult();
		result.setInputFile(_xmlFile);
		try {
			Validator validator = getValidator();
			Source source = getSource(_xmlFile);
			validator.validate(source);
		} catch (FileNotFoundException e) {
			result.setException(e);
			result.setStatus(SchemaValidationStatus.FILE_READ_FAILURE);
			result.setMessage(e.getMessage());
		} catch (SAXException e) {
			result.setException(e);
			result.setStatus(SchemaValidationStatus.FAILURE);
		} catch (IOException e) {
			result.setException(e);
			result.setStatus(SchemaValidationStatus.FAILURE);
		}
		
		return result;
	}
	
	/**
	 * 
	 * @return <code>javax.xml.validation.Validator</code>
	 * @throws SchemaValidationException
	 */
	private Validator getValidator() throws SAXException {
		Schema schema = getSchema();
		Validator validator = schema.newValidator();
		
		return validator;
	}
	
	/**
	 * 
	 * @return <code>javax.xml.validation.Schema</code>
	 * @throws SchemaValidationException
	 */
	private Schema getSchema() throws SAXException {
		URL xsdURL = getSchemaURL();
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdURL);
		
		return schema;
	}
	
	/**
	 * Gets the schema file
	 * @return File
	 */
	private URL getSchemaURL() {
		URL xsdURL = classLoader.getResource(xsdFilePath);
		
		return xsdURL;
	}
	
	/**
	 * 
	 * @param _xmlFile
	 * @return <code>javax.xml.transform.Source</code>
	 * @throws SchemaValidationException
	 */
	private Source getSource(File _xmlFile) throws FileNotFoundException{
		InputSource inputSource = new InputSource(new FileInputStream(_xmlFile));
		SAXSource saxSource = new SAXSource(inputSource);
		
		return saxSource;
	}

}
