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
import org.qsse.client.actions.CreateAnswer;
import org.qsse.client.actions.CreateQuestion;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.Answer;
import org.qsse.server.model.Question;
import org.qsse.server.model.User;
import org.qsse.shared.dto.VoidResult;

public class CreateAnswerHandler  implements
		ActionHandler<CreateAnswer, VoidResult> {

	private final EntityManager entityManager;
	private final QsseUserService userService;

	@Inject
	public CreateAnswerHandler(EntityManager entityManager,
			QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoidResult execute(CreateAnswer action, ExecutionContext context)
			throws DispatchException {

		SimpleQuery query = entityManager.createQuery(User.class).addFilter(
				"email", Query.FilterOperator.EQUAL,
				userService.getCurrentUser().getEmail());
		User user = entityManager.findSingle(query);

		// Create new Question

		Answer newAnswer = new Answer();

		newAnswer.setSubmissionDate(new Date());
		newAnswer.setUserId(user.getKey().getId());
		newAnswer.setAnswer(new Text(action.getAnswser()));
		newAnswer.setQuestionId(action.getQuestionId());

		entityManager.put(newAnswer);

		return new VoidResult();
	}

	@Override
	public Class<CreateAnswer> getActionType() {
		return CreateAnswer.class;
	}

	@Override
	public void rollback(CreateAnswer action, VoidResult result,
			ExecutionContext context) throws DispatchException {

	}
}
