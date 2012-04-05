package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.UserLogout;
import org.qsse.client.ui.article.ArticlesListPage;
import org.qsse.client.ui.discussion.QuestionsListPage;
import org.qsse.client.ui.event.EventsListPage;
import org.qsse.shared.dto.AuthenticationResult;
import org.qsse.shared.type.UserType;

public class Header extends Composite implements Page {

	private static HeaderUiBinder uiBinder = GWT.create(HeaderUiBinder.class);

	interface HeaderUiBinder extends UiBinder<Widget, Header> {
	}
	
	final private DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private final SideBarContainer sideBarContainer;
	

	public Header(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		this.dispatch = dispatch;
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.sideBarContainer = sideBarContainer;

		pageContainer.add(new WelcomePage(pageContainer, sideBarContainer, dispatch));
		home.addStyleName("active");
		
		if(Login.userType != null && !Login.userType.equals("")){
			login.removeFromParent();
			
		}
		if(Login.userType == null || Login.userType.equals("")){
			logout.removeFromParent();
		}

		if(Login.userType != null && !Login.userType.equals(UserType.ADMIN)){
			register.removeFromParent();
		}
	}
	
	@UiField
	HorizontalPanel menuBar;	
	
	@UiField
	Hyperlink home;
	
	@UiField
	Hyperlink readQuran;
	
	@UiField
	Hyperlink searchQuran;
	
	@UiField
	Hyperlink articlesList;
	
	@UiField
	Hyperlink questionsList;
	
	@UiField
	Hyperlink eventsList;
	
	@UiField
	Hyperlink contactus;
	
	@UiField
	Hyperlink login;
	
	@UiField
	Hyperlink register;
	
	@UiField
	Hyperlink logout;
	
	@UiField
	Hyperlink usersList;
		
	@UiHandler("home")
	void home(ClickEvent event){
		pageContainer.add(new WelcomePage(pageContainer, sideBarContainer, dispatch));
		setActiveStyle(home);
	}
	
	@UiHandler("login")
	void login(ClickEvent event){
		pageContainer.add(new Login(pageContainer, sideBarContainer, dispatch));
		setActiveStyle(login);
	}
	
	@UiHandler("register")
	void register(ClickEvent event){
		pageContainer.add(new Register(pageContainer, sideBarContainer, dispatch));
		setActiveStyle(register);
	}
	
	@UiHandler("usersList")
	void userList(ClickEvent event){
		pageContainer.add(new UsersListPage(pageContainer, dispatch));
		setActiveStyle(usersList);
	}
	
	@UiHandler("readQuran")
	void readQuran(ClickEvent event){
		pageContainer.add(new ReadQuranPage(sideBarContainer, pageContainer, dispatch));
		setActiveStyle(readQuran);
	}
	
	@UiHandler("searchQuran")
	void searchQuran(ClickEvent event){
		pageContainer.add(new QuranSearchPage(pageContainer, dispatch));
		setActiveStyle(searchQuran);
	}
	
	@UiHandler("articlesList")
	void articlesList(ClickEvent event){
		pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
		setActiveStyle(articlesList);
	}
	
	@UiHandler("eventsList")
	void eventsList(ClickEvent event){
		pageContainer.add(new EventsListPage(pageContainer, dispatch));
		setActiveStyle(eventsList);
	}
	
	@UiHandler("questionsList")
	void questionList(ClickEvent event){
		pageContainer.add(new QuestionsListPage(pageContainer, dispatch));
		setActiveStyle(questionsList);
	}
	
	@UiHandler("contactus")
	void contactus(ClickEvent event){
		pageContainer.add(new ContactUs(pageContainer, sideBarContainer, dispatch));
		setActiveStyle(contactus);
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
	
	public void setActiveStyle(Hyperlink hyperlink){
		
		home.removeStyleName("active");
		readQuran.removeStyleName("active");
		searchQuran.removeStyleName("active");
		articlesList.removeStyleName("active");
		questionsList.removeStyleName("active");
		eventsList.removeStyleName("active");
		contactus.removeStyleName("active");
		login.removeStyleName("active");
		register.removeStyleName("active");
		usersList.removeStyleName("active");
		
		hyperlink.addStyleName("active");
	}
}
