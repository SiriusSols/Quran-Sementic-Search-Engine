package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.DeleteArticle;
import org.qsse.server.model.Article;
import org.qsse.shared.dto.VoidResult;

public class DeleteArticleHandler  implements ActionHandler<DeleteArticle, VoidResult> {

	private final EntityManager entityManager;
	
	@Inject
	public DeleteArticleHandler(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Override
	public VoidResult execute(DeleteArticle action, ExecutionContext context) throws DispatchException {
		
		entityManager.delete(Article.key(action.getArticleId()));
		
		return new VoidResult();
	}
	
	@Override
	public Class<DeleteArticle> getActionType(){
		return DeleteArticle.class;	
	}
	
	@Override
	public void rollback(DeleteArticle action, VoidResult result, ExecutionContext context) throws DispatchException {
		
	}

}
