package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CaDSRObjectRegistry {
	
	private static final Map<String, Concept> conceptsMap = new HashMap<String, Concept>();
	private static final Map<String, Property> propertiesMap = new HashMap<String, Property>();
	private static final Map<String, ObjectClass> objectClassMap = new HashMap<String, ObjectClass>();
	private static final Map<String, ValueDomain> valueDomainMap = new HashMap<String, ValueDomain>();
	private static final Map<String, DataElementConcept> dataElementConceptMap = new HashMap<String, DataElementConcept>();
	private static final Map<String, DataElement> dataElementMap = new HashMap<String, DataElement>();
	private static final Map<String, ConceptualDomain> conceptualDomainMap = new HashMap<String, ConceptualDomain>();
	private static final Map<String, ValueMeaning> valueMeaningMap = new HashMap<String, ValueMeaning>();
	
	public void addConcept(String tagId, Concept con) {
		conceptsMap.put(tagId, con);
	}
	
	public void addProperty(String tagId, Property prop) {
		propertiesMap.put(tagId, prop);
	}
	
	public void addObjectClass(String tagId, ObjectClass oc) {
		objectClassMap.put(tagId, oc);
	}
	
	public void addValueDomain(String tagId, ValueDomain vd) {
		valueDomainMap.put(tagId, vd);
	}
	
	public void addDataElementConcept(String tagId, DataElementConcept dec) {
		dataElementConceptMap.put(tagId, dec);
	}
	
	public void addDataElement(String tagId, DataElement de) {
		dataElementMap.put(tagId, de);
	}
	
	public void addConceptualDomain(String tagId, ConceptualDomain cd) {
		conceptualDomainMap.put(tagId, cd);
	}
	
	public void addValueMeaning(String tagId, ValueMeaning vm) {
		valueMeaningMap.put(tagId, vm);
	}
	
	
	public Concept getConcept(String tagId) {
		return conceptsMap.get(tagId);
	}
	
	public Property getProperty(String tagId) {
		return propertiesMap.get(tagId);
	}
	
	public ObjectClass getObjectClass(String tagId) {
		return objectClassMap.get(tagId);
	}
	
	public ValueDomain getValueDomain(String tagId) {
		return valueDomainMap.get(tagId);
	}
	
	public DataElementConcept getDataElementConcept(String tagId) {
		return dataElementConceptMap.get(tagId);
	}
	
	public DataElement getDataElement(String tagId) {
		return dataElementMap.get(tagId);
	}
	
	public ConceptualDomain getConceptualDomain(String tagId) {
		return conceptualDomainMap.get(tagId);
	}
	
	public ValueMeaning getValueMeaning(String tagId) {
		return valueMeaningMap.get(tagId);
	}
	
	public List<Concept> getConcepts() {
		return getValuesAsList(conceptsMap);
	}
	
	public List<Property> getProperties() {
		return getValuesAsList(propertiesMap);
	}
	
	public List<ObjectClass> getObjectClasses() {
		return getValuesAsList(objectClassMap);
	}
	
	public List<ValueDomain> getValueDomains() {
		return getValuesAsList(valueDomainMap);
	}
	
	public List<DataElementConcept> getDataElementConcepts() {
		return getValuesAsList(dataElementConceptMap);
	}
	
	public List<DataElement> getDataElements() {
		return getValuesAsList(dataElementMap);
	}
	
	public List<ConceptualDomain> getConceptualDomains() {
		return getValuesAsList(conceptualDomainMap);
	}
	
	public List<ValueMeaning> getValueMeanings() {
		return getValuesAsList(valueMeaningMap);
	}
	
	private <T> List<T> getValuesAsList(Map<String, T> map) {
		Collection<T> collection = map.values();
		List<T> retList = new ArrayList<T>();
		
		Iterator<T> collectionIter = collection.iterator();
		while(collectionIter.hasNext()) {
			retList.add(collectionIter.next());
		}
		
		return retList;
	}

}
