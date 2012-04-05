package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.GetUserProfile;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.User;
import org.qsse.shared.dto.UsersListResult;
import org.qsse.shared.dto.UsersListDTO;

public class GetUserProfileHandler  implements ActionHandler<GetUserProfile, UsersListResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager ;
	
	@Inject
	public GetUserProfileHandler(QsseUserService userService, EntityManager entityManager){
		this.entityManager = entityManager;
		this.userService = userService;
	}
	
	@Override
	public UsersListResult execute(GetUserProfile action, ExecutionContext context) throws DispatchException {
		
		String emailUser = userService.getCurrentUser().getEmail();
		List<User> users =   entityManager.find(entityManager.createQuery(User.class).addFilter("email", FilterOperator.EQUAL, emailUser));
		
		List<UsersListDTO> dtos = new ArrayList<UsersListDTO>();
		
		for(User user : users){
			
			UsersListDTO dto = new UsersListDTO();
			
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
	public Class<GetUserProfile> getActionType() {
		return GetUserProfile.class;
	}

	@Override
	public void rollback(GetUserProfile action, UsersListResult result, ExecutionContext context) throws DispatchException {
		// TODO Auto-generated method stub
		
	}

}
