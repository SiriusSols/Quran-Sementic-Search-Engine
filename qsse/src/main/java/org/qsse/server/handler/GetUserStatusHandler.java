package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;
import org.simpleds.SimpleQuery;
import org.simpleds.exception.EntityNotFoundException;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.GetUserStatus;
import org.qsse.server.BCrypt;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.User;
import org.qsse.shared.dto.AuthenticationResult;

public class GetUserStatusHandler implements ActionHandler<GetUserStatus, AuthenticationResult>{

	private final QsseUserService userService;
	private final EntityManager entityManager;
	
	@Inject
	public GetUserStatusHandler(QsseUserService userService, EntityManager entityManager){
		this.userService = userService;
		this.entityManager = entityManager;
	}
	
	@Override
	public AuthenticationResult execute(GetUserStatus action, ExecutionContext context) throws DispatchException {
		
		AuthenticationResult result = new AuthenticationResult();
		
		try {
			SimpleQuery query = entityManager.createQuery(User.class).addFilter("email", FilterOperator.EQUAL, userService.getCurrentUser().getEmail());
			
			User user = entityManager.findSingle(query);
			
			if(userService.getCurrentUser() == null){
				result.setLoggedIn(false);
				result.setSucceeded(false);
				result.setUserType(user.getUserType());
				return result;
			}
			
			result.setLoggedIn(true);
			result.setEmail(user.getEmail());
			result.setUserType(user.getUserType());
			result.setSucceeded(true);
			return result;
		
		}catch (EntityNotFoundException e) {
			e.printStackTrace();
			return new AuthenticationResult(true);
		}
	}

	@Override
	public Class<GetUserStatus> getActionType() {
		return GetUserStatus.class;
	}

	@Override
	public void rollback(GetUserStatus action, AuthenticationResult result, ExecutionContext conext) throws DispatchException {

	}
}
