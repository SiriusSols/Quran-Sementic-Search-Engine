package org.qsse.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class PostQuestion extends Composite implements Page {

	private static PostQuestionUiBinder uiBinder = GWT
			.create(PostQuestionUiBinder.class);

	interface PostQuestionUiBinder extends UiBinder<Widget, PostQuestion> {
	}

	public PostQuestion() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public PostQuestion(String pageName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiField
	TextArea question;
	
	@UiField
	PushButton postQuestion;
	
	@UiHandler ("postQuestion")
	void postQuestion(ClickEvent event){
		//TODO
		
	}
	
}
