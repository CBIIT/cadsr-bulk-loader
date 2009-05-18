package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.CaDSRBulkLoadProcessor;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.CaDSRBulkLoader;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.bind.ObjectBinder;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.Translator;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.Persister;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.Transformer;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.Validation;
import gov.nih.nci.ncicb.cadsr.loader.util.UserPreferences;

import java.util.Properties;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 16, 2009
 * @since 
 */

public class SpringBeansUtil {

	private static final String BULK_LOAD_PROCESSOR = "bulkLoadProcessor";
	private static final String BULK_LOADER_BEAN_NAME = "bulkLoader";
	private static final String PARSER_BEAN_NAME = "parser";
	private static final String VALIDATOR_BEAN_NAME = "validator";
	private static final String PERSISTER_BEAN_NAME = "persister";
	private static final String DAO_FACADE = "DAOFacade";
	private static final String OBJECT_BINDER_BEAN_NAME = "objectBinder";
	private static final String EXCEL_TRANSFORMER_BEAN_NAME = "excelTransformer";
	private static final String USER_PREFERENCES_BEAN_NAME = "usersPreferences";
	
	static XmlBeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
	
	static {
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
		reader.loadBeanDefinitions(new ClassPathResource("loader-spring.xml"));
		reader.loadBeanDefinitions(new ClassPathResource("bulkloader-beans.xml"));
		reader.loadBeanDefinitions(new ClassPathResource("bulkloader-datasources-beans.xml"));
	}
	
	private static SpringBeansUtil instance;
	
	private SpringBeansUtil() {}
	
	public static SpringBeansUtil getInstance() {
		if (instance == null) {
			instance = new SpringBeansUtil();
		}
		
		return instance;
	}
	
	public void initialize(Properties props) {
		
		PropertyPlaceholderConfigurer config = new PropertyPlaceholderConfigurer();
		config.setProperties(props);
		config.postProcessBeanFactory(beanFactory);
	}
	
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
	
	public Object getBean(String beanName) {
		return beanFactory.getBean(beanName);
	}
	
	public CaDSRBulkLoadProcessor getBulkLoadProcessor() {
		CaDSRBulkLoadProcessor bulkLoadProcessor = (CaDSRBulkLoadProcessor)beanFactory.getBean(BULK_LOAD_PROCESSOR);
		
		return bulkLoadProcessor;
	}
	
	public CaDSRBulkLoader getBulkLoader() {
		CaDSRBulkLoader bulkLoader = (CaDSRBulkLoader)beanFactory.getBean(BULK_LOADER_BEAN_NAME);
		
		return bulkLoader;
	}
	
	public Parser getParser() {
		Parser parser = (Parser)beanFactory.getBean(PARSER_BEAN_NAME);
		
		return parser;
	}
	
	public Validation getValidator() {
		Validation validation = (Validation)beanFactory.getBean(VALIDATOR_BEAN_NAME);
		
		return validation;
	}
	
	public BulkLoaderDAOFacade getDAOFacade() {
		BulkLoaderDAOFacade daoFacade = (BulkLoaderDAOFacade)beanFactory.getBean(DAO_FACADE);
		
		return daoFacade;
	}
	
	public Persister getPersister() {
		Persister persister = (Persister)beanFactory.getBean(PERSISTER_BEAN_NAME);
		
		return persister;
	}
	
	public ObjectBinder getObjectBinder() {
		ObjectBinder objBinder = (ObjectBinder)beanFactory.getBean(OBJECT_BINDER_BEAN_NAME);
		
		return objBinder;
	}

	
	public Translator<CaDSRObjects> getCaDSRObjectsTranslator() {
		Translator<CaDSRObjects> translator = (Translator<CaDSRObjects>)beanFactory.getBean("caDSRObjectsTranslator");
		
		return translator;
	}	
	
	public Transformer getExcelTransformer() {
		Transformer transformer = (Transformer)beanFactory.getBean(EXCEL_TRANSFORMER_BEAN_NAME);
		
		return transformer;
	}
	
	public UserPreferences getUserPreferences() {
		UserPreferences userPrefs = (UserPreferences)beanFactory.getBean(USER_PREFERENCES_BEAN_NAME);
		
		return userPrefs;
	}
}
