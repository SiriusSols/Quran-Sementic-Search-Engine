package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.List;

import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.GetUsers;
import org.qsse.server.model.User;
import org.qsse.shared.dto.UsersListResult;
import org.qsse.shared.dto.UsersListDTO;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

public class GetUsersHandler implements
		ActionHandler<GetUsers, UsersListResult> {

	private final EntityManager entityManager;

	@Inject
	public GetUsersHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("deprecation")
	@Override
	public UsersListResult execute(GetUsers action, ExecutionContext context)
			throws DispatchException {

		List<User> users = entityManager.find(entityManager
				.createQuery(User.class));

		List<UsersListDTO> dtos = new ArrayList<UsersListDTO>();

		for (User user : users) {

			UsersListDTO dto = new UsersListDTO();

			dto.setUserID(user.getKey().getId());
			dto.setEmail(user.getEmail());
			dto.setPassword(user.getPassword());
			dto.setFirstName(user.getFirstName());
			dto.setLastName(user.getLastName());
			dto.setUserType(user.getUserType());
			dto.setDob(user.getDob());
			dto.setGender(user.getGender());
			dto.setAddress(user.getAddress());
			dto.setPhone(user.getPhone());
			dto.setUserDp(user.getUserDp());

			dtos.add(dto);
		}

		return new UsersListResult(dtos);

	}

	@Override
	public Class<GetUsers> getActionType() {
		return GetUsers.class;
	}

	@Override
	public void rollback(GetUsers action, UsersListResult result,
			ExecutionContext context) throws DispatchException {
		// TODO Auto-generated method stub

	}

}
