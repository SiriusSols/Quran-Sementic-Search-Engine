package org.qsse.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@Entity
public class Answer {
	
	public static final String KIND = "Answer";
	
	@Id
    @GeneratedValue
    private Key key;
	private Text answer;
	private Date submissionDate;
	private long questionId;
	private long userId;
	
	public static Key key(long id) {
		return KeyFactory.createKey(KIND, id);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public Text getAnswer() {
		return answer;
	}

	public void setAnswer(Text answer) {
		this.answer = answer;
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
}
