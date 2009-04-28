package gov.nih.nci.ncicb.cadsr.bulkloader.validate;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;

import java.util.ArrayList;
import java.util.List;

public class ValidatorTestCase extends MainTestCase {

	public void testValidator() {
		Validation validation = SpringBeansUtil.getValidator();
		ValidationResult validationResult = validation.validate(getObject(), true);
		
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
	
	private CaDSRObjects getObject() {
		CaDSRObjects caDSRObjects = new CaDSRObjects();
		
		DataElement de = DomainObjectFactory.newDataElement();
		de.setPublicId("6565");
		de.setVersion(new Float(1.0));
		
		List<DataElement> dataElements = new ArrayList<DataElement>();
		dataElements.add(de);
		
		Concept con = DomainObjectFactory.newConcept();
		con.setPreferredName("C0000");
		
		List<Concept> concepts = new ArrayList<Concept>();
		concepts.add(con);
		
		caDSRObjects.setDataElements(dataElements);
		caDSRObjects.setConcepts(concepts);
		
		return caDSRObjects;
	}
}
