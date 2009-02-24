package gov.nih.nci.ncicb.cadsr.bulkloader.beans.castor;

import java.util.Date;
import java.util.List;

public class ValueMeaning_caDSR11179 extends AdminItem_ISO11179 {

	private Date beginDate;
	private Date endDate;
	private String description;
	private String id;
	private ConceptDerivationRule_caDSR11179 conceptDerivationRule;
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ConceptDerivationRule_caDSR11179 getConceptDerivationRule() {
		return conceptDerivationRule;
	}
	public void setConceptDerivationRule(
			ConceptDerivationRule_caDSR11179 conceptDerivationRule) {
		this.conceptDerivationRule = conceptDerivationRule;
	}
}
