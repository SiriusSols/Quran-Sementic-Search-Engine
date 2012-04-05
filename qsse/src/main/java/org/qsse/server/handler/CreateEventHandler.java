package org.qsse.server.handler;


import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;


import com.google.inject.Inject;
import org.qsse.client.actions.CreateEvent;
import org.qsse.server.QsseUserService;
import org.qsse.server.model.Event;
import org.qsse.shared.dto.VoidResult;

public class CreateEventHandler implements
		ActionHandler<CreateEvent, VoidResult> {

	private final EntityManager entityManager;
	private final QsseUserService userService;

	@Inject
	public CreateEventHandler(EntityManager entityManager,
			QsseUserService userService) {
		this.entityManager = entityManager;
		this.userService = userService;
	}

	@Override
	public VoidResult execute(CreateEvent action, ExecutionContext context)
			throws DispatchException {

		
		if(action.getTask() == Long.MIN_VALUE){
			
			Event event = entityManager.get(Event.key(action.getEvendId()));
			event.setPublish(action.isPublish());
			
			entityManager.put(event);
			
			return new VoidResult();
		}
		else if(action.getTask() == Long.MAX_VALUE){
			
			Event event = entityManager.get(Event.key(action.getEvendId()));
			event.setPublish(action.isPublish());
			
			event.setTitle(action.getTitle());
			event.setDetail(action.getDetail());
			event.setLocation(action.getLocation());
			event.setStartDate(action.getStartDate());
			event.setEndDate(action.getEndDate());
			event.setTiming(action.getTiming());
			event.setPublish(action.isPublish());
			
			entityManager.put(event);
			
			return new VoidResult();
		}
		else{
			
			Event event = new Event();
			
			event.setTitle(action.getTitle());
			event.setDetail(action.getDetail());
			event.setLocation(action.getLocation());
			event.setStartDate(action.getStartDate());
			event.setEndDate(action.getEndDate());
			event.setTiming(action.getTiming());
			event.setPublish(action.isPublish());
			
			entityManager.put(event);

			return new VoidResult();
			
		}	
		
	}

	@Override
	public Class<CreateEvent> getActionType() {
		return CreateEvent.class;
	}

	@Override
	public void rollback(CreateEvent action, VoidResult result,
			ExecutionContext context) throws DispatchException {

	}
}

