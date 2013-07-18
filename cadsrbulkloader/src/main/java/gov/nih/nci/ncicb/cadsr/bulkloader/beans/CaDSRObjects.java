/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.beans;

import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 21, 2009
 * @since 
 */

public class CaDSRObjects {

	private List<DataElement> dataElements;
	private List<DataElementConcept> dataElementConcepts;
	private List<ValueDomain> valueDomains;
	private List<ObjectClass> objectClasses;
	private List<Property> properties;
	private List<Concept> concepts;
	private List<ValueMeaning> valueMeanings;
	private List<Representation> repTerms;
	
	private Context loadContext;
	private ClassificationScheme loadClassificationScheme;
	
	private List<AdminComponent> emptyList = new ArrayList<AdminComponent>();
	
	public List<DataElement> getDataElements() {
		return dataElements;
	}
	public void setDataElements(List<DataElement> dataElements) {
		this.dataElements = dataElements;
	}
	public List<DataElementConcept> getDataElementConcepts() {
		return dataElementConcepts;
	}
	public void setDataElementConcepts(List<DataElementConcept> dataElementConcepts) {
		this.dataElementConcepts = dataElementConcepts;
	}
	public List<ValueDomain> getValueDomains() {
		return valueDomains;
	}
	public void setValueDomains(List<ValueDomain> valueDomains) {
		this.valueDomains = valueDomains;
	}
	public List<ObjectClass> getObjectClasses() {
		return objectClasses;
	}
	public void setObjectClasses(List<ObjectClass> objectClasses) {
		this.objectClasses = objectClasses;
	}
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public List<Concept> getConcepts() {
		return concepts;
	}
	public void setConcepts(List<Concept> concepts) {
		this.concepts = concepts;
	}
	
	public List<ValueMeaning> getValueMeanings() {
		return valueMeanings;
	}

	public void setValueMeanings(List<ValueMeaning> valueMeanings) {
		this.valueMeanings = valueMeanings;
	}
	
	public List<Representation> getRepTerms() {
		return repTerms;
	}
	public void setRepTerms(List<Representation> repTerms) {
		this.repTerms = repTerms;
	}
	public Context getLoadContext() {
		return loadContext;
	}
	public void setLoadContext(Context loadContext) {
		this.loadContext = loadContext;
	}
	public ClassificationScheme getLoadClassificationScheme() {
		return loadClassificationScheme;
	}
	public void setLoadClassificationScheme(
			ClassificationScheme loadClassificationScheme) {
		this.loadClassificationScheme = loadClassificationScheme;
	}
	public <T extends AdminComponent> List<? extends AdminComponent> getList(T element) {
		if (element instanceof Concept) {
			return getNonNullList(concepts);
		}
		else if (element instanceof ObjectClass) {
			return getNonNullList(objectClasses);
		}
		else if (element instanceof Property) {
			return getNonNullList(properties);
		}
		else if (element instanceof Representation) {
			return getNonNullList(repTerms);
		}
		else if (element instanceof ValueDomain) {
			return getNonNullList(valueDomains);
		}
		else if (element instanceof DataElementConcept) {
			return getNonNullList(dataElementConcepts);
		}
		else if (element instanceof DataElement) {
			return getNonNullList(dataElements);
		}
		return getNonNullList(null);
	}
	
	public List<? extends AdminComponent> getList() {
		List<AdminComponent> adminComponents = new ArrayList<AdminComponent>();
		if (concepts != null) {
			adminComponents.addAll(concepts);
		}
		if (objectClasses != null) {
			adminComponents.addAll(objectClasses);
		}
		if (properties != null) {
			adminComponents.addAll(properties);
		}
		if (valueDomains != null) {
			adminComponents.addAll(valueDomains);
		}
		if (valueMeanings != null) {
			adminComponents.addAll(valueMeanings);
		}
		if (dataElementConcepts != null) {
			adminComponents.addAll(dataElementConcepts);
		}
		if (dataElements != null) {
			adminComponents.addAll(dataElements);
		}
		if (repTerms != null) {
			adminComponents.addAll(repTerms);
		}
		
		return adminComponents;
	}
	
	private <T> List<? extends AdminComponent> getNonNullList(List<? extends AdminComponent> someAdminCompList) {
		if (someAdminCompList == null) {
			return emptyList;
		}
		else return someAdminCompList;
	}
	
	public Memento saveToMemento() {
		return new Memento(this);
	}
	
	public static class Memento {
		public List<String> deIds;
		public List<String> decIds;
		public List<String> vdIds;
		public List<String> ocIds;
		public List<String> propIds;
		public List<String> conIds;
		public List<String> vmIds;
		public List<String> repIds;
		public String loadCtxId;
		public String loadCSId;
		
		public Memento(CaDSRObjects _caDSRObjects) {
			deIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getDataElements(), deIds);
			
			decIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getDataElementConcepts(), decIds);
			
			vdIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getValueDomains(), vdIds);
			
			ocIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getObjectClasses(), ocIds);
			
			propIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getProperties(), propIds);
			
			conIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getConcepts(), conIds);
			
			vmIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getValueMeanings(), vmIds);
			
			repIds = new ArrayList<String>();
			doAdd(_caDSRObjects.getRepTerms(), repIds);
			
			if (_caDSRObjects.getLoadContext() != null && _caDSRObjects.getLoadContext().getId() != null) {
				loadCtxId = _caDSRObjects.getLoadContext().getId();
			}
			
			if (_caDSRObjects.getLoadClassificationScheme() != null && _caDSRObjects.getLoadClassificationScheme().getId() != null) {
				loadCtxId = _caDSRObjects.getLoadClassificationScheme().getId();
			}
		}
		
		private void doAdd(List<? extends AdminComponent> fromList, List<String> toList) {
			for (AdminComponent ac: fromList) {
				if (ac.getPublicId() != null) {
					toList.add(ac.getPublicId());
				}
				else if (ac.getId() != null){
					toList.add(ac.getId());
				}
			}
		}

	}
			
}
