package org.qsse.client.actions;

import org.qsse.shared.dto.EventListResult;

import net.customware.gwt.dispatch.shared.Action;

public class GetEvent implements Action<EventListResult>{

	private long task;
	private long eventId;
	public GetEvent(){
		
	}
	public long getTask() {
		return task;
	}
	public void setTask(long task) {
		this.task = task;
	}
	public long getEventId() {
		return eventId;
	}
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	
}
