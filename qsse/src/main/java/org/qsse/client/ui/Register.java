package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import org.qsse.client.actions.CreateUser;
import org.qsse.client.actions.GetUploadUrl;
import org.qsse.client.ui.sidebar.UserSidebarMenu;
import org.qsse.shared.dto.UploadUrl;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.exception.UserAlreadyExistsException;
import org.qsse.shared.type.UserType;

public class Register extends Composite implements Page {

	private static RegisterUiBinder uiBinder = GWT
			.create(RegisterUiBinder.class);

	final private DispatchAsync dispatch;
	final private PageContainer pageContainer;
	private final SideBarContainer sideBarContainer;

	public String imageUrl = "";
	
	/*
	 * UiBinder Elements *
	 */

	@UiField
	TextBox email;

	@UiField
	PasswordTextBox password;

	@UiField
	ListBox userTypeList;

	@UiField
	TextBox firstName;

	@UiField
	TextBox lastName;

	@UiField
	DateBox dob;

	@UiField
	RadioButton maleGender;

	@UiField
	RadioButton femaleGender;

	@UiField
	TextBox address;

	@UiField
	TextBox phone;

	@UiField
	FileUpload userDp;

	@UiField
	Label register;

	@UiField
	Label validationMessage;

	@UiField
	HorizontalPanel genderPanel;

	@UiField
	FormPanel registrationForm;

	

	interface RegisterUiBinder extends UiBinder<Widget, Register> {
	}
	

	/*
	 * Constructor
	 */
	public Register(PageContainer pageContainer,
			SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.dispatch = dispatch;
		this.sideBarContainer = sideBarContainer;
		userDp.setName("datafile");
		sideBarContainer.removeExisting();
		createUserTypeList();
		addSideBar();
		registrationForm();
	}

	/*
	 * Function to add/remove side bar menu
	 */

	public void addSideBar() {

		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new UserSidebarMenu(sideBarContainer, pageContainer,
				dispatch));
		if (Login.userType != null && !Login.userType.equals("")) {
			sideBarContainer.add(verticalPanel);
		}
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

	public boolean validateDropdown() {
		if (userTypeList.getSelectedIndex() != 0) {
			userTypeList.removeStyleName("red");
			return false;
		}
		userTypeList.addStyleName("red");
		return true;
	}

	public boolean validateTextBox(TextBox textBox) {
		if (textBox.getValue() == "") {
			textBox.addStyleName("red");
			return true;
		}
		textBox.removeStyleName("red");
		return false;
	}

	public boolean validateDate() {
		if (dob.getValue() != null) {
			dob.removeStyleName("red");
			return false;
		}
		dob.addStyleName("red");
		return true;
	}

	public boolean validateGender() {
		if ((!maleGender.getValue() && !femaleGender.getValue())) {
			genderPanel.addStyleName("red");
			return true;
		}
		genderPanel.removeStyleName("red");
		return false;
	}

	public boolean validateForm() {
		if (validateEmail() || validatePassword(password) || validateDropdown()
				|| validateTextBox(firstName) || validateDate()
				|| validateGender()) {

			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}
	
	/*
	 * Function to handle form
	 */
	
	@SuppressWarnings("deprecation")
	public void registrationForm() {

		registrationForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		registrationForm.setMethod(FormPanel.METHOD_POST);

		// Add an event handler to the form.
		registrationForm.addFormHandler(new FormHandler() {
			public void onSubmit(FormSubmitEvent event) {

			}

			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				if (event.getResults().toLowerCase().indexOf("error") > -1) {
					Window
							.alert("Error, you are not allowed to upload this file.");
				} else {
					imageUrl = event.getResults();
					GWT.log("Image Uploaded At Registration");
					createUser();
				}
			}
		});

	}

	private void uploadFile() {
		doUpload();
	}

	/*
	 * Function to upload image
	 */
	
	private void doUpload() {

		dispatch.execute(new GetUploadUrl(), new AsyncCallback<UploadUrl>() {
			public void onFailure(Throwable throwable) {
				onGetUploadUrlFailure(throwable);
			}

			public void onSuccess(UploadUrl uploadUrl) {
				registrationForm.setAction(uploadUrl.getUrl());
				registrationForm.submit();

			}
		});
	}

	private void onGetUploadUrlFailure(Throwable throwable) {
		if (throwable instanceof InvocationException) {
			Window.alert("Cannot connect to the server, please try again");
		} else {
			Window
					.alert("The BlobStore Service is unavailable. Please try again in a bit.");
		}
	}

	private void onUploadFailed(String message) {
		Window.alert("Upload failed: " + message);
	}

	/*
	 * Action function to create user invoke when clicking on register button
	 */

	public CreateUser createUserAction() {

		String gender = new String();
		if (maleGender.getValue()) {
			gender = "male";
		}
		if (femaleGender.getValue()) {
			gender = "female";
		}

		CreateUser action = new CreateUser();

		action.setEmail(email.getValue());
		action.setPassword(password.getValue());
		action.setUserType(UserType.valueOf(userTypeList.getValue(userTypeList
				.getSelectedIndex())));
		action.setFirstName(firstName.getValue());
		action.setLastName(lastName.getValue());
		action.setDob(dob.getValue());
		action.setGender(gender);
		action.setAddress(address.getValue());
		action.setPhone(phone.getValue());
		action.setUserDp(imageUrl);
		
		return action;
	}

	public void createUser() {

		dispatch.execute(createUserAction(), new AsyncCallback<VoidResult>() {

			@Override
			public void onFailure(Throwable caught) {
				if (caught instanceof UserAlreadyExistsException) {
					Window.alert("Email Address Already Exists.");
					email.addStyleName("red");
				}
				GWT.log("Authentication Failed at creating user...");
			}

			@Override
			public void onSuccess(VoidResult result) {
				Window.alert("User Created Successfuly.");
				pageContainer.add(new Login(pageContainer, sideBarContainer,
						dispatch));
			}
		});

	}

	@UiHandler("register")
	void onRegister(ClickEvent e) {

		if (validateForm()) {
			uploadFile();
		}

	}

	/*
	 * Generate user type dropdown box
	 */

	public void createUserTypeList() {
		userTypeList.addItem("--Please Select--");
		if (Login.userType != null && Login.userType.equals(UserType.ADMIN)) {
			userTypeList.addItem("Administrator", UserType.ADMIN.toString());
		}
		userTypeList.addItem("Aalim", UserType.AALIM.toString());
		userTypeList.addItem("User", UserType.USER.toString());
	}
}
