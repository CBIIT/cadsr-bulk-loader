package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoader;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAO;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.Translator;

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
	private static final String CONCEPTS_DAO_BEAN_NAME = "conceptsDAO";
	private static final String OBJECT_CLASS_DAO_BEAN_NAME = "objectClassDAO";
	private static final String PROPERTIES_DAO_BEAN_NAME = "propertyDAO";
	private static final String DEC_DAO_BEAN_NAME = "dataElementConceptDAO";
	private static final String VD_DAO_BEAN_NAME = "valueDomainDAO";
	private static final String DE_DAO_BEAN_NAME = "dataElementDAO";
	
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
	
	public static BulkLoaderDAO getConceptsDAO() {
		BulkLoaderDAO conceptsDAO = (BulkLoaderDAO)beanFactory.getBean(CONCEPTS_DAO_BEAN_NAME);
		
		return conceptsDAO;
	}
	
	public static BulkLoaderDAO getObjectClassDAO() {
		BulkLoaderDAO conceptsDAO = (BulkLoaderDAO)beanFactory.getBean(OBJECT_CLASS_DAO_BEAN_NAME);
		return conceptsDAO;
	}
	
	public static BulkLoaderDAO getPropertyDAO() {
		BulkLoaderDAO conceptsDAO = (BulkLoaderDAO)beanFactory.getBean(PROPERTIES_DAO_BEAN_NAME);
		return conceptsDAO;
	}
	
	public static BulkLoaderDAO getDECDAO() {
		BulkLoaderDAO decDAO = (BulkLoaderDAO)beanFactory.getBean(DEC_DAO_BEAN_NAME);
		return decDAO;
	}
	
	public static BulkLoaderDAO getVDDAO() {
		BulkLoaderDAO decDAO = (BulkLoaderDAO)beanFactory.getBean(VD_DAO_BEAN_NAME);
		return decDAO;
	}
	
	public static BulkLoaderDAO getDEDAO() {
		BulkLoaderDAO decDAO = (BulkLoaderDAO)beanFactory.getBean(DE_DAO_BEAN_NAME);
		return decDAO;
	}
	
	public static Translator<CaDSRObjects> getCaDSRObjectsTranslator() {
		Translator<CaDSRObjects> translator = (Translator<CaDSRObjects>)beanFactory.getBean("caDSRObjectsTranslator");
		
		return translator;
	}	
}
