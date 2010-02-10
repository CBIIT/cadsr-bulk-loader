package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
import java.util.List;

public class CaDSRObjectsUtil {

	private final String CONCEPT_CONCAT_STRING = ":";
	
	public static DataElement createDataElement() {
		return DomainObjectFactory.newDataElement();
	}
	
	public static DataElement createDataElement(int publicId, double version) {
		DataElement de = DomainObjectFactory.newDataElement();
		setPublicIdAndVersion(de, publicId, version);
		
		return de;
	}
	
	public static DataElementConcept createDataElementConcept() {
		return DomainObjectFactory.newDataElementConcept();
	}
	
	public static DataElementConcept createDataElementConcept(int publicId, double version) {
		DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
		setPublicIdAndVersion(dec, publicId, version);
		
		return dec;
	}
	
	public static ValueDomain createValueDomain() {
		return DomainObjectFactory.newValueDomain();
	}
	
	public static ValueDomain createValueDomain(int publicId, double version) {
		ValueDomain vd = DomainObjectFactory.newValueDomain();
		setPublicIdAndVersion(vd, publicId, version);
		
		return vd;
	}
	
	public static ObjectClass createObjectClass() {
		return DomainObjectFactory.newObjectClass();
	}
	
	public static ObjectClass createObjectClass(int publicId, double version) {
		ObjectClass objectClass = DomainObjectFactory.newObjectClass();
		setPublicIdAndVersion(objectClass, publicId, version);
		
		return objectClass;
	}
	
	public static ObjectClass createObjectClass(List<Concept> concepts) {
		ConceptDerivationRule cdr = createConceptDerivationRule(concepts);
		
		ObjectClass oc = DomainObjectFactory.newObjectClass();
		oc.setConceptDerivationRule(cdr);
		
		return oc;
	}
	
	public static Property createProperty() {
		return DomainObjectFactory.newProperty();
	}
	
	public static Property createProperty(List<Concept> concepts) {
		ConceptDerivationRule cdr = createConceptDerivationRule(concepts);
		
		Property property = DomainObjectFactory.newProperty();
		property.setConceptDerivationRule(cdr);
		
		return property;
	}
	
	public static Property createProperty(int publicId, double version) {
		Property property = DomainObjectFactory.newProperty();
		setPublicIdAndVersion(property, publicId, version);
		
		return property;
	}
	
	public static ConceptualDomain createConceptualDomain() {
		return DomainObjectFactory.newConceptualDomain();
	}
	
	public static ConceptualDomain createConceptualDomain(int publicId, double version) {
		ConceptualDomain conceptualDomain = DomainObjectFactory.newConceptualDomain();
		setPublicIdAndVersion(conceptualDomain, publicId, version);
		
		return conceptualDomain;
	}

	public static ConceptDerivationRule createConceptDerivationRule(List<Concept> concepts) {
		ConceptDerivationRule cdr = DomainObjectFactory.newConceptDerivationRule();
		
		List<ComponentConcept> compConcepts = getComponentConcepts(concepts, cdr);
		String codesConcat = getConceptCodesConcatenation(concepts);
		
		cdr.setCodesConcatenation(codesConcat);
		cdr.setComponentConcepts(compConcepts);
		
		return cdr;
	}
	
	public static List<ComponentConcept> getComponentConcepts(List<Concept> concepts, ConceptDerivationRule cdr) {
		List<ComponentConcept> compConcepts = new ArrayList<ComponentConcept>();
		int noOfConcepts = concepts.size();
		for (int i=0;i<noOfConcepts;i++) {
			ComponentConcept compCon = DomainObjectFactory.newComponentConcept();
			compCon.setConcept(concepts.get(i));
			int compConceptOrder = getComponentConceptOrder(noOfConcepts, i);
			compCon.setOrder(compConceptOrder);
			compCon.setConceptDerivationRule(cdr);
			
			compConcepts.add(compCon);
		}
		
		return compConcepts;
	}
	
	private static int getComponentConceptOrder(int noOfConcepts, int conceptIndex) {
		int maxOrderNo = noOfConcepts-1;
		int compConceptOrder = maxOrderNo - conceptIndex;
		
		return compConceptOrder;
	}
	
