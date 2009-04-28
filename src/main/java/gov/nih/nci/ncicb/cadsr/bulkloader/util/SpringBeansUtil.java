package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoader;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind.ObjectBinder;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.Translator;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.Persister;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.Transformer;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.Validation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 16, 2009
 * @since 
 */

public class SpringBeansUtil {

	private static final String BULK_LOADER_BEAN_NAME = "bulkLoader";
	private static final String PARSER_BEAN_NAME = "parser";
	private static final String VALIDATOR_BEAN_NAME = "validator";
	private static final String PERSISTER_BEAN_NAME = "persister";
	
	private static final String OBJECT_BINDER_BEAN_NAME = "objectBinder";
	
	private static final String EXCEL_TRANSFORMER_BEAN_NAME = "excelTransformer";
	
	static ClassPathXmlApplicationContext beanFactory = new ClassPathXmlApplicationContext(new String[]{"beans.xml", "loader-spring.xml", "bulkloader-beans.xml", "spring-datasources.xml"});
	
	public static Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}
	
	public static CaDSRBulkLoader getBulkLoader() {
		CaDSRBulkLoader bulkLoader = (CaDSRBulkLoader)beanFactory.getBean(BULK_LOADER_BEAN_NAME);
		
		return bulkLoader;
	}
	
	public static Parser getParser() {
		Parser parser = (Parser)beanFactory.getBean(PARSER_BEAN_NAME);
		
		return parser;
	}
	
	public static Validation getValidator() {
		Validation validation = (Validation)beanFactory.getBean(VALIDATOR_BEAN_NAME);
		
		return validation;
	}
	
	public static Persister getPersister() {
		Persister persister = (Persister)beanFactory.getBean(PERSISTER_BEAN_NAME);
		
		return persister;
	}
	
	public static ObjectBinder getObjectBinder() {
		ObjectBinder objBinder = (ObjectBinder)beanFactory.getBean(OBJECT_BINDER_BEAN_NAME);
		
		return objBinder;
	}

	
	public static Translator<CaDSRObjects> getCaDSRObjectsTranslator() {
		Translator<CaDSRObjects> translator = (Translator<CaDSRObjects>)beanFactory.getBean("caDSRObjectsTranslator");
		
		return translator;
	}	
	
	public static Transformer getExcelTransformer() {
		Transformer transformer = (Transformer)beanFactory.getBean(EXCEL_TRANSFORMER_BEAN_NAME);
		
		return transformer;
	}
}
