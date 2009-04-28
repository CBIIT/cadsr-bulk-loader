package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.List;

public class CaDSRObjectsUtil {

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
		return sb.substring(1);
	}
	
	public static AdminComponent setPublicIdAndVersion(AdminComponent adminComp, int publicId, double version) {
		adminComp.setPublicId(String.valueOf(publicId));
		adminComp.setVersion(new Float(version));
		
		return adminComp;
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
}
