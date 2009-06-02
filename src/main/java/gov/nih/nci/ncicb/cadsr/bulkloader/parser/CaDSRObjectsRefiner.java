package gov.nih.nci.ncicb.cadsr.bulkloader.parser;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;

import java.util.ArrayList;
import java.util.List;

public class CaDSRObjectsRefiner {

	private List<DataElementConcept> deletedDECs;
	private List<ObjectClass> deletedOCs;
	private List<Property> deletedProps;
	private List<ValueDomain> deletedVDs;
	private List<Concept> deletedConcepts;
	
	private CaDSRObjectsRefiner() {
		deletedDECs = new ArrayList<DataElementConcept>();
		deletedOCs = new ArrayList<ObjectClass>();
		deletedProps = new ArrayList<Property>();
		deletedVDs = new ArrayList<ValueDomain>();
		deletedConcepts = new ArrayList<Concept>();
	}
	
	public static synchronized CaDSRObjects refine(CaDSRObjects caDSRObjects) {
		CaDSRObjectsRefiner refiner = new CaDSRObjectsRefiner();
		refiner.refineCDEs(caDSRObjects);
		refiner.refineDECs(caDSRObjects);
		refiner.refineOCs(caDSRObjects);
		refiner.refineProps(caDSRObjects);
		refiner.refineVDs(caDSRObjects);
		
		refiner.finalize(caDSRObjects);
		
		return caDSRObjects;
	}
	
	private void finalize(CaDSRObjects caDSRObjects) {
		caDSRObjects.getDataElementConcepts().removeAll(deletedDECs);
		caDSRObjects.getObjectClasses().removeAll(deletedOCs);
		caDSRObjects.getProperties().removeAll(deletedProps);
		caDSRObjects.getValueDomains().removeAll(deletedVDs);
		caDSRObjects.getConcepts().removeAll(deletedConcepts);
	}
	
	private void refineCDEs(CaDSRObjects caDSRObjects) {
		List<DataElement> dataElements = caDSRObjects.getDataElements();
		
		for (DataElement dataElement: dataElements) {
			DataElementConcept dec = dataElement.getDataElementConcept();
			ValueDomain vd = dataElement.getValueDomain();
			if (dataElement.getPublicId() != null) {
				if (dec != null) {
					deletedDECs.add(dec);
					dataElement.setDataElementConcept(null);
				}
				if (vd != null) {
					deletedVDs.add(vd);
					dataElement.setValueDomain(null);
				}
			}
			else {
				deletedDECs.remove(dec);
				deletedVDs.remove(vd);
			}
		}
	}
	
	private void refineDECs(CaDSRObjects caDSRObjects) {
		List<DataElementConcept> dataElementConcepts = caDSRObjects.getDataElementConcepts();
		
		for (DataElementConcept dataElementConcept: dataElementConcepts) {
			ObjectClass oc = dataElementConcept.getObjectClass();
			Property prop = dataElementConcept.getProperty();
			
			if (dataElementConcept.getPublicId() != null || deletedDECs.contains(dataElementConcept)) {
				if (oc != null) {
					deletedOCs.add(oc);
					dataElementConcept.setObjectClass(null);
				}
				if (prop != null) {
					deletedProps.add(prop);
					dataElementConcept.setProperty(null);
				}
			}
			else {
				deletedOCs.remove(oc);
				deletedProps.remove(prop);
			}
		}
	}
	
	private void refineOCs(CaDSRObjects caDSRObjects) {
		List<ObjectClass> objectClasses = caDSRObjects.getObjectClasses();
		
		for (ObjectClass objectClass: objectClasses) {
			ConceptDerivationRule cdr = objectClass.getConceptDerivationRule();
			
			boolean retain = true;
			if (objectClass.getPublicId() != null || deletedOCs.contains(objectClass)) {
				retain = false;
				objectClass.setConceptDerivationRule(null);
			}
			
			processConceptDerivationRule(cdr, retain);
		}
	}
	
	private void refineProps(CaDSRObjects caDSRObjects) {
		List<Property> properties = caDSRObjects.getProperties();
		
		for (Property prop: properties) {
			ConceptDerivationRule cdr = prop.getConceptDerivationRule();
			
			boolean retain = true;
			if (prop.getPublicId() != null || deletedProps.contains(prop)) {
				retain = false;
				prop.setConceptDerivationRule(null);
			}
			
			processConceptDerivationRule(cdr, retain);
		}
	}
	
	private void refineVDs(CaDSRObjects caDSRObjects) {
		List<ValueDomain> valueDomains = caDSRObjects.getValueDomains();
		
		for (ValueDomain valueDomain: valueDomains) {
			boolean retain = true;
			Representation representation = valueDomain.getRepresentation();
			
			if (valueDomain.getPublicId() != null || deletedVDs.contains(valueDomain)) {
				retain = false;
				valueDomain.setRepresentation(null);
			}
			
			if (representation != null) {
				ConceptDerivationRule cdr = representation.getConceptDerivationRule();
				processConceptDerivationRule(cdr, retain);
			}
			
			List<PermissibleValue> pvs = valueDomain.getPermissibleValues();
			if (pvs != null) {
				for (PermissibleValue pv: pvs) {
					ValueMeaning vm = pv.getValueMeaning();
					if (vm != null) {
						ConceptDerivationRule cdr = vm.getConceptDerivationRule();
						processConceptDerivationRule(cdr, retain);
					}
				}
			}
		}
	}
	
	private void processConceptDerivationRule(ConceptDerivationRule cdr, boolean retain) {
		if (cdr != null) {
			List<ComponentConcept> compConcepts = cdr.getComponentConcepts();
			if (compConcepts != null) {
				for (ComponentConcept compConcept: compConcepts) {
					Concept con = compConcept.getConcept();
					if (!retain) {
						deletedConcepts.add(con);
					}
					else {
						deletedConcepts.remove(con);
					}
				}
			}
		}
	}
}
