package org.qsse.client.actions;

import net.customware.gwt.dispatch.shared.Action;

import org.qsse.shared.dto.ArticleListResult;

public class GetArticle  implements Action<ArticleListResult> {

	private long articleId = Long.MIN_VALUE;
	
	public GetArticle() {

	}
	
	public GetArticle(long articleId){
		this.articleId = articleId;
	}

	public long getArticleId() {
		return articleId;
	}

	public void setArticleId(long articleId) {
		this.articleId = articleId;
	}
	
	
	
}
