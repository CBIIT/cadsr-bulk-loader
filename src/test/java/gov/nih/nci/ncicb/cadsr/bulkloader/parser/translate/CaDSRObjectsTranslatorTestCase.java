package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.MainTestCase;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.AdminRecord_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Concept_caDSR11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.Definition_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ItemIdentifier_ISO11179;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate.Translator;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.domain.Concept;

import java.util.ArrayList;
import java.util.List;

public class CaDSRObjectsTranslatorTestCase extends MainTestCase {

	public void testTranslator() {
		Translator<CaDSRObjects> translator = SpringBeansUtil.getCaDSRObjectsTranslator();
		ISO11179Elements iso11179Elements = get11179Elements();
		
		CaDSRObjects caDSRObjects = translator.translate(iso11179Elements);
		
		assertNotNull(caDSRObjects);
		
		List<Concept> concepts = caDSRObjects.getConcepts();
		
		assertNotNull(concepts);
		
		assertTrue(concepts.size()==1);
		
		Concept concept = concepts.get(0);
		
		assertSame(concept.getPreferredName(),OBJECT_CLASS_QUALIFIER_CONCEPT_ID);
		
		assertSame(concept.getLongName(),OBJECT_CLASS_QUALIFIER_CONCEPT_LONG_NAME);
		
		String definition = concept.getPreferredDefinition();
		
		assertNotNull(definition);
		
		assertSame(definition, DEFINITION_TEXT);
	}
	
	private ISO11179Elements get11179Elements() {
		ISO11179Elements iso11179Elements = new ISO11179Elements();
		
		List<Concept_caDSR11179> concepts = new ArrayList<Concept_caDSR11179>();
		concepts.add(get11179Concept());
		
	//	iso11179Elements.setConcepts(concepts);
		
		return iso11179Elements;
	}
	
	private Concept_caDSR11179 get11179Concept() {
		Concept_caDSR11179 con = new Concept_caDSR11179();
		
		ItemIdentifier_ISO11179 identifier = new ItemIdentifier_ISO11179();
		identifier.setDataIdentifier(OBJECT_CLASS_QUALIFIER_CONCEPT_ID);
		
		AdminRecord_ISO11179 adminRecord = new AdminRecord_ISO11179();
		adminRecord.setIdentifier(identifier);
		
		con.setAdminRecord(adminRecord);
		
		Definition_ISO11179 def = new Definition_ISO11179();
		def.setText(DEFINITION_TEXT);
		def.setPreferredDefinition(true);
		
		List<Definition_ISO11179> defs = new ArrayList<Definition_ISO11179>();
		defs.add(def);
		con.setDefinitions(defs);
		//con.setLongName(OBJECT_CLASS_QUALIFIER_CONCEPT_LONG_NAME);
		
		return con;
	}
}
