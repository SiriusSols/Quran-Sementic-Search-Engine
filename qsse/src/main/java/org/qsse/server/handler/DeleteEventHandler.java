package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.DeleteEvent;
import org.qsse.server.model.Event;
import org.qsse.shared.dto.VoidResult;

public class DeleteEventHandler implements ActionHandler<DeleteEvent, VoidResult> {

	private final EntityManager entityManager;
	
	@Inject
	public DeleteEventHandler(EntityManager entityManager){
		this.entityManager = entityManager;
	}
	
	@Override
	public VoidResult execute(DeleteEvent action, ExecutionContext context) throws DispatchException {
		
		entityManager.delete(Event.key(action.getEventId()));
		
		return new VoidResult();
	}
	
	@Override
	public Class<DeleteEvent> getActionType(){
		return DeleteEvent.class;	
	}
	
	@Override
	public void rollback(DeleteEvent action, VoidResult result, ExecutionContext context) throws DispatchException {
		
	}

}

