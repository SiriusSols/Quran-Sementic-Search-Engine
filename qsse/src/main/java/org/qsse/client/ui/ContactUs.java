package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ContactUs extends Composite implements Page {

	private static ContactUSUiBinder uiBinder = GWT.create(ContactUSUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private final SideBarContainer sideBarContainer;
	
	interface ContactUSUiBinder extends UiBinder<Widget, ContactUs> {
	}

	public ContactUs(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		this.sideBarContainer = sideBarContainer;
		
		sideBarContainer.removeExisting();
	}
	
	@UiField
	Label submit;
	
	@UiHandler("submit")
	void submit(ClickEvent e){

		
		
	}

}
