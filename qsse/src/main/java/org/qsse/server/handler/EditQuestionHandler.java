package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.EditProfile;
import org.qsse.client.actions.EditQuestion;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.Question;
import org.qsse.shared.dto.VoidResult;

public class EditQuestionHandler implements ActionHandler<EditQuestion, VoidResult> {

	private final QsseUserService userService; 
	private final EntityManager entityManager;

	@Inject
	public EditQuestionHandler(EntityManager entityManager, QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@Override
	public VoidResult execute(EditQuestion action, ExecutionContext context) throws DispatchException {

		Question q = entityManager.get(Question.key(action.getQuestionId()));
		
		if(action.getQuestion() != null){
			q.setQuestion(new Text(action.getQuestion()));
		}
		q.setStatus(action.getStatus());
		
		entityManager.put(q);

		return new VoidResult();
	}

	@Override
	public Class<EditQuestion> getActionType() {
		return EditQuestion.class;
	}

	@Override
	public void rollback(EditQuestion action, VoidResult result, ExecutionContext context) throws DispatchException {

	}
}
