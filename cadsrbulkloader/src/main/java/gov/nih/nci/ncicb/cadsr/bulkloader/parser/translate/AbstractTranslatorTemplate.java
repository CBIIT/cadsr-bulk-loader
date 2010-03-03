package gov.nih.nci.ncicb.cadsr.bulkloader.parser.translate;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor.ISO11179Elements;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.util.ParserUtil;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Jan 21, 2009
 * @since 
 */

public abstract class AbstractTranslatorTemplate implements Translator<CaDSRObjects> {

	ParserUtil util = new ParserUtil();
	
	private AbstractTranslatorTemplate child;
	protected BulkLoaderDAOFacade daoFacade;
	
	public AbstractTranslatorTemplate getChild() {
		return child;
	}

	public void setChild(AbstractTranslatorTemplate child) {
		this.child = child;
	}

	public void setDaoFacade(BulkLoaderDAOFacade daoFacade) {
		this.daoFacade = daoFacade;
	}

	public final TranslatorResult<CaDSRObjects> translate(ISO11179Elements iso11179Elements) {
		TranslatorResult<CaDSRObjects> result = new TranslatorResult<CaDSRObjects>();
		result.setIso11179Elements(iso11179Elements);
		
		try {
			CaDSRObjectRegistry objRegistry = new CaDSRObjectRegistry();
			
			objRegistry = callChildAndTranslateElement(iso11179Elements, objRegistry);
			
			CaDSRObjects caDSRObjects = new CaDSRObjects();
			
			caDSRObjects.setConcepts(objRegistry.getConcepts());
			caDSRObjects.setProperties(objRegistry.getProperties());
			caDSRObjects.setObjectClasses(objRegistry.getObjectClasses());
			caDSRObjects.setValueDomains(objRegistry.getValueDomains());
			caDSRObjects.setRepTerms(getRepTerms(objRegistry.getValueDomains()));
			caDSRObjects.setDataElementConcepts(objRegistry.getDataElementConcepts());
			caDSRObjects.setDataElements(objRegistry.getDataElements());
			caDSRObjects.setValueMeanings(objRegistry.getValueMeanings());
			
			result.setTranslatedObject(caDSRObjects);
			result.setStatus(TranslatorStatus.SUCCESS);
			
		} catch (Exception e) {
			result.setException(e);
			result.setStatus(TranslatorStatus.FAILURE);
		}
		
		return result;
	}
	
	private List<Representation> getRepTerms(List<ValueDomain> valueDomains) {
		List<Representation> repTerms = new ArrayList<Representation>();
		
		if (valueDomains != null) {
			for (ValueDomain valueDomain: valueDomains) {
				Representation repTerm = valueDomain.getRepresentation();
				if (repTerm != null) {
					repTerms.add(repTerm);
				}
			}
		}
		
		return repTerms;
	}
	
	private CaDSRObjectRegistry callChildAndTranslateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry) {
		CaDSRObjectRegistry currentObjRegistry = objRegistry;
		if (child != null) {
			currentObjRegistry = child.callChildAndTranslateElement(iso11179Elements, objRegistry);
		}
		return translateElement(iso11179Elements, currentObjRegistry);
	}
		
	protected abstract CaDSRObjectRegistry translateElement(ISO11179Elements iso11179Elements, CaDSRObjectRegistry objRegistry);

}