	public static String getConceptCodesConcatenation(List<Concept> concepts) {
		StringBuffer sb = new StringBuffer();
		for (Concept concept: concepts) {
			sb.append(":");
			sb.append(concept.getPreferredName());
		}
		if (sb.length() > 0) {
			return sb.substring(1);
		}
		
		return sb.toString();
	}
	
	public static AdminComponent setPublicIdAndVersion(AdminComponent adminComp, int publicId, double version) {
		adminComp.setPublicId(String.valueOf(publicId));
		adminComp.setVersion(new Float(version));
		
		return adminComp;
	}
	
	public static Definition createDefinition() {
		Definition definition = DomainObjectFactory.newDefinition();
		
		return definition;
	}
	
	public static Concept createConcept() {
		Concept concept = DomainObjectFactory.newConcept();
		
		return concept;
	}
	
	public static Concept createConcept(String cui) {
		Concept concept = createConcept();
		concept.setPreferredName(cui);
		
		return concept;
	}
	
	public static Context createContext() {
		Context context =  DomainObjectFactory.newContext();
		
		return context;
	}
	
	public static Context createContext(String contextName) {
		Context context = createContext();
		
		context.setName(contextName);
		
		return context;
	}
	
	public static ValueMeaning createValueMeaning() {
		ValueMeaning vm = DomainObjectFactory.newValueMeaning();
		vm.setConceptDerivationRule(createConceptDerivationRule(new ArrayList<Concept>()));
		
		return vm;
	}
	
	public String getDELongName(DataElementConcept dec, ValueDomain vd) {
		String decLongName = getDECLongName(dec);
		String vdLongName = getVDLongName(vd);
		
		return decLongName+" "+vdLongName;
	}
	
	public String getDECLongName(DataElementConcept dec) {
		return deriveLongName(dec.getObjectClass(), dec.getProperty());
	}
	
	public String deriveLongName(ObjectClass oc, Property prop) {
		String ocLongName = deriveLongName(oc.getConceptDerivationRule());
		String propLongName = deriveLongName(prop.getConceptDerivationRule());
		
		return ocLongName+" "+propLongName;
	}
	
	public String deriveLongName(ConceptDerivationRule cdr) {
		StringBuffer longName = new StringBuffer();
		
		if (cdr != null) {
			List<ComponentConcept> compConcepts = cdr.getComponentConcepts();
			for (ComponentConcept compCon: compConcepts) {
				String conLongName = compCon.getConcept().getLongName();
				if (conLongName != null) {
					longName.append(" ");
					longName.append(conLongName);
				}
			}
			
			if (longName.length() > 0) {
				return longName.substring(1);
			}
			else {
				return "";
			}
		}
		return null;
	}
	
	public String getVDLongName(ValueDomain vd) {
		if (vd == null) return "";
		
		return deriveLongName(vd.getConceptDerivationRule());
	}
	
	public String getLongName(ConceptDerivationRule cdr) {
		StringBuffer longName = new StringBuffer();
		
		if (cdr != null) {
			List<ComponentConcept> compCons = cdr.getComponentConcepts();
			for (ComponentConcept compCon: compCons) {
				String conLongName = compCon.getConcept().getLongName();
				if (conLongName != null) {
					longName.append(" ");
					longName.append(conLongName);
					
				}
			}
		}
		
		if (longName.length() > 0) {
			return longName.substring(1);
		}
		else {
			return "";
		}
	}
	
	public static List<Concept> getConcepts(ConceptDerivationRule cdr) {
		List<Concept> concepts = new ArrayList<Concept>();
		
		if (cdr == null) return concepts;
		
		List<ComponentConcept> compCons = cdr.getComponentConcepts();
		for (ComponentConcept compCon: compCons) {
			Concept concept = compCon.getConcept();
			concepts.add(concept);
		}
		
		return concepts;
	}
	
	public static String[] getConceptCodes(ConceptDerivationRule cdr) {
		List<Concept> concepts = getConcepts(cdr);
		String[] conceptCodes = new String[concepts.size()];
		for (int i=0;i<concepts.size();i++) {
			conceptCodes[i] = concepts.get(i).getPreferredName();
		}
		
		return conceptCodes;
	}
	
	public String getDefinition(ConceptDerivationRule cdr) {
		
		List<Concept> concepts = getConcepts(cdr);
		
		return getDefinitionFromConcepts(concepts);
	}
	
