/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.TransformerInputParams;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ComponentConcept;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.ConceptDerivationRule;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ObjectClass;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.Property;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.domain.ValueMeaning;
import gov.nih.nci.ncicb.cadsr.domain.bean.ConceptBean;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 1, 2008
 * @since 
 */

public abstract class TestCaseHelper extends TestCase {

	protected final String validFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/valid.xml";
	protected final String invalidFilePath = "gov/nih/nci/ncicb/cadsr/bulkloader/schema/validator/invalid.xml";
	
	protected final String OBJECT_CLASS_QUALIFIER_CONCEPT_ID = "C12551";
	protected final String OBJECT_CLASS_QUALIFIER_CONCEPT_LONG_NAME = "HSC";
	
	protected final String OBJECT_CLASS_PRIMARY_CONCEPT_ID = "C15342";
	protected final String OBJECT_CLASS_PRIMARY_CONCEPT_LONG_NAME = "Transplantation";
	
	protected final String PROPERTY_QUALIFIER_CONCEPT_ID = "C25594";
	protected final String PROPERTY_QUALIFIER_CONCEPT_LONG_NAME = "Not";
	
	protected final String PROPERTY_PRIMARY_CONCEPT_ID = "C38000";
	protected final String PROPERTY_PRIMARY_CONCEPT_LONG_NAME = "Performed";
	
	protected final String DEFINITION_TEXT = "def2";
	protected final String DEFINITION_SOURCE = "NCI";
	
	protected final String CONCEPT_CONCAT_STR = ":";
	
