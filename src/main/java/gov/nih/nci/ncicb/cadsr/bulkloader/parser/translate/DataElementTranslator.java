package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItemRef_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElement_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Designation_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.LanguageSection_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ReferenceDocument_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.TerminologicalEntry_ISO11179;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ReferenceDocument;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.List;

public class DataElementTranslator extends AbstractTranslatorTemplate {

	private static final int DOC_NAME_COL_LENGTH = 255;
	
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
		
		addAlternateNames(isoDE, de);
		addReferenceDocs(isoDE, de);
		
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
	
	private void addAlternateNames(DataElement_ISO11179 isoDE, DataElement de) {
		List<TerminologicalEntry_ISO11179> isoTermEntries = isoDE.getHaving();
		for (TerminologicalEntry_ISO11179 termEntry: isoTermEntries) {
			List<LanguageSection_ISO11179> langSections = termEntry.getContainingEntries();
			for (LanguageSection_ISO11179 langSection: langSections) {
				List<Designation_ISO11179> designations = langSection.getNamingEntries();
				for (Designation_ISO11179 designation: designations) {
					String name = designation.getName();
					String type = designation.getType();
					
					AlternateName altName = DomainObjectFactory.newAlternateName();
					altName.setName(name);
					altName.setType(type);
					
					de.addAlternateName(altName);
				}
			}
		}
	}
	
	private void addReferenceDocs(DataElement_ISO11179 isoDE, DataElement de) {
		List<ReferenceDocument_ISO11179> isoRefDocs = isoDE.getDescribedBy();
		if (isoRefDocs != null) {
			List<ReferenceDocument> refDocs = new ArrayList<ReferenceDocument>(isoRefDocs.size());
			for (ReferenceDocument_ISO11179 isoRefDoc: isoRefDocs) {
				ReferenceDocument refDoc = DomainObjectFactory.newReferenceDocument();
				String docText = isoRefDoc.getTitle();
				String docName = docText;
				
				if (docName.length() > DOC_NAME_COL_LENGTH) { //truncate length of 'name' to size of column
					docName = docName.substring(0, DOC_NAME_COL_LENGTH);
				}
				
				refDoc.setType(isoRefDoc.getDescription());
				refDoc.setText(docText);
				refDoc.setName(docName);
				
				refDocs.add(refDoc);
			}
			
			de.setReferenceDocuments(refDocs);
		}
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
			
			AdminComponentClassSchemeClassSchemeItem acCSCSI = DomainObjectFactory.newAdminComponentClassSchemeClassSchemeItem();
			acCSCSI.setCsCsi(csCSI);
			
			acCSCSIList.add(acCSCSI);
		}
		
		return acCSCSIList;
	}

}
