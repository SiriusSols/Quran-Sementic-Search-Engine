package org.qsse.shared.dto;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Result;

public class QuestionListDTO implements Result{

	public QuestionListDTO(){
		
	}
	
	private Long questionID;
	private String question;
	private Date submissionDate;
	private Boolean status;
	private Long userID;
	private String userEmail;
	private String userDp;	
	private String totalAnswers;
	private String userFirstName;

	
	public Long getQuestionID() {
		return questionID;
	}
	public void setQuestionID(Long questionID) {
		this.questionID = questionID;
	}
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
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
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
	public String getTotalAnswers() {
		return totalAnswers;
	}
	public void setTotalAnswers(String totalAnswers) {
		this.totalAnswers = totalAnswers;
	}
	public String getUserFirstName() {
		return userFirstName;
	}
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	
}
