package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class AlternateNameTypeValidator extends AbstractValidator {

	@Override
	public ValidationItems validate() {
		ValidationItems validationItems = ValidationItems.getInstance();
		DataElement de = DomainObjectFactory.newDataElement();
		List<DataElement> deList = elementsList.getElements(de);
		
		for (DataElement dataElement: deList) {
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
		
		return validationItems;
	}

}
