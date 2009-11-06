package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.validation;

import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelForm;
import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelQuestion;
import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.util.QuestionsIterator;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidation;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationLineItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExcelValidation implements TransformerValidation {

	private static final Pattern CONCEPTS_PATTERN = Pattern.compile("[a-zA-Z0-9]+");
	
	public TransformerValidationResult validate(Item toValidate) {
		
		TransformerValidationResult result = new TransformerValidationResult();
		
		ExcelForm form = (ExcelForm) toValidate;
		
		List<Item> formItems = new ArrayList<Item>();
		formItems.add(form);
		TransformerValidationLineItemResult formLineItemResult = new TransformerValidationLineItemResult(0, formItems);
		result.addLineItemResult(formLineItemResult);
		
		validateFormHeader(form, formLineItemResult);
		
		List<ExcelQuestion> questions = form.getQuestions();
		
		QuestionsIterator questionsIter = new QuestionsIterator(questions);
		
		while (questionsIter.hasNext()) {
			List<ExcelQuestion> oneRecord = questionsIter.next();
			List<Item> items = new ArrayList<Item>();
			for (ExcelQuestion question: oneRecord) {
				items.add(question);
			}
			
			ExcelQuestion question = oneRecord.get(0);
			
			TransformerValidationLineItemResult lineItemResult = new TransformerValidationLineItemResult(question.getQuestionNumber(), items);
			result.addLineItemResult(lineItemResult);
			
			try {
				lineItemResult = validateCDE(question, lineItemResult);
				lineItemResult = validatePVsAndVMs(oneRecord, lineItemResult);
			} catch(Exception e) {
				result.setValidationException(e);
				result.setStatus(ExcelValidationStatus.FAILED_UNKNOWN);
				
				return result;
			}
		}
		
		if (result.hasErrors()) {
			result.setStatus(ExcelValidationStatus.FAILED);
		}
		else {
			result.setStatus(ExcelValidationStatus.PASSED);
		}
		
		return result;
	}
	
	private TransformerValidationLineItemResult validateFormHeader(ExcelForm excelForm, TransformerValidationLineItemResult lineItemResult) {
		String formName = excelForm.getFormName();
		String contextName = excelForm.getContext();
		String classScheme = excelForm.getClassScheme();
		String classSchemeItem = excelForm.getClassSchemeItem();
		String source = excelForm.getSource();
		
		if (formName == null || formName.trim().equals("")) {
			lineItemResult.addStatus(ExcelValidationStatus.BLANK_FORM_NAME);
		}
		if (contextName == null || contextName.trim().equals("")) {
			lineItemResult.addStatus(ExcelValidationStatus.BLANK_CONTEXT_NAME);
		}
		if (classScheme == null || classScheme.trim().equals("")) {
			lineItemResult.addStatus(ExcelValidationStatus.BLANK_CS);
		}
		if (classSchemeItem == null || classSchemeItem.trim().equals("")) {
			lineItemResult.addStatus(ExcelValidationStatus.BLANK_CSI);
		}
		if (source == null || source.trim().equals("")) {
			lineItemResult.addStatus(ExcelValidationStatus.BLANK_SOURCE);
		}
		
		if (!lineItemResult.hasErrors()) {
			lineItemResult.addStatus(ExcelValidationStatus.PASSED);
		}
		
		return lineItemResult;
	}

	private TransformerValidationLineItemResult validateCDE(ExcelQuestion question, TransformerValidationLineItemResult lineItemResult){
		String cdeId = question.getCdeId();
		String decId = question.getDecId();
		String vdId = question.getVdId();
		
		if (cdeId == null || cdeId.trim().equals("")) {
			validateDEC(question, lineItemResult);
			validateVD(question, lineItemResult);
		}
		else {
			if (!testIdFormat(cdeId)) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_CDEID);
			}
			/*if (decId!=null || !areAllDECFieldsBlank(question) || vdId!=null || !areAllVDFieldsBlank(question)) {
				lineItemResult.addStatus(ExcelValidationStatus.CDEID_AND_DATA_PRESENT);
			}*/
		}
		
		if (!lineItemResult.hasErrors()) {
			lineItemResult.addStatus(ExcelValidationStatus.PASSED);
		}
		
		return lineItemResult;
	}
	
	private TransformerValidationLineItemResult validateDEC(ExcelQuestion question, TransformerValidationLineItemResult lineItemResult) {
		String decId = question.getDecId();
		String ocQualConcepts = question.getOcQualConcepts();
		String ocPrimConcepts = question.getOcPrimConcepts();
		String propQualConcepts = question.getPropQualConcepts();
		String propPrimConcepts = question.getPropPrimConcepts();
		String decConceptualDomainId = question.getDecConceptualDomainId();
		
		if (decId == null || decId.trim().equals("")) {
			
			if (isAnyDECFieldBlank(question)) {
				lineItemResult.addStatus(ExcelValidationStatus.DECID_AND_DATA_NOT_PRESENT);
			}
			else {
				if (ocQualConcepts != null && !testConceptFormat(ocQualConcepts)) {
					lineItemResult.addStatus(ExcelValidationStatus.INVALID_QUAL_CONCEPT_ID);
				}
				if (!testConceptFormat(ocPrimConcepts)) {
					lineItemResult.addStatus(ExcelValidationStatus.INVALID_PRIM_CONCEPT_ID);
				}
				if (propQualConcepts != null && !testConceptFormat(propQualConcepts)) {
					lineItemResult.addStatus(ExcelValidationStatus.INVALID_QUAL_CONCEPT_ID);
				}
				if (!testConceptFormat(propPrimConcepts)) {
					lineItemResult.addStatus(ExcelValidationStatus.INVALID_PRIM_CONCEPT_ID);
				}
				if (!testIdFormat(decConceptualDomainId)) {
					lineItemResult.addStatus(ExcelValidationStatus.INVALID_DEC_CD_ID);
				}
			}
		}
		else {
			if (!testIdFormat(decId)) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_DECID);
			}
			
			/*if (!areAllDECFieldsBlank(question)) {
				lineItemResult.addStatus(ExcelValidationStatus.DECID_AND_DATA_PRESENT);
			}*/
		}
		
		return lineItemResult;
	}
	
	private boolean isAnyDECFieldBlank(ExcelQuestion question) {
		String ocPrimConcepts = question.getOcPrimConcepts();
		String propPrimConcepts = question.getPropPrimConcepts();
		String decConceptualDomainId = question.getDecConceptualDomainId();
		
		List<String> decFieldValues = new ArrayList<String>();
		decFieldValues.add(ocPrimConcepts);
		decFieldValues.add(propPrimConcepts);
		decFieldValues.add(decConceptualDomainId);
		
		return anyFieldBlank(decFieldValues);
	}
	
	private boolean isAnyVDFieldBlank(ExcelQuestion question) {
		String repTermQualConcepts = question.getRepTermQualConcepts();
		String repTermPrimConcepts = question.getRepTermPrimConcepts();
		String vdCdId = question.getVdConceptualDomainId();
		String datatype = question.getDataType();
		String vdMaxLength = question.getVdMaxLength();
		String enumerated = question.getEnumerated();
		
		List<String> vdFieldValues = new ArrayList<String>();
		vdFieldValues.add(repTermQualConcepts);
		vdFieldValues.add(repTermPrimConcepts);
		vdFieldValues.add(vdCdId);
		vdFieldValues.add(datatype);
		vdFieldValues.add(vdMaxLength);
		vdFieldValues.add(enumerated);
		
		return anyFieldBlank(vdFieldValues);
	}
	
	private boolean anyFieldBlank(List<String> values) {
		for (String value: values) {
			if (value == null || value.trim().equals("")) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean areAllDECFieldsBlank(ExcelQuestion question) {
		String ocQualConcepts = question.getOcQualConcepts();
		String ocPrimConcepts = question.getOcPrimConcepts();
		String propQualConcepts = question.getPropQualConcepts();
		String propPrimConcepts = question.getPropPrimConcepts();
		String decConceptualDomainId = question.getDecConceptualDomainId();
		
		List<String> decFieldValues = new ArrayList<String>();
		decFieldValues.add(ocQualConcepts);
		decFieldValues.add(ocPrimConcepts);
		decFieldValues.add(propQualConcepts);
		decFieldValues.add(propPrimConcepts);
		decFieldValues.add(decConceptualDomainId);
		
		return allFieldsBlank(decFieldValues);
	}
	
	
	private boolean areAllVDFieldsBlank(ExcelQuestion question) {
		String repTermQualConcepts = question.getRepTermQualConcepts();
		String repTermPrimConcepts = question.getRepTermPrimConcepts();
		String vdCdId = question.getVdConceptualDomainId();
		String datatype = question.getDataType();
		String vdMaxLength = question.getVdMaxLength();
		String enumerated = question.getEnumerated();
		
		List<String> vdFieldValues = new ArrayList<String>();
		vdFieldValues.add(repTermQualConcepts);
		vdFieldValues.add(repTermPrimConcepts);
		vdFieldValues.add(vdCdId);
		vdFieldValues.add(datatype);
		vdFieldValues.add(vdMaxLength);
		vdFieldValues.add(enumerated);
		
		return allFieldsBlank(vdFieldValues);
	}
	
	private boolean allFieldsBlank(List<String> values) {
		for (String value: values) {
			if (value != null && !value.trim().equals("")) {
				return false;
			}
		}
		
		return true;
	}
	
	private TransformerValidationLineItemResult validateVD(ExcelQuestion question, TransformerValidationLineItemResult lineItemResult) {
		
		String vdId = question.getVdId();
		String repTermQualConcepts = question.getRepTermQualConcepts();
		String repTermPrimConcepts = question.getRepTermPrimConcepts();
		String vdCdId = question.getVdConceptualDomainId();
		String datatype = question.getDataType();
		String vdMaxLength = question.getVdMaxLength();
		String enumerated = question.getEnumerated();
		
		if (vdId == null || vdId.trim().equals("")) {
			List<String> vdFieldValues = new ArrayList<String>();
			vdFieldValues.add(repTermPrimConcepts);
			vdFieldValues.add(vdCdId);
			vdFieldValues.add(datatype);
			vdFieldValues.add(enumerated);
			
			if (anyFieldBlank(vdFieldValues)) {
				lineItemResult.addStatus(ExcelValidationStatus.VDID_AND_DATA_NOT_PRESENT);
				return lineItemResult;
			}
						
			if (repTermQualConcepts!=null && !testConceptFormat(repTermQualConcepts)) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_QUAL_CONCEPT_ID);
			}
			if (!testConceptFormat(repTermPrimConcepts)) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_PRIM_CONCEPT_ID);
			}
			if (!testIdFormat(vdCdId)) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_VD_CD_ID);
			}
			if (enumerated == null 
					|| (!enumerated.trim().equalsIgnoreCase("yes")
					&& !enumerated.trim().equalsIgnoreCase("no"))) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_ENUMERATED);
			}
			
			if (vdMaxLength != null) {
				try {
					Integer.parseInt(vdMaxLength);
				} catch (Exception e) {
					lineItemResult.addStatus(ExcelValidationStatus.INVALID_MAX_LENGTH);
				}
			}
		}
		else {
			if (!testIdFormat(vdId)) {
				lineItemResult.addStatus(ExcelValidationStatus.INVALID_VDID);
			}
		}
		
		return lineItemResult;
	}
	
	private TransformerValidationLineItemResult validatePVsAndVMs(List<ExcelQuestion> oneRecord, TransformerValidationLineItemResult lineItemResult) {
		if (isEnumerated(oneRecord)) {
			boolean pvPresent = false;
			List<String> vmConcepts = new ArrayList<String>();
			for (ExcelQuestion excelQuestion: oneRecord) {
				String pv = excelQuestion.getPv();
				if (!pvPresent && pv != null && !pv.trim().equals("")) {
					pvPresent  = true;
				}
				String vmConceptStrs = excelQuestion.getVmConcepts();
				if (vmConceptStrs != null) {
					String[] vmConceptsAsArray = getFieldMainElements(vmConceptStrs);
					for (String vmConceptWithName: vmConceptsAsArray) {
						String[] vmConceptAndName = getElementSubElements(vmConceptWithName);
						String vmConcept = vmConceptAndName[0];
						if (vmConcepts.contains(vmConcept)) {
							lineItemResult.addStatus(ExcelValidationStatus.generateErrorStatus("Duplicate VM concept ["+vmConcept+"] for the Question # ["+excelQuestion.getQuestionNumber()+"]"));
						}
						else {
							vmConcepts.add(vmConcept);
						}
					}
				}
			}
			
			if (!pvPresent) {
				lineItemResult.addStatus(ExcelValidationStatus.ENUM_VD_NO_PV);
			}
		}
		
		return lineItemResult;
	}
	
	private boolean isEnumerated(List<ExcelQuestion> oneRecord) {
		ExcelQuestion mainQuestion = oneRecord.get(0);
		String enumerated = mainQuestion.getEnumerated();
		String vdId = mainQuestion.getVdId();
		if (vdId == null && enumerated != null && enumerated.equalsIgnoreCase("yes")) {
			return true;
		}
		else return false;
	}
	
	private String[] getFieldMainElements(String fieldStr) {
		if (fieldStr == null || fieldStr.trim().equals("")) {
			return new String[0];
		}
		String[] fieldMainElements = fieldStr.split(";");
		
		return fieldMainElements;
	}
	
	private String[] getElementSubElements(String elementStr) {
		if (elementStr == null || elementStr.trim().equals("")) {
			return new String[0];
		}
		String[] elementsSubElements = elementStr.split(":");
		
		return elementsSubElements;
	}
	
	private boolean testIdFormat(String id) {
		Pattern idPattern = Pattern.compile("[0-9]*v[0-9]*\\.?[0-9]+");
		return patternMatch(id, idPattern);
	}
	
	private boolean testConceptFormat(String qualConcepts) {
		String[] conceptFields = qualConcepts.split(";");
		
		if (conceptFields.length < 1) {
			return false;
		}
		
		for (String conceptField: conceptFields) {
			String[] conceptTerms = conceptField.split(":");
			
			if (conceptTerms.length < 1) {
				return false;
			}
			if (CONCEPTS_PATTERN.matcher(conceptTerms[0]).matches()) {
				continue;
			}
			else {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean patternMatch(String toMatch, Pattern p) {
		Matcher matcher = p.matcher(toMatch);
		return matcher.matches();
	}
}
