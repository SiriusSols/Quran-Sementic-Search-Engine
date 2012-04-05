package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.EditProfile;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.User;
import org.qsse.shared.dto.VoidResult;

public class EditProfileHandler implements ActionHandler<EditProfile, VoidResult> {

	private final QsseUserService userService; 
	private final EntityManager entityManager;

	@Inject
	public EditProfileHandler(EntityManager entityManager, QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@Override
	public VoidResult execute(EditProfile action, ExecutionContext context) throws DispatchException {

		String emailUser = userService.getCurrentUser().getEmail();
		User user =   entityManager.findSingle(entityManager.createQuery(User.class).addFilter("email", FilterOperator.EQUAL, emailUser));
		
		user.setFirstName(action.getFirstName());
		user.setLastName(action.getLastName());
		user.setDob(action.getDob());
		user.setGender(action.getGender());
		user.setAddress(action.getAddress());
		user.setPhone(action.getPhone());
		if(!action.getUserDp().equals("")){
			user.setUserDp(action.getUserDp());
		}
		entityManager.put(user);

		return new VoidResult();
	}

	@Override
	public Class<EditProfile> getActionType() {
		return EditProfile.class;
	}

	@Override
	public void rollback(EditProfile action, VoidResult result, ExecutionContext context) throws DispatchException {

	}
}
