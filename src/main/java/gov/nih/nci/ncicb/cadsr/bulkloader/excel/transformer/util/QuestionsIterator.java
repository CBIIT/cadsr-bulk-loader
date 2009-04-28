package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelQuestion;

import java.util.ArrayList;
import java.util.List;

public class QuestionsIterator {
	private final List<ExcelQuestion> questions;
	int i = 0;
	
	public QuestionsIterator(List<ExcelQuestion> _questions) {
		this.questions = _questions;
	}
	
	public boolean hasNext() {
		return i < questions.size();
	}
	
	public synchronized List<ExcelQuestion> next() {
		ExcelQuestion question = questions.get(i);

		String enumerated = question.getEnumerated();
		if (enumerated == null || enumerated.equalsIgnoreCase("no")) {
			i++;
			return questions.subList(i-1, i);
		}
		else {
			int questionNumber = question.getQuestionNumber();
			int nextQuestionNumber = questionNumber;
			int startIndex = i;
			
			while (i < questions.size()-1 && nextQuestionNumber <= questionNumber) {
				i++;
				question = questions.get(i);
				nextQuestionNumber = question.getQuestionNumber();
			}
			
			if (startIndex == i) {
				i++;
				List<ExcelQuestion> deSubList = new ArrayList<ExcelQuestion>();
				deSubList.add(questions.get(startIndex));
				return deSubList;
			}
			else {
				return questions.subList(startIndex, i);
			}
		}
	}
}
