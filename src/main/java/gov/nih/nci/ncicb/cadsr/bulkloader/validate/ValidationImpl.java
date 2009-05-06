package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationFatal;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationWarning;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class ValidationImpl implements Validation {

	private List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators;
	private ElementsLists elementsList = ElementsLists.getInstance();
	private HashMap<String, Context> contextsCache = new HashMap<String, Context>();
	private HashMap<String, ClassificationScheme> classSchemesCache = new HashMap<String, ClassificationScheme>();
	
	public void setValidators(List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> validators) {
		this.validators = validators;
	}
	public List<gov.nih.nci.ncicb.cadsr.loader.validator.Validator> getValidators() {
		return validators;
	}
	
	public synchronized ValidationResult validate(CaDSRObjects caDSRObjects, LoadProperties loadProperties) {
		ValidationResult result = new ValidationResult();
		result.setValidationItem(caDSRObjects);
		
		List<ValidationItemResult> itemResults = new ArrayList<ValidationItemResult>();
		result.setItemResults(itemResults);
		
		try {
			loadElements(caDSRObjects, loadProperties);
			
			for (gov.nih.nci.ncicb.cadsr.loader.validator.Validator validator: validators) {
				ValidationItems validationItems = validator.validate();
				processValidationItems(validationItems, itemResults);
			}
			
			unLoadElements(caDSRObjects, loadProperties);
			
			result.setValidationStatus(ValidationStatus.SUCCESS);
		} catch (Exception e) {
			result.setException(e);
			result.setValidationStatus(ValidationStatus.FAILURE);
		}
		
		return result;
	}
	
	private void loadElements(CaDSRObjects caDSRObjects, LoadProperties loadProperties) {
		List<? extends AdminComponent> adminComponents = caDSRObjects.getList();
		for (AdminComponent adminComponent: adminComponents) {
			elementsList.addElement(adminComponent);
		}
		
		Context loadContext = getContext(loadProperties.getContextName());
		elementsList.addElement(loadContext);
		
		ClassificationScheme loadClassScheme = getClassificationScheme(loadProperties.getClassificationSchemeName(), loadProperties.getClassificationSchemeItemName());			
		elementsList.addElement(loadClassScheme);
		
	}
	
	private void unLoadElements(CaDSRObjects caDSRObjects, LoadProperties loadProperties) {
		List<? extends AdminComponent> adminComponents = caDSRObjects.getList();
		for (AdminComponent adminComponent: adminComponents) {
			elementsList.removeElement(adminComponent);
		}
		
		Context loadContext = getContext(loadProperties.getContextName());
		elementsList.removeElement(loadContext);
		
		ClassificationScheme loadClassScheme = getClassificationScheme(loadProperties.getClassificationSchemeName(), loadProperties.getClassificationSchemeItemName());			
		elementsList.removeElement(loadClassScheme);
	}
	
	private List<ValidationItemResult> processValidationItems(ValidationItems validationItems, List<ValidationItemResult> itemResults) {
		
		Set<ValidationError> validationErrors = validationItems.getErrors();
		Set<ValidationFatal> validationFatals = validationItems.getFatals();
		Set<ValidationWarning> validationWarnings = validationItems.getWarnings();
		
		List<ValidationItem> errors = new ArrayList<ValidationItem>();
		errors.addAll(validationErrors);
		errors.addAll(validationFatals);
		errors.addAll(validationWarnings);
			
		for (ValidationItem error: errors) {
			Object item = error.getRootCause();
			String message = error.getMessage();
			ValidationItemResult lineItemResult = new ValidationItemResult(item, ValidationStatus.FAILURE);
			lineItemResult.setMessage(message);
			
			itemResults.add(lineItemResult);
		}
		
		return itemResults;
	}
	
	private Context getContext(String contextName) {
		Context loadContext = contextsCache.get(contextName);
		if ( loadContext == null) {
			loadContext = DomainObjectFactory.newContext();
			loadContext.setName(contextName);
			contextsCache.put(contextName, loadContext);
		}
		
		return loadContext;
	}
	
	private ClassificationScheme getClassificationScheme(String classSchemeName, String classSchemeItemName) {
		ClassificationScheme loadClassScheme = classSchemesCache.get(classSchemeName);
		if (loadClassScheme == null) {
			loadClassScheme = DomainObjectFactory.newClassificationScheme();
			loadClassScheme.setPreferredName(classSchemeName);
			
			ClassificationSchemeItem loadCSI = DomainObjectFactory.newClassificationSchemeItem();
			loadCSI.setPreferredName(classSchemeItemName);
			
			ClassSchemeClassSchemeItem loadCsCSI = DomainObjectFactory.newClassSchemeClassSchemeItem();
			loadCsCSI.setCs(loadClassScheme);
			loadCsCSI.setCsi(loadCSI);
			
			List<ClassSchemeClassSchemeItem> loadCSCSIs = new ArrayList<ClassSchemeClassSchemeItem>();
			loadCSCSIs.add(loadCsCSI);
			
			loadClassScheme.setCsCsis(loadCSCSIs);
			
			classSchemesCache.put(classSchemeName, loadClassScheme);
		}
		
		return loadClassScheme;
	}
	
}
