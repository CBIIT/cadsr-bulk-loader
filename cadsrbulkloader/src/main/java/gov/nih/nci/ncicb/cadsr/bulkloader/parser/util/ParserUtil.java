package gov.nih.nci.ncicb.cadsr.bulkloader.parser.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.AdminItem_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.AdminRecord_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ClassificationSchemeItemRef_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ComponentConceptList_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ComponentConcept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ConceptDerivationRule_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Concept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Designation_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ItemIdentifier_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.LanguageSection_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.TerminologicalEntry_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.CaDSRObjectRegistry;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

public class ParserUtil {
	
	private CaDSRObjectsUtil caDSRUtil = new CaDSRObjectsUtil();
	
	public String getDesignation(AdminItem_ISO11179 adminItem) {
		List<TerminologicalEntry_ISO11179> termEntries = adminItem.getHaving();
		if (termEntries != null && termEntries.size() > 0) {
			List<LanguageSection_ISO11179> langSections = termEntries.get(0).getContainingEntries();
			if (langSections != null && langSections.size() > 0) {
				List<Designation_ISO11179> designations = langSections.get(0).getNamingEntries();
				if (designations != null && designations.size() > 0) {
					return designations.get(0).getName();
				}
			}
		}
		
		return null;
	}
	
	public List<Concept> getConceptsFromRegistry(List<String> conceptRefs, CaDSRObjectRegistry objRegistry) {
		List<Concept> concepts = new ArrayList<Concept>();
		
		for (String conceptRef: conceptRefs) {
			Concept concept = objRegistry.getConcept(conceptRef);
			concepts.add(concept);
		}
		
		return concepts;
	}
		
	public ConceptDerivationRule getConceptDerivationRule(ConceptDerivationRule_caDSR11179 caDSRConDerivRule, CaDSRObjectRegistry objRegistry) {
		ConceptDerivationRule cdr = DomainObjectFactory.newConceptDerivationRule();
		List<String> conceptRefs = getConceptReferences(caDSRConDerivRule);
		List<Concept> concepts = getConceptsFromRegistry(conceptRefs, objRegistry);
		List<ComponentConcept> compConcepts = getComponentConceptsFromConcepts(concepts);
		
		for (ComponentConcept compConcept: compConcepts) {
			compConcept.setConceptDerivationRule(cdr);
		}
		
		cdr.setComponentConcepts(compConcepts);
		return cdr;
	}
	
	

	public String getIdentifier(AdminItem_ISO11179 adminItem) {
		AdminRecord_ISO11179 adminRecord = adminItem.getAdminRecord();
		ItemIdentifier_ISO11179 itemId = adminRecord.getIdentifier();
		String id = itemId.getDataIdentifier();
		
		return id;
	}
	
	public Float getIdVersion(AdminItem_ISO11179 adminItem) {
		AdminRecord_ISO11179 adminRecord = adminItem.getAdminRecord();
		ItemIdentifier_ISO11179 itemId = adminRecord.getIdentifier();
		String versionStr = itemId.getVersion();
		
		if (versionStr == null) {
			return null;
		}
		
		try {
			Float version = Float.parseFloat(versionStr);
			
			return version;
		} catch (NumberFormatException e) {
			
			return 1.0f;
		}
	}
	
	public String getPreferredQuestionText(AdminItem_ISO11179 adminItem) {
		List<TerminologicalEntry_ISO11179> termEntries = adminItem.getHaving();
		if (termEntries != null) {
			for (TerminologicalEntry_ISO11179 termEntry: termEntries) {
				List<LanguageSection_ISO11179> languageSections = termEntry.getContainingEntries();
				if (languageSections != null) {
					for (LanguageSection_ISO11179 langSection: languageSections) {
						List<Designation_ISO11179> designations = langSection.getNamingEntries();
						if (designations != null) {
							for (Designation_ISO11179 designation: designations) {
								if (designation.isPreferredDesignation()) {
									return designation.getName();
								}
							}
						}
						
					}
				}
			}
		}
		
		
		return null;
	}
	
	public List<ComponentConcept> getComponentConcepts(List<ComponentConcept_caDSR11179> caDSRCompConcepts, List<Concept_caDSR11179> concepts) {
		List<ComponentConcept> compConcepts = new ArrayList<ComponentConcept>();
		Map<String, Concept_caDSR11179> conceptMap = mapTagIdToConcept(concepts);
		
		for (ComponentConcept_caDSR11179 caDSRCompConcept: caDSRCompConcepts) {
			String conRefId = caDSRCompConcept.getConceptRefId();
			Concept_caDSR11179 caDSRConcept = conceptMap.get(conRefId);
			
			if (caDSRConcept != null) {
				Concept concept = getConcept(caDSRConcept.getCode());
				
				ComponentConcept compConcept = DomainObjectFactory.newComponentConcept();
				compConcept.setConcept(concept);
				
				compConcepts.add(compConcept);
			}
			
		}
		
		return compConcepts;
	}

