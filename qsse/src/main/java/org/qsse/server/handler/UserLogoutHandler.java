package org.qsse.server.handler;

import org.simpleds.EntityManager;
import org.simpleds.SimpleQuery;
import org.simpleds.exception.EntityNotFoundException;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.AuthenticateLogin;
import org.qsse.client.actions.UserLogout;
import org.qsse.server.BCrypt;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.User;
import org.qsse.shared.dto.AuthenticationResult;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class UserLogoutHandler implements ActionHandler<UserLogout, AuthenticationResult>{

	private final QsseUserService userService;
	private final EntityManager entityManager;
	
	@Inject
	public UserLogoutHandler(QsseUserService userService, EntityManager entityManager){
		this.userService = userService;
		this.entityManager = entityManager;
	}
	
	@Override
	public AuthenticationResult execute(UserLogout action, ExecutionContext context) throws DispatchException {

		userService.clearUser();
		return new AuthenticationResult(false);
		
	}

	@Override
	public Class<UserLogout> getActionType() {
		return UserLogout.class;
	}

	@Override
	public void rollback(UserLogout action, AuthenticationResult result, ExecutionContext conext) throws DispatchException {

	}
}
