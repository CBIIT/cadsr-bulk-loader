package gov.nih.nci.ncicb.cadsr.bulkloader.dao;

import gov.nih.nci.ncicb.cadsr.bulkloader.util.SpringBeansUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.TestCaseHelper;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.PermissibleValue;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsModule;
import gov.nih.nci.ncicb.cadsr.loader.ext.EvsResult;

import java.util.ArrayList;
import java.util.List;

public class DAOTestCase extends TestCaseHelper {

	private BulkLoaderDAOFacade dao = SpringBeansUtil.getInstance().getDAOFacade();
		
	protected boolean ignoreVD() {
		return true;
	}
	
	protected boolean isUsePrivateAPI() {
		return true;
	}
	
/*	public void testClassificationScheme() {
		
		ClassificationScheme cs = DomainObjectFactory.newClassificationScheme();
		cs.setLongName("NMDP: CDEs to be Reviewed");
		
		ClassificationScheme retrievedCS = dao.getClassificationScheme(cs);
		assertNotNull(retrievedCS);
	}*/
	
	public void testGetVDs1() {
		List<ValueDomain> vds = getVDList();
		
		List<ValueDomain> foundList1 = new ArrayList<ValueDomain>();
		for (ValueDomain vd: vds) {
			List<ValueDomain> foundVDs = dao.findValueDomains(vd);
			foundList1.addAll(foundVDs);
		}
		
		for (int i=0; i<foundList1.size(); i++) {
			assertNotNull(foundList1.get(i).getPermissibleValues());
			
			List<PermissibleValue> pvList1 = foundList1.get(i).getPermissibleValues();
			for (PermissibleValue pv: pvList1) {
				assertNotNull(pv);
			}
			
		}
		
	}
	
	public void testGetVDs2() {
		List<ValueDomain> vds = getVDList();
		
		List<ValueDomain> foundList2 = new ArrayList<ValueDomain>();
		for (ValueDomain vd: vds) {
			List<ValueDomain> foundVDs = dao.findValueDomains(vd);
			foundList2.addAll(foundVDs);
		}
		
		for (int i=0; i<foundList2.size(); i++) {
			assertNotNull(foundList2.get(i).getPermissibleValues());
			
			List<PermissibleValue> pvList2 = foundList2.get(i).getPermissibleValues();
			for (PermissibleValue pv: pvList2) {
				assertNotNull(pv);
			}
		}
		
	}
	
	public void testGetPreNCIConcepts() {
		Concept con = dao.findEVSConceptByCUI("C27232", false);
		assertNotNull(con);
		System.out.println(con.getPreferredDefinition());
	}
	
	public void testEVSModule() {
		EvsModule evsModule = new EvsModule("NCI Thesaurus");
		EvsResult result = evsModule.findByConceptCode("C1234", false);
		Concept con = result.getConcept();
		assertNotNull(con);
		System.out.println(con.getPreferredDefinition());
	}
}
