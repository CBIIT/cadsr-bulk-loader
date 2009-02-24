package gov.nih.nci.ncicb.cadsr.bulkloader.transformer;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.AdminItem_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.AdminRecord_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItemList_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItemRef_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItem_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationScheme_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ComponentConceptList_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ComponentConcept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Concept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptualDomainList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptualDomainRef_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptualDomainRelationship_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptualDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Contact_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConceptList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConceptRef_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConceptRelationship_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementConcept_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElementList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.DataElement_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Datatype_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Definition_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Designation_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.EnumeratedValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ItemIdentifier_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.LanguageIdentification_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.LanguageSection_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.NonEnumeratedConceptualDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.NonEnumeratedValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ObjectClassList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ObjectClass_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Organization_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.PermissibleValue_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.PropertyList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Property_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ReferenceDocument_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Registrar_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.RegistrationAuthorityIdentifier_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.RegistrationAuthority_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Stewardship_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Submission_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.TerminologicalEntry_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueDomain_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueDomainsList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueMeaning_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ValueMeaningsList_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.DataEntry;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.DataEntryList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.exolab.castor.xml.XMLContext;

public class ISO11179Transformer implements Transformer {

	private static final String MAPPING_FILE = "gov/nih/nci/ncicb/cadsr/bulkloader/transformer/dataentry_mapping.xml";
	
	private final Map<String, Concept_caDSR11179> conceptsMap = new HashMap<String, Concept_caDSR11179>();
	private final Map<String, ObjectClass_caDSR11179> ocMap = new HashMap<String, ObjectClass_caDSR11179>();
	private final Map<String, Property_caDSR11179> propMap = new HashMap<String, Property_caDSR11179>();
	private final Map<String, ValueMeaning_caDSR11179> vmMap = new HashMap<String, ValueMeaning_caDSR11179>();
	private final Map<String, NonEnumeratedConceptualDomain_caDSR11179> cdMap = new HashMap<String, NonEnumeratedConceptualDomain_caDSR11179>();
	private final Map<String, DataElementConcept_ISO11179> decMap = new HashMap<String, DataElementConcept_ISO11179>();
	private final Map<String, NonEnumeratedValueDomain_caDSR11179> nonEnumVDMap = new HashMap<String, NonEnumeratedValueDomain_caDSR11179>();
	private final Map<String, EnumeratedValueDomain_caDSR11179> enumVDMap = new HashMap<String, EnumeratedValueDomain_caDSR11179>();
	private final Map<String, ClassificationScheme_ISO11179> csMap = new HashMap<String, ClassificationScheme_ISO11179>();
	private final Map<String, ClassificationSchemeItem_caDSR11179> csiMap = new HashMap<String, ClassificationSchemeItem_caDSR11179>();
	private final Map<String, DataElement_ISO11179> deMap = new HashMap<String, DataElement_ISO11179>();
	
