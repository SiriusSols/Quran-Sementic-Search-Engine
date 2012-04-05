package org.qsse.shared.dto;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Result;

public class AnswerDTO implements Result{

	private long answerId;
	private String  answer;
	private Date submissionDate;
	private long questionId;
	private long userId;
	private String userEmail;
	private String userDp;
	private String userFirstName;
	private String userType;

	public AnswerDTO(){
		
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getAnswerId() {
		return answerId;
	}
	public void setAnswerId(long answerId) {
		this.answerId = answerId;
	}
	public Date getSubmissionDate() {
		return submissionDate;
	}
	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}
	public long getQuestionId() {
		return questionId;
	}
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserDp() {
		return userDp;
	}
	public void setUserDp(String userDp) {
		this.userDp = userDp;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
		
}
