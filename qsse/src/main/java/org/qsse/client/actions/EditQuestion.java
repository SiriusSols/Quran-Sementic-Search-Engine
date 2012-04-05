package org.qsse.client.actions;

import org.qsse.shared.dto.VoidResult;

import net.customware.gwt.dispatch.shared.Action;

public class EditQuestion implements Action<VoidResult>{

	private long questionId;
	private String question;
	private Boolean status; // 1 = none flagged : 0 = flagged
	
	public EditQuestion(){
		
	}
	
	
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
