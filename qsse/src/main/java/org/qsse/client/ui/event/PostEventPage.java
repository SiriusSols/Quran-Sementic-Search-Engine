package org.qsse.client.ui.event;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import org.qsse.client.actions.CreateEvent;
import org.qsse.client.ui.Login;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.exception.UserAlreadyExistsException;

public class PostEventPage extends Composite implements Page {

	private static PostEventPageUiBinder uiBinder = GWT.create(PostEventPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;

	interface PostEventPageUiBinder extends UiBinder<Widget, PostEventPage> {
	}

	public PostEventPage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;

	}

	@UiField
	Label validationMessage;

	@UiField
	TextBox eventTitle;

	@UiField
	TextArea eventDetail;

	@UiField
	TextBox eventLocation;

	@UiField
	DateBox eventStartDate;

	@UiField
	DateBox eventEndDate;

	@UiField
	TextBox eventTiming;

	@UiField
	Label postEvent;

	public boolean validateTextBox(TextBox textBox) {
		if (textBox.getValue() == "") {
			textBox.addStyleName("red");
			return true;
		}
		textBox.removeStyleName("red");
		return false;
	}

	public boolean validateDate(DateBox date) {
		if (date.getValue() != null) {
			date.removeStyleName("red");
			return false;
		}
		date.addStyleName("red");
		return true;
	}

	public boolean validateForm() {
		if (validateTextBox(eventTitle) || validateTextBox(eventLocation)
				|| validateDate(eventStartDate) || validateDate(eventEndDate) || validateTextBox(eventTiming)) {

			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}
	
	public void createEvent(){
		
		CreateEvent action = new CreateEvent();
		
		action.setTitle(eventTitle.getText());
		action.setDetail(eventDetail.getText());
		action.setLocation(eventLocation.getText());
		action.setStartDate(eventStartDate.getValue());
		action.setEndDate(eventEndDate.getValue());
		action.setTiming(eventTiming.getText());
		action.setPublish(true);
		
		dispatch.execute(action, new AsyncCallback<VoidResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed at creating event...");
			}

			@Override
			public void onSuccess(VoidResult result) {
				Window.alert("Event Created Successfuly.");
				pageContainer.add(new EventsListPage(pageContainer, dispatch));
			}
		});
		
	}
	
	@UiHandler("postEvent")
	void postEvent(ClickEvent e) {

		if (validateForm()) {
			createEvent();
		}

	}
}
