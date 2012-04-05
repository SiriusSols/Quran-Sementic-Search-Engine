package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.GetAnswers;
import org.qsse.server.model.Answer;
import org.qsse.server.model.Question;
import org.qsse.server.model.User;
import org.qsse.shared.dto.AnswerDTO;
import org.qsse.shared.dto.AnswerListResult;
import org.qsse.shared.dto.QuestionListDTO;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class GetAnswerHandler implements ActionHandler<GetAnswers, AnswerListResult> {

	private final EntityManager entityManager;

	@Inject
	public GetAnswerHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Class<GetAnswers> getActionType() {
		// TODO Auto-generated method stub
		return GetAnswers.class;
	}

	@SuppressWarnings("deprecation")
	@Override
	public AnswerListResult execute(GetAnswers action, ExecutionContext context) throws DispatchException {
		
		// Get Question
		Question question = entityManager.get(Question.key(action.getQuestionId()));
		
		// Get Answer
		List<Answer> answers = entityManager.find(entityManager.createQuery(Answer.class).addFilter("questionId",
				FilterOperator.EQUAL, action.getQuestionId()));
		
		// Generate Question Action Result
		QuestionListDTO questionDTO = new QuestionListDTO();
		questionDTO.setQuestion(question.getQuestion().getValue());
		questionDTO.setQuestionID(question.getKey().getId());
		questionDTO.setStatus(question.getStatus());

		
		AnswerListResult answersResult = new AnswerListResult();
		List<AnswerDTO> list = new ArrayList<AnswerDTO>();
		
		// Retrieve users who answered
		List<Key> idList = new ArrayList<Key>();

		for (Answer a : answers) {
			if (!idList.contains(User.key(a.getUserId()))) {
				idList.add(User.key(a.getUserId()));
			}

		}

		List<User> users = entityManager.get(idList);

		HashMap<Long, User> userMap = new HashMap<Long, User>();

		for (User u : users) {
			userMap.put(u.getKey().getId(), u);
		}
		
		for (Answer a : answers) {
			AnswerDTO dto = new AnswerDTO();
			dto.setAnswer(a.getAnswer().getValue());
			dto.setAnswerId(a.getQuestionId());
			dto.setSubmissionDate(a.getSubmissionDate());
			dto.setUserId(a.getUserId());
			
			User user = userMap.get(a.getUserId());

			dto.setUserEmail(user.getEmail());
			dto.setUserDp(user.getUserDp());
			dto.setUserFirstName(user.getFirstName());
			dto.setUserType(user.getUserType().toString());
			
			list.add(dto);
		}

		answersResult.setAnswers(list);
		answersResult.setQuestionDTO(questionDTO);
		
		return answersResult;
	}

	@Override
	public void rollback(GetAnswers action, AnswerListResult arg1, ExecutionContext context) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
