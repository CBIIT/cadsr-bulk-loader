package gov.nih.nci.ncicb.cadsr.bulkloader.loader;

import gov.nih.nci.ncicb.cadsr.bulkloader.beans.CaDSRObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadObjects;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoadProperties;
import gov.nih.nci.ncicb.cadsr.bulkloader.beans.LoaderInput;
import gov.nih.nci.ncicb.cadsr.bulkloader.dao.BulkLoaderDAOFacade;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.ParseResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.parser.Parser;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.Persister;
import gov.nih.nci.ncicb.cadsr.bulkloader.persist.PersisterResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.util.CaDSRObjectsUtil;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.Validation;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationResult;
import gov.nih.nci.ncicb.cadsr.bulkloader.validate.ValidationStatus;
import gov.nih.nci.ncicb.cadsr.domain.AdminComponent;
import gov.nih.nci.ncicb.cadsr.domain.ClassSchemeClassSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationScheme;
import gov.nih.nci.ncicb.cadsr.domain.ClassificationSchemeItem;
import gov.nih.nci.ncicb.cadsr.domain.ConceptualDomain;
import gov.nih.nci.ncicb.cadsr.domain.Context;
import gov.nih.nci.ncicb.cadsr.domain.DataElement;
import gov.nih.nci.ncicb.cadsr.domain.DataElementConcept;
import gov.nih.nci.ncicb.cadsr.domain.DomainObjectFactory;
import gov.nih.nci.ncicb.cadsr.domain.ValueDomain;

import java.util.ArrayList;
import java.util.List;

public class CaDSRBulkLoaderImpl implements CaDSRBulkLoader{

	private Parser parser;
	private Validation validator;
	private Persister persister;
	private BulkLoaderDAOFacade daoFacade;
	
	private CaDSRObjectsUtil caDSRObjectsUtil = new CaDSRObjectsUtil();
	
	public Parser getParser() {
		return parser;
	}
	public void setParser(Parser parser) {
		this.parser = parser;
	}
	public Validation getValidator() {
		return validator;
	}
	public void setValidator(Validation validator) {
		this.validator = validator;
	}
	
	public Persister getPersister() {
		return persister;
	}
	public void setPersister(Persister persister) {
		this.persister = persister;
	}
	public BulkLoaderDAOFacade getDaoFacade() {
		return daoFacade;
	}
	public void setDaoFacade(BulkLoaderDAOFacade daoFacade) {
		this.daoFacade = daoFacade;
	}
	public LoadResult load(LoaderInput input, LoadProperties loadProperties) {
		LoadResult result = new LoadResult(input);
		
		ParseResult parseResult = parser.parse(input.getFileToLoad());
		result.setParseResult(parseResult);
		if (!parseResult.hasErrors()) {
			CaDSRObjects caDSRObjects = parseResult.getCaDSRObjects();
			LoadObjects loadObjects = getLoadObjects(loadProperties);
			
			caDSRObjectsUtil.sanitize(caDSRObjects);
			caDSRObjects = daoFacade.loadFromCaDSR(caDSRObjects, loadObjects);
			
			ValidationResult validationResult = performValidation(input, loadObjects, caDSRObjects);
			result.setValidationResult(validationResult);
			if (validationResult.isSuccessful() && !validationResult.hasErrors()) {
				removeDecommissionedObjects(validationResult.getDecommissionedItems(), caDSRObjects);
				PersisterResult persisterResult = persister.persist(caDSRObjects, loadObjects);
				result.setPersisterResult(persisterResult);
			}
		}
		
		return result;
	}
	