	public File transform(File inputFile) {
		try {
			ClassLoader cl = ISO11179Transformer.class.getClassLoader();
			URL url = cl.getResource(MAPPING_FILE);
			
			Mapping mapping = new Mapping();
			mapping.loadMapping(url);
			
			XMLContext context = new XMLContext();
			context.addMapping(mapping);
			
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setClass(DataEntryList.class);
			
			Reader ipFileReader = new FileReader(inputFile);
			DataEntryList dataEntryList = (DataEntryList)unmarshaller.unmarshal(ipFileReader);
			ISO11179Elements isoElements = processDataEntryList(dataEntryList);
			marshall(isoElements);
			
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private void marshall(ISO11179Elements isoElements) {
		try {
			ClassLoader cl = ISO11179Transformer.class.getClassLoader();
			URL url = cl.getResource("gov/nih/nci/ncicb/cadsr/bulkloader/parser/mapping.xml");
			
			Mapping mapping = new Mapping();
			mapping.loadMapping(url);
			
			Writer writer = new FileWriter(new File("c:\\docume~1\\mathura2\\desktop\\11179sample.xml"));
			
			Marshaller marshaller = new Marshaller();
			marshaller.setMapping(mapping);
			//marshaller.setRootElement("Administered_Item_List");
			marshaller.setNamespaceMapping("iso11179", "http://www.cancergrid.org/schema/ISO11179");
			marshaller.setNamespaceMapping("caDSR", "http://www.ncicb.nih.gov/caDSR/schema/ISO11179");
			marshaller.setSchemaLocation("http://www.cancergrid.org/schema/ISO11179 file:/C:/work/code/cadsrbulkloader/src/java/gov/nih/nci/ncicb/cadsr/bulkloader/schema/iso11179_updated.xsd " +
											"http://www.ncicb.nih.gov/caDSR/schema/ISO11179 file:/C:/work/code/cadsrbulkloader/src/java/gov/nih/nci/ncicb/cadsr/bulkloader/schema/cadsr_iso11179.xsd");
			//marshaller.setNoNamespaceSchemaLocation("http://www.ncicb.nih.gov/caDSR/schema/ISO11179 C:/work/code/cadsrbulkloader/src/java/gov/nih/nci/ncicb/cadsr/bulkloader/schema/cadsr_iso11179.xsd");
			//marshaller.setSuppressNamespaces(true);
			//marshaller.setSchemaLocation("http://www.ncicb.nih.gov/caDSR/schema/ISO11179 ../../../../../../../../../java/gov/nih/nci/ncicb/cadsr/bulkloader/schema/cadsr_iso11179.xsd");			
			
			marshaller.setWriter(writer);
			marshaller.marshal(isoElements);
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private ISO11179Elements processDataEntryList(DataEntryList dataEntryList) {
		ISO11179Elements isoElements = new ISO11179Elements();
		List<DataEntry> dataEntries = dataEntryList.getDataEntries();
		
		DataEntryListIterator dataEntryIter = new DataEntryListIterator(dataEntries);
		while (dataEntryIter.hasNext()) {
			List<DataEntry> oneRecord = dataEntryIter.next();
			getDE(oneRecord);
		}
		
		List<Concept_caDSR11179> isoConcepts = new ArrayList<Concept_caDSR11179>();
		List<ObjectClass_caDSR11179> isoOCs = new ArrayList<ObjectClass_caDSR11179>();
		List<Property_caDSR11179> isoProps = new ArrayList<Property_caDSR11179>();
		List<ValueMeaning_caDSR11179> isoVMs = new ArrayList<ValueMeaning_caDSR11179>();
		List<NonEnumeratedConceptualDomain_caDSR11179> isoCDs = new ArrayList<NonEnumeratedConceptualDomain_caDSR11179>();
		List<DataElementConcept_ISO11179> isoDECs = new ArrayList<DataElementConcept_ISO11179>();
		List<ValueDomain_caDSR11179> isoEnumVDs = new ArrayList<ValueDomain_caDSR11179>();
		List<ValueDomain_caDSR11179> isoNonEnumVDs = new ArrayList<ValueDomain_caDSR11179>();
		List<DataElement_ISO11179> isoDEs = new ArrayList<DataElement_ISO11179>();
		List<ClassificationScheme_ISO11179> isoCSs = new ArrayList<ClassificationScheme_ISO11179>();
		List<ClassificationSchemeItem_caDSR11179> isoCSIs = new ArrayList<ClassificationSchemeItem_caDSR11179>();
		
		isoConcepts.addAll(conceptsMap.values());
		isoOCs.addAll(ocMap.values());
		isoProps.addAll(propMap.values());
		isoVMs.addAll(vmMap.values());
		isoCDs.addAll(cdMap.values());
		isoDECs.addAll(decMap.values());
		isoEnumVDs.addAll(enumVDMap.values());
		isoNonEnumVDs.addAll(nonEnumVDMap.values());
		isoDEs.addAll(deMap.values());
		isoCSs.addAll(csMap.values());
		isoCSIs.addAll(csiMap.values());
		
		ConceptList_ISO11179 isoConceptsList = new ConceptList_ISO11179();
		isoConceptsList.setConcepts(isoConcepts);
		
		ObjectClassList_ISO11179 isoOCList = new ObjectClassList_ISO11179();
		isoOCList.setObjectClasses(isoOCs);
		
		PropertyList_ISO11179 isoPropList = new PropertyList_ISO11179();
		isoPropList.setProperties(isoProps);
		
		ValueMeaningsList_ISO11179 isoVMList = new ValueMeaningsList_ISO11179();
		isoVMList.setValueMeanings(isoVMs);
		
		ConceptualDomainList_ISO11179 isoCDList = new ConceptualDomainList_ISO11179();
		isoCDList.setNonEnumCDs(isoCDs);
		
		DataElementConceptList_ISO11179 isoDECList = new DataElementConceptList_ISO11179();
		isoDECList.setDataElementConcepts(isoDECs);
		
		ValueDomainsList_ISO11179 isoVDList = new ValueDomainsList_ISO11179();
		isoVDList.setEnumVDs(isoEnumVDs);
		isoVDList.setNonEnumVDs(isoNonEnumVDs);
		
		DataElementList_ISO11179 isoDEList = new DataElementList_ISO11179();
		isoDEList.setDataElements(isoDEs);
		
		ClassificationSchemeList_ISO11179 csList = new ClassificationSchemeList_ISO11179();
		csList.setClassSchemes(isoCSs);
		
		ClassificationSchemeItemList_caDSR11179 csiList = new ClassificationSchemeItemList_caDSR11179();
		csiList.setClassSchemeItems(isoCSIs);
		
		isoElements.setConceptsList(isoConceptsList);
		isoElements.setObjectClassList(isoOCList);
		isoElements.setPropertiesList(isoPropList);
		isoElements.setValueMeaningsList(isoVMList);
		isoElements.setConceptualDomainsList(isoCDList);
		isoElements.setDataElementConcepts(isoDECList);
		isoElements.setValueDomainsList(isoVDList);
		isoElements.setDataElements(isoDEList);
		isoElements.setClassSchemeItemList(csiList);
		isoElements.setClassSchemeList(csList);
		
		return isoElements;
	}
	
	private List<Concept_caDSR11179> getConcepts (String conceptsStr) {
		List<Concept_caDSR11179> isoConcepts = new ArrayList<Concept_caDSR11179>();
		if (conceptsStr != null && conceptsStr.trim().length() > 0) {
			String[][] idAndNames = getIdAndName(conceptsStr);
			
			for (int i=0;i<idAndNames.length;i++) {
				Concept_caDSR11179 isoConcept = new Concept_caDSR11179();
				//fillupAdminItem(isoConcept);
				isoConcept.setSubmittedBy(getBlankSubmittedBy());
				
				String conceptId = idAndNames[i][0];
				String conceptName = idAndNames[i][1];
				
				isoConcept.setCode(conceptId);
				setNameDefAndSource(conceptName, conceptName, "NMDP", isoConcept);
				String randTagId = "CON-"+getRandomString();
				isoConcept.setTagId(randTagId);
				
				isoConcepts.add(isoConcept);
			}
		}
		
		return isoConcepts;
	}
	
	private String[][] getIdAndName(String str) {
		if (str == null) return new String[1][1];
		
		String[] arr = str.split(",");
		String[][] idAndNames = new String[arr.length][2];
		for (int i=0;i<arr.length;i++) {
			String[] strArr2 = arr[i].split("\\(");
			if (strArr2 != null) {
				idAndNames[i][0] = strArr2[0].trim();
				if (strArr2.length > 1) {
					String[] strArr3 = strArr2[1].split("\\)");
					if (strArr3 != null) {
						idAndNames[i][1] = strArr3[0].trim();
					}
				}
			}
		}
		return idAndNames;
	}
	
	/**
	 * @param dataEntries
	 * @return
	 */
	private DataElement_ISO11179 getDE(List<DataEntry> dataEntries) {
		if (dataEntries == null) {
			return new DataElement_ISO11179();
		}
		
		DataEntry mainDataEntry = dataEntries.get(0);
		String deId = mainDataEntry.getCdeId();
		if (deMap.containsKey(deId)) {
			return deMap.get(deId);
		}
		else {
			DataElement_ISO11179 isoDE = new DataElement_ISO11179();
			fillupAdminItem(isoDE);
			setItemId(isoDE, deId);
			
			DataElementConcept_ISO11179 isoDEC = getDEC(mainDataEntry);
			ValueDomain_caDSR11179 isoVD = getVD(dataEntries);
			
			ClassificationScheme_ISO11179 isoCS = getCS();
			ClassificationSchemeItem_caDSR11179 isoCSI = getCSI();
			ClassificationSchemeItemRef_ISO11179 isoCSIRef = new ClassificationSchemeItemRef_ISO11179();
			isoCSIRef.setCsRefId(isoCS.getTagId());
			isoCSIRef.setCsiRefId(isoCSI.getTagId());
			List<ClassificationSchemeItemRef_ISO11179> isoCSIRefs = new ArrayList<ClassificationSchemeItemRef_ISO11179>();
			isoCSIRefs.add(isoCSIRef);
			
			isoDE.setDecRefId(isoDEC.getTagId());
			isoDE.setVdRefId(isoVD.getTagId());
			isoDE.setClassifiedBy(isoCSIRefs);
			
			addNameAndDef(isoDE,mainDataEntry.getPreferredQuestionText(),"", true);
			addNameAndDef(isoDE,mainDataEntry.getAlternateQuestionText(),"", true);
			
			String tagId = "DE-"+getRandomString();
			isoDE.setTagId(tagId);
			
			deMap.put(deId, isoDE);
			
			return isoDE;
		}
	}
	
	private DataElementConcept_ISO11179 getDEC(DataEntry dataEntry) {
		String decId = dataEntry.getDecId();
		if (decId != null) decId = decId.trim();
		if (decMap.containsKey(decId)) {
			return decMap.get(decId);
		}
		else {
			DataElementConcept_ISO11179 isoDEC = new DataElementConcept_ISO11179();
			fillupAdminItem(isoDEC);
			setItemId(isoDEC, decId);
			
			List<Concept_caDSR11179> ocQualConcepts = getConcepts(dataEntry.getObjectClassQualifierConcepts());
			List<Concept_caDSR11179> ocPrimConcepts = getConcepts(dataEntry.getObjectClassPrimaryConcepts());
			List<Concept_caDSR11179> propQualConcepts = getConcepts(dataEntry.getPropertyQualifierConcepts());
			List<Concept_caDSR11179> propPrimConcepts = getConcepts(dataEntry.getPropertyPrimaryConcepts());
			
			List<Concept_caDSR11179> allOCConcepts = new ArrayList<Concept_caDSR11179>();
			List<Concept_caDSR11179> allPropConcepts = new ArrayList<Concept_caDSR11179>();
			
			allOCConcepts.addAll(ocQualConcepts);
			allOCConcepts.addAll(ocPrimConcepts);
			
			allPropConcepts.addAll(propQualConcepts);
			allPropConcepts.addAll(propPrimConcepts);
			
			ObjectClass_caDSR11179 isoOC = getObjectClassAndAddConcepts(allOCConcepts);
			Property_caDSR11179 isoProp = getPropertyAndAddConcepts(allPropConcepts);
			ConceptualDomain_caDSR11179 isoCD = getConceptualDomain(dataEntry.getDecConceptualDomain());
			
			isoDEC.setObjectClassRefId(isoOC.getTagId());
			isoDEC.setPropertyRefId(isoProp.getTagId());
			isoDEC.setConceptualDomainRefId(isoCD.getTagId());
			
			ClassificationScheme_ISO11179 isoCS = getCS();
			ClassificationSchemeItem_caDSR11179 isoCSI = getCSI();
			ClassificationSchemeItemRef_ISO11179 isoCSIRef = new ClassificationSchemeItemRef_ISO11179();
			isoCSIRef.setCsRefId(isoCS.getTagId());
			isoCSIRef.setCsiRefId(isoCSI.getTagId());
			List<ClassificationSchemeItemRef_ISO11179> isoCSIRefs = new ArrayList<ClassificationSchemeItemRef_ISO11179>();
			isoCSIRefs.add(isoCSIRef);
			
			isoDEC.setClassifiedBy(isoCSIRefs);
			
			String tagId = "DEC-"+getRandomString();
			
			DataElementConceptRelationship_ISO11179 decRel = new DataElementConceptRelationship_ISO11179();
			decRel.setRelationshipTypeDescription("");
			
			DataElementConceptRef_ISO11179 deRef = new DataElementConceptRef_ISO11179();
			deRef.setDataElementConceptRefId(tagId);
			List<DataElementConceptRef_ISO11179> deRefs = new ArrayList<DataElementConceptRef_ISO11179>();
			deRefs.add(deRef);
			
			decRel.setRelatedTo(deRefs);
			
			isoDEC.setDecRelationship(decRel);
			
			isoDEC.setTagId(tagId);
			
			decMap.put(decId, isoDEC);
			
			return isoDEC;
		}
	}
	
	private ValueDomain_caDSR11179 getVD(List<DataEntry> dataEntriesSubList) {
		if (dataEntriesSubList == null) {
			return new NonEnumeratedValueDomain_caDSR11179();
		}
		
		DataEntry mainDataEntry = dataEntriesSubList.get(0);
		String vdId = mainDataEntry.getVdId();
		ValueDomain_caDSR11179 isoVD = null;
		
		String enumerated = mainDataEntry.getEnumerated();
		if (enumerated != null && enumerated.trim().equalsIgnoreCase("yes")) {
			if (enumVDMap.containsKey(vdId)) {
				return enumVDMap.get(vdId);
			}
			
			EnumeratedValueDomain_caDSR11179 isoEnumVD = new EnumeratedValueDomain_caDSR11179();
			//fillupAdminItem(isoEnumVD);
			isoEnumVD.setAdminRecord(getBlankAdminRecord());
			setItemId(isoEnumVD, vdId);
			
			List<PermissibleValue_ISO11179> isoPVList = new ArrayList<PermissibleValue_ISO11179>();
			
			for (DataEntry dataEntry: dataEntriesSubList) {
				PermissibleValue_ISO11179 isoPV = getPV(dataEntry);
				isoPVList.add(isoPV);
			}
			isoEnumVD.setPermissibleValues(isoPVList);
			
			enumVDMap.put(vdId, isoEnumVD);
			
			isoVD = isoEnumVD;
		}
		else {
			if (nonEnumVDMap.containsKey(vdId)) {
				return nonEnumVDMap.get(vdId);
			}
			
			NonEnumeratedValueDomain_caDSR11179 isoNonEnumVD = new NonEnumeratedValueDomain_caDSR11179();
			isoNonEnumVD.setAdminRecord(getBlankAdminRecord());
			setItemId(isoNonEnumVD, vdId);
			
			isoNonEnumVD.setDescription("");
			nonEnumVDMap.put(vdId, isoNonEnumVD);
			
			isoVD = isoNonEnumVD;
		}
		
		List<Concept_caDSR11179> vdQualConcepts = getConcepts(mainDataEntry.getVdAttributeQualifierConcepts());
		List<Concept_caDSR11179> vdPrimConcepts = getConcepts(mainDataEntry.getVdAttributePrimaryConcepts());
		
		List<Concept_caDSR11179> vdConcepts = new ArrayList<Concept_caDSR11179>();
		vdConcepts.addAll(vdQualConcepts);
		vdConcepts.addAll(vdPrimConcepts);
		
		isoVD.setConDerivationRule(getCDRAndAddConcepts(vdConcepts));
		
		Datatype_ISO11179 isoDatatype = new Datatype_ISO11179();
		isoDatatype.setName(mainDataEntry.getDataType());
		isoDatatype.setSchemeReference("");
		isoVD.setDatatype(isoDatatype);
		
		ConceptualDomain_caDSR11179 isoCD = getConceptualDomain(mainDataEntry.getVdConceptualDomain());
		if (isoCD != null) {
			isoVD.setConceptualDomainRefId(isoCD.getTagId());
		}
		String tagId = "VD-"+getRandomString();
		isoVD.setTagId(tagId);
		
		return isoVD;
	}
	
	private PermissibleValue_ISO11179 getPV(DataEntry dataEntry) {
		String pvStr = dataEntry.getAgnisPermissibleValue();
		if (pvStr != null) pvStr = pvStr.trim();
		PermissibleValue_ISO11179 isoPV = new PermissibleValue_ISO11179();
		isoPV.setValue(pvStr);
		isoPV.setBeginDate(new Date());
		
		String vmConIdStr = dataEntry.getVmConceptIds();
		List<Concept_caDSR11179> vmConcepts = getConcepts(vmConIdStr);
		ValueMeaning_caDSR11179 isoVM = getValueMeaning(vmConcepts);
		if (isoVM != null) {
			isoPV.setValueMeaningRefId(isoVM.getTagId());
		}
		
		return isoPV;
	}
	
	private ObjectClass_caDSR11179 getObjectClassAndAddConcepts(List<Concept_caDSR11179> isoConcepts) {
		String codesConcat = getConceptConcatenation(isoConcepts);
		if (ocMap.containsKey(codesConcat)) {
			return ocMap.get(codesConcat);
		}
		else {
			ObjectClass_caDSR11179 isoObjClass = new ObjectClass_caDSR11179();
			//fillupAdminItem(isoObjClass);
			isoObjClass.setConceptDerivationRule(getCDRAndAddConcepts(isoConcepts));
			String tagId = "OC-"+getRandomString();
			isoObjClass.setTagId(tagId);
			
			ocMap.put(codesConcat, isoObjClass);
			
			return isoObjClass;
		}
		
	}
	
	private Property_caDSR11179 getPropertyAndAddConcepts(List<Concept_caDSR11179> isoConcepts) {
		String codesConcat = getConceptConcatenation(isoConcepts);
		if (propMap.containsKey(codesConcat)) {
			return propMap.get(codesConcat);
		}
		else {
			Property_caDSR11179 isoProp = new Property_caDSR11179();
			//fillupAdminItem(isoProp);
			isoProp.setConceptDerivationRule(getCDRAndAddConcepts(isoConcepts));
			String tagId = "Prop-"+getRandomString();
			isoProp.setTagId(tagId);
			
			propMap.put(codesConcat, isoProp);
			
			return isoProp;
		}
	}
	
	private ValueMeaning_caDSR11179 getValueMeaning(List<Concept_caDSR11179> isoConcepts) {
		String codesConcat = getConceptConcatenation(isoConcepts);
		if (vmMap.containsKey(codesConcat)) {
			return vmMap.get(codesConcat);
		}
		else {
			ValueMeaning_caDSR11179 isoVM = new ValueMeaning_caDSR11179();
			//fillupAdminItem(isoVM);
			isoVM.setConceptDerivationRule(getCDRAndAddConcepts(isoConcepts));
			isoVM.setBeginDate(new Date());
			String tagId = "VM-"+getRandomString();
			isoVM.setTagId(tagId);
			isoVM.setId("");
			
			vmMap.put(codesConcat, isoVM);
			
			return isoVM;
		}
	}
	
	private ConceptualDomain_caDSR11179 getConceptualDomain(String conDomainStr) {
		NonEnumeratedConceptualDomain_caDSR11179 isoCD = new NonEnumeratedConceptualDomain_caDSR11179();
		//fillupAdminItem(isoCD);
		isoCD.setAdminRecord(getBlankAdminRecord());
		String[][] idAndName = getIdAndName(conDomainStr);
		if (idAndName != null) {
			String id = idAndName[0][0];
			if (cdMap.containsKey(id)) {
				return cdMap.get(id);
			}
			setItemId(isoCD, idAndName[0][0]);
			String tagId = "CD-"+getRandomString();
			isoCD.setTagId(tagId);
			isoCD.setDescription("");
			
			ConceptualDomainRelationship_ISO11179 cdRel = new ConceptualDomainRelationship_ISO11179();
			cdRel.setRelationshipTypeDescription("");
			
			ConceptualDomainRef_ISO11179 cdRef = new ConceptualDomainRef_ISO11179();
			cdRef.setConceptualDomainTagIdRef(tagId);
			List<ConceptualDomainRef_ISO11179> cdRefList = new ArrayList<ConceptualDomainRef_ISO11179>();
			cdRefList.add(cdRef);
			cdRel.setRelatedTo(cdRefList);
			
			isoCD.setRelatedTo(cdRel);
			
			cdMap.put(id, isoCD);
		}
		
		return isoCD;
	}
	
	private ClassificationScheme_ISO11179 getCS() {
		String csId = "2695319";
		if (csMap.containsKey(csId)) {
			return csMap.get(csId);
		}
		else {
			ClassificationScheme_ISO11179 isoCS = new ClassificationScheme_ISO11179();
			fillupAdminItem(isoCS);
			isoCS.setTypeName("");
			isoCS.setName("NMDP: CDEs to be Reviewed");
			setItemId(isoCS, csId);
			
			String tagId = "CS-"+getRandomString();
			isoCS.setTagId(tagId);

			ClassificationSchemeItem_caDSR11179 isoCSI = getCSI();
			List<ClassificationSchemeItemRef_ISO11179> isoCSIs = new ArrayList<ClassificationSchemeItemRef_ISO11179>();
			ClassificationSchemeItemRef_ISO11179 csiRef = new ClassificationSchemeItemRef_ISO11179();
			csiRef.setCsiRefId(isoCSI.getTagId());
			csiRef.setCsRefId(tagId);
			
			isoCSIs.add(csiRef);
			isoCS.setContaining(isoCSIs);
			
			csMap.put(csId, isoCS);
			
			return isoCS;
		}
	}
	
	private ClassificationSchemeItem_caDSR11179 getCSI() {
		String csiId = "2813015";
		if (csiMap.containsKey(csiId)) {
			return csiMap.get(csiId);
		}
		else {
			ClassificationSchemeItem_caDSR11179 isoCSI = new ClassificationSchemeItem_caDSR11179();
			fillupAdminItem(isoCSI);
			isoCSI.setCsiValue("2100:");
			//isoCSI.setCsiName("2100:");
			setItemId(isoCSI, csiId);
			
			String tagId = "CSI-"+getRandomString();
			isoCSI.setTagId(tagId);
			
			csiMap.put(csiId, isoCSI);
			
			return isoCSI;
		}
	}
	
	private ConceptDerivationRule_caDSR11179 getCDRAndAddConcepts(List<Concept_caDSR11179> isoConcepts) {
		ConceptDerivationRule_caDSR11179 isoCDR = new ConceptDerivationRule_caDSR11179();
		ComponentConceptList_caDSR11179 isoCompConList = new ComponentConceptList_caDSR11179();
		List<ComponentConcept_caDSR11179> isoCompConcepts = new ArrayList<ComponentConcept_caDSR11179>();
		int i=1;
		for (Concept_caDSR11179 isoConcept: isoConcepts) {
			ComponentConcept_caDSR11179 isoCompConcept = new ComponentConcept_caDSR11179();
			String code = isoConcept.getCode();
			String tagId = "";
			if (conceptsMap.containsKey(code)) {
				tagId = conceptsMap.get(code).getTagId();
			}
			else {
				conceptsMap.put(code, isoConcept);
				tagId = isoConcept.getTagId();
			}
			
			isoCompConcept.setConceptRefId(tagId);
			isoCompConcept.setOrder(i);
			
			isoCompConcepts.add(isoCompConcept);
			i++;
		}
		
		isoCompConList.setComponentConcepts(isoCompConcepts);
		isoCDR.setComponentConceptsList(isoCompConList);
		
		TerminologicalEntry_ISO11179 isoTermEntry = getBlankTermEntry();
		List<TerminologicalEntry_ISO11179> isoTermEntries = new ArrayList<TerminologicalEntry_ISO11179>();
		isoTermEntries.add(isoTermEntry);
		isoCDR.setHaving(isoTermEntries);
		isoCDR.setSpecification("");
		
		return isoCDR;
	}
	
	private String getConceptConcatenation(List<Concept_caDSR11179> isoConcepts) {
		StringBuffer sb = new StringBuffer();
		for (Concept_caDSR11179 isoConcept: isoConcepts) {
			String code = isoConcept.getCode();
			if (sb.length() > 0) {
				sb.append(":"+code);
			}
			else {
				sb.append(code);
			}
		}
		
		return sb.toString();
	}
	
	private void fillupAdminItem(AdminItem_ISO11179 isoAdminItem) {
	
		isoAdminItem.setAdminRecord(getBlankAdminRecord());
		isoAdminItem.setAdministeredBy(getBlankAdministeredBy());
		
		isoAdminItem.setClassifiedBy(new ArrayList<ClassificationSchemeItemRef_ISO11179>());
		isoAdminItem.setDescribedBy(new ArrayList<ReferenceDocument_ISO11179>());
		isoAdminItem.setHaving(new ArrayList<TerminologicalEntry_ISO11179>());
		
		isoAdminItem.setRegisteredBy(getBlankRegisteredBy());
		isoAdminItem.setHaving(getBlankHaving());
		
		/*Submission_ISO11179 isoSubmission = new Submission_ISO11179();
		isoSubmission.setContact(isoContact);
		isoSubmission.setOrganization(isoOrg);
		isoAdminItem.setSubmittedBy(isoSubmission);
		*/
	}
	
	private AdminRecord_ISO11179 getBlankAdminRecord() {
		AdminRecord_ISO11179 isoAdminRecord = new AdminRecord_ISO11179();
		
		ItemIdentifier_ISO11179 isoItemId = new ItemIdentifier_ISO11179();
		RegistrationAuthorityIdentifier_ISO11179 isoRegId = new RegistrationAuthorityIdentifier_ISO11179();
		isoRegId.setInternationalCodeDesignator("");
		isoItemId.setItemRegistrationAuthorityIdentifier(isoRegId);
		
		isoAdminRecord.setIdentifier(isoItemId);
		isoAdminRecord.setCreationDate(new Date());
		isoAdminRecord.setAdministrativeStatus("");
		isoAdminRecord.setRegistrationStatus("");
		isoAdminRecord.setCreationDate(new Date());
		
		return isoAdminRecord;
	}
	
	private Stewardship_ISO11179 getBlankAdministeredBy() {
		Stewardship_ISO11179 isoStewardship = new Stewardship_ISO11179();
		isoStewardship.setOrganization(getBlankOrganization());
		isoStewardship.setContact(getBlankContact());
		
		return isoStewardship;
	}
	
	private Contact_ISO11179 getBlankContact() {
		Contact_ISO11179 isoContact = new Contact_ISO11179();
		isoContact.setInformation("");
		isoContact.setName("");
		
		return isoContact;
	}
	
	private Organization_ISO11179 getBlankOrganization() {
		Organization_ISO11179 isoOrg = new Organization_ISO11179();
		isoOrg.setName("");

		return isoOrg;
	}
	
	private RegistrationAuthority_ISO11179 getBlankRegisteredBy() {
		RegistrationAuthority_ISO11179 isoRegAuth = new RegistrationAuthority_ISO11179();
		isoRegAuth.setDocumentationLanguageIdentifier(new ArrayList<LanguageIdentification_ISO11179>());
		isoRegAuth.setRepresentedBy(new ArrayList<Registrar_ISO11179>());
		RegistrationAuthorityIdentifier_ISO11179 isoRegId2 = new RegistrationAuthorityIdentifier_ISO11179();
		isoRegId2.setInternationalCodeDesignator("");
		isoRegAuth.setIdentifier(isoRegId2);
		isoRegAuth.setName("");
		isoRegAuth.setDocumentationLanguageIdentifier(getBlankLanguageIdentifiers());
		isoRegAuth.setRepresentedBy(getBlankRepresentedBy());
		
		return isoRegAuth;
	}
	
	private Submission_ISO11179 getBlankSubmittedBy() {
		Submission_ISO11179 isoSubmission = new Submission_ISO11179();
		isoSubmission.setContact(getBlankContact());
		isoSubmission.setOrganization(getBlankOrganization());
		
		return isoSubmission;
	}
	
	private List<TerminologicalEntry_ISO11179> getBlankHaving() {
		TerminologicalEntry_ISO11179 isoTermEntry = getBlankTermEntry();
		List<TerminologicalEntry_ISO11179> isoTermEntries = new ArrayList<TerminologicalEntry_ISO11179>();
		isoTermEntries.add(isoTermEntry);
		
		return isoTermEntries;
	}
	
	private List<LanguageIdentification_ISO11179> getBlankLanguageIdentifiers() {
		LanguageIdentification_ISO11179 isoLangIdentify = new LanguageIdentification_ISO11179();
		isoLangIdentify.setLanguageIdentifier("en");
		isoLangIdentify.setCountryIdentifier("US");
		List<LanguageIdentification_ISO11179> isoLangIdentifyList = new ArrayList<LanguageIdentification_ISO11179>();
		isoLangIdentifyList.add(isoLangIdentify);
		
		return isoLangIdentifyList;
	}
	
	private List<Registrar_ISO11179> getBlankRepresentedBy() {
		Registrar_ISO11179 isoRegistrar = new Registrar_ISO11179();
		isoRegistrar.setRegistrarIdentifier("");
	
		isoRegistrar.setRegistrarContact(getBlankContact());
		
		List<Registrar_ISO11179> isoRegistrars = new ArrayList<Registrar_ISO11179>();
		isoRegistrars.add(isoRegistrar);
		
		return isoRegistrars;
	}
	
	private void setItemId(AdminItem_ISO11179 isoAdminItem, String id) {
		isoAdminItem.getAdminRecord().getIdentifier().setDataIdentifier(id);
	}
	
	private void addNameAndDef(AdminItem_ISO11179 isoAdminItem, String name, String def, boolean isPreferred) {
		List<TerminologicalEntry_ISO11179> isoTermEntries = isoAdminItem.getHaving();
		TerminologicalEntry_ISO11179 isoTermEntry = null;
		if (isoTermEntries.size() > 0) {
			isoTermEntry = isoTermEntries.get(0);
		}
		else {
			isoTermEntry = getBlankTermEntry();
		}
		
		List<LanguageSection_ISO11179> isoLangSections = isoTermEntry.getContainingEntries();
		LanguageSection_ISO11179 isoLangSection = isoLangSections.get(0);
		
		List<Designation_ISO11179> isoDesigns = isoLangSection.getNamingEntries();
		List<Definition_ISO11179> isoDefs = isoLangSection.getDefiningEntries();
		
		if (isPreferred) {
			for (Designation_ISO11179 isoDesign: isoDesigns) {
				isoDesign.setPreferredDesignation(false);
			}
			
			for (Definition_ISO11179 isoDef: isoDefs) {
				isoDef.setPreferredDefinition(false);
			}
		}

		Designation_ISO11179 isoDesig = new Designation_ISO11179();
		isoDesig.setName(name);
		isoDesig.setPreferredDesignation(isPreferred);
		
		isoDesigns.add(isoDesig);
		
		Definition_ISO11179 isoDef = new Definition_ISO11179();
		isoDef.setText(def);
		isoDef.setPreferredDefinition(isPreferred);
		
		isoDefs.add(isoDef);
	}
	
	private TerminologicalEntry_ISO11179 getBlankTermEntry() {
		TerminologicalEntry_ISO11179 isoTermEntry = new TerminologicalEntry_ISO11179();
		
		LanguageSection_ISO11179 isoLangSection = new LanguageSection_ISO11179();
		isoLangSection.setNamingEntries(new ArrayList<Designation_ISO11179>());
		isoLangSection.setDefiningEntries(new ArrayList<Definition_ISO11179>());
		
		LanguageIdentification_ISO11179 isoLangId = new LanguageIdentification_ISO11179();
		isoLangId.setLanguageIdentifier("");
		isoLangSection.setIdentifier(isoLangId);
		
		List<LanguageSection_ISO11179> isoLangSections = new ArrayList<LanguageSection_ISO11179>();
		isoLangSections.add(isoLangSection);
		
		isoTermEntry.setContainingEntries(isoLangSections);
		
		return isoTermEntry;
	}
	
	
	
	private void setNameDefAndSource(String name, String def, String source, AdminItem_ISO11179 isoAdminItem) {
		
		LanguageSection_ISO11179 isoLangSection = new LanguageSection_ISO11179();
		
		Designation_ISO11179 isoDesignation = new Designation_ISO11179();
		isoDesignation.setName(name);
		isoDesignation.setPreferredDesignation(true);
		List<Designation_ISO11179> desigs = new ArrayList<Designation_ISO11179>();
		desigs.add(isoDesignation);
		
		Definition_ISO11179 isoDef = new Definition_ISO11179();
		isoDef.setText(def);
		isoDef.setPreferredDefinition(true);
		ReferenceDocument_ISO11179 isoRefDoc = new ReferenceDocument_ISO11179();
		Organization_ISO11179 isoOrg = new Organization_ISO11179();
		isoOrg.setName(source);
		List<Organization_ISO11179> orgs = new ArrayList<Organization_ISO11179>();
		orgs.add(isoOrg);
		isoRefDoc.setProvidedBy(orgs);
		isoRefDoc.setIdentifier("");
		isoRefDoc.setTitle("");
		isoDef.setSourceReference(isoRefDoc);
		
		List<Definition_ISO11179> defins = new ArrayList<Definition_ISO11179>();
		defins.add(isoDef);
		
		isoLangSection.setNamingEntries(desigs);
		isoLangSection.setDefiningEntries(defins);
		
		LanguageIdentification_ISO11179 isoLangId = new LanguageIdentification_ISO11179();
		isoLangId.setLanguageIdentifier("");
		
		isoLangSection.setIdentifier(isoLangId);
		
		List<LanguageSection_ISO11179> langSections = new ArrayList<LanguageSection_ISO11179>();
		langSections.add(isoLangSection);
		
		TerminologicalEntry_ISO11179 isoTermEntry = new TerminologicalEntry_ISO11179();
		isoTermEntry.setContainingEntries(langSections);
		
		List<TerminologicalEntry_ISO11179> havingList = isoAdminItem.getHaving();
		if (havingList == null) {
			havingList = new ArrayList<TerminologicalEntry_ISO11179>();
			isoAdminItem.setHaving(havingList);
		}
		havingList.add(isoTermEntry);
	}
	
	private DataElement_ISO11179 getISODataElement(DataEntry dataEntry) {
		DataElement_ISO11179 isoDE = new DataElement_ISO11179();
		
		String deId = dataEntry.getCdeId();
		String deLongName = dataEntry.getCdeLongName();
		
		return isoDE;
	}
	
	
	private String getRandomString() {
		StringBuffer randomStringBuffer = new StringBuffer();
		int rand = 0;

		while (randomStringBuffer.length() < 5) {
			while (true) {
				rand = (int)(Math.random()*100);
				if ((rand >= 65 && rand <= 90) || (rand >= 97 && rand <= 122)) {
					break;
				}
			}
			randomStringBuffer.append((char)rand);
		}
		return randomStringBuffer.toString();
	}

	public static void main(String[] args) {
		ISO11179Transformer transformer = new ISO11179Transformer();
		transformer.transform(new File("C:\\Docume~1\\mathura2\\Desktop\\ExcelXML\\test2.xml"));
	}
	
	private class DataEntryListIterator {
		private final List<DataEntry> dataEntries;
		int i = 0;
		
		public DataEntryListIterator(List<DataEntry> _dataEntries) {
			this.dataEntries = _dataEntries;
		}
		
		public boolean hasNext() {
			return i<dataEntries.size();
		}
		
		public List<DataEntry> next() {
			DataEntry dataEntry = dataEntries.get(i);
			int questionOrder = dataEntry.getQuestionOrder();
			int questionOrder2 = questionOrder;
			int startIndex = i;
			
			while (i<dataEntries.size()-1 && questionOrder2 != questionOrder+1) {
				i++;
				dataEntry = dataEntries.get(i);
				questionOrder2 = dataEntry.getQuestionOrder();
			}
			
			if (startIndex == i) {
				i++;
				List<DataEntry> deSubList = new ArrayList<DataEntry>();
				deSubList.add(dataEntries.get(startIndex));
				return deSubList;
			}
			else {
				return dataEntries.subList(startIndex, i);
			}
		}
	}
}