	private Map<String, Concept_caDSR11179> mapTagIdToConcept(List<Concept_caDSR11179> caDSRConcepts) {
		Map<String, Concept_caDSR11179> conceptMap = new HashMap<String, Concept_caDSR11179>();
		
		for (Concept_caDSR11179 caDSRConcept: caDSRConcepts) {
			conceptMap.put(caDSRConcept.getTagId(), caDSRConcept);
		}
		
		return conceptMap;
	}
	
	public List<String> getConceptReferences(ConceptDerivationRule_caDSR11179 conceptDerivationRule) {
		List<String> concepts = new ArrayList<String>();

		ComponentConceptList_caDSR11179 compConceptsList = conceptDerivationRule.getComponentConceptsList();
		if (compConceptsList != null) {
			List<ComponentConcept_caDSR11179> componentConcepts = compConceptsList.getComponentConcepts();
			
			if (componentConcepts != null) {
				ListIterator<ComponentConcept_caDSR11179> listIter = componentConcepts.listIterator();
				for (int i=0;listIter.hasNext();i++) {
					ComponentConcept_caDSR11179 componentConcept = listIter.next();
					String conceptId = componentConcept.getConceptRefId();
					int order = componentConcept.getOrder();
					if (order > 0 && order <= concepts.size()) {
						concepts.add(order-1, conceptId);
					}
					else {
						concepts.add(conceptId);
					}
				}
			}
		}
		
		
		return concepts;
	}
	
	public String getAdminComponentId(AdminItem_ISO11179 adminItem) {
		try {
			AdminRecord_ISO11179 adminRecord = adminItem.getAdminRecord();
			ItemIdentifier_ISO11179 itemId = adminRecord.getIdentifier();
			String dataId = itemId.getDataIdentifier();
			
			return dataId;
		}
		catch (Exception e) {
			return null;
		}
	}
	
	public List<AdminComponentClassSchemeClassSchemeItem> getAdminComponentCSCSI(AdminItem_ISO11179 isoAdminItem, CaDSRObjectRegistry objRegistry) {
		List<AdminComponentClassSchemeClassSchemeItem> acCSCSIList = new ArrayList<AdminComponentClassSchemeClassSchemeItem>();
		List<ClassificationSchemeItemRef_ISO11179> isoCSIRefs = isoAdminItem.getClassifiedBy().getClassifiedBy();
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
	
	public String getOrigin(AdminItem_ISO11179 isoAdminItem) {
		try {
			return isoAdminItem.getSubmittedBy().getOrganization().getName();
		} catch (Exception e) {
			return "";
		}
	}
	
	private List<ComponentConcept> getComponentConceptsFromConcepts(
			List<Concept> concepts) {
		return caDSRUtil.getComponentConceptsFromConcepts(concepts);
	}
	
	private Concept getConcept(String code) {
		return caDSRUtil.getConcept(code);
	}
	
	public String getLongNameFromConcepts(List<Concept> concepts) {
		return caDSRUtil.getLongNameFromConcepts(concepts);
	}

	public String getPreferredNameFromConcepts(List<Concept> concepts) {
		return caDSRUtil.getPreferredNameFromConcepts(concepts);
	}

	public String getDefinitionFromConcepts(List<Concept> concepts) {
		return caDSRUtil.getDefinitionFromConcepts(concepts);
	}
	
	public String getPreferredDefinitionFromConcepts(List<Concept> concepts) {
		return caDSRUtil.getPreferredDefinitionFromConcepts(concepts);
	}

	public String getLongName(ConceptDerivationRule cdr) {
		return caDSRUtil.getLongName(cdr);
	}

	public String getDefinition(ConceptDerivationRule cdr) {
		return caDSRUtil.getDefinition(cdr);
	}
	
	public String getPreferredDefinition(ConceptDerivationRule cdr) {
		return caDSRUtil.getPreferredDefinition(cdr);
	}

	public String getDECLongName(DataElementConcept dec) {
		return caDSRUtil.getDECLongName(dec);
	}

	public String getVDLongName(ValueDomain vd) {
		return caDSRUtil.getVDLongName(vd);
	}
}