	private void removeDecommissionedObjects(List<AdminComponent> decommissionedItems, CaDSRObjects caDSRObjects) {
		if (decommissionedItems != null && decommissionedItems.size() > 0) {
			List<DataElement> des = caDSRObjects.getDataElements();
			List<DataElement> deRemoveList = new ArrayList<DataElement>();
			for (DataElement de: des) {
				if (decommissionedItems.contains(de) 
						|| decommissionedItems.contains(de.getDataElementConcept())
						|| decommissionedItems.contains(de.getValueDomain())) {
					deRemoveList.add(de);
				}
			}
			des.removeAll(deRemoveList);
			
			List<DataElementConcept> decs = caDSRObjects.getDataElementConcepts();
			List<DataElementConcept> decRemoveList = new ArrayList<DataElementConcept>();
			for (DataElementConcept dec: decs) {
				if (decommissionedItems.contains(dec) 
						|| decommissionedItems.contains(dec.getObjectClass())
						|| decommissionedItems.contains(dec.getProperty())) {
					decRemoveList.add(dec);
				}
			}
			decs.removeAll(decRemoveList);
			
			List<ValueDomain> vds = caDSRObjects.getValueDomains();
			List<ValueDomain> vdRemoveList = new ArrayList<ValueDomain>();
			for (ValueDomain vd: vds) {
				if (decommissionedItems.contains(vd) 
						|| decommissionedItems.contains(vd.getRepresentation())) {
					vdRemoveList.add(vd);
				}
			}
			vds.removeAll(vdRemoveList);
		}
	}
	
	private ValidationResult performValidation(LoaderInput input, LoadObjects loadObjects, CaDSRObjects caDSRObjects) {
		ValidationResult validationResult = null;
		if (input.isValidate()) {
			validationResult = validator.validate(caDSRObjects,loadObjects);
		}
		else {
			validationResult = new ValidationResult();
			validationResult.setValidationStatus(ValidationStatus.SUCCESS);
			validationResult.setValidationItem(caDSRObjects);
		}
		
		return validationResult;
	}
	
	private LoadObjects getLoadObjects(LoadProperties loadProperties) {
		LoadObjects loadObjects = new LoadObjects();
		
		Context loadContext = daoFacade.findContextByName(loadProperties.getContextName());
		ClassificationScheme loadClassScheme = daoFacade.getClassificationScheme(loadProperties.getClassificationSchemeName());
		
		if (loadClassScheme == null || loadClassScheme.getPublicId() == null) {
			throw new BulkLoaderRuntimeException("Could not find the given CS ["+loadProperties.getClassificationSchemeName()+"]");
		}
		
		ClassificationSchemeItem csi = getCSI(loadClassScheme, loadProperties.getClassificationSchemeItemName());
		if (csi == null) {
			throw new BulkLoaderRuntimeException("Could not find the CSI specified ["+loadProperties.getClassificationSchemeItemName()+"] for the given CS ["+loadProperties.getClassificationSchemeName()+"]");
		}
		
		String defaultCDName = loadProperties.getDefaultConceptualDomain();
		ConceptualDomain defaultCD = getDefaultCD(defaultCDName);
		
		if (defaultCD == null || defaultCD.getPublicId() == null) {
			throw new BulkLoaderRuntimeException("Could not find the default CD specified ["+loadProperties.getDefaultConceptualDomain()+"] in caDSR");
		}
		
		String source = loadProperties.getLoadSource();
		if (source != null && !source.trim().equals("")) {
			if (!daoFacade.sourceExists(source)) {
				throw new BulkLoaderRuntimeException("Could not find the source ["+source+"] in the list of approved sources");
			}
		}
		
		loadObjects.setLoadContext(loadContext);
		loadObjects.setLoadClassScheme(loadClassScheme);
		loadObjects.setLoadConceptualDomain(defaultCD);
		
		return loadObjects;
	}
	
	private ConceptualDomain getDefaultCD(String defaultCDName) {
		ConceptualDomain defaultCD = null;
		if (defaultCDName != null) {
			ConceptualDomain cd = DomainObjectFactory.newConceptualDomain();
			cd.setPreferredName(defaultCDName);
			
			List<ConceptualDomain> cds = daoFacade.findConceptualDomains(cd);
			if (cds != null && cds.size() > 0) {
				defaultCD = cds.get(0);
			}
		}
		
		return defaultCD;
	}
	private ClassificationSchemeItem getCSI(ClassificationScheme cs, String _csiLongName) {
		if (cs == null || _csiLongName == null) {
			return null;
		}
		
		List<ClassSchemeClassSchemeItem> csCsis = cs.getCsCsis();
		for (ClassSchemeClassSchemeItem csCsi: csCsis) {
			ClassificationSchemeItem csi = csCsi.getCsi();
			String csiLongName = csi.getLongName();
			if (csiLongName != null && csiLongName.equalsIgnoreCase(_csiLongName.trim())) {
				return csi;
			}
		}
		
		return null;
	}
	
}
