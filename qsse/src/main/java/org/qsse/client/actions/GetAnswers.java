package org.qsse.client.actions;

import org.qsse.shared.dto.AnswerListResult;

import net.customware.gwt.dispatch.shared.Action;

public class GetAnswers implements Action<AnswerListResult>{

	private long questionId;

	public GetAnswers() {

	}

	public GetAnswers(long questionId) {
		super();
		this.questionId = questionId;
	}

	public long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}

}
