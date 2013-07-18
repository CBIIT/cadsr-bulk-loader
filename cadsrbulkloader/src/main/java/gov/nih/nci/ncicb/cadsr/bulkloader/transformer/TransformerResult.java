/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshallerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation.TransformerTransformationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;


public class TransformerResult {

	private TransformerInputParams inputParams;
	private TransformerMarshallerResult marshallerResult;
	private TransformerValidationResult validationResult;
	private TransformerTransformationResult transformationResult;
	private TransformerUnMarshallResult unmarshallerResult;
	
	private TransformerStatus status;

	public TransformerInputParams getInputParams() {
		return inputParams;
	}

	public void setInputParams(TransformerInputParams inputParams) {
		this.inputParams = inputParams;
	}

	public TransformerMarshallerResult getMarshallerResult() {
		return marshallerResult;
	}

	public void setMarshallerResult(TransformerMarshallerResult marshallerResult) {
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

	public TransformerUnMarshallResult getUnmarshallerResult() {
		return unmarshallerResult;
	}

	public void setUnmarshallerResult(
			TransformerUnMarshallResult unmarshallerResult) {
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
