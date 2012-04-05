package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.ui.sidebar.UserSidebarMenu;
import org.qsse.shared.type.UserType;

public class UserWelcomeScreen extends Composite implements Page {

	private static UserWelcomeScreenUiBinder uiBinder = GWT
			.create(UserWelcomeScreenUiBinder.class);

	interface UserWelcomeScreenUiBinder extends
			UiBinder<Widget, UserWelcomeScreen> {
	}

	final private DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private final SideBarContainer sideBarContainer;

	public UserWelcomeScreen(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		this.dispatch = dispatch;
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.sideBarContainer = sideBarContainer;

		//addSideBar();

	}
	
	public void addSideBar(){
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new UserSidebarMenu(sideBarContainer, pageContainer, dispatch));
//		if(userType != null){
//			sideBarContainer.add(verticalPanel);
//		}
	}
	
	
}