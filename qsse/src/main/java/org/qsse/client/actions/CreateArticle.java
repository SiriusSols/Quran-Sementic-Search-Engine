package org.qsse.client.actions;

import java.util.Date;

import org.qsse.shared.dto.VoidResult;

import net.customware.gwt.dispatch.shared.Action;

public class CreateArticle implements Action<VoidResult> {

	private long articleId = Long.MIN_VALUE;
	private String title;
	private String articleTxt;
	private Date submissionDate;
	private boolean publish;
	
	public CreateArticle(){
		
	}
	
	public long getArticleId() {
		return articleId;
	}
	
	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArticleTxt() {
		return articleTxt;
	}

	public void setArticleTxt(String articleTxt) {
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
