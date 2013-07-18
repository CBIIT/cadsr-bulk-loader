/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 2, 2008
 * @since 
 */

public class ISO11179Elements {

	private DataElementList_ISO11179 dataElements = new DataElementList_ISO11179();
	private List<DataElementRef_ISO11179> dataElementRefs;
	
	private DataElementConceptList_ISO11179 dataElementConcepts;
	private List<DataElementConceptRef_ISO11179> dataElementConceptRefs;
	
	private ObjectClassList_ISO11179 objectClassList;
	private List<ObjectClassRef_ISO11179> objectClassRefs;
	
	private PropertyList_ISO11179 propertiesList;
	private List<PropertyRef_ISO11179> propertyRefs;
	
	private ConceptualDomainList_ISO11179 conceptualDomainsList;
	private List<ConceptualDomainRef_ISO11179> conceptualDomainRefs;
	
	private ValueDomainsList_ISO11179 valueDomainsList;
	private List<ValueDomainRef_ISO11179> valueDomainRefs;
	
	private ValueMeaningsList_ISO11179 valueMeaningsList;
	
	private ConceptList_ISO11179 conceptsList;
	
	private ClassificationSchemeItemList_caDSR11179 classSchemeItemList;
	private ClassificationSchemeList_ISO11179 classSchemeList;
	
	public DataElementList_ISO11179 getDataElements() {
		if (dataElements == null) return new DataElementList_ISO11179();
		return dataElements;
	}
	public void setDataElements(DataElementList_ISO11179 dataElements) {
		this.dataElements = dataElements;
	}
	public List<DataElementRef_ISO11179> getDataElementRefs() {
		return getNonNullList(dataElementRefs);
	}
	public void setDataElementRefs(List<DataElementRef_ISO11179> dataElementRefs) {
		this.dataElementRefs = dataElementRefs;
	}
	public DataElementConceptList_ISO11179 getDataElementConcepts() {
		if (dataElementConcepts == null) return new DataElementConceptList_ISO11179();
		return dataElementConcepts;
	}
	public void setDataElementConcepts(
			DataElementConceptList_ISO11179 dataElementConcepts) {
		this.dataElementConcepts = dataElementConcepts;
	}
	public List<DataElementConceptRef_ISO11179> getDataElementConceptRefs() {
		return getNonNullList(dataElementConceptRefs);
	}
	public void setDataElementConceptRefs(
			List<DataElementConceptRef_ISO11179> dataElementConceptRefs) {
		this.dataElementConceptRefs = dataElementConceptRefs;
	}
	
