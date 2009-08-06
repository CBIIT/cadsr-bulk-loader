package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.List;

public class DefinitionLengthValidator extends AbstractValidator {

	private static final int MAX_DEF_FIELD_SIZE = 2000;
	
	@Override
	public ValidationItems validate() {
		ValidationItems validationItems = ValidationItems.getInstance();
		
		validateOCs(validationItems);
		validateProps(validationItems);
		validateDEC(validationItems);
		validateVD(validationItems);
		validateDE(validationItems);
		
		return validationItems;
	}
	
	private ValidationItems validateOCs(ValidationItems validationItems) {
		List<ObjectClass> objectClasses = elementsList.getElements(DomainObjectFactory.newObjectClass());
		for (ObjectClass objClass: objectClasses) {
			if (objClass.getPublicId() == null) {
				String objDef = objClass.getPreferredDefinition();
				if (objDef!=null && objDef.length() > MAX_DEF_FIELD_SIZE) {
					ValidationError error = new ValidationError("Length of Object Class ("+objClass.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", objClass);
					validationItems.addItem(error);
				}
			}
		}
		
		return validationItems;
	}
	
	private ValidationItems validateProps(ValidationItems validationItems) {
		List<Property> props = elementsList.getElements(DomainObjectFactory.newProperty());
		for (Property prop: props) {
			if (prop.getPublicId() == null) {
				String propDef = prop.getPreferredDefinition();
				if (propDef != null && propDef.length() > MAX_DEF_FIELD_SIZE) {
					ValidationError error = new ValidationError("Length of Property ("+prop.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", prop);
					validationItems.addItem(error);
				}
			}
		}
		
		return validationItems;
	}
	
	private ValidationItems validateDEC(ValidationItems validationItems) {
		List<DataElementConcept> decs = elementsList.getElements(DomainObjectFactory.newDataElementConcept());
		for (DataElementConcept dec: decs) {
			if (dec.getPublicId() == null) {
				String ocDef = dec.getObjectClass().getPreferredDefinition();
				String propDef = dec.getProperty().getPreferredDefinition();
				if (ocDef == null) ocDef = "";
				if (propDef == null) propDef = "";
				
				String decDef = ocDef+" "+propDef;
				if (decDef.length() > MAX_DEF_FIELD_SIZE) {
					ValidationError error = new ValidationError("Length of DEC ("+dec.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", dec);
					validationItems.addItem(error);
				}
			}
		}
		return validationItems;
	}
	
	private ValidationItems validateVD(ValidationItems validationItems) {
		List<ValueDomain> vds = elementsList.getElements(DomainObjectFactory.newValueDomain());
		for (ValueDomain vd: vds) {
			if (vd.getPublicId() == null) {
				String vdPrefDef = vd.getPreferredDefinition();
				if (vdPrefDef != null && vdPrefDef.length() > MAX_DEF_FIELD_SIZE) {
					ValidationError error = new ValidationError("Length of VD ("+vd.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", vd);
					validationItems.addItem(error);
				}
			}
		}
		return validationItems;
	}
	
	private ValidationItems validateDE(ValidationItems validationItems) {
		List<DataElement> des = elementsList.getElements(DomainObjectFactory.newDataElement());
		for (DataElement de: des) {
			if (de.getPublicId() == null) {
				String decDef = de.getDataElementConcept().getPreferredDefinition();
				String vdDef = de.getValueDomain().getPreferredDefinition();
				if (decDef == null) decDef = "";
				if (vdDef == null) vdDef = "";
				
				String deDef = decDef+" "+vdDef;
				if (deDef.length() > MAX_DEF_FIELD_SIZE) {
					ValidationError error = new ValidationError("Length of DE ("+de.getLongName()+") definition exceeds the max allowed length of ["+MAX_DEF_FIELD_SIZE+"] characters", de);
					validationItems.addItem(error);
				}
			}
		}
		return validationItems;
	}

}
