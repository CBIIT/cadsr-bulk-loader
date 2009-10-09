package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Datatype_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.EnumeratedValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.PermissibleValue_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.RepresentationClass_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
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
		if (vdLongName == null) vdLongName = "";
		int maxLength = isoVD.getMaxCharacters();
		
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
		if (maxLength > 0) {
			vd.setMaximumLength(maxLength);
		}
		
		String id = util.getIdentifier(isoVD);
		Float version = util.getIdVersion(isoVD);
		
		if (id == null) {
			RepresentationClass_caDSR11179 repTerm = isoVD.getRepresentationClass();
			
			ConceptDerivationRule_caDSR11179 repTermCDR = repTerm.getConceptDerivationRule();
			ConceptDerivationRule cdr = util.getConceptDerivationRule(repTermCDR, objRegistry);
			String longName = util.getLongName(cdr);
			String definition = util.getDefinition(cdr);
			
			List<String> conceptRefs = util.getConceptReferences(repTermCDR);
			List<Concept> concepts = util.getConceptsFromRegistry(conceptRefs, objRegistry);
			String preferredName = util.getPreferredNameFromConcepts(concepts);
			
			Representation rep = DomainObjectFactory.newRepresentation();
			rep.setLongName(longName);
			rep.setPreferredName(preferredName);
			
			vd.setLongName(longName);
			vd.setPreferredDefinition(definition);
			vd.setRepresentation(rep);
		}
		else {
			vd.setPublicId(id);
			vd.setVersion(version);
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
		PermissibleValue pv = DomainObjectFactory.newPermissibleValue();
		String pvValue = isoPV.getValue();
		pv.setValue(pvValue);
		
		String vmRefId = isoPV.getValueMeaningRefId();
		ValueMeaning valueMeaning = getValueMeaning(vmRefId, objRegistry);
		valueMeaning.setLongName(pvValue);
		
		pv.setValueMeaning(valueMeaning);
		
		return pv;
	}
	
	private ValueMeaning getValueMeaning(String vmRefId, CaDSRObjectRegistry objRegistry) {
		ValueMeaning valueMeaning = null;
		
		if (vmRefId != null && !vmRefId.trim().equals("")) {
			valueMeaning = objRegistry.getValueMeaning(vmRefId);			
		}
		else {
			valueMeaning = CaDSRObjectsUtil.createValueMeaning();
		}
		
		return valueMeaning;
	}
	

}
