package org.qsse.client.ui.article;

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
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.CreateArticle;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.shared.dto.VoidResult;

public class CreateArticlePage extends Composite implements Page {

	private static CreateArticlePageUiBinder uiBinder = GWT
			.create(CreateArticlePageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	CKEditor textEditor = new CKEditor(CKConfig.full);
	
	interface CreateArticlePageUiBinder extends
			UiBinder<Widget, CreateArticlePage> {
	}

	@SuppressWarnings("deprecation")
	public CreateArticlePage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		textEditor.setWidth("650px");
		articleTxtEditor.add(textEditor);
	}

	@UiField
	Label validationMessage;
	
	@UiField
	TextBox articleTitle;
	
	@UiField
	HorizontalPanel articleTxtEditor;
	
	@UiField
	Button saveArticle;
	
	public void saveArticle(){
		
		CreateArticle action = new CreateArticle();
		
		action.setTitle(articleTitle.getText());
		action.setArticleTxt(textEditor.getData());
		
		dispatch.execute(action, new AsyncCallback<VoidResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at create question");
			}

			public void onSuccess(VoidResult result) {
				Window.alert("Article saved successfully.");
			pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
			}
		});
		
	}
	
	public boolean validateTextBox(TextBox textBox) {
		if (textBox.getValue() == "") {
			textBox.addStyleName("red");
			return true;
		}
		textBox.removeStyleName("red");
		return false;
	}
	
	public boolean validateCKEditor(CKEditor editor) {
		String data = editor.getData();
		if (data.equals("")) {
			return true;
		}
		return false;
	}
	
	public boolean validateArticle(){
		if (validateTextBox(articleTitle) || validateCKEditor(textEditor)) {
			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}
	
	@UiHandler("saveArticle")
	public void saveArticle(ClickEvent event){
		if(validateArticle()){
			saveArticle();
		}
	}
}
