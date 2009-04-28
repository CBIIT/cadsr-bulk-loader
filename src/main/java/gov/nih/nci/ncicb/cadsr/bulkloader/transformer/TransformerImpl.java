package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshaller;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshallerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation.TransformerTransformation;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation.TransformerTransformationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshaller;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidation;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;

import java.io.File;

public class TransformerImpl implements Transformer {

	private TransformerUnMarshaller unmarshaller;
	private TransformerValidation transformerValidation;
	private TransformerTransformation transformation;
	private TransformerMarshaller marshaller;
	
	public TransformerValidation getTransformerValidation() {
		return transformerValidation;
	}

	public void setTransformerValidation(TransformerValidation transformerValidation) {
		this.transformerValidation = transformerValidation;
	}

	public TransformerTransformation getTransformation() {
		return transformation;
	}

	public void setTransformation(TransformerTransformation transformation) {
		this.transformation = transformation;
	}
	

	public TransformerUnMarshaller getUnmarshaller() {
		return unmarshaller;
	}

	public void setUnmarshaller(TransformerUnMarshaller unmarshaller) {
		this.unmarshaller = unmarshaller;
	}

	public TransformerMarshaller getMarshaller() {
		return marshaller;
	}

	public void setMarshaller(TransformerMarshaller marshaller) {
		this.marshaller = marshaller;
	}

	public TransformerResult transform(TransformerInputParams _inputParams)
			throws TransformerException {
		TransformerResult result = new TransformerResult();
		
		File inputFile = _inputParams.getInputFile();
		File outputFile = _inputParams.getOutputFile();
		
		TransformerUnMarshallResult marshallResult = unmarshaller.read(inputFile);
		result.setMarshallerResult(marshallResult);
		
		if (marshallResult.getStatus().validationPassed()) {
			if (_inputParams.isValidate()) {
				TransformerValidationResult validationResult = transformerValidation.validate(marshallResult.getUnMarshalledObject());
				result.setValidationResult(validationResult);
				
				if (!validationResult.getStatus().validationPassed()) {
					return result;
				}
			}
			TransformerTransformationResult transformationResult = transformation.transform(marshallResult.getUnMarshalledObject());
			result.setTransformationResult(transformationResult);
			
			if (!transformationResult.getStatus().validationPassed()) {
				return result;
			}
			
			TransformerMarshallerResult unmarshallerResult = marshaller.marshall(transformationResult.getIsoElements(), outputFile);
			result.setUnmarshallerResult(unmarshallerResult);
			
		}
		
		
		return result;
	}
}
