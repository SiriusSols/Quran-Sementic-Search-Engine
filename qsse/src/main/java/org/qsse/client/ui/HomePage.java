package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class HomePage extends Composite {

	private final DispatchAsync dispatch;
	public static PageContainer pageContainer ;
	public static SideBarContainer sideBarContainer;
	
	private static HomePageUiBinder uiBinder = GWT.create(HomePageUiBinder.class);

	interface HomePageUiBinder extends UiBinder<Widget, HomePage> {
	}

	
	@UiField
	HorizontalPanel mainContent;

	@UiField
	SimplePanel headerSimplePanel;

	@UiField
	SimplePanel footerSimplePanel;

	
	public HomePage(DispatchAsync dispatch) {
		this.dispatch = dispatch;
		initWidget(uiBinder.createAndBindUi(this));
		pageContainer  = new PageContainer();
		sideBarContainer =  new SideBarContainer();
		init();
	}

	void init() {
		
		headerSimplePanel.add(new Header(pageContainer, sideBarContainer, dispatch));

		mainContent.add(sideBarContainer);
		
		mainContent.add(pageContainer);
		
		footerSimplePanel.add(new Footer());
	}


}
