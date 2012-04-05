package org.qsse.shared.dto;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class ArticleListResult implements Result{

	private List<ArticlesListDTO> articles;

	public ArticleListResult() {

	}
	public ArticleListResult(List<ArticlesListDTO> articles) {
		this.articles = articles;
	}
	
	public List<ArticlesListDTO> getArticles() {
		return articles;
	}
		
}