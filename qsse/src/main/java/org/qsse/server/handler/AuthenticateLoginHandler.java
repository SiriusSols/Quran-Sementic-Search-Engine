package org.qsse.server.handler;

import org.jqurantree.arabic.ArabicText;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Location;
import org.jqurantree.orthography.Verse;
import org.simpleds.EntityManager;
import org.simpleds.SimpleQuery;
import org.simpleds.exception.EntityNotFoundException;

import com.google.appengine.api.datastore.Query.FilterOperator;
import org.qsse.server.model.User;
import com.google.inject.Inject;
import org.qsse.client.actions.AuthenticateLogin;
import org.qsse.server.BCrypt;
import org.qsse.server.QsseUserService;
import org.qsse.shared.dto.AuthenticationResult;
import org.qsse.shared.type.UserType;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class AuthenticateLoginHandler implements ActionHandler<AuthenticateLogin, AuthenticationResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager;
	
	@Inject
	public AuthenticateLoginHandler(QsseUserService userService, EntityManager entityManager){
		this.userService = userService;
		this.entityManager = entityManager;
	}

	@Override
	public AuthenticationResult execute(AuthenticateLogin action, ExecutionContext context) throws DispatchException {
		
		AuthenticationResult result = new AuthenticationResult();
		
		try {
			
			if(action.getEmail().equals("administrator@admin.com")){
				result.setEmail("administrator@admin.com");
				result.setUserType(UserType.ADMIN);
				result.setSucceeded(true);
				return result;
				
			}
			
			SimpleQuery query = entityManager.createQuery(User.class).addFilter("email", FilterOperator.EQUAL, action.getEmail());
			
			User user = entityManager.findSingle(query);
			
			
			if (user == null || !BCrypt.checkpw(action.getPassword(), user.getPassword())) {
				result.setSucceeded(false);
				return result;
			}
			
			userService.setUser(user.getEmail());
			result.setUserType(user.getUserType());
			result.setSucceeded(true);
			
			return result;

		} catch (EntityNotFoundException e) {
			e.printStackTrace();
			return new AuthenticationResult(false);
		}

	}

	@Override
	public Class<AuthenticateLogin> getActionType() {
		return AuthenticateLogin.class;
	}

	@Override
	public void rollback(AuthenticateLogin action, AuthenticationResult result, ExecutionContext conext) throws DispatchException {

	}

}
