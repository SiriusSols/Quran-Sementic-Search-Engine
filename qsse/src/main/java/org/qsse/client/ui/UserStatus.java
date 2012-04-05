package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.qsse.client.actions.GetUserStatus;
import org.qsse.shared.dto.AuthenticationResult;

public class UserStatus {

	private final DispatchAsync dispatch;
	
	private boolean succeeded;
	private String userType;
	private boolean isLoggedIn;
	private String email;
	
	public UserStatus(DispatchAsync disptach){
		this.dispatch = disptach;
		userStatus();
	}
	
	
	public GetUserStatus userStatusAction(){
		GetUserStatus action  = new GetUserStatus();
		
		return action;
	}
	
	public void userStatus(){
		
		dispatch.execute(userStatusAction(), new AsyncCallback<AuthenticationResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed...");
			}

			@Override
			public void onSuccess(AuthenticationResult result) {
				if(result.isSucceeded()){
					setLoggedIn(result.isSucceeded());
					setUserType(result.getUserType().toString());
					setLoggedIn(result.isLoggedIn());
					setEmail(result.getEmail());
				}	
			}
		});
	}

	public boolean isSucceeded() {
		return succeeded;
	}


	public void setSucceeded(boolean succeeded) {
		this.succeeded = succeeded;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
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

