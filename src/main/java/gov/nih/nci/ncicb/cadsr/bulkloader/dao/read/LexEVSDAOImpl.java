package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.evs.domain.Atom;
import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.domain.MetaThesaurusConcept;
import gov.nih.nci.evs.query.EVSQuery;
import gov.nih.nci.evs.query.EVSQueryImpl;
import gov.nih.nci.evs.security.SecurityToken;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOException;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.system.applicationservice.ApplicationException;
import gov.nih.nci.system.applicationservice.EVSApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 8, 2008
 * @since 
 */

public class LexEVSDAOImpl implements LexEVSDAO {

	private String _service = "EvsServiceInfo";
	
	public MetaThesaurusConcept getMetaThesaurusConcept(String cui) {
		try {
			MetaThesaurusConcept metaConcept = getMetaThesaurusConceptByCUI(cui);
			MetaThesaurusConcept searchedMetaConcept = doEVSSearch(metaConcept);
			
			return searchedMetaConcept;
		} catch (ApplicationException e) {
			throw new BulkLoaderDAORuntimeException(e);
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}
	
	public DescLogicConcept getDescLogicConcept(String code) {
		try {
			DescLogicConcept descConcept = getDescLogicConceptByCode(code);
			DescLogicConcept searchedDescLogicConcept = doEVSSearch(descConcept);
			
			return searchedDescLogicConcept;
		} catch (ApplicationException e) {
			throw new BulkLoaderDAORuntimeException(e);
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}
	
	public List<Atom> getAtoms(String cui){
		MetaThesaurusConcept metaConcept = getMetaThesaurusConcept(cui);
		List<Atom> atoms = metaConcept.getAtomCollection();
		return atoms;
	}
	
	private MetaThesaurusConcept doEVSSearch(MetaThesaurusConcept metaConcept) throws ApplicationException, Exception{
		EVSApplicationService evsAppService = getEVSAppService();
		List<Object> searchResults = evsAppService.search(gov.nih.nci.evs.domain.MetaThesaurusConcept.class, metaConcept);
		return getDefaultMetaConcept(searchResults);
	}
	
	private DescLogicConcept doEVSSearch(DescLogicConcept descConcept) throws ApplicationException, Exception{
		EVSApplicationService evsAppService = getEVSAppService();
		EVSQuery query = new EVSQueryImpl();
		SecurityToken token = new SecurityToken();
		token.setAccessToken("NCI2006_10D");
		query.addSecurityToken("NCI_Thesaurus", token);
		
		query.searchDescLogicConcepts("NCI_Thesaurus", descConcept.getCode(), 1);
		List<Object> searchResults = evsAppService.evsSearch(query);
		return getDefaultDescLogicConcept(searchResults);
	}
	
	private MetaThesaurusConcept getDefaultMetaConcept(List<Object> metaConcepts) {
		if (metaConcepts != null && metaConcepts.size() > 0) {
			MetaThesaurusConcept newMetaConcept = (MetaThesaurusConcept)metaConcepts.get(0);
			
			return newMetaConcept;
		}
		
		else return null; //createEmptyMetaConcept();
	}
	
	private DescLogicConcept getDefaultDescLogicConcept(List<Object> descConcepts) {
		if (descConcepts != null && descConcepts.size() > 0) {
			DescLogicConcept newDescConcept = (DescLogicConcept)descConcepts.get(0);
			
			return newDescConcept;
		}
		
		else return null; //createEmptyMetaConcept();
	}
	
	private MetaThesaurusConcept createEmptyMetaConcept() {
		MetaThesaurusConcept metaConcept = new MetaThesaurusConcept();
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		metaConcept.setAtomCollection(atoms);
		
		return metaConcept;
	}
	
	private MetaThesaurusConcept getMetaThesaurusConceptByCUI(String cui) {
		MetaThesaurusConcept metaConcept = new MetaThesaurusConcept();
		metaConcept.setCui(cui);
		
		return metaConcept;
	}
	
	private DescLogicConcept getDescLogicConceptByCode(String code) {
		DescLogicConcept descConcept = new DescLogicConcept();
		descConcept.setCode(code);
		
		return descConcept;
	}
	
	private EVSApplicationService getEVSAppService() throws Exception{
		EVSApplicationService appSvc = (EVSApplicationService)ApplicationServiceProvider.getApplicationService(_service);
		
		return appSvc;
	}
}
