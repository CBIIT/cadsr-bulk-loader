/*L
 * Copyright Oracle Inc, SAIC-F.
 *
 * Distributed under the OSI-approved BSD 3-Clause License.
 * See http://ncip.github.com/cadsr-bulk-loader/LICENSE.txt for details.
 */

package gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.util;

import gov.nih.nci.ncicb.cadsr.bulkloader.excel.transformer.beans.ExcelQuestion;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionsIterator {
	private final List<ExcelQuestion> questions;
	private AtomicInteger i = new AtomicInteger(0);
	
	public QuestionsIterator(List<ExcelQuestion> _questions) {
		if (_questions == null) {
			this.questions = new ArrayList<ExcelQuestion>();
		}
		else {
			this.questions = removeBlanks(_questions);
		}
	}
	
	private List<ExcelQuestion> removeBlanks(List<ExcelQuestion> _questions) {
		List<ExcelQuestion> questions = new ArrayList<ExcelQuestion>();
		for (ExcelQuestion question: _questions) {
			if (!question.isBlank()) {
				questions.add(question);
			}
		}
		
		return questions;
	}
	
	public boolean hasNext() {
		return i.get() < questions.size();
	}
	
	public synchronized List<ExcelQuestion> next() {
		List<ExcelQuestion> subList = new ArrayList<ExcelQuestion>();
		
		if (hasNext()) {
			ExcelQuestion question = questions.get(i.get());
			subList.add(question);
			i.incrementAndGet();

			while (hasNext()) {
				ExcelQuestion nextQuestion = questions.get(i.get());
				if (nextQuestion.getQuestionNumber() <= question.getQuestionNumber()) {
					subList.add(nextQuestion);
					i.incrementAndGet();
				}
				else {
					break;
				}
			}
		}
		
		return subList;
	}
}
