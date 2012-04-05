package org.qsse.client.actions;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Action;

import org.qsse.shared.dto.VoidResult;

public class CreateQuestion implements Action<VoidResult> {
	
	private String question;
	private Date submissionDate;
	private long userID;
	private String userEmail;
	
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}
