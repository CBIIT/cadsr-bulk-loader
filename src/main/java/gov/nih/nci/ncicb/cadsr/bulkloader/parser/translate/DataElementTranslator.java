package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItemRef_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElement_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
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
		
		String publicId = util.getIdentifier(isoDE);
		Float version = util.getIdVersion(isoDE);
		de.setPublicId(publicId);
		de.setVersion(version);
		
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIList = getAdminComponentCSCSI(isoDE, objRegistry);
		de.setAcCsCsis(acCSCSIList);
		
		return de;
	}
	
	private String getDELongName(DataElementConcept dec, ValueDomain vd) {
		String decLongName = util.getDECLongName(dec);
		String vdLongName = util.getVDLongName(vd);
		
		return decLongName+" "+vdLongName;
	}
	
	private List<AdminComponentClassSchemeClassSchemeItem> getAdminComponentCSCSI(DataElement_ISO11179 isoDE, CaDSRObjectRegistry objRegistry) {
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIList = new ArrayList<AdminComponentClassSchemeClassSchemeItem>();
		List<ClassificationSchemeItemRef_ISO11179> isoCSIRefs = isoDE.getClassifiedBy();
		for (ClassificationSchemeItemRef_ISO11179 isoCSIRef: isoCSIRefs) {
			String isoCSRefId = isoCSIRef.getCsRefId();
			String isoCSIRefId = isoCSIRef.getCsiRefId();
			
			ClassificationScheme cs = objRegistry.getClassificationScheme(isoCSRefId);
			ClassificationSchemeItem csi = objRegistry.getClassificationSchemeItem(isoCSIRefId);
			
			ClassSchemeClassSchemeItem csCSI = DomainObjectFactory.newClassSchemeClassSchemeItem();
			csCSI.setCs(cs);
			csCSI.setCsi(csi);
			csCSI.setId("4E5E03B0-CEA6-202B-E044-0003BA3F9857");
			
			AdminComponentClassSchemeClassSchemeItem acCSCSI = DomainObjectFactory.newAdminComponentClassSchemeClassSchemeItem();
			acCSCSI.setCsCsi(csCSI);
			
			acCSCSIList.add(acCSCSI);
		}
		
		return acCSCSIList;
	}

}
