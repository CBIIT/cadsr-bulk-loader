package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Datatype_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.EnumeratedValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.PermissibleValue_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
import java.util.List;

public class ValueDomainTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<ValueDomain_caDSR11179> isoVDs = iso11179Elements.getValueDomains();
		for (ValueDomain_caDSR11179 isoVD: isoVDs) {
			ValueDomain vd = getValueDomain(isoVD, objRegistry);
			objRegistry.addValueDomain(isoVD.getTagId(), vd);
		}
		
		return objRegistry;
	}
	
	public ValueDomain getValueDomain(ValueDomain_caDSR11179 isoVD, CaDSRObjectRegistry objRegistry) {
		ValueDomain vd = DomainObjectFactory.newValueDomain();
		
		List<PermissibleValue_ISO11179> isoPVs = isoVD.getPermissibleValues();
		List<PermissibleValue> pvs = getPermissibleValues(isoPVs, objRegistry);
		String cdRefId = isoVD.getConceptualDomainRefId();
		ConceptualDomain conceptDomain = objRegistry.getConceptualDomain(cdRefId);
		String vdLongName = util.getPreferredQuestionText(isoVD);
		
		String enumerated = "N";
		if (isoVD instanceof  EnumeratedValueDomain_caDSR11179) {
			enumerated = "E";
		}
		String dataType = "CHARACTER";
		
		Datatype_ISO11179 isoDataType = isoVD.getDatatype();
		if (isoDataType != null) {
			String name = isoDataType.getName();
			if (name != null) {
				dataType = name.toUpperCase();
			}
		}
		
		vd.setPermissibleValues(pvs);
		vd.setConceptualDomain(conceptDomain);
		vd.setLongName(vdLongName);
		vd.setPreferredDefinition(vdLongName);
		vd.setVdType(enumerated);
		vd.setDataType(dataType);
		
		String id = util.getIdentifier(isoVD);
		
		if (id == null) {
			ConceptDerivationRule_caDSR11179 isoCDR = isoVD.getConDerivationRule();
			ConceptDerivationRule cdr = util.getConceptDerivationRule(isoCDR, objRegistry);
			String longName = util.getLongName(cdr);
			String definition = util.getDefinition(cdr);
			Representation rep = DomainObjectFactory.newRepresentation();
			rep.setPreferredName(longName);
			
			vd.setLongName(longName);
			vd.setPreferredDefinition(definition);
			vd.setConceptDerivationRule(cdr);
			vd.setRepresentation(rep);
		}
		else {
			vd.setPublicId(id);
			vd.setVersion(1.0f);
		}
		
		return vd;
	}
	
	private List<PermissibleValue> getPermissibleValues(List<PermissibleValue_ISO11179> isoPVs, CaDSRObjectRegistry objRegistry) {
		List<PermissibleValue> pvs = new ArrayList<PermissibleValue>();
		if (isoPVs != null) {
			for (PermissibleValue_ISO11179 isoPV: isoPVs) {
				pvs.add(getPermissibleValue(isoPV, objRegistry));
			}
		}		
		
		return pvs;
	}
	
	private PermissibleValue getPermissibleValue(PermissibleValue_ISO11179 isoPV, CaDSRObjectRegistry objRegistry) {
		String vmRefId = isoPV.getValueMeaningRefId();
		ValueMeaning valueMeaning = objRegistry.getValueMeaning(vmRefId);
		
		PermissibleValue pv = DomainObjectFactory.newPermissibleValue();
		pv.setValueMeaning(valueMeaning);
		
		return pv;
	}
	
	

}
