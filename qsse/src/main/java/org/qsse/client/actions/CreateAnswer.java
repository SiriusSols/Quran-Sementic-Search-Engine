package org.qsse.client.actions;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

import org.qsse.shared.dto.VoidResult;

public class CreateAnswer implements Action<VoidResult> {
	
	private String Answser;
	private Date submissionDate;
	private long userID;
	private long questionId;
	
	public CreateAnswer() {
	}
	
	public CreateAnswer(long questionId) {
		super();
		this.questionId = questionId;
	}
	
	public String getAnswser() {
		return Answser;
	}
	public void setAnswser(String answser) {
		Answser = answser;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	
}