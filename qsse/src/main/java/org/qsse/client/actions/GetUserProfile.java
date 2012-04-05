package org.qsse.client.actions;

import org.qsse.shared.dto.UsersListResult;

import net.customware.gwt.dispatch.shared.Action;

public class GetUserProfile implements Action<UsersListResult> {

	private long id;
	private String email;
	
	public GetUserProfile(){
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
