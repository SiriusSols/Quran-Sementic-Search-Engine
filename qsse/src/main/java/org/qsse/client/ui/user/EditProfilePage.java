package org.qsse.client.ui.user;

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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import org.qsse.client.actions.EditProfile;
import org.qsse.client.actions.GetUploadUrl;
import org.qsse.client.actions.GetUserProfile;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.SideBarContainer;
import org.qsse.client.ui.UserStatus;
import org.qsse.client.ui.UserWelcomeScreen;
import org.qsse.shared.dto.UploadUrl;
import org.qsse.shared.dto.UsersListDTO;
import org.qsse.shared.dto.UsersListResult;
import org.qsse.shared.dto.VoidResult;

public class EditProfilePage extends Composite implements Page {

	private static EditProfileUiBinder uiBinder = GWT.create(EditProfileUiBinder.class);

	final private DispatchAsync dispatch;
	final private PageContainer pageContainer;
	final private SideBarContainer sideBarContainer;
	
	public String imageUrl = "";
	
	@UiField
	Label email;
	
	@UiField
	Label userTypeList;
	
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
	Label update;
	
	@UiField
	Label validationMessage;
	
	@UiField
	HorizontalPanel genderPanel;
	
	@UiField
	FormPanel profileUpdateForm;
	
	@UiField
	Image displayUserDp;
	
	@UiField
	FileUpload userDp;
	
	interface EditProfileUiBinder extends UiBinder<Widget, EditProfilePage> {
	}
	
	/*
	 * Constructor
	 */

	public EditProfilePage(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.dispatch = dispatch;
		this.sideBarContainer = sideBarContainer;
		userDp.setName("datafile");
		getUserProfile();
		editProfileForm();
	}
		
	public boolean validatePassword(PasswordTextBox passwrodTextBox){
		if(passwrodTextBox.getValue() == ""){
			passwrodTextBox.addStyleName("red");
			return true;	
		}
		passwrodTextBox.removeStyleName("red");
		return false;
	}
	
	public boolean validateTextBox(TextBox textBox){
		if(textBox.getValue() == ""){
			textBox.addStyleName("red");
			return true;	
		}
		textBox.removeStyleName("red");
		return false;
	}
	
	public boolean validateDate(){
		if(dob.getValue() != null){
			dob.removeStyleName("red");
			return false;
		}
		dob.addStyleName("red");
		return true;
	}
	
	public boolean validateGender(){
		if((!maleGender.getValue() && !femaleGender.getValue())){
			genderPanel.addStyleName("red");
			return true;
		}
		genderPanel.removeStyleName("red");
		return false;
	}
	
	public boolean validateForm(){
		if( validateTextBox(firstName) || validateDate() || validateGender()){	
			
			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;		
	}
	
	/*
	 * Get user profile detail and populate the fields
	 */
	
	public GetUserProfile getUserProfileAction(){
		
		GetUserProfile action = new GetUserProfile();
		
		return action;
	}
	
	public void getUserProfile(){
		
		dispatch.execute(getUserProfileAction(), new AsyncCallback<UsersListResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed at getting user profile detail...");
			}

			@Override
			public void onSuccess(UsersListResult result) {

				for(UsersListDTO user : result.getUsers()){
					userTypeList.setText(user.getUserType().toString());
					email.setText(user.getEmail());
					firstName.setValue(user.getFirstName());
					lastName.setValue(user.getLastName());
					dob.setValue(user.getDob());
					if(user.getGender().equals("male")){
						maleGender.setChecked(true);
					}
					else if(user.getGender().equals("female")){
						femaleGender.setChecked(true);
					}
					address.setValue(user.getAddress());
					phone.setValue(user.getPhone());
					String userImage = user.getUserDp();
					if(!userImage.equals("")){
						displayUserDp.setUrl(user.getUserDp());
					}else{
						displayUserDp.setUrl("images/noProfileImage.png");
					}
					
				}		
			}
		});
		
	}
	
	/*
	 * Function to handle form
	 */
	
	@SuppressWarnings("deprecation")
	public void editProfileForm() {

		profileUpdateForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		profileUpdateForm.setMethod(FormPanel.METHOD_POST);

		// Add an event handler to the form.
		profileUpdateForm.addFormHandler(new FormHandler() {
			public void onSubmit(FormSubmitEvent event) {

			}

			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				if (event.getResults().toLowerCase().indexOf("error") > -1) {
					Window
							.alert("Error, you are not allowed to upload this file.");
				} else {
					imageUrl = event.getResults();
					GWT.log("Image Uploaded At Edit Profile");
					editProfile();
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
				profileUpdateForm.setAction(uploadUrl.getUrl());
				profileUpdateForm.submit();

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
	 * action for saving new profile information
	 */
	
	public EditProfile editProfileAction(){
		
		String gender = new String();
		if(maleGender.getValue()){
			gender = "male";
		}
		if(femaleGender.getValue()){
			gender = "female";
		}
		
		EditProfile action = new EditProfile();
		
		action.setFirstName(firstName.getValue());
		action.setLastName(lastName.getValue());
		action.setDob(dob.getValue());
		action.setGender(gender);
		action.setAddress(address.getValue());
		action.setPhone(phone.getValue());
		action.setUserDp(imageUrl);
		
		return action;
	}
	
	public void editProfile(){
		
		dispatch.execute(editProfileAction(), new AsyncCallback<VoidResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed at saving user updated profile...");
			}

			@Override
			public void onSuccess(VoidResult result) {
				Window.alert("Profile Updated Successfuly.");	
				pageContainer.add(new EditProfilePage(pageContainer, sideBarContainer, dispatch));
			}
		});
	}
	
	@UiHandler("update")
	void onSubmit(ClickEvent e){
		
		 if(validateForm()){
			 uploadFile();
		 }
		 
	}

}
