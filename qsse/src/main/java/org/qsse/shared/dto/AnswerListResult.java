package org.qsse.shared.dto;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class AnswerListResult implements Result {

	private QuestionListDTO questionDTO;

	private List<AnswerDTO> answers;

	public AnswerListResult() {

	}

	public AnswerListResult(List<AnswerDTO> answers) {
		this.answers = answers;
	}

	public List<AnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<AnswerDTO> answers) {
		this.answers = answers;
	}

	public QuestionListDTO getQuestionDTO() {
		return questionDTO;
	}

	public void setQuestionDTO(QuestionListDTO questionDTO) {
		this.questionDTO = questionDTO;
	}

}
