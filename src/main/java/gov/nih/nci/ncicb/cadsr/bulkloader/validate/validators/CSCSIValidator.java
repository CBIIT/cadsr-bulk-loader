package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class CSCSIValidator extends AbstractValidator {

	public ValidationItems validate() {
		ValidationItems validationItems = ValidationItems.getInstance();
		
		DataElement de = DomainObjectFactory.newDataElement();
		List<DataElement> dataElements = elementsList.getElements(de);
		for (DataElement dataElement: dataElements) {
			List<AdminComponentClassSchemeClassSchemeItem> acCSCSIs = dataElement.getAcCsCsis();
			for (AdminComponentClassSchemeClassSchemeItem acCSCSI: acCSCSIs) {
				ClassSchemeClassSchemeItem csCSI = acCSCSI.getCsCsi();
				ClassificationScheme cs = csCSI.getCs();
				ClassificationSchemeItem csi = csCSI.getCsi();
				
				if (cs == null || cs.getPreferredName()==null || csi==null || csi.getLongName()==null) {
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
		return validationItems;
	}

}
