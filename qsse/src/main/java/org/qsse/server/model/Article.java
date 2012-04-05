package org.qsse.server.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

@Entity
public class Article {

	
	public static final String KIND = "Article";
	
	@Id
    @GeneratedValue
    private Key key;
	
	private String title;
	private Text articleTxt;
	private Date submissionDate;
	private boolean publish; // 1 = publish : 0 = unpublish
	
	public static Key key(long id) {
		return KeyFactory.createKey(KIND, id);
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Text getArticleTxt() {
		return articleTxt;
	}

	public void setArticleTxt(Text articleTxt) {
		this.articleTxt = articleTxt;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public boolean isPublish() {
		return publish;
	}

	public void setPublish(boolean publish) {
		this.publish = publish;
	}
	
	
}

