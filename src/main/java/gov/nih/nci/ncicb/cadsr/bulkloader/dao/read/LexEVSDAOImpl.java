package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAORuntimeException;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedCodedNodeReference;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.PropertyType;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.LBConstants.MatchAlgorithms;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 8, 2008
 * @since 
 */

public class LexEVSDAOImpl implements LexEVSDAO {

	private static final String EVS_NCIT_VOCAB_NAME = "NCI_Thesaurus";
	private static final String EVS_PRE_NCIT_VOCAB_NAME = "Pre NCI Thesaurus";
	
	private static LexBIGService service;
	private String _service = "EvsServiceInfo";
	
	static {
		try {
			service = (LexBIGService)ApplicationServiceProvider.getApplicationService("EvsServiceInfo");
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
	}
	
	public ResolvedCodedNodeReference getMetaThesaurusConcept(String cui, boolean includeRetired) {
		try {
			ResolvedConceptReferenceList conRefList = getEVSConcept(cui, "MetaThesaurus", includeRetired);
			if (conRefList.getResolvedConceptReferenceCount() > 0) {
				return conRefList.getResolvedConceptReference(0);
			}
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
		
		return new ResolvedCodedNodeReference();
	}
	
	public ResolvedCodedNodeReference getEVSNCItConcept(String code, boolean includeRetired) {
		try {
			ResolvedConceptReferenceList conRefList = getEVSConcept(code, "NCI Thesaurus", includeRetired);
			if (conRefList.getResolvedConceptReferenceCount() > 0) {
				return conRefList.getResolvedConceptReference(0);
			}
		} catch (Exception e) {
			throw new BulkLoaderDAORuntimeException(e);
		}
		
		return new ResolvedCodedNodeReference();
	}
	
	private ResolvedConceptReferenceList getEVSConcept(String cui, String vocab, boolean includeRetired) throws Exception {
		CodedNodeSet cns = service.getNodeSet(vocab, null, null);
		if (!includeRetired) {
			cns.restrictToStatus(CodedNodeSet.ActiveOption.ACTIVE_ONLY, null);
		}
		PropertyType[] propTypes = new PropertyType[]{PropertyType.PRESENTATION, PropertyType.DEFINITION};
		cns = cns.restrictToMatchingProperties(
				Constructors.createLocalNameList("conceptCode"), 
				propTypes, 
				cui, 
				MatchAlgorithms.exactMatch.name(), 
				null
			);
		
		ResolvedConceptReferenceList conRefList = cns.resolveToList(null, null, null, null, -1);
		
		return conRefList;
	}

}
