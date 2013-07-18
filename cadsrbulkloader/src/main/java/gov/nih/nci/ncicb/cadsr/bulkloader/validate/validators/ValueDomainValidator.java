/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.validate.validators;

import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationError;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItem;
import gov.nih.nci.ncicb.cadsr.loader.validator.ValidationItems;

import java.util.ArrayList;
import java.util.List;

public class ValueDomainValidator extends AbstractValidator {
	
	@Override
	public ValidationItems validate() {
		ValueDomain vd = DomainObjectFactory.newValueDomain();
		List<ValueDomain> valueDomains = elementsList.getElements(vd);
		for (ValueDomain valueDomain: valueDomains) {
			validateId(valueDomain);
			validateDefinitionLength(valueDomain);
			validateRetiredValueDomain(valueDomain);
			validatePVs(valueDomain);
		}
		
		return validationItems;
	}
	
	private void validateId(ValueDomain valueDomain) {
		String publicId = valueDomain.getPublicId();
		Float version = valueDomain.getVersion();
		
		if (publicId != null && version != null) {
			ValueDomain vdGot = dao.findValueDomainById(Integer.parseInt(publicId), new Double(version).doubleValue());
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
		if (valueDomain != null 
				&& valueDomain.getPreferredName() != null
				&& !valueDomain.getPreferredName().trim().equals("")) {
			
			ValueDomain searchVD = getSearchAC(valueDomain, DomainObjectFactory.newValueDomain());
			
			List<ValueDomain> foundVDs = dao.findValueDomains(searchVD);
			
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
	
	private void validatePVs(ValueDomain valueDomain) {
		List<PermissibleValue> pvs = valueDomain.getPermissibleValues();
		List<String> pvValueCache = new ArrayList<String>();
		for (PermissibleValue pv: pvs) {
			String pvValueString = getPVValueString(pv);
			
			if (pvValueCache.contains(pvValueString)) {
				ValidationItem error = new ValidationError("The Value Domain to be created ["+valueDomain.getLongName()+"] has duplicate permissible values ["+pv.getValue()+"]", valueDomain);
				validationItems.addItem(error);
			}
			else {
				pvValueCache.add(pvValueString);
			}
		}
	}
	
	private String getPVValueString(PermissibleValue pv) {
		StringBuffer sb = new StringBuffer();
		sb.append(pv.getValue());
		ValueMeaning vm = pv.getValueMeaning();
		if (vm != null) {
			sb.append(":");
			sb.append(vm.getLongName());
		}
		
		return sb.toString();
	}

}
