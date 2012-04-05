package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.DeleteUser;
import org.qsse.server.model.User;
import org.qsse.shared.dto.VoidResult;

public class DeleteUserHandler  implements ActionHandler<DeleteUser, VoidResult> {

	private final EntityManager entityManager;
	
	@Inject
	public DeleteUserHandler(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Override
	public VoidResult execute(DeleteUser action, ExecutionContext context) throws DispatchException {
		
		entityManager.delete(User.key(action.getUserID()));
		
		return new VoidResult();
	}
	
	@Override
	public Class<DeleteUser> getActionType(){
		return DeleteUser.class;	
	}
	
	@Override
	public void rollback(DeleteUser action, VoidResult result, ExecutionContext context) throws DispatchException {
		
	}

}
