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
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.CreateArticle;
import org.qsse.client.actions.GetArticle;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.shared.dto.ArticleListResult;
import org.qsse.shared.dto.ArticlesListDTO;
import org.qsse.shared.dto.VoidResult;

public class EditArticle extends Composite implements Page {

	private static EditArticleUiBinder uiBinder = GWT.create(EditArticleUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	CKEditor textEditor = new CKEditor(CKConfig.full);
	private long articleId;
	
	interface EditArticleUiBinder extends UiBinder<Widget, EditArticle> {
	}

	@SuppressWarnings("deprecation")
	public EditArticle(PageContainer pageContainer, DispatchAsync dispatch, long articleId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		textEditor.setWidth("650px");
		articleTxtEditor.add(textEditor);
		this.articleId = articleId;
		getArticles();
	}

	@UiField
	Label validationMessage;
	
	@UiField
	TextBox articleTitle;
	
	@UiField
	Label createdDate;
	
	@UiField
	RadioButton publishYes;
	
	@UiField
	RadioButton publishNo;
	
	@UiField
	HorizontalPanel articleTxtEditor;
	
	@UiField
	Button saveArticle;
	
	public void getArticles() {

		GetArticle action = new GetArticle();
		action.setArticleId(articleId);

		dispatch.execute(action, new AsyncCallback<ArticleListResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at fetching edit article");
			}

			@SuppressWarnings("deprecation")
			public void onSuccess(ArticleListResult result) {
				for(ArticlesListDTO a : result.getArticles()){
				articleTitle.setText(a.getTitle());
				textEditor.setData(a.getArticleTxt());
				createdDate.setText(a.getSubmissionDate().toString());
				if(a.isPublish()){
					publishYes.setChecked(true);
				}
				else{
					publishNo.setChecked(true);
				}
				}
			}
		});
	}

	
	
	public void saveArticle(){
		
		CreateArticle action = new CreateArticle();
		
		action.setArticleId(articleId);
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