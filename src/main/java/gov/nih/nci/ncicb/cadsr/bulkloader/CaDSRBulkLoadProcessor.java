package gov.nih.nci.ncicb.cadsr.bulkloader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.event.EventRecorder;
import gov.nih.nci.ncicb.cadsr.bulkloader.event.EventRecorderImpl;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.CaDSRBulkLoader;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.Transformer;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CaDSRBulkLoadProcessor {

	private Transformer transformer;
	private CaDSRBulkLoader bulkLoader;
	
	private static Log log = LogFactory.getLog(CaDSRBulkLoadProcessor.class);
	
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
	
	public BulkLoadProcessResult[] process(String _inputDir, String _outputDir, boolean validate) {
		List<BulkLoadProcessResult> opResults = new ArrayList<BulkLoadProcessResult>();
		
		try {
			File[] inputFiles = getInputFiles(_inputDir);
			for (File inputFile: inputFiles) {
				File outputFile = createOutputFile(inputFile, _outputDir);
				
				TransformerInputParams transformerParams = new TransformerInputParams();
				transformerParams.setInputFile(inputFile);
				transformerParams.setOutputFile(outputFile);
				transformerParams.setValidate(validate);
				
				BulkLoadProcessResult processResult = process(transformerParams);
				opResults.add(processResult);
			}
		} catch (Exception e) {
			BulkLoadProcessResult processResult = new BulkLoadProcessResult();
			processResult.setProcessorStatus(BulkLoadProcessorStatus.FAILURE);
			log.error("Error processing. Input dir ["+_inputDir+"], Output dir ["+_outputDir+"]", e);
		}
		
		return opResults.toArray(new BulkLoadProcessResult[]{});
	}
	
	private BulkLoadProcessResult process(TransformerInputParams transformerParams) {
		BulkLoadProcessResult processResult = new BulkLoadProcessResult();
		
		try {
			TransformerResult transformerResult = transformer.transform(transformerParams);
			processResult.setTransformResult(transformerResult);
			
			if (!transformerResult.hasErrors()) {
				LoaderInput loaderInput = new LoaderInput();
				loaderInput.setFileToLoad(transformerParams.getOutputFile());
				loaderInput.setValidate(transformerParams.isValidate());
				
				LoadProperties loadProperties = transformerResult.getTransformationResult().getLoadProperties();
				
				EventRecorder recorder = new EventRecorderImpl();
				bulkLoader.setRecorder(recorder);
				
				bulkLoader.load(loaderInput, loadProperties);
				
				LoadResult loadResult = recorder.getResult();
				
				processResult.setLoadResult(loadResult);
			}
		} catch (Exception e) {
			BulkLoadProcessorStatus processorStatus = BulkLoadProcessorStatus.OP_FILE_CREATION_FAILURE;
			processResult.setProcessorStatus(processorStatus);
			processResult.setException(e);
		}
		
		return processResult;
	}
	
	private File[] getInputFiles(String inputDir) {
		final File inputDirFile = new File(inputDir);
		File[] inputFiles = inputDirFile.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (dir.getAbsolutePath().equalsIgnoreCase(inputDirFile.getAbsolutePath()) 
						&& name.endsWith(".xml")) {
					return true;
				}
				return false;
			}
			
		});
		
		return inputFiles;
	}
	
	private File createOutputFile(File inputFile, String outputDir) throws Exception{
		File outputDirFile = new File(outputDir);
		outputDirFile.mkdirs();
		
		String opFilePath = outputDirFile.getAbsolutePath()+File.separatorChar+inputFile.getName();
		File opFile = new File(opFilePath);
		if (!opFile.exists()) {
			boolean opFileCreated = opFile.createNewFile();
			if (!opFileCreated) {
				throw new RuntimeException("Error writing to dir ["+outputDir+"]. Please make sure this dir is writable");
			}
		}
		return opFile;
	}
}
