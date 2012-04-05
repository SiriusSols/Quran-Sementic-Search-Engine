package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.AuthenticateLogin;
import org.qsse.client.actions.UserLogout;
import org.qsse.client.actions.GetUserStatus;
import org.qsse.client.ui.sidebar.UserSidebarMenu;
import org.qsse.shared.dto.AuthenticationResult;
import org.qsse.shared.type.UserType;

public class Login extends Composite implements Page {

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);

	private final PageContainer pageContainer;
	private final DispatchAsync dispatch;
	private final SideBarContainer sideBarContainer;

	public static UserType userType;

	@UiField
	Label heading;

	@UiField
	VerticalPanel loginBox;

	@UiField
	TextBox email;

	@UiField
	PasswordTextBox password;

	@UiField
	Button login;

	@UiField
	Hyperlink register;

	@UiField
	Label validationMessage;

	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	public Login(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		this.sideBarContainer = sideBarContainer;
		// this.userType = userType;
		sideBarContainer.removeExisting();
		userStatus();

	}

	private final static String EMAIL_VALIDATION_REGEX = "[a-z0-9!#$%&'*+/ =?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

	public boolean validateEmail() {
		String emailTemp = email.getValue();
		if (emailTemp.matches(EMAIL_VALIDATION_REGEX)) {
			email.removeStyleName("red");
			return false;
		}
		email.addStyleName("red");
		return true;
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
		if (validateEmail() || validatePassword(password)) {
			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}

	public AuthenticateLogin loginAction() {

		AuthenticateLogin action = new AuthenticateLogin();

		action.setEmail(email.getValue());
		action.setPassword(password.getValue());

		return action;
	}

	public void login() {

		dispatch.execute(loginAction(),
				new AsyncCallback<AuthenticationResult>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Authentication Failed...");
						userType = null;

						RootPanel rootPanel = RootPanel.get();
						rootPanel.clear();

						rootPanel.add(new HomePage(dispatch));

					}

					@Override
					public void onSuccess(AuthenticationResult result) {
						if (result.isSucceeded()) {
							Window.alert("Welcome back!");
							userType = result.getUserType();

							RootPanel rootPanel = RootPanel.get();
							rootPanel.clear();
							rootPanel.add(new HomePage(dispatch));
							pageContainer.clear();
							pageContainer.add(new UserWelcomeScreen(pageContainer, sideBarContainer, dispatch));

						} else {
							Window.alert("Login Faild! Please enter correct Email and password.");
						}
					}
				});
	}

	public GetUserStatus userStatusAction() {
		GetUserStatus action = new GetUserStatus();

		return action;
	}

	public void userStatus() {

		dispatch.execute(userStatusAction(),
				new AsyncCallback<AuthenticationResult>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Authentication Failed...");
					}

					@SuppressWarnings("deprecation")
					@Override
					public void onSuccess(AuthenticationResult result) {
						if (result.isSucceeded()) {
							userType = result.getUserType();
							loginBox.clear();
							heading.setText("Logout");

							Hyperlink logoutButton = new Hyperlink("Logout",
									"logout");
							logoutButton.addStyleName("button");
							logoutButton.addClickHandler(new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									logout();
								}
							});

							loginBox.add(logoutButton);
							Window.alert("You are already login!");
						}
					}
				});
	}

	public UserLogout userLogoutAction() {

		UserLogout action = new UserLogout();

		return action;
	}

	public void logout() {

		dispatch.execute(userLogoutAction(),
			new AsyncCallback<AuthenticationResult>() {

				@Override
				public void onFailure(Throwable caught) {
					GWT.log("Authentication Failed...");
				}

				@Override
				public void onSuccess(AuthenticationResult result) {
					Window.alert("Have a nice day. Allah Hafiz!");
					Login.userType = null;
					RootPanel rootPanel = RootPanel.get();
					rootPanel.clear();
					rootPanel.add(new HomePage(dispatch));
					pageContainer.add(new UserWelcomeScreen(pageContainer, sideBarContainer, dispatch));

				}
			});
	}
	@UiHandler("login")
	void onLogin(ClickEvent e) {
		if (validateForm()) {
			login();
		}
	}

	@UiHandler("register")
	void onResiter(ClickEvent e) {
		// pageContainer.add(new Register(pageContainer, sideBarContainer,
		// dispatch, userType));
	}

	public void addSideBar(String userType) {

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new UserSidebarMenu(sideBarContainer, pageContainer,
				dispatch));
		if (userType != "") {
			sideBarContainer.add(verticalPanel);
		}
	}
}
