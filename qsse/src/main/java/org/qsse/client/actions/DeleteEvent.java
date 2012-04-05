package org.qsse.client.actions;

import org.qsse.shared.dto.VoidResult;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteEvent implements Action<VoidResult> {
	

	private long eventId;
	
	public DeleteEvent(){
		
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
}
