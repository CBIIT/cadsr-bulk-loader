package gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans;

import java.io.File;

public class TransformerInputParams {

	private File inputFile;
	private File outputFile;
	private boolean validate;
	
	public File getInputFile() {
		return inputFile;
	}
	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}
	public File getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
	
}
