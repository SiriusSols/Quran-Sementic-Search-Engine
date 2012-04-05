package org.qsse.client.ui.sidebar;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.UserLogout;
import org.qsse.client.ui.HomePage;
import org.qsse.client.ui.Login;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.ReadQuranPage;
import org.qsse.client.ui.SideBarContainer;
import org.qsse.client.ui.UserWelcomeScreen;
import org.qsse.client.ui.UsersListPage;
import org.qsse.client.ui.article.ArticlesListPage;
import org.qsse.client.ui.article.CreateArticlePage;
import org.qsse.client.ui.discussion.PostQuestionPage;
import org.qsse.client.ui.event.EventsListPage;
import org.qsse.client.ui.event.PostEventPage;
import org.qsse.client.ui.user.EditProfilePage;
import org.qsse.client.ui.user.PasswordResetPage;
import org.qsse.shared.dto.AuthenticationResult;
import org.qsse.shared.type.UserType;

public class UserSidebarMenu extends Composite implements Page {

	private final PageContainer pageContainer;
	private final DispatchAsync dispatch;
	private final SideBarContainer sideBarContainer;
	
	private static UserSidebarPageUiBinder uiBinder = GWT
			.create(UserSidebarPageUiBinder.class);

	interface UserSidebarPageUiBinder extends UiBinder<Widget, UserSidebarMenu> {
	}

	public UserSidebarMenu(SideBarContainer sideBarContainer, PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.dispatch = dispatch;
		this.sideBarContainer = sideBarContainer;
		
		if(Login.userType != null && !Login.userType.equals(UserType.ADMIN)){
			createArticle.removeFromParent();
			editArticle.removeFromParent();
			deleteArticle.removeFromParent();
			postEvent.removeFromParent();
			removeEvent.removeFromParent();
			publishEvent.removeFromParent();
		}
		
		if(Login.userType != null && !Login.userType.equals(UserType.USER)){
			postQuestion.removeFromParent();
		}
		
		if(Login.userType != null && Login.userType.equals(UserType.USER)){
			removeUser.removeFromParent();
		}
	}
	
	@UiField
	Hyperlink readQuran;
	
	@UiField 
	Hyperlink resetPassword;
	
	@UiField 
	Hyperlink editProfile;
	
	@UiField
	Hyperlink createArticle;
	
	@UiField
	Hyperlink editArticle;
	
	@UiField
	Hyperlink deleteArticle;
	
	@UiField
	Hyperlink postEvent;
	
	@UiField
	Hyperlink publishEvent;
	
	@UiField
	Hyperlink removeEvent;
	
	@UiField
	Hyperlink postQuestion;
	
	@UiField
	Hyperlink removeUser;
	
	@UiField
	Hyperlink logout;
	
	@UiHandler ("readQuran")
	void readQuran(ClickEvent event){
		pageContainer.add(new ReadQuranPage(sideBarContainer, pageContainer, dispatch));
		
	}

	
	@UiHandler ("editProfile")
	void editProfile(ClickEvent event){
		pageContainer.add(new EditProfilePage(pageContainer, sideBarContainer, dispatch));
	}
	
	@UiHandler ("resetPassword")
	void selectSurah(ClickEvent event){
		pageContainer.add(new PasswordResetPage(pageContainer, sideBarContainer, dispatch));
	}
	
	@UiHandler ("postQuestion")
	void postQuestion(ClickEvent event){
		pageContainer.add(new PostQuestionPage(dispatch));
	}
	
	@UiHandler ("createArticle")
	void createArticle(ClickEvent event){
		pageContainer.add(new CreateArticlePage(pageContainer, dispatch));
	}
	
	@UiHandler ("editArticle")
	void editArticle(ClickEvent event){
		pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
	}
	
	@UiHandler ("deleteArticle")
	void deleteArticle(ClickEvent event){
		pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
	}
	
	@UiHandler ("postEvent")
	void postEvent(ClickEvent event){
		pageContainer.add(new PostEventPage(pageContainer, dispatch));
	}
	
	@UiHandler ("publishEvent")
	void publishEvent(ClickEvent event){
		pageContainer.add(new EventsListPage(pageContainer, dispatch));
	}
	
	@UiHandler ("removeEvent")
	void removeEvent(ClickEvent event){
		pageContainer.add(new EventsListPage(pageContainer, dispatch));
	}
	
	@UiHandler ("removeUser")
	void userListPage(ClickEvent event){
		pageContainer.add(new UsersListPage(pageContainer, dispatch));
	}
	
	@UiHandler("logout")
	void logout(ClickEvent event){
		logout();
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

}
