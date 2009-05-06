package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.CaDSRBulkLoader;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.Transformer;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;

import java.io.File;

public class CaDSRBulkLoadProcessor {

	private Transformer transformer;
	private CaDSRBulkLoader bulkLoader;
	
	public Transformer getTransformer() {
		return transformer;
	}
	public void setTransformer(Transformer transformer) {
		this.transformer = transformer;
	}
	public CaDSRBulkLoader getBulkLoader() {
		return bulkLoader;
	}
	public void setBulkLoader(CaDSRBulkLoader bulkLoader) {
		this.bulkLoader = bulkLoader;
	}
	
	public BulkLoadProcessResult process(String _inputFile, String _outputFile, boolean validate) {
		BulkLoadProcessResult processResult = new BulkLoadProcessResult();
		
		TransformerInputParams transformerParams = new TransformerInputParams();
		File inputFile = new File(_inputFile);
		File outputFile = new File(_outputFile);
		
		transformerParams.setInputFile(inputFile);
		transformerParams.setOutputFile(outputFile);
		transformerParams.setValidate(validate);
		
		TransformerResult transformerResult = transformer.transform(transformerParams);
		processResult.setTransformResult(transformerResult);
		
		if (!transformerResult.hasErrors()) {
			LoaderInput loaderInput = new LoaderInput();
			loaderInput.setFileToLoad(outputFile);
			loaderInput.setValidate(validate);
			
			LoadProperties loadProperties = transformerResult.getTransformationResult().getLoadProperties();
			
			LoadResult loadResult = bulkLoader.load(loaderInput, loadProperties);
			processResult.setLoadResult(loadResult);
		}
		
		return processResult;
	}
}
