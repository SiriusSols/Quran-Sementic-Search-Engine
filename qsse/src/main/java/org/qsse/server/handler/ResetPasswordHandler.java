package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.ResetPassword;
import org.qsse.server.BCrypt;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.User;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.exception.WrongPasswordException;

public class ResetPasswordHandler implements
		ActionHandler<ResetPassword, VoidResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager;

	@Inject
	public ResetPasswordHandler(EntityManager entityManager,
			QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@Override
	public VoidResult execute(ResetPassword action, ExecutionContext context)
			throws DispatchException {

		String emailUser = userService.getCurrentUser().getEmail();
		User user = entityManager
				.findSingle(entityManager.createQuery(User.class).addFilter(
						"email", FilterOperator.EQUAL, emailUser));
		
		if (!BCrypt.checkpw(action.getOldPassword(), user.getPassword())){
			throw new WrongPasswordException();
		}

		user.setPassword(BCrypt.hashpw(action.getNewPassword(), BCrypt
				.gensalt()));
		entityManager.put(user);

		return new VoidResult();
	}

	@Override
	public Class<ResetPassword> getActionType() {
		return ResetPassword.class;
	}

	@Override
	public void rollback(ResetPassword action, VoidResult result,
			ExecutionContext context) throws DispatchException {

	}
}
