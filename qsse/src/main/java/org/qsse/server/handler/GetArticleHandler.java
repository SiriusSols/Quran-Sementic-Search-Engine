package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.GetArticle;
import org.qsse.server.model.Article;
import org.qsse.shared.dto.ArticleListResult;
import org.qsse.shared.dto.ArticlesListDTO;

public class GetArticleHandler implements ActionHandler<GetArticle, ArticleListResult> {

	private final EntityManager entityManager;

	@Inject
	public GetArticleHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("deprecation")
	@Override
	public ArticleListResult execute(GetArticle action, ExecutionContext context) throws DispatchException {

		if (action.getArticleId() == Long.MAX_VALUE) {
			// Get all articles for admin
			List<Article> articles;
			articles = entityManager.find(entityManager.createQuery(Article.class));
			List<ArticlesListDTO> dtos = new ArrayList<ArticlesListDTO>();

			for (Article a : articles) {

				ArticlesListDTO dto = new ArticlesListDTO();

				dto.setArticleId(a.getKey().getId());
				dto.setTitle(a.getTitle());
				dto.setArticleTxt(a.getArticleTxt().getValue());
				dto.setSubmissionDate(a.getSubmissionDate());
				dto.setPublish(a.isPublish());

				dtos.add(dto);
			}
			return new ArticleListResult(dtos);

		} else if (action.getArticleId() == Long.MIN_VALUE) {
			// Get published articles for users
			List<Article> articles;
			articles = entityManager.find(entityManager.createQuery(Article.class).addFilter("publish",
					FilterOperator.EQUAL, true));
			List<ArticlesListDTO> dtos = new ArrayList<ArticlesListDTO>();

			for (Article a : articles) {

				ArticlesListDTO dto = new ArticlesListDTO();

				dto.setArticleId(a.getKey().getId());
				dto.setTitle(a.getTitle());
				dto.setArticleTxt(a.getArticleTxt().getValue());
				dto.setSubmissionDate(a.getSubmissionDate());
				dto.setPublish(a.isPublish());

				dtos.add(dto);
			}
			return new ArticleListResult(dtos);

		} else {
			// Get individual article
			Article article = entityManager.get(Article.key(action.getArticleId()));
			List<ArticlesListDTO> dtos = new ArrayList<ArticlesListDTO>();
			ArticlesListDTO dto = new ArticlesListDTO();

			dto.setArticleId(article.getKey().getId());
			dto.setTitle(article.getTitle());
			dto.setArticleTxt(article.getArticleTxt().getValue());
			dto.setSubmissionDate(article.getSubmissionDate());
			dto.setPublish(article.isPublish());
			dtos.add(dto);
			return new ArticleListResult(dtos);

		}
	}

	@Override
	public Class<GetArticle> getActionType() {
		return GetArticle.class;
	}

	@Override
	public void rollback(GetArticle action, ArticleListResult result, ExecutionContext context)
			throws DispatchException {
		// TODO Auto-generated method stub
	}

}
