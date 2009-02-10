package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElement_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.List;

public class DataElementTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<DataElement_ISO11179> isoDEs = iso11179Elements.getDataElements().getDataElements();
		
		for (DataElement_ISO11179 isoDE: isoDEs) {
			DataElement de = getDataElement(isoDE, objRegistry);
			objRegistry.addDataElement(isoDE.getTagId(), de);
		}
		
		return objRegistry;
	}
	
	private DataElement getDataElement(DataElement_ISO11179 isoDE, CaDSRObjectRegistry objRegistry) {
		String decRefId = isoDE.getDecRefId();
		String vdRefId = isoDE.getVdRefId();
		
		DataElementConcept dec = objRegistry.getDataElementConcept(decRefId);
		ValueDomain vd = objRegistry.getValueDomain(vdRefId);
		
		DataElement de = DomainObjectFactory.newDataElement();
		de.setDataElementConcept(dec);
		de.setValueDomain(vd);
		de.setLongName(getDELongName(dec, vd));
		
		return de;
	}
	
	private String getDELongName(DataElementConcept dec, ValueDomain vd) {
		String decLongName = util.getDECLongName(dec);
		String vdLongName = util.getVDLongName(vd);
		
		return decLongName+" "+vdLongName;
	}

}
