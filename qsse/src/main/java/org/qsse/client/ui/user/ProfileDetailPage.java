package org.qsse.client.ui.user;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.GetUserProfile;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.UsersListPage;
import org.qsse.shared.dto.UsersListDTO;
import org.qsse.shared.dto.UsersListResult;

public class ProfileDetailPage extends Composite implements Page {

	private static ProfileDetailPageUiBinder uiBinder = GWT
			.create(ProfileDetailPageUiBinder.class);

	private final PageContainer pageContainer;
	private final DispatchAsync dispatch;
	public String userEmail;

	@UiField
	Label userID;

	@UiField
	Label email;
	
	@UiField
	Label userTypeList;
	
	@UiField
	Label firstName;
	
	@UiField
	Label lastName;
	
	@UiField
	Label dob;
	
	@UiField
	Label gender;
	
	@UiField
	Label address;
	
	@UiField
	Label phone;
	
	@UiField
	Image displayUserDp;

	@UiField
	Label back;
	
	interface ProfileDetailPageUiBinder extends
			UiBinder<Widget, ProfileDetailPage> {
	}

	public ProfileDetailPage(PageContainer pageContainer, DispatchAsync dispatch, String userEmail) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.dispatch = dispatch;
		this.userEmail = userEmail;
		getUserProfile();
	}

	
	/*
	 * Get user profile detail and populate the fields
	 */
	
	public GetUserProfile getUserProfileAction(){
		
		GetUserProfile action = new GetUserProfile();
		
		action.setEmail(userEmail);
		
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
					userID.setText(user.getUserID().toString());
					userTypeList.setText(user.getUserType().toString());
					email.setText(user.getEmail());
					firstName.setText(user.getFirstName());
					lastName.setText(user.getLastName());
					dob.setText(user.getDob().toString());
					gender.setText(user.getGender());
					address.setText(user.getAddress());
					phone.setText(user.getPhone());
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
	
	@UiHandler("back")
	void backToUsersList(ClickEvent e){
		pageContainer.add(new UsersListPage(pageContainer, dispatch));
	}
	
	
	
}
