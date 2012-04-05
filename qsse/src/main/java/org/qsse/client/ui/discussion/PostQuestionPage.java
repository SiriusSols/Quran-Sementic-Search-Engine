package org.qsse.client.ui.discussion;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.CreateQuestion;
import org.qsse.client.ui.Page;
import org.qsse.shared.dto.VoidResult;

public class PostQuestionPage extends Composite implements Page {

	private static PostQuestionPageUiBinder uiBinder = GWT
			.create(PostQuestionPageUiBinder.class);
	
	private final DispatchAsync dispatch;
	
	CKEditor questionEditor = new CKEditor(CKConfig.full);
	
	@UiField
	VerticalPanel questionPanel;
	
	@UiField
	Label postQuestion;
	
	@UiField
	Label validationMessage;
	
	interface PostQuestionPageUiBinder extends
			UiBinder<Widget, PostQuestionPage> {
	}
	
	@SuppressWarnings("deprecation")
	public PostQuestionPage(DispatchAsync dispatch){
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		questionEditor.setWidth("650px");
		questionPanel.add(questionEditor);

	}
	
	public CreateQuestion createQuestionAction(){
		
		CreateQuestion action = new CreateQuestion();
		
		action.setQuestion(questionEditor.getHTML());
		
		return action;
		
		
	}
	
	
	private void createQuestion() {

		dispatch.execute(createQuestionAction(), new AsyncCallback<VoidResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at create question");
			}

			public void onSuccess(VoidResult result) {
				Window.alert("Your Question Successfully Posted!");
			}
		});
	}

	public boolean validateForm() {
		String data = questionEditor.getData();
		if (data.equals("")) {
			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}
	
	@UiHandler("postQuestion")
	public void onPostQuestion(ClickEvent e){
		if(validateForm()){
			createQuestion();
		}
	}
	
}