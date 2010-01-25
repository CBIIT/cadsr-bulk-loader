package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConceptList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConcept_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;

import java.util.List;

public class DataElementConceptTranslator extends AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		DataElementConceptList_ISO11179 isoDECList = iso11179Elements.getDataElementConcepts();
		List<DataElementConcept_ISO11179> isoDECList2 = isoDECList.getDataElementConcepts();
		for (DataElementConcept_ISO11179 isoDEC: isoDECList2) {
			DataElementConcept dec = getDEC(isoDEC, objRegistry);
			objRegistry.addDataElementConcept(isoDEC.getTagId(), dec);
		}
		
		return objRegistry;
	}
	
	private DataElementConcept getDEC(DataElementConcept_ISO11179 isoDEC, CaDSRObjectRegistry objRegistry) {
		String objRefId = isoDEC.getObjectClassRefId();
		String propRefId = isoDEC.getPropertyRefId();
		
		ObjectClass obj = objRegistry.getObjectClass(objRefId);
		Property prop = objRegistry.getProperty(propRefId);
		
		DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
		dec.setObjectClass(obj);
		dec.setProperty(prop);
		
		String longName = util.getDECLongName(dec);
		String publicId = util.getIdentifier(isoDEC);
		Float version = util.getIdVersion(isoDEC);
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIList = util.getAdminComponentCSCSI(isoDEC, objRegistry);
		
		dec.setLongName(longName);
		dec.setPublicId(publicId);
		dec.setVersion(version);
		dec.setAcCsCsis(acCSCSIList);
		dec.setOrigin(util.getOrigin(isoDEC));
		
		return dec;
	}

}
