package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItem_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import java.util.List;

public class ClassificationSchemeItemTranslator extends
		AbstractTranslatorTemplate {

	@Override
	protected CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		List<ClassificationSchemeItem_caDSR11179> csiList = iso11179Elements.getClassSchemeItems();
		for (ClassificationSchemeItem_caDSR11179 isoCSI: csiList) {
			ClassificationSchemeItem csi = getCSI(isoCSI);
			
			objRegistry.addClassificationSchemeItem(isoCSI.getTagId(), csi);
		}
		
		return objRegistry;
	}
	
	private ClassificationSchemeItem getCSI(ClassificationSchemeItem_caDSR11179 isoCSI) {
		ClassificationSchemeItem csi = DomainObjectFactory.newClassificationSchemeItem();
		String publicId = util.getIdentifier(isoCSI);
		csi.setPublicId(publicId);
		String isoCSIValue = isoCSI.getCsiValue();
		csi.setPreferredName(publicId);
		csi.setLongName(isoCSIValue);

		return csi;
	}

}
