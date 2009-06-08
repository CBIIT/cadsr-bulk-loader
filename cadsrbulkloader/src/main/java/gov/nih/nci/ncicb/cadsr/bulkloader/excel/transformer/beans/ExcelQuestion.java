package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

public class ExcelQuestion extends Item {

	private String moduleName;
	private int questionNumber;
	private String alternateName;
	private String alternateNameType;
	private String preferredQuestion;
	private String alternateQuestion;
	private String questionNotes;
	
	private String cdeId;
	private String cdeLongName;
	private String cdeNotes;
	
	private String decId;
	private String ocQualConcepts;
	private String ocPrimConcepts;
	private String propQualConcepts;
	private String propPrimConcepts;
	private String decConceptualDomainId;
	private String decNotes;
	
	private String vdId;
	private String repTermQualConcepts;
	private String repTermPrimConcepts;
	private String vdConceptualDomainId;
	private String dataType;
	private String vdMaxLength;
	private String enumerated;
	private String pv;
	private String pvLength;
	private String vmConcepts;
	private String vmNotes;
	
	private boolean isBlank = true;
	
	@Override
	public int getItemNumber() {
		return 0;
	}
	
	@Override
	public List<Object> getNames() {
		List<Object> names = new ArrayList<Object>();
		names.add("Module Name");
		names.add("Question Number");
		names.add("Alternate Name");
		names.add("Alternate Name Type");
		names.add("Preferred Question Text");
		names.add("Alternate Question Text");
		names.add("Question Notes");
		
		names.add("CDE Id");
		names.add("CDE Long Name");
		names.add("CDE Notes");
		
		names.add("DEC Id");
		names.add("Object Class Qualifier Concepts");
		names.add("Object Class Primary Concepts");
		names.add("Property Qualifier Concepts");
		names.add("Property Qualifier Concepts");
		names.add("DEC Conceptual Domain Id");
		names.add("DEC Notes");
		
		names.add("VD Id");
		names.add("Representation Term Qualifier Concepts");
		names.add("Representation Term Primary Concepts");
		names.add("VD Conceptual Domain Id");
		names.add("Datatype");
		names.add("VD Max Length");
		names.add("Enumerated");
		names.add("Permissible Value");
		names.add("Permissible Value Length");
		names.add("VM Concepts");
		names.add("VM Notes");
		
		return names;
	}
	@Override
	public List<Object> getValues() {
		List<Object> values = new ArrayList<Object>();
		values.add(moduleName);
		values.add(questionNumber);
		values.add(alternateName);
		values.add(alternateNameType);
		values.add(preferredQuestion);
		values.add(alternateQuestion);
		values.add(questionNotes);
		
		values.add(cdeId);
		values.add(cdeLongName);
		values.add(cdeNotes);
		
		values.add(decId);
		values.add(ocQualConcepts);
		values.add(ocPrimConcepts);
		values.add(propQualConcepts);
		values.add(propPrimConcepts);
		values.add(decConceptualDomainId);
		values.add(decNotes);
		
		values.add(vdId);
		values.add(repTermQualConcepts);
		values.add(repTermPrimConcepts);
		values.add(vdConceptualDomainId);
		values.add(dataType);
		values.add(vdMaxLength);
		values.add(enumerated);
		values.add(pv);
		values.add(pvLength);
		values.add(vmConcepts);
		values.add(vmNotes);
		
		return values;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public int getQuestionNumber() {
		return questionNumber;
	}
	public void setQuestionNumber(int questionNumber) {
		this.questionNumber = questionNumber;
	}
	public String getAlternateName() {
		return alternateName;
	}
	public void setAlternateName(String alternateName) {
		this.alternateName = alternateName;
		if (isBlank && alternateName != null) { 
			isBlank = false; 
		}
	}
	public String getAlternateNameType() {
		return alternateNameType;
	}

	public void setAlternateNameType(String alternateNameType) {
		this.alternateNameType = alternateNameType;
		if (isBlank && alternateNameType != null) { 
			isBlank = false; 
		}
	}

	public String getPreferredQuestion() {
		return preferredQuestion;
	}
	public void setPreferredQuestion(String preferredQuestion) {
		this.preferredQuestion = preferredQuestion;
		if (isBlank && preferredQuestion != null) { 
			isBlank = false; 
		}
	}
	public String getAlternateQuestion() {
		return alternateQuestion;
	}
	public void setAlternateQuestion(String alternateQuestion) {
		this.alternateQuestion = alternateQuestion;
		if (isBlank && alternateQuestion != null) { 
			isBlank = false; 
		}
	}
	public String getQuestionNotes() {
		return questionNotes;
	}
	public void setQuestionNotes(String questionNotes) {
		this.questionNotes = questionNotes;
		if (isBlank && questionNotes != null) { 
			isBlank = false; 
		}
	}
	public String getCdeId() {
		return cdeId;
	}
	public void setCdeId(String cdeId) {
		this.cdeId = cdeId;
		if (isBlank && cdeId != null) { 
			isBlank = false; 
		}
	}
	public String getCdeLongName() {
		return cdeLongName;
	}
	public void setCdeLongName(String cdeLongName) {
		this.cdeLongName = cdeLongName;
		if (isBlank && cdeLongName != null) { 
			isBlank = false; 
		}
	}
	public String getCdeNotes() {
		return cdeNotes;
	}
	public void setCdeNotes(String cdeNotes) {
		this.cdeNotes = cdeNotes;
		if (isBlank && cdeNotes != null) { 
			isBlank = false; 
		}
	}
	public String getDecId() {
		return decId;
	}
	public void setDecId(String decId) {
		this.decId = decId;
		if (isBlank && decId != null) { 
			isBlank = false; 
		}
	}
	public String getOcQualConcepts() {
		return ocQualConcepts;
	}
	public void setOcQualConcepts(String ocQualConcepts) {
		this.ocQualConcepts = ocQualConcepts;
		if (isBlank && ocQualConcepts!=null) { 
			isBlank = false; 
		}
	}
	public String getOcPrimConcepts() {
		return ocPrimConcepts;
	}
	public void setOcPrimConcepts(String ocPrimConcepts) {
		this.ocPrimConcepts = ocPrimConcepts;
		if (isBlank && ocPrimConcepts != null) { 
			isBlank = false; 
		}
	}
	public String getPropQualConcepts() {
		return propQualConcepts;
	}
	public void setPropQualConcepts(String propQualConcepts) {
		this.propQualConcepts = propQualConcepts;
		if (isBlank && propQualConcepts != null) { 
			isBlank = false; 
		}
	}
	public String getPropPrimConcepts() {
		return propPrimConcepts;
	}
	public void setPropPrimConcepts(String propPrimConcepts) {
		this.propPrimConcepts = propPrimConcepts;
		if (isBlank && propPrimConcepts != null) { 
			isBlank = false; 
		}
	}
	public String getDecConceptualDomainId() {
		return decConceptualDomainId;
	}
	public void setDecConceptualDomainId(String decConceptualDomainId) {
		this.decConceptualDomainId = decConceptualDomainId;
		if (isBlank && decConceptualDomainId != null) { 
			isBlank = false; 
		}
	}
	public String getDecNotes() {
		return decNotes;
	}
	public void setDecNotes(String decNotes) {
		this.decNotes = decNotes;
	}
	public String getVdId() {
		return vdId;
	}
	public void setVdId(String vdId) {
		this.vdId = vdId;
		if (isBlank && vdId != null) { 
			isBlank = false; 
		}
	}
	public String getRepTermQualConcepts() {
		return repTermQualConcepts;
	}
	public void setRepTermQualConcepts(String repTermQualConcepts) {
		this.repTermQualConcepts = repTermQualConcepts;
		if (isBlank && repTermQualConcepts != null) { 
			isBlank = false; 
		}
	}
	public String getRepTermPrimConcepts() {
		return repTermPrimConcepts;
	}
	public void setRepTermPrimConcepts(String repTermPrimConcepts) {
		this.repTermPrimConcepts = repTermPrimConcepts;
		if (isBlank && repTermPrimConcepts != null) { 
			isBlank = false; 
		}
	}
	public String getVdConceptualDomainId() {
		return vdConceptualDomainId;
	}
	public void setVdConceptualDomainId(String vdConceptualDomainId) {
		this.vdConceptualDomainId = vdConceptualDomainId;
		if (isBlank && vdConceptualDomainId != null) { 
			isBlank = false; 
		}
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
		if (isBlank && dataType != null) { 
			isBlank = false; 
		}
	}
	public String getVdMaxLength() {
		return vdMaxLength;
	}
	public void setVdMaxLength(String vdMaxLength) {
		this.vdMaxLength = vdMaxLength;
		if (isBlank && vdMaxLength != null) { 
			isBlank = false; 
		}
	}
	public String getEnumerated() {
		return enumerated;
	}
	public void setEnumerated(String enumerated) {
		this.enumerated = enumerated;
		if (isBlank && enumerated != null) { 
			isBlank = false; 
		}
	}
	public String getPv() {
		return pv;
	}
	public void setPv(String pv) {
		this.pv = pv;
		if (isBlank && pv != null) { 
			isBlank = false; 
		}
	}
	public String getPvLength() {
		return pvLength;
	}
	public void setPvLength(String pvLength) {
		this.pvLength = pvLength;
		if (isBlank && pvLength != null) { 
			isBlank = false; 
		}
	}
	public String getVmConcepts() {
		return vmConcepts;
	}
	public void setVmConcepts(String vmConcepts) {
		this.vmConcepts = vmConcepts;
		if (isBlank && vmConcepts != null) { 
			isBlank = false; 
		}
	}
	public String getVmNotes() {
		return vmNotes;
	}
	public void setVmNotes(String vmNotes) {
		this.vmNotes = vmNotes;
	}
	
	public boolean isBlank() {
		return isBlank;
	}
}