	private List<DataElement> dataElements;
	private List<DataElementConcept> dataElementConcepts;
	private List<ValueDomain> valueDomains;
	private List<ObjectClass> objectClasses;
	private List<Property> properties;
	private List<Concept> objectClassConcepts;
	private List<Concept> propertiesConcepts;
	
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
	}

	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
	protected File getValidFile() {
		return getClasspathFile(validFilePath);
	}
	
	protected File getInValidFile() {
		return getClasspathFile(invalidFilePath);
	}
	
	protected String getClasspath() {
		ClassLoader classLoader = TestCaseHelper.class.getClassLoader();
		String filePath = classLoader.getResource(".").getPath();
		
		return filePath;
	}
	
	protected File getClasspathFile(String fileName) {
		String classpath = getClasspath();
		File f  = new File(classpath+fileName);
		
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return f;
	}
	
	protected TransformerInputParams getParams(String _inputFilePath, String _outputFilePath) {
		TransformerInputParams inputParams = new TransformerInputParams();
		
		File inputFile = getClasspathFile(_inputFilePath);
		File outputFile = getClasspathFile(_outputFilePath);
		
		inputParams.setInputFile(inputFile);
		inputParams.setOutputFile(outputFile);
		inputParams.setValidate(true);
		
		return inputParams;
	}
	
	protected List<DataElement> getDEList() {
		if (dataElements == null) {
			dataElements = new ArrayList<DataElement>();
			
			List<DataElementConcept> decList = getDECList();
			List<ValueDomain> vds = getVDList();
			
			Iterator<DataElementConcept> decIter = decList.iterator();
			Iterator<ValueDomain> vdIter = vds.iterator();
			
			while (decIter.hasNext() && vdIter.hasNext()) {
				DataElement de = DomainObjectFactory.newDataElement();
				DataElementConcept dec = decIter.next();
				ValueDomain vd = vdIter.next();
				
				de.setDataElementConcept(dec);
				de.setValueDomain(vd);
				de.setLongName(getDELongName(dec, vd));
				addCSCSIs(de);
				
				dataElements.add(de);
			}
		}
		
		
		return dataElements;
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
		List<ComponentConcept> compConcepts = cdr.getComponentConcepts();
		String longName = "";
		for (ComponentConcept compCon: compConcepts) {
			String conLongName = compCon.getConcept().getLongName();
			longName = longName.length()==0?conLongName:longName+" "+conLongName;
		}
		
		return longName;
	}
	
	public String getVDLongName(ValueDomain vd) {
		return deriveLongName(vd.getConceptDerivationRule());
	}
		
	protected List<ValueDomain> getVDList() {
		if (valueDomains == null) {
			valueDomains = new ArrayList<ValueDomain>();
			
			List<PermissibleValue> pvs = getPermissibleValues();
			ValueDomain vd = DomainObjectFactory.newValueDomain();
			vd.setPermissibleValues(pvs);
			vd.setConceptualDomain(getVDConceptualDomain());
			
			Representation rep = DomainObjectFactory.newRepresentation();
			rep.setPreferredName("C12551:C15342:C25594:C38000:C2");
			
			vd.setRepresentation(rep);
			vd.setPreferredDefinition("C12551:C15342:C25594:C38000:C2");
			vd.setConceptDerivationRule(getVMConDerRules().get(0));
			vd.setDataType("CHARACTER");
			vd.setVdType("E");
			vd.setLongName(getVDLongName(vd));
			
			valueDomains.add(vd);
		}
		
		return valueDomains;
	}
	
	private ConceptualDomain getVDConceptualDomain() {
		ConceptualDomain conDomain = DomainObjectFactory.newConceptualDomain();
		conDomain.setPublicId("2008538");
		conDomain.setVersion(1.0f);
		conDomain.setPreferredName("Therapies");
		conDomain.setWorkflowStatus("RELEASED");
		
		Context ctx = DomainObjectFactory.newContext();
		ctx.setName("caBIG");
		ctx.setId("D9344734-8CAF-4378-E034-0003BA12F5E7");
		
		conDomain.setContext(ctx);
		
		return conDomain;
	}
	
	protected List<PermissibleValue> getPermissibleValues() {
		List<PermissibleValue> pvs = new ArrayList<PermissibleValue>();
		
		PermissibleValue pv1 = DomainObjectFactory.newPermissibleValue();
		pv1.setValue("HSCT was canceled, but patient is alive");
		
		PermissibleValue pv2 = DomainObjectFactory.newPermissibleValue();
		pv2.setValue("patient died between start of preparative regimen and HSCT");
		
		
		pvs.add(pv1);
		pvs.add(pv2);
		
		List<ValueMeaning> vms = getValueMeanings();
		
		Iterator<PermissibleValue> pvIter = pvs.iterator();
		Iterator<ValueMeaning> vmIter = vms.iterator();
		
		while (pvIter.hasNext() && vmIter.hasNext()) {
			PermissibleValue pv = pvIter.next();
			pv.setValueMeaning(vmIter.next());
		}
		
		return pvs;
	}
	
	protected List<ValueMeaning> getValueMeanings() {
		List<ValueMeaning> vms = new ArrayList<ValueMeaning>();
		
		List<ConceptDerivationRule> conDerRules = getVMConDerRules();
		
		for (ConceptDerivationRule conDerRule: conDerRules) {
			ValueMeaning vm = DomainObjectFactory.newValueMeaning();
			vm.setConceptDerivationRule(conDerRule);
			
			String vmName = "";
			List<ComponentConcept> compCons = conDerRule.getComponentConcepts();
			for (ComponentConcept compCon: compCons) {
				String conLongName = compCon.getConcept().getLongName();
				vmName = vmName.length()==0?conLongName:vmName+"_"+conLongName;
			}
			
			vm.setLongName(vmName);
			vms.add(vm);
		}
		
		return vms;
	}
	
	private List<ConceptDerivationRule> getVMConDerRules() {
		List<ConceptDerivationRule> conDerRules = new ArrayList<ConceptDerivationRule> ();
		
		ConceptDerivationRule conDerRule1 = DomainObjectFactory.newConceptDerivationRule();
		List<Concept> vm1Concepts = getObjectClassConcepts();
		conDerRule1.setComponentConcepts(getComponentConcepts(conDerRule1, vm1Concepts));
		
		ConceptDerivationRule conDerRule2 = DomainObjectFactory.newConceptDerivationRule();
		List<Concept> vm2Concepts = getPropertiesConcepts();
		conDerRule2.setComponentConcepts(getComponentConcepts(conDerRule2, vm2Concepts));
		
		conDerRules.add(conDerRule1);
		conDerRules.add(conDerRule2);
		
		return conDerRules;
	}
	
	private List<ComponentConcept> getComponentConcepts(ConceptDerivationRule conDerivRule, List<Concept> concepts) {
		List<ComponentConcept> compCons = new ArrayList<ComponentConcept>();
		
		for (Concept con: concepts) {
			ComponentConcept compCon = DomainObjectFactory.newComponentConcept();
			compCon.setConcept(con);
			compCon.setConceptDerivationRule(conDerivRule);
			
			compCons.add(compCon);
		}
		return compCons;
	}
	
	protected List<DataElementConcept> getDECList() {
		if (dataElementConcepts == null) {
			List<ObjectClass> objClasses = getObjectClassList();
			List<Property> props = getPropertiesList();
			
			Iterator<ObjectClass> objClassIter = objClasses.iterator();
			Iterator<Property> propsIter = props.iterator();

			dataElementConcepts = new ArrayList<DataElementConcept>(objClasses.size());
			
			while (objClassIter.hasNext() && propsIter.hasNext()) {
				DataElementConcept dec = DomainObjectFactory.newDataElementConcept();
				dec.setObjectClass(objClassIter.next());
				dec.setProperty(propsIter.next());
				dec.setLongName(getDECLongName(dec));
				
				dataElementConcepts.add(dec);
			}
		}
		
		return dataElementConcepts;
	}
	
	protected List<ObjectClass> getObjectClassList() {
		
		if (objectClasses == null) {
			objectClasses = new ArrayList<ObjectClass>();
			
			ObjectClass objectClass = DomainObjectFactory.newObjectClass();
			
			String preferredName = OBJECT_CLASS_QUALIFIER_CONCEPT_ID
									+CONCEPT_CONCAT_STR
									+OBJECT_CLASS_PRIMARY_CONCEPT_ID;
			
			objectClass.setPreferredName(preferredName);
			objectClass.setPreferredDefinition(DEFINITION_TEXT);
			objectClass.setConceptDerivationRule(getConceptDerivationRule(getObjectClassConcepts()));
			
			objectClasses.add(objectClass);
		}
		
		return objectClasses;
	}
	
	public ConceptDerivationRule getConceptDerivationRule(List<Concept> concepts) {
		ConceptDerivationRule cdr = DomainObjectFactory.newConceptDerivationRule();
		List<ComponentConcept> compConcepts = getComponentConcepts(cdr, concepts);
		
		cdr.setComponentConcepts(compConcepts);
		
		return cdr;
	}
	
	protected List<Concept> getObjectClassConcepts() {
		if (objectClassConcepts == null) {
			objectClassConcepts = new ArrayList<Concept>();
			
			Concept concept1 = new ConceptBean();
			concept1.setPreferredName(OBJECT_CLASS_QUALIFIER_CONCEPT_ID);
			concept1.setLongName(OBJECT_CLASS_QUALIFIER_CONCEPT_LONG_NAME);
			concept1.setPreferredDefinition(DEFINITION_TEXT);
			concept1.setDefinitionSource(DEFINITION_SOURCE);
			
			Concept concept2 = new ConceptBean();
			concept2.setPreferredName(OBJECT_CLASS_PRIMARY_CONCEPT_ID);
			concept2.setLongName(OBJECT_CLASS_PRIMARY_CONCEPT_LONG_NAME);
			concept2.setPreferredDefinition(DEFINITION_TEXT);
			concept2.setDefinitionSource(DEFINITION_SOURCE);
			
			objectClassConcepts.add(concept1);
			objectClassConcepts.add(concept2);
		}

		return objectClassConcepts;
	}
	
	protected List<Property> getPropertiesList() {
		if (properties == null) {
			properties = new ArrayList<Property>();
			
			Property property = DomainObjectFactory.newProperty();
			
			String preferredName = PROPERTY_QUALIFIER_CONCEPT_ID
									+CONCEPT_CONCAT_STR
									+PROPERTY_PRIMARY_CONCEPT_ID;
			
			property.setPreferredName(preferredName);
			property.setPreferredDefinition(DEFINITION_TEXT);
			property.setConceptDerivationRule(getConceptDerivationRule(getPropertiesConcepts()));
			
			properties.add(property);
		}
		
		return properties;
	}
	
	protected List<Concept> getPropertiesConcepts() {
		if (propertiesConcepts == null) {
			propertiesConcepts = new ArrayList<Concept>();
			
			Concept concept1 = new ConceptBean();
			concept1.setPreferredName(PROPERTY_QUALIFIER_CONCEPT_ID);
			concept1.setLongName(PROPERTY_QUALIFIER_CONCEPT_LONG_NAME);
			concept1.setPreferredDefinition(DEFINITION_TEXT);
			concept1.setDefinitionSource(DEFINITION_SOURCE);
			
			Concept concept2 = new ConceptBean();
			concept2.setPreferredName(PROPERTY_PRIMARY_CONCEPT_ID);
			concept2.setLongName(PROPERTY_PRIMARY_CONCEPT_LONG_NAME);
			concept2.setPreferredDefinition(DEFINITION_TEXT);
			concept2.setDefinitionSource(DEFINITION_SOURCE);
			
			propertiesConcepts.add(concept1);
			propertiesConcepts.add(concept2);
		}
		
		return propertiesConcepts;
	}
	
	private void addCSCSIs(AdminComponent adminComp) {
		List<AdminComponentClassSchemeClassSchemeItem> newACCsCSIs = new ArrayList<AdminComponentClassSchemeClassSchemeItem>();
		
		ClassificationScheme classScheme = DomainObjectFactory.newClassificationScheme();
		classScheme.setLongName("NMDP: CDEs to review");
		
		ClassificationSchemeItem classSchemeItem = DomainObjectFactory.newClassificationSchemeItem();
		classSchemeItem.setLongName("2100: 100 Days Post-HSCT Data");
		
		ClassSchemeClassSchemeItem csCsi = DomainObjectFactory.newClassSchemeClassSchemeItem();
		csCsi.setCs(classScheme);
		csCsi.setCsi(classSchemeItem);
		
		AdminComponentClassSchemeClassSchemeItem acCsCsi = DomainObjectFactory.newAdminComponentClassSchemeClassSchemeItem();
		acCsCsi.setCsCsi(csCsi);
		
		newACCsCSIs.add(acCsCsi);
		
		adminComp.setAcCsCsis(newACCsCSIs);
	}
	
	protected Context getDefaultContext() {
		Context ctx = DomainObjectFactory.newContext();
		ctx.setName("NHLBI");
		
		return ctx;
	}
	
	protected ClassificationScheme getDefaultClassificationScheme() {
		ClassificationScheme classScheme = DomainObjectFactory.newClassificationScheme();
		classScheme.setPreferredName("NMDP: CDEs to be Reviewed");
		classScheme.setLongName("NMDP: CDEs to review");
		classScheme.setPreferredDefinition("NMDP CDEs to be reviewed");
		
		return classScheme;
	}
	
	protected LoadProperties getDefaultLoadProperties() {
		
		LoadProperties loadProperties = new LoadProperties();
		loadProperties.setContextName("NHLBI");
		loadProperties.setClassificationSchemeName("NMDP: CDEs to be Reviewed");
		loadProperties.setClassificationSchemeItemName("2100: 100 Days Post-HSCT Data");
		
		return loadProperties;
	}
	
	protected LoadObjects getDefaultLoadObjects() {
		LoadObjects loadObjects = new LoadObjects();
		
		loadObjects.setLoadContext(getDefaultContext());
		loadObjects.setLoadClassScheme(getDefaultClassificationScheme());
		
		return loadObjects;
	}
}
