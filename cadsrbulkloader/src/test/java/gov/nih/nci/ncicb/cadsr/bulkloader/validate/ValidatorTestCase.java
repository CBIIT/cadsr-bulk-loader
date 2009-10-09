package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.UnitTestsUtil;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ValidatorTestCase extends gov.nih.nci.ncicb.cadsr.bulkloader.util.MainTestCase {

	private final static String dataURL = "/gov/nih/nci/ncicb/cadsr/bulkloader/validate/server_validation.xls";
	
	public ValidatorTestCase() {
		super("ValidatorTestCase", ValidatorTestCase.class, dataURL);
	}
	
	@Override
	protected void containerSetUp() throws Exception {
		
	}

	@Override
	protected boolean requiresDatabase() {
		return true;
	}

	@Override
	protected boolean runInRealContainer() {
		return false;
	}
	
	public void setUp() {
		try {
			super.setUp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testValidator() {
		Properties dbProps = getDBProperties();
		SpringBeansUtil.getInstance().initialize(dbProps);
		Validation validation = SpringBeansUtil.getInstance().getValidator();
		ValidationResult validationResult = validation.validate(getObject(), UnitTestsUtil.getDefaultLoadObjects());
		
		assertNotNull(validationResult);
		if (!validationResult.isSuccessful() || validationResult.hasErrors() ) {
			Exception validationException = validationResult.getException();
			if (validationException != null) {
				validationException.printStackTrace();
			}
			List<ValidationItemResult> itemResults = validationResult.getItemResults();
			assertNotNull(itemResults);
			for (ValidationItemResult itemResult: itemResults) {
				Object item = itemResult.getItem();
				assertNotNull(item);
				ValidationStatus itemStatus = itemResult.getStatus();
				assertNotNull(itemStatus);
				System.out.println("Validation object: "+item.getClass() +"; Status: "+ itemStatus.getMessage()+"; Message: "+itemResult.getMessage());
			}
		}
	}
	
	public void testValidatorFail() {
		
	}
	
	private CaDSRObjects getObject() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		DataElement de = DomainObjectFactory.newDataElement();
		de.setPublicId("6565");
		de.setVersion(new Float(1.0));
		
		AlternateName altName = DomainObjectFactory.newAlternateName();
		altName.setType("NMDP_FN");
		altName.setName("test alt name");
		
		de.addAlternateName(altName);
		
		List<DataElement> dataElements = new ArrayList<DataElement>();
		dataElements.add(de);
		
		Concept con = DomainObjectFactory.newConcept();
		con.setPreferredName("C0000");
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.add(con);
		
		caDSRObjects.setDataElements(dataElements);
		//caDSRObjects.setConcepts(concepts);
		
		return caDSRObjects;
	}
	
	private CaDSRObjects getInvalidObject() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		DataElement de = DomainObjectFactory.newDataElement();
		de.setPublicId("6565343");
		de.setVersion(new Float(1.0));
		
		List<DataElement> dataElements = new ArrayList<DataElement>();
		dataElements.add(de);
		
		Concept con = DomainObjectFactory.newConcept();
		con.setPreferredName("C0000");
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.add(con);
		
		caDSRObjects.setDataElements(dataElements);
		//caDSRObjects.setConcepts(concepts);
		
		return caDSRObjects;
	}
}
