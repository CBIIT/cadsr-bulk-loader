package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.unmarshall;

import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelForm;
import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelQuestion;
import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.util.ExcelTransformerUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshaller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.List;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLContext;

public class ExcelUnMarshaller implements TransformerUnMarshaller {

	private static final String FORM_MAPPING_FILE = "gov/nih/nci/ncicb/cadsr/bulkloader/excel/transformer/unmarshall/bl_form_mapping.xml";
	
	public TransformerUnMarshallResult read(File inputFile) {
		TransformerUnMarshallResult result = null;
		
		try {
			Reader ipFileReader = new FileReader(inputFile);
			result = unmarshal(ipFileReader);
		} catch (Exception e) {
			result = new TransformerUnMarshallResult();
			result.setStatus(ExcelUnmarshallerStatus.FAILURE);
			result.setUnMarshallException(e);
		}
		
		return result;
	}
	
	public TransformerUnMarshallResult read(InputStream is) {
		TransformerUnMarshallResult result = null;
		
		try {
			Reader reader = new InputStreamReader(is);
			result = unmarshal(reader);
		} catch (Exception e) {
			result = new TransformerUnMarshallResult();
			result.setStatus(ExcelUnmarshallerStatus.FAILURE);
			result.setUnMarshallException(e);
		}
		
		return result;
	}
	
	private TransformerUnMarshallResult unmarshal(Reader reader) throws Exception{
		TransformerUnMarshallResult result = new TransformerUnMarshallResult();
		
		Unmarshaller unmarshaller = getDefaultUnmarshaller();
		
		ExcelForm excelForm = (ExcelForm)unmarshaller.unmarshal(reader);
		
		cleanForm(excelForm);
		
		result.setUnMarshalledObject(excelForm);
		result.setStatus(ExcelUnmarshallerStatus.SUCCESS);
		
		return result;
	}
	
	private Unmarshaller getDefaultUnmarshaller() throws IOException, MappingException {
		Mapping mapping = getFormMappings();
		Unmarshaller unmarshaller = getUnmarshaller(mapping);
		
		return unmarshaller;
	}
	
	private Mapping getFormMappings() throws IOException, MappingException {
		return getMapping(FORM_MAPPING_FILE);
	}
	
	private Mapping getMapping(String mappingFile) throws IOException, MappingException {
		ClassLoader cl = ExcelUnMarshaller.class.getClassLoader();
		URL url = cl.getResource(mappingFile);
		
		Mapping mapping = new Mapping();
		mapping.loadMapping(url);
		
		return mapping;
	}
	
	private Unmarshaller getUnmarshaller(Mapping mapping) throws MappingException {
		XMLContext context = new XMLContext();
		context.addMapping(mapping);
		
		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setClass(ExcelForm.class);
		
		return unmarshaller; 
	}
	
	private void cleanForm(ExcelForm excelForm) {
		List<ExcelQuestion> excelQuestions = excelForm.getQuestions();
		if (excelQuestions != null) {
			for (ExcelQuestion excelQuestion: excelQuestions) {
				excelQuestion.setCdeId(ExcelTransformerUtil.getPublicIdFromCompositeString(excelQuestion.getCdeId()));
				excelQuestion.setDecId(ExcelTransformerUtil.getPublicIdFromCompositeString(excelQuestion.getDecId()));
				excelQuestion.setVdId(ExcelTransformerUtil.getPublicIdFromCompositeString(excelQuestion.getVdId()));
				excelQuestion.setVdConceptualDomainId(ExcelTransformerUtil.getPublicIdFromCompositeString(excelQuestion.getVdConceptualDomainId()));
				excelQuestion.setDecConceptualDomainId(ExcelTransformerUtil.getPublicIdFromCompositeString(excelQuestion.getDecConceptualDomainId()));
			}
		}
		
	}

}
