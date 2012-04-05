package org.qsse.shared.dto;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class QuestionListResult implements Result{

	private List<QuestionListDTO> questions;

	public QuestionListResult() {

	}
	public QuestionListResult(List<QuestionListDTO> questions) {
		this.questions = questions;
	}
	
	public List<QuestionListDTO> getQuestions() {
		return questions;
	}
		
}
