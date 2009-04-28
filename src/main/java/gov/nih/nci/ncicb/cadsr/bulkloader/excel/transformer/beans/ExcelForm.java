package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans;

import gov.nih.nci.ncicb.cadsr.bulkloader.transformer.beans.Item;

import java.util.ArrayList;
import java.util.List;

public class ExcelForm extends Item {

	private String formName;
	private String context;
	private String classScheme;
	private String classSchemeItem;
	private String source;
	private List<ExcelQuestion> questions;
	
	@Override
	public int getItemNumber() {
		return 0;
	}
	
	@Override
	public List<Object> getNames() {
		List<Object> names = new ArrayList<Object>();
		names.add("Form Name");
		names.add("Context");
		names.add("Classification Scheme");
		names.add("Classification Scheme Item");
		names.add("Source");
		
		return names;
	}
	@Override
	public List<Object> getValues() {
		List<Object> values = new ArrayList<Object>();
		values.add(formName);
		values.add(context);
		values.add(classScheme);
		values.add(classSchemeItem);
		values.add(source);
		
		return values;
	}
	
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getClassScheme() {
		return classScheme;
	}
	public void setClassScheme(String classScheme) {
		this.classScheme = classScheme;
	}
	public String getClassSchemeItem() {
		return classSchemeItem;
	}
	public void setClassSchemeItem(String classSchemeItem) {
		this.classSchemeItem = classSchemeItem;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public List<ExcelQuestion> getQuestions() {
		return questions;
	}
	public void setQuestions(List<ExcelQuestion> questions) {
		this.questions = questions;
		List<Item> items = new ArrayList<Item>();
		
		for (ExcelQuestion question: questions) {
			items.add(question);
		}
		super.setSubItems(items);
	}
	
	
}
