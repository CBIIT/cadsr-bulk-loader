package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class ValueDomainValidator extends AbstractValidator {

	private static ValueDomain testVD = DomainObjectFactory.newValueDomain();
	
	static {
		testVD.setWorkflowStatus(AdminComponent.WF_STATUS_ALL);
	}
	
	@Override
	public ValidationItems validate() {
		ValueDomain vd = DomainObjectFactory.newValueDomain();
		List<ValueDomain> valueDomains = elementsList.getElements(vd);
		for (ValueDomain valueDomain: valueDomains) {
			validateId(valueDomain);
			validateDefinitionLength(valueDomain);
			validateRetiredValueDomain(valueDomain);
		}
		
		return validationItems;
	}
	
	private void validateId(ValueDomain valueDomain) {
		String publicId = valueDomain.getPublicId();
		Float version = valueDomain.getVersion();
		
		if (publicId != null && version != null) {
			ValueDomain vdGot = dao.findValueDomainsById(Integer.parseInt(publicId), new Double(version).doubleValue());
			if (vdGot.getPublicId() == null) {
				ValidationItem validationItem = new ValidationError("Value Domain Id ["+publicId+"v"+version+"] not valid", valueDomain);
				validationItems.addItem(validationItem);
			}
		}
	}
	
	private void validateDefinitionLength(ValueDomain valueDomain) {
		if (valueDomain.getPublicId() == null) {
			String vdPrefDef = valueDomain.getPreferredDefinition();
			if (vdPrefDef != null && vdPrefDef.length() > MAX_DEF_FIELD_SIZE) {
				ValidationError error = new ValidationError("Length of VD ("+valueDomain.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", valueDomain);
				validationItems.addItem(error);
			}
		}
	}
	
	private void validateRetiredValueDomain(ValueDomain valueDomain) {
		testVD.setPreferredName(valueDomain.getPreferredName());
		
		List<ValueDomain> foundVDs = dao.findValueDomains(testVD);
		
		if (foundVDs != null) {
			for (ValueDomain foundVD: foundVDs) {
				String foundOCWFStatus = foundVD.getWorkflowStatus();
				if (foundOCWFStatus.contains("RETIRED")) {
					ValidationItem error = new ValidationError("The Value Domain to be created ["+valueDomain.getPreferredName()+"] already exists but is retired. Please correct this and reload", valueDomain);
					validationItems.addItem(error);
				}
			}
		}
	}

}
