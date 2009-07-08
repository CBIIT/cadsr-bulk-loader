package gov.nih.nci.ncicb.cadsr.loader.persister;

import gov.nih.nci.ncicb.cadsr.dao.DAOCreateException;
import gov.nih.nci.ncicb.cadsr.dao.RepresentationDAO;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponentClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.AlternateName;
import gov.nih.nci.ncicb.cadsr.domain.Concept;
import gov.nih.nci.ncicb.cadsr.domain.Definition;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.Representation;
import gov.nih.nci.ncicb.cadsr.loader.ElementsLists;
import gov.nih.nci.ncicb.cadsr.loader.defaults.UMLDefaults;
import gov.nih.nci.ncicb.cadsr.loader.event.ProgressListener;
import gov.nih.nci.ncicb.cadsr.loader.util.ConceptUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.ConventionUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.DAOAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.LookupUtil;
import gov.nih.nci.ncicb.cadsr.loader.util.PropertyAccessor;
import gov.nih.nci.ncicb.cadsr.loader.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

public class RepresentationPersister implements Persister {

	private ElementsLists elements = ElementsLists.getInstance();
	private UMLDefaults defaults = UMLDefaults.getInstance();
	private PersisterUtil persisterUtil;
	private RepresentationDAO representationDAO;
	
	private static Logger logger = Logger.getLogger(ObjectClassPersister.class);
	
	public RepresentationPersister() {
		initDAOs();
	}
	
	public PersisterUtil getPersisterUtil() {
		return persisterUtil;
	}

	public void setPersisterUtil(PersisterUtil persisterUtil) {
		this.persisterUtil = persisterUtil;
	}

	@Override
	public void persist() {
		Representation rep = DomainObjectFactory.newRepresentation();
	    List<Representation> reps = elements.getElements(rep);

	    if (reps != null)
	    {
	      int count = 0;

	      for (ListIterator<Representation> it = reps.listIterator(); it.hasNext();)
	      {
	    	  Representation newRep = null;

	        rep = it.next();
	        logger.debug(rep.getLongName());

	        rep.setContext(defaults.getMainContext());

	        String newDef = rep.getPreferredDefinition();

	        List<AlternateName> parsedAltNames = new ArrayList<AlternateName>(rep
	                .getAlternateNames());
	        rep.removeAlternateNames();
	        rep.removeDefinitions();

	        // Use case for existing Element
	        if (!StringUtil.isEmpty(rep.getPublicId()) && rep.getVersion() != null)
	        {
	          newRep = existingMapping(rep);
	          it.set(newRep);
	          persisterUtil.addPackageClassification(rep);

	          for (AlternateName an : parsedAltNames)
	          {
	            rep.addAlternateName(an);
	            newRep.addAlternateName(an);
	            persisterUtil.addAlternateName(rep, an);
	          }

	          logger.info(PropertyAccessor.getProperty("mapped.to.existing.oc"));
	          continue;
	        } // otherwise search by concepts

	        // does this oc exist?
	        List<String> eager = new ArrayList<String>();

	        String[] conceptCodes = rep.getPreferredName().split(":");
	        Concept[] concepts = new Concept[conceptCodes.length];
	        for (int i = 0; i < concepts.length; concepts[i] = LookupUtil
	                .lookupConcept(conceptCodes[i++]));

	        List<Representation> l = representationDAO.find(rep);

	        Concept primaryConcept = concepts[concepts.length - 1];

	        if (l.size() == 0)
	        {
	          rep.setLongName(ConceptUtil.longNameFromConcepts(concepts));
	          rep.setPreferredDefinition(ConceptUtil
	                  .preferredDefinitionFromConcepts(concepts));
	          //rep.setDefinitionSource(primaryConcept.getDefinitionSource());

	          rep.setVersion(1.0f);
	          rep.setWorkflowStatus(AdminComponent.WF_STATUS_RELEASED);
	          rep.setAudit(defaults.getAudit());
	          rep.setOrigin(defaults.getOrigin());
	          rep.setLifecycle(defaults.getLifecycle());

	          List<AdminComponentClassSchemeClassSchemeItem> acCsCsis = rep.getAcCsCsis();
	          try
	          {
	            newRep = representationDAO.create(rep, conceptCodes);
	            logger.info(PropertyAccessor.getProperty("created.oc"));

	          }
	          catch (DAOCreateException e)
	          {
	            logger.error(PropertyAccessor.getProperty("created.oc.failed", e
	                    .getMessage()));
	          } // end of try-catch
	          // restore this since we use for package
	          rep.setAcCsCsis(acCsCsis);

	        }
	        else {
	        	newRep = l.get(0);
	        	rep.setId(newRep.getId());
	        }
	        /*else
	        {
	          String newDefSource = primaryConcept.getDefinitionSource();
	          String newConceptDef = primaryConcept.getPreferredDefinition();
	          newRep = l.get(0);

	          rep.setId(newRep.getId());

	          for (AlternateName an : parsedAltNames)
	          {
	            newRep.addAlternateName(an);
	          }

	          logger.info(PropertyAccessor.getProperty("existed.oc"));

	          // is concept source the same?
	          // if not, then add alternate Def
	          if (!newDefSource.equals(newRep.getDefinitionSource()))
	          {
	            persisterUtil.addAlternateDefinition(rep, newConceptDef, newDefSource);
	          }

	        }*/

	        LogUtil.logAc(newRep, logger);
	        logger.info("public ID: " + newRep.getPublicId());

	        rep.setId(newRep.getId());
	        
	        if (newDef!=null) {
	        	persisterUtil.addAlternateDefinition(rep, newDef, Definition.TYPE_UML_CLASS);
	        }
	        

	        for (AlternateName an : parsedAltNames)
	        {
	          rep.addAlternateName(an);
	          persisterUtil.addAlternateName(rep, an);
	        }

	        it.set(newRep);
	        rep.setLongName(newRep.getLongName());
	        rep.setPreferredName(newRep.getPreferredName());
	        persisterUtil.addPackageClassification(rep);

	      }
	    }

	  }

	  private Representation existingMapping(Representation rep)  {

	    String newDef = rep.getPreferredDefinition();

//	     List<AlternateName> parsedAltNames = new ArrayList<AlternateName>(oc
//	             .getAlternateNames());
	    List<Representation> l = representationDAO.find(rep);

	    if (l.size() == 0)
	      throw new PersisterException(PropertyAccessor.getProperty(
	              "oc.existing.error", ConventionUtil.publicIdVersion(rep)));

	    Representation existingRep = l.get(0);

//	     for (AlternateName an : parsedAltNames)
//	     {
//	       persisterUtil.addAlternateName(existingOc, an);
//	       existingOc.addAlternateName(an);
//	     }

	    rep.setId(existingRep.getId());

	    if (!StringUtil.isEmpty(newDef)) 
	      persisterUtil.addAlternateDefinition(rep, newDef, Definition.TYPE_UML_CLASS);

	    return existingRep;

	  }
	  
	  private void initDAOs()  {
	    representationDAO = DAOAccessor.getRepresentationDAO();
	  }

	@Override
	public void setProgressListener(ProgressListener listener) {
		// TODO Auto-generated method stub
		
	}


}
