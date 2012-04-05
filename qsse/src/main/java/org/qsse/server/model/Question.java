package org.qsse.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@Entity
public class Question {
	
	public static final String KIND = "Question";
	
	@Id
    @GeneratedValue
    private Key key;
	
	private Text question;
	private Date submissionDate;
	private long userID;
	private Boolean status; // 1 = none flagged : 0 = flagged
	
	public static Key key(long id) {
		return KeyFactory.createKey(KIND, id);
	}
	public Key getKey() {
		return key;
	}
	public void setKey(Key key) {
		this.key = key;
	}
	public Text getQuestion() {
		return question;
	}
	public void setQuestion(Text question) {
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}

}
