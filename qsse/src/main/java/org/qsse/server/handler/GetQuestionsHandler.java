package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.GetQuestions;
import org.qsse.server.model.Answer;
import org.qsse.server.model.Question;
import org.qsse.server.model.User;
import org.qsse.shared.dto.QuestionListDTO;
import org.qsse.shared.dto.QuestionListResult;

public class GetQuestionsHandler implements
		ActionHandler<GetQuestions, QuestionListResult> {

	private final EntityManager entityManager;

	@Inject
	public GetQuestionsHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("deprecation")
	@Override
	public QuestionListResult execute(GetQuestions action,
			ExecutionContext context) throws DispatchException {

		List<Question> questions = entityManager.find(entityManager
				.createQuery(Question.class));

		List<QuestionListDTO> dtos = new ArrayList<QuestionListDTO>();

		List<Key> idList = new ArrayList<Key>();

		for (Question q : questions) {
			if (!idList.contains(User.key(q.getUserID()))) {
				idList.add(User.key(q.getUserID()));
			}

		}

		List<User> users = entityManager.get(idList);

		HashMap<Long, User> userMap = new HashMap<Long, User>();

		for (User u : users) {
			userMap.put(u.getKey().getId(), u);
		}

		for (Question q : questions) {

			QuestionListDTO dto = new QuestionListDTO();

			dto.setQuestionID(q.getKey().getId());
			dto.setQuestion(q.getQuestion().getValue());
			dto.setSubmissionDate(q.getSubmissionDate());
			dto.setStatus(q.getStatus());

			dto.setUserID(q.getUserID());

			User user = userMap.get(q.getUserID());

			dto.setUserEmail(user.getEmail());
			dto.setUserDp(user.getUserDp());
			dto.setUserFirstName(user.getFirstName());

			dto.setTotalAnswers(String.valueOf(entityManager
					.count(entityManager.createQuery(Answer.class).addFilter(
							"questionId", FilterOperator.EQUAL,
							q.getKey().getId()))));

			dtos.add(dto);
		}

		return new QuestionListResult(dtos);

	}

	@Override
	public Class<GetQuestions> getActionType() {
		return GetQuestions.class;
	}

	@Override
	public void rollback(GetQuestions action, QuestionListResult result,
			ExecutionContext context) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
