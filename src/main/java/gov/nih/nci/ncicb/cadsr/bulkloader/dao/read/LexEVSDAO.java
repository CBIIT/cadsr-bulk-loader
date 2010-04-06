package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import org.LexGrid.LexBIG.DataModel.Core.ResolvedCodedNodeReference;


/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 8, 2008
 * @since 
 */

public interface LexEVSDAO {

	public ResolvedCodedNodeReference getMetaThesaurusConcept(String cui, boolean includeRetired);
	public ResolvedCodedNodeReference getEVSNCItConcept(String code, boolean includeRetired);
}
