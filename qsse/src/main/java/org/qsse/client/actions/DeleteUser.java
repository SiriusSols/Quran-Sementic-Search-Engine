package org.qsse.client.actions;

import org.qsse.shared.dto.VoidResult;

import net.customware.gwt.dispatch.shared.Action;

public class DeleteUser implements Action<VoidResult> {

	public DeleteUser(){
		
	}
	
	private long userID;

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	
}
