package org.qsse.shared.dto;

import java.util.Date;

import net.customware.gwt.dispatch.shared.Result;

public class ArticlesListDTO implements Result{

	public ArticlesListDTO(){
		
	}
	
	private long articleId;
	private String title;
	private String articleTxt;
	private Date submissionDate;
	private boolean publish;
	
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
