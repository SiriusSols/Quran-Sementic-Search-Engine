package org.qsse.shared.dto;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class EventListResult implements Result {

	private List<EventsListDTO> events;

	public EventListResult() {

	}
	public EventListResult(List<EventsListDTO> events) {
		this.events = events;
	}
	
	public List<EventsListDTO> getEvents() {
		return events;
	}
	
}
