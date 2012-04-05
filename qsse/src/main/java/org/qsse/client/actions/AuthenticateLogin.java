package org.qsse.client.actions;

import org.qsse.shared.dto.AuthenticationResult;

import net.customware.gwt.dispatch.shared.Action;

public class AuthenticateLogin implements Action<AuthenticationResult> {

	private String email;
	private String password;
	
	public AuthenticateLogin(){
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
