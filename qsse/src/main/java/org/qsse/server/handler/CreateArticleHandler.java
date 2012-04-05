package org.qsse.server.handler;

import java.util.Date;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Text;
import com.google.inject.Inject;
import org.qsse.client.actions.CreateArticle;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.Article;
import org.qsse.shared.dto.VoidResult;

public class CreateArticleHandler implements ActionHandler<CreateArticle, VoidResult> {

	private final EntityManager entityManager;
	private final QsseUserService userService;

	@Inject
	public CreateArticleHandler(EntityManager entityManager, QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@Override
	public VoidResult execute(CreateArticle action, ExecutionContext context) throws DispatchException {

		if (action.getArticleId() == Long.MIN_VALUE) {
			// Create new Artice
			Article a = new Article();

			a.setTitle(action.getTitle());
			a.setArticleTxt(new Text(action.getArticleTxt()));
			a.setSubmissionDate(new Date());
			a.setPublish(true);

			entityManager.put(a);

			return new VoidResult();
		} else {
			Article a = entityManager.get(Article.key(action.getArticleId()));

			if (!action.getTitle().equals("") || !action.getArticleTxt().equals("")) {
				a.setTitle(action.getTitle());
				a.setArticleTxt(new Text(action.getArticleTxt()));
			}
			a.setPublish(action.isPublish());
			
			entityManager.put(a);
			
			return new VoidResult();
		}

	}

	@Override
	public Class<CreateArticle> getActionType() {
		return CreateArticle.class;
	}

	@Override
	public void rollback(CreateArticle action, VoidResult result, ExecutionContext context) throws DispatchException {

	}
}
