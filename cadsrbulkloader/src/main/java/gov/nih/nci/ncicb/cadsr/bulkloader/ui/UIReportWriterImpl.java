/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.ui;

import gov.nih.nci.ncicb.cadsr.bulkloader.BulkLoadProcessResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.event.LoaderEvent;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.loader.LoadStatus;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.TransformerStatus;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.marshall.TransformerMarshallerResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.transformation.TransformerTransformationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.unmarshall.TransformerUnMarshallResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationLineItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.validation.TransformerValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationItemResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationStatus;

import java.io.File;
import java.util.List;

public class UIReportWriterImpl implements UIReportWriter {

	public void writeReport(BulkLoadProcessResult processResult) {
		
		boolean processSuccessful = processResult.isSuccessful();
		TransformerResult transformResult = processResult.getTransformResult();
		LoadResult loadResult = processResult.getLoadResult();
		
		System.out.println("\n=====================================");
		System.out.println("Bulk Load Process Status");
		System.out.println("=====================================");
		System.out.println(processResult.getMessage());
		
		if (transformResult != null) {
			TransformerInputParams transformerInputParams = transformResult.getInputParams();
			
			if (transformerInputParams != null) {
				File inputFile = transformerInputParams.getInputFile();
				if (inputFile != null) {
					System.out.println("Input file: "+inputFile.getAbsolutePath());
				}
			}
			
			if (transformResult.hasErrors()) {
				System.out.println(transformResult.getStatus().getMessage());
				
				TransformerUnMarshallResult unmarshallResult = transformResult.getUnmarshallerResult();
				TransformerValidationResult validationResult = transformResult.getValidationResult();
				TransformerTransformationResult transformationResult = transformResult.getTransformationResult();
				TransformerMarshallerResult marshallResult = transformResult.getMarshallerResult();
				
				if (unmarshallResult != null && unmarshallResult.hasErrors()) {
					System.out.println(unmarshallResult.getStatus().getMessage());
				}
				if (validationResult != null && validationResult.hasErrors()) {
					System.out.println();
					System.out.println(validationResult.getStatus().getMessage());
					if (validationResult.hasErrors()) {
						List<TransformerValidationLineItemResult> lineItemResults = validationResult.getLineItemResults();
						for (TransformerValidationLineItemResult lineItemResult: lineItemResults) {
							List<TransformerStatus> transformStatuses = lineItemResult.getStatuses();
							for (TransformerStatus transformStatus: transformStatuses) {
								if (!transformStatus.validationPassed()) {
									System.out.println(lineItemResult.getRecordNumber()+": "+transformStatus.getMessage());
								}
							}
						}
					}
				}
				if (transformationResult != null && transformationResult.hasErrors()) {
					System.out.println(transformationResult.getStatus().getMessage());
				}
				if (marshallResult != null && marshallResult.hasErrors()) {
					System.out.println(marshallResult.getMarshallerStatus().getMessage());
				}
			}
		}
		
		if (loadResult != null) {
			LoadStatus loadStatus = loadResult.getLoadStatus();
			ValidationResult validationResult = loadResult.getValidationResult();
			if (loadStatus != null) {
				System.out.println("Data Load Status: "+loadStatus.getStatusMessage());
			}
			
			List<CaDSRObjects.Memento> snapshots = loadResult.getSnapshotsForEvent(LoaderEvent.PERSISTING);
			if (snapshots != null && snapshots.size() > 1) {
				List<String> prePersistDEIds = snapshots.get(0).deIds;
				List<String> postPersistDEIds = snapshots.get(1).deIds;
				
				System.out.println("Number of CDEs reused = "+prePersistDEIds.size());
				postPersistDEIds.removeAll(prePersistDEIds);
				System.out.println("Number of CDEs newly created = "+postPersistDEIds.size());
			}
			
			boolean loadSuccessful = loadResult.isSuccessful();
			if (!loadSuccessful) {
				
				if (!processSuccessful && loadStatus.equals(LoadStatus.FAILED_WITH_VALIDATION_ERROR)) {
					List<ValidationItemResult> itemResults = validationResult.getItemResults();
					
					System.out.println("Validation Errors:");
					System.out.println("-------------------------------------");
					int i=1;
					for (ValidationItemResult itemResult: itemResults) {
						ValidationStatus itemStatus = itemResult.getStatus();
						if (!itemStatus.isSuccessful()) {
							System.out.println(i+": "+itemResult.getMessage());
						}
						i++;
					}
				}
			}
			
			if (validationResult != null && validationResult.hasWarnings()) {
				List<ValidationItemResult> itemResults = validationResult.getItemResults();
				System.out.println("\n-------------------------------------");
				System.out.println("Validation Warnings:");
				System.out.println("-------------------------------------");
				int i=1;
				for (ValidationItemResult itemResult: itemResults) {
					if (itemResult.hasWarnings()) {
						System.out.println(i+":"+itemResult.getMessage());
					}
				}
			}
		}
		System.out.println("\n=====================================");

	}

}
