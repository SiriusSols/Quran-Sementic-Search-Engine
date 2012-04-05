package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;
import org.simpleds.SimpleQuery;
import org.simpleds.exception.EntityNotFoundException;

import com.google.appengine.api.datastore.Query;
import com.google.inject.Inject;
import org.qsse.client.actions.CreateUser;
import org.qsse.server.BCrypt;
import org.qsse.server.model.User;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.exception.UserAlreadyExistsException;

public class CreateUserHandler implements ActionHandler<CreateUser, VoidResult> {

	private final EntityManager entityManager;

	@Inject
	public CreateUserHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("deprecation")
	@Override
	public VoidResult execute(CreateUser action, ExecutionContext context)
			throws DispatchException {

		/*
		 * Throw exception if email entered while registration is already
		 * registered.
		 */

		try {
			SimpleQuery query = entityManager.createQuery(User.class)
					.addFilter("email", Query.FilterOperator.EQUAL,
							action.getEmail());
			User u = entityManager.findSingle(query);
			throw new UserAlreadyExistsException();

		} catch (EntityNotFoundException e) {
		}

		// Create new user

		User newUser = new User();

		newUser.setEmail(action.getEmail());
		newUser.setPassword(BCrypt.hashpw(action.getPassword(), BCrypt
				.gensalt()));
		newUser.setUserType(action.getUserType());
		newUser.setActive(true);
		newUser.setFirstName(action.getFirstName());
		newUser.setLastName(action.getLastName());
		newUser.setGender(action.getGender());
		newUser.setDob(action.getDob());
		newUser.setPhone(action.getPhone());
		newUser.setAddress(action.getAddress());
		newUser.setUserDp(action.getUserDp());

		entityManager.put(newUser);

		return new VoidResult();
	}

	@Override
	public Class<CreateUser> getActionType() {
		return CreateUser.class;
	}

	@Override
	public void rollback(CreateUser action, VoidResult result,
			ExecutionContext context) throws DispatchException {

	}
}