	public ObjectClassList_ISO11179 getObjectClassList() {
		return objectClassList;
	}
	public void setObjectClassList(ObjectClassList_ISO11179 objectClassList) {
		this.objectClassList = objectClassList;
	}
	public PropertyList_ISO11179 getPropertiesList() {
		return propertiesList;
	}
	public void setPropertiesList(PropertyList_ISO11179 propertiesList) {
		this.propertiesList = propertiesList;
	}
	public ConceptualDomainList_ISO11179 getConceptualDomainsList() {
		return conceptualDomainsList;
	}
	public void setConceptualDomainsList(
			ConceptualDomainList_ISO11179 conceptualDomainsList) {
		this.conceptualDomainsList = conceptualDomainsList;
	}
	public ValueDomainsList_ISO11179 getValueDomainsList() {
		return valueDomainsList;
	}
	public void setValueDomainsList(ValueDomainsList_ISO11179 valueDomainsList) {
		this.valueDomainsList = valueDomainsList;
	}
	public ValueMeaningsList_ISO11179 getValueMeaningsList() {
		return valueMeaningsList;
	}
	public void setValueMeaningsList(ValueMeaningsList_ISO11179 valueMeaningsList) {
		this.valueMeaningsList = valueMeaningsList;
	}
	public ConceptList_ISO11179 getConceptsList() {
		return conceptsList;
	}
	public void setConceptsList(ConceptList_ISO11179 conceptsList) {
		this.conceptsList = conceptsList;
	}
	public ClassificationSchemeItemList_caDSR11179 getClassSchemeItemList() {
		return classSchemeItemList;
	}
	public void setClassSchemeItemList(
			ClassificationSchemeItemList_caDSR11179 classSchemeItemList) {
		this.classSchemeItemList = classSchemeItemList;
	}
	public ClassificationSchemeList_ISO11179 getClassSchemeList() {
		return classSchemeList;
	}
	public void setClassSchemeList(ClassificationSchemeList_ISO11179 classSchemeList) {
		this.classSchemeList = classSchemeList;
	}
	public List<ObjectClass_caDSR11179> getObjectClasses() {
		if (objectClassList != null) {
			return getNonNullList(objectClassList.getObjectClasses());
		}
		else return new ArrayList<ObjectClass_caDSR11179>();
	}
	public List<ObjectClassRef_ISO11179> getObjectClassRefs() {
		return getNonNullList(objectClassRefs);
	}
	public void setObjectClassRefs(List<ObjectClassRef_ISO11179> objectClassRefs) {
		this.objectClassRefs = objectClassRefs;
	}
	public List<Property_caDSR11179> getProperties() {
		if (propertiesList != null) {
			return getNonNullList(propertiesList.getProperties());
		}
		else return new ArrayList<Property_caDSR11179>();
		
	}
	public List<PropertyRef_ISO11179> getPropertyRefs() {
		return getNonNullList(propertyRefs);
	}
	public void setPropertyRefs(List<PropertyRef_ISO11179> propertyRefs) {
		this.propertyRefs = propertyRefs;
	}
	public List<ConceptualDomain_caDSR11179> getConceptualDomains() {
		if(conceptualDomainsList != null) {
			return getNonNullList(conceptualDomainsList.getConceptualDomains());
		}
		else return new ArrayList<ConceptualDomain_caDSR11179>();
	}
	public List<ConceptualDomainRef_ISO11179> getConceptualDomainRefs() {
		return getNonNullList(conceptualDomainRefs);
	}
	public void setConceptualDomainRefs(
			List<ConceptualDomainRef_ISO11179> conceptualDomainRefs) {
		this.conceptualDomainRefs = conceptualDomainRefs;
	}
	public List<ValueDomain_caDSR11179> getValueDomains() {
		if (valueDomainsList != null) {
			return getNonNullList(valueDomainsList.getValueDomains());
		}
		else return new ArrayList<ValueDomain_caDSR11179>();
		
	}
	public List<ValueDomainRef_ISO11179> getValueDomainRefs() {
		return getNonNullList(valueDomainRefs);
	}
	public void setValueDomainRefs(List<ValueDomainRef_ISO11179> valueDomainRefs) {
		this.valueDomainRefs = valueDomainRefs;
	}
	public List<ValueMeaning_caDSR11179> getValueMeanings() {
		if (valueMeaningsList != null) {
			return getNonNullList(valueMeaningsList.getValueMeanings());
		}
		else return new ArrayList<ValueMeaning_caDSR11179>();
	}
	public List<Concept_caDSR11179> getConcepts() {
		if (conceptsList != null) {
			return getNonNullList(conceptsList.getConcepts());
		}
		else return new ArrayList<Concept_caDSR11179>();
	}
	
	public List<ClassificationSchemeItem_caDSR11179> getClassSchemeItems() {
		if (classSchemeItemList != null) {
			return getNonNullList(classSchemeItemList.getClassSchemeItems());
		}
		else return new ArrayList<ClassificationSchemeItem_caDSR11179>();
	}
	
	
	public List<ClassificationScheme_ISO11179> getClassSchemes() {
		if (classSchemeList != null) {
			return getNonNullList(classSchemeList.getClassSchemes());
		}
		else return new ArrayList<ClassificationScheme_ISO11179>();
	}
	

	
	
	private <T> List<T> getNonNullList(List<T> t) {
		if (t == null) {
			return new ArrayList<T>();
		}
		else return t;
	}
}
