package org.qsse.shared.dto;

import org.qsse.shared.type.UserType;

import net.customware.gwt.dispatch.shared.Result;

public class AuthenticationResult implements Result {

	private boolean succeeded;
	private UserType userType;
	private boolean isLoggedIn;
	private String email;

	public AuthenticationResult() {

	}

	public AuthenticationResult(boolean succeeded) {
		super();
		this.succeeded = succeeded;
	}

	public boolean isSucceeded() {
		return succeeded;
	}

	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