	public String getPreferredDefinition(ConceptDerivationRule cdr) {
		List<Concept> concepts = getConcepts(cdr);
		
		return getPreferredDefinitionFromConcepts(concepts);
	}
	
	public List<ComponentConcept> getComponentConceptsFromConcepts(List<Concept> concepts) {
		List<ComponentConcept> compConcepts = new ArrayList<ComponentConcept>();
		int order = 0;
		for (Concept concept: concepts) {
			ComponentConcept compCon = DomainObjectFactory.newComponentConcept();
			compCon.setConcept(concept);
			compCon.setOrder(order);
			
			compConcepts.add(compCon);
			order++;
		}
		
		return compConcepts;
	}
	
	public Concept getConcept(String preferredName) {
		Concept con = DomainObjectFactory.newConcept();
		con.setPreferredName(preferredName);
		
		return con;
	}
	
	public String getLongNameFromConcepts(List<Concept> concepts) {
		StringBuffer concatConceptsBuffer = new StringBuffer();
		
		boolean apndFlag = false;
		for (Concept concept: concepts) {
			if (apndFlag) concatConceptsBuffer.append(" ");
			else apndFlag = true;
			concatConceptsBuffer.append(concept.getLongName().trim());
		}
		
/*		int lastIndexOfConcatStr = concatConceptsBuffer.lastIndexOf(CONCEPT_CONCAT_STRING);
		
		String concatConceptsStr = lastIndexOfConcatStr>=0?concatConceptsBuffer.substring(0, lastIndexOfConcatStr):concatConceptsBuffer.toString();*/
		
		return concatConceptsBuffer.toString();
	}
	
	public String getPreferredNameFromConcepts(List<Concept> concepts) {
		StringBuffer concatConceptsBuffer = new StringBuffer();
		
		for (Concept concept: concepts) {
			concatConceptsBuffer.append(concept.getPreferredName());
			concatConceptsBuffer.append(CONCEPT_CONCAT_STRING);
		}
		
		int lastIndexOfConcatStr = concatConceptsBuffer.lastIndexOf(CONCEPT_CONCAT_STRING);
		
		String concatConceptsStr = lastIndexOfConcatStr>=0?concatConceptsBuffer.substring(0, lastIndexOfConcatStr):concatConceptsBuffer.toString();
		
		return concatConceptsStr;
	}
	
	public String getDefinitionFromConcepts(List<Concept> concepts) {
		StringBuffer concatConceptsBuffer = new StringBuffer();
		
		for (Concept concept: concepts) {
			List<Definition> defs = concept.getDefinitions();
			String prefDef = concept.getPreferredDefinition();
			
			if (concatConceptsBuffer.length() > 0) {
				concatConceptsBuffer.append(CONCEPT_CONCAT_STRING);
			}
			
			concatConceptsBuffer.append(prefDef);
			
			for (Definition def: defs) {
				String defStr = def.getDefinition();
				if (!defStr.equalsIgnoreCase(prefDef)) {
					concatConceptsBuffer.append(CONCEPT_CONCAT_STRING);
					concatConceptsBuffer.append(defStr);
				}
			}
		}
		
		return concatConceptsBuffer.toString();
	}
	
	public String getPreferredDefinitionFromConcepts(List<Concept> concepts) {
		StringBuffer concatConceptsBuffer = new StringBuffer();
		
		for (Concept concept: concepts) {
			String prefDef = concept.getPreferredDefinition();
			
			if (concatConceptsBuffer.length() > 0) {
				concatConceptsBuffer.append(CONCEPT_CONCAT_STRING);
			}
			
			concatConceptsBuffer.append(prefDef);
		}
		
		return concatConceptsBuffer.toString();
	}
	
	public void sanitize(CaDSRObjects caDSRObjects) {
		List<Concept> adminComps = (List<Concept>)caDSRObjects.getList(DomainObjectFactory.newConcept());
		sanitize(adminComps);
	}
	
	private void sanitize(List<Concept> concepts) {
		for (Concept concept: concepts) {
			String longName = concept.getLongName();
			if (longName != null && !longName.trim().equals("")) {
				String sanitizedLongName = gov.nih.nci.ncicb.cadsr.bulkloader.util.StringUtil.replaceSpecialCharacters(longName);
				concept.setLongName(sanitizedLongName);
			}
		}
	}
}
