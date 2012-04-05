package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.simpleds.EntityManager;

import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.inject.Inject;
import org.qsse.client.actions.GetEvent;
import org.qsse.server.model.Event;
import org.qsse.shared.dto.EventListResult;
import org.qsse.shared.dto.EventsListDTO;

public class GetEventHandler  implements ActionHandler<GetEvent, EventListResult> {

	private final EntityManager entityManager;

	@Inject
	public GetEventHandler(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@SuppressWarnings("deprecation")
	@Override
	public EventListResult execute(GetEvent action, ExecutionContext context) throws DispatchException {
		
		
		if(action.getEventId() == Long.MAX_VALUE){
			//Get all events
			List<Event> events;
			
			events = entityManager.find(entityManager.createQuery(Event.class));
			List<EventsListDTO> dtos = new ArrayList<EventsListDTO>();
			
			for (Event e : events) {

				EventsListDTO dto = new EventsListDTO();

				dto.setEventId(e.getKey().getId());
				dto.setTitle(e.getTitle());
				dto.setDetail(e.getDetail());
				dto.setLocation(e.getLocation());
				dto.setStartDate(e.getStartDate());
				dto.setEndDate(e.getEndDate());
				dto.setTiming(e.getTiming());
				dto.setPublish(e.isPublish());

				dtos.add(dto);
			}
			return new EventListResult(dtos);
			
		}else if (action.getEventId() == Long.MIN_VALUE) {
			// Get published events for users
			List<Event> events;
			
			events = entityManager.find(entityManager.createQuery(Event.class).addFilter("publish",
					FilterOperator.EQUAL, true));
			List<EventsListDTO> dtos = new ArrayList<EventsListDTO>();
			
			for (Event e : events) {

				EventsListDTO dto = new EventsListDTO();

				dto.setEventId(e.getKey().getId());
				dto.setTitle(e.getTitle());
				dto.setDetail(e.getDetail());
				dto.setLocation(e.getLocation());
				dto.setStartDate(e.getStartDate());
				dto.setEndDate(e.getEndDate());
				dto.setTiming(e.getTiming());
				dto.setPublish(e.isPublish());

				dtos.add(dto);
			}
			return new EventListResult(dtos);

		} else {
			// Get individual event
			Event e = entityManager.get(Event.key(action.getEventId()));
			List<EventsListDTO> dtos = new ArrayList<EventsListDTO>();
			EventsListDTO dto = new EventsListDTO();

			dto.setEventId(e.getKey().getId());
			dto.setTitle(e.getTitle());
			dto.setDetail(e.getDetail());
			dto.setLocation(e.getLocation());
			dto.setStartDate(e.getStartDate());
			dto.setEndDate(e.getEndDate());
			dto.setTiming(e.getTiming());
			dto.setPublish(e.isPublish());
			
			dtos.add(dto);
			return new EventListResult(dtos);

		}
	}

	@Override
	public Class<GetEvent> getActionType() {
		return GetEvent.class;
	}

	@Override
	public void rollback(GetEvent action, EventListResult result, ExecutionContext context)
			throws DispatchException {
		// TODO Auto-generated method stub
	}

}

