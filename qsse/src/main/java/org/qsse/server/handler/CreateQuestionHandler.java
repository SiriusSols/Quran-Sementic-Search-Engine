package org.qsse.server.handler;

import java.util.Date;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;
import org.simpleds.SimpleQuery;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;
import com.google.inject.Inject;
import org.qsse.client.actions.CreateQuestion;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.Question;
import org.qsse.server.model.User;
import org.qsse.shared.dto.VoidResult;


public class CreateQuestionHandler implements
		ActionHandler<CreateQuestion, VoidResult> {

	private final EntityManager entityManager;
	private final QsseUserService userService;

	@Inject
	public CreateQuestionHandler(EntityManager entityManager,
			QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoidResult execute(CreateQuestion action, ExecutionContext context)
			throws DispatchException {

		SimpleQuery query = entityManager.createQuery(User.class).addFilter(
				"email", Query.FilterOperator.EQUAL,
				userService.getCurrentUser().getEmail());
		User user = entityManager.findSingle(query);

		// Create new Question

		Question newQuestion = new Question();

		newQuestion.setSubmissionDate(new Date());
		newQuestion.setUserID(user.getKey().getId());
		newQuestion.setQuestion(new Text(action.getQuestion()));
		newQuestion.setStatus(true);

		entityManager.put(newQuestion);

		return new VoidResult();
	}

	@Override
	public Class<CreateQuestion> getActionType() {
		return CreateQuestion.class;
	}

	@Override
	public void rollback(CreateQuestion action, VoidResult result,
			ExecutionContext context) throws DispatchException {

	}
}
