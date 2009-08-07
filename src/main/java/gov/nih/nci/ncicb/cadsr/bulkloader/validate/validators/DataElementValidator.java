package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class DataElementValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		DataElement de = DomainObjectFactory.newDataElement();
		List<DataElement> dataElements = elementsList.getElements(de);
		for (DataElement dataElement: dataElements) {
			validateId(dataElement);
			validateAlternateName(dataElement);
			validateCSCSI(dataElement);
			validateDefinitionLength(dataElement);
		}
		
		return validationItems;
	}
	
	private void validateId(DataElement dataElement) {
		String publicId = dataElement.getPublicId();
		Float version = dataElement.getVersion();
		if (publicId != null && version != null) {
			DataElement deGot = dao.findDataElementById(Integer.parseInt(publicId), version);
			if (deGot.getPublicId() == null) {
				ValidationItem validationItem = new ValidationError("Data Element Id ["+publicId+"v"+version+"] not valid", dataElement);
				validationItems.addItem(validationItem);
			}
		}
	}
	
	private void validateAlternateName(DataElement dataElement) {
		List<AlternateName> alternateNames = dataElement.getAlternateNames();
		for (AlternateName alternateName: alternateNames) {
			String altNameType = alternateName.getType();
			if (altNameType == null || altNameType.trim().equals("")) {
				ValidationItem validationItem = new ValidationError("Alternate Name type for the DataElement ["+dataElement.getLongName()+"] cannot be blank", dataElement);
				validationItems.addItem(validationItem);
			}
			else {
				List<String> alternateNameTypes = dao.getAlternateNameTypes();
				if (!alternateNameTypes.contains(altNameType)) {
					ValidationItem validationItem = new ValidationError("The Alternate Name Type ["+altNameType+"] for the DataElement ["+dataElement.getLongName()+"] is not a vlid type", dataElement);
					validationItems.addItem(validationItem);
				}
			}
		}
	}
	
	private void validateCSCSI(DataElement dataElement) {
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIs = dataElement.getAcCsCsis();
		for (AdminComponentClassSchemeClassSchemeItem acCSCSI: acCSCSIs) {
			ClassSchemeClassSchemeItem csCSI = acCSCSI.getCsCsi();
			ClassificationScheme cs = csCSI.getCs();
			ClassificationSchemeItem csi = csCSI.getCsi();
			
			if (cs == null || cs.getPreferredName() == null || csi == null || csi.getLongName() == null) {
				ValidationItem validationItem = new ValidationError("All Data Elements need to have a Classification Scheme and a Classification Scheme Item", dataElement);
				validationItems.addItem(validationItem);
			}
			else {
				ClassificationScheme retrievedCS = dao.getClassificationScheme(cs);
				if (retrievedCS.getPublicId() == null) {
					ValidationItem validationItem = new ValidationError("Classification scheme ["+cs.getLongName()+"] could not be found", cs);
					validationItems.addItem(validationItem);
				}
				else {
					boolean csiValidated = false;
					List<ClassSchemeClassSchemeItem> retrievedCsCsis = (List<ClassSchemeClassSchemeItem>)retrievedCS.getCsCsis();
					
					if (retrievedCsCsis != null) {
						for (ClassSchemeClassSchemeItem retrievedCsCsi: retrievedCsCsis) {
							ClassificationSchemeItem retrievedCSI = retrievedCsCsi.getCsi();
							if (retrievedCSI.getLongName().equalsIgnoreCase(csi.getLongName())) {
								csiValidated = true;
								break;
							}
						}
					}
					
					if (!csiValidated) {
						ValidationItem validationItem = new ValidationError("Classification scheme ["+cs.getPreferredName()+"] does not contain the Classification Scheme Item ["+csi.getLongName()+"]", csi);
						validationItems.addItem(validationItem);
					}
				}
			}
			
		}
	}
	
	private void validateDefinitionLength(DataElement dataElement) {
		if (dataElement.getPublicId() == null) {
			String decDef = dataElement.getDataElementConcept().getPreferredDefinition();
			String vdDef = dataElement.getValueDomain().getPreferredDefinition();
			if (decDef == null) decDef = "";
			if (vdDef == null) vdDef = "";
			
			String deDef = decDef+" "+vdDef;
			if (deDef.length() > MAX_DEF_FIELD_SIZE) {
				ValidationError error = new ValidationError("Length of DE ("+dataElement.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", dataElement);
				validationItems.addItem(error);
			}
		}
	}

}
