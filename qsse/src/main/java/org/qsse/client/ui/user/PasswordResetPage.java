package org.qsse.client.ui.user;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.ResetPassword;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.SideBarContainer;
import org.qsse.shared.dto.UsersListDTO;
import org.qsse.shared.dto.UsersListResult;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.exception.UserAlreadyExistsException;
import org.qsse.shared.exception.WrongPasswordException;

public class PasswordResetPage extends Composite implements Page {

	private static PasswordResetPageUiBinder uiBinder = GWT
			.create(PasswordResetPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private final SideBarContainer sideBarContainer;
	
	@UiField
	PasswordTextBox oldPassword;
	
	@UiField
	PasswordTextBox newPassword;
	
	@UiField
	Label validationMessage;
	
	@UiField
	Label save;
	
	interface PasswordResetPageUiBinder extends
			UiBinder<Widget, PasswordResetPage> {
	}
	
	
	public PasswordResetPage(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		this.sideBarContainer = sideBarContainer;
	}
	
	public boolean validatePassword(PasswordTextBox passwrodTextBox) {
		if (passwrodTextBox.getValue() == "") {
			passwrodTextBox.addStyleName("red");
			return true;
		}
		passwrodTextBox.removeStyleName("red");
		return false;
	}

	public boolean validateForm() {
		if (validatePassword(oldPassword) || validatePassword(newPassword)) {

			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}
	
	public ResetPassword resetPasswordAction(){
		
		ResetPassword action = new ResetPassword();
		
		action.setOldPassword(oldPassword.getValue());
		action.setNewPassword(newPassword.getValue());
		
		return action;
	}
	
	public void resetPassword(){
		
		dispatch.execute(resetPasswordAction(), new AsyncCallback<VoidResult>() {

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof WrongPasswordException) {
					Window.alert("Password does not macth.\n Please enter correct password.");
					oldPassword.addStyleName("red");
				}
				GWT.log("Authentication Failed at password reset...");
			}

			@Override
			public void onSuccess(VoidResult result) {
				Window.alert("New password successfully saved.");
				pageContainer.add(new PasswordResetPage(pageContainer, sideBarContainer, dispatch));
				
			}
		});
		
	}
	
	@UiHandler("save")
	void onSave(ClickEvent e){
		if(validateForm()){
			resetPassword();
		}
		
	}
	
}
