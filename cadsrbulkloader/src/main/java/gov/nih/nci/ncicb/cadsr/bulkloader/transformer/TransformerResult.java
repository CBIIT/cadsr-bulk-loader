package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshallerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation.TransformerTransformationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;

import java.util.ArrayList;
import java.util.List;


public class TransformerResult {

	private TransformerUnMarshallResult marshallerResult;
	private TransformerValidationResult validationResult;
	private TransformerTransformationResult transformationResult;
	private TransformerMarshallerResult unmarshallerResult;
	
	private TransformerStatus status;

	public TransformerUnMarshallResult getMarshallerResult() {
		return marshallerResult;
	}

	public void setMarshallerResult(TransformerUnMarshallResult marshallerResult) {
		this.marshallerResult = marshallerResult;
	}

	public TransformerValidationResult getValidationResult() {
		return validationResult;
	}

	public void setValidationResult(TransformerValidationResult validationResult) {
		this.validationResult = validationResult;
	}

	public TransformerTransformationResult getTransformationResult() {
		return transformationResult;
	}

	public void setTransformationResult(
			TransformerTransformationResult transformationResult) {
		this.transformationResult = transformationResult;
	}

	public TransformerMarshallerResult getUnmarshallerResult() {
		return unmarshallerResult;
	}

	public void setUnmarshallerResult(
			TransformerMarshallerResult unmarshallerResult) {
		this.unmarshallerResult = unmarshallerResult;
	}

	public TransformerStatus getStatus() {
		return status;
	}

	public void setStatus(TransformerStatus status) {
		this.status = status;
	}
	
	public boolean hasErrors() {
		if ((marshallerResult!=null && marshallerResult.hasErrors())
				|| (validationResult!=null && validationResult.hasErrors())
				|| (transformationResult!=null && transformationResult.hasErrors())
				|| (unmarshallerResult!=null && unmarshallerResult.hasErrors())) {
			return true;
		}
		else return false;
	}
}
