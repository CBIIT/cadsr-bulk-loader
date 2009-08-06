package gov.nih.nci.ncicb.cadsr.bulkloader.dao.read;

import gov.nih.nci.evs.domain.Atom;
import gov.nih.nci.evs.domain.DescLogicConcept;
import gov.nih.nci.evs.domain.MetaThesaurusConcept;

import java.util.List;


/**
 * 
 * @author Ashwin Mathur
 * @version 1.0, Dec 8, 2008
 * @since 
 */

public interface LexEVSDAO {

	public MetaThesaurusConcept getMetaThesaurusConcept(String cui);
	public DescLogicConcept getEVSNCItConcept(String code);
	public DescLogicConcept getEVSPreNCItConcept(String code);
	public List<Atom> getAtoms(String cui);
}
