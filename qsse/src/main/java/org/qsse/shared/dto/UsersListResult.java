package org.qsse.shared.dto;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class UsersListResult implements Result{

	private List<UsersListDTO> users;

	public UsersListResult() {

	}
	public UsersListResult(List<UsersListDTO> users) {
		this.users = users;
	}
	
	public List<UsersListDTO> getUsers() {
		return users;
	}
		
}
