package org.qsse.client.actions;

import org.qsse.shared.dto.VoidResult;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteArticle implements Action<VoidResult>{
	
	private long articleId;
	
	public DeleteArticle(){}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}

}
