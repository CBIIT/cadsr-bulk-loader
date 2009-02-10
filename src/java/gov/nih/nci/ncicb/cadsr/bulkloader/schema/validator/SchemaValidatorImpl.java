package gov.nih.nci.ncicb.cadsr.bulkloader.schema.validator;

import gov.nih.nci.ncicb.cadsr.bulkloader.ExceptionCode;

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

	public void validate(File _xmlFile) throws SchemaValidationException {
		Validator validator = getValidator();
		Source source = getSource(_xmlFile);
		try {
			validator.validate(source);
		} catch (SAXException e) {
			throw new SchemaValidationException(ExceptionCode.MALFORMED_XML_FILE, e);
		} catch (IOException e) {
			throw new SchemaValidationException(ExceptionCode.IO_EXCEPTION, e);
		} catch (Exception e) {
			throw new SchemaValidationException(ExceptionCode.UNKNOWN, e);
		}
	}
	
	/**
	 * 
	 * @return <code>javax.xml.validation.Validator</code>
	 * @throws SchemaValidationException
	 */
	private Validator getValidator() throws SchemaValidationException {
		Schema schema = getSchema();
		Validator validator = schema.newValidator();
		
		return validator;
	}
	
	/**
	 * 
	 * @return <code>javax.xml.validation.Schema</code>
	 * @throws SchemaValidationException
	 */
	private Schema getSchema() throws SchemaValidationException{
		try {
			File xsdFile = getSchemaFile();
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(xsdFile);
			
			return schema;
		} catch (SAXException e) {
			log.error(ExceptionCode.MALFORMED_SCHEMA_FILE, e);
			throw new SchemaValidationException(ExceptionCode.MALFORMED_SCHEMA_FILE, ExceptionCode.MALFORMED_SCHEMA_FILE.toString(), e);
		} catch (Exception e) {
			log.error(ExceptionCode.UNKNOWN, e);
			throw new SchemaValidationException(ExceptionCode.UNKNOWN, ExceptionCode.UNKNOWN.toString(), e);
		}
	}
	
	/**
	 * Gets the schema file
	 * @return File
	 */
	private File getSchemaFile() {
		URL xsdURL = classLoader.getResource(xsdFilePath);
		File xsdFile = new File(xsdURL.getPath());
		
		return xsdFile;
	}
	
	/**
	 * 
	 * @param _xmlFile
	 * @return <code>javax.xml.transform.Source</code>
	 * @throws SchemaValidationException
	 */
	private Source getSource(File _xmlFile) throws SchemaValidationException{
		try {
			InputSource inputSource = new InputSource(new FileInputStream(_xmlFile));
			SAXSource saxSource = new SAXSource(inputSource);
			
			return saxSource;
		} catch (FileNotFoundException e) {
			throw new SchemaValidationException(ExceptionCode.FILE_NOT_FOUND,"File: "+_xmlFile, e);
		} catch (Exception e) {
			throw new SchemaValidationException(ExceptionCode.UNKNOWN, e);
		}
	}

}
