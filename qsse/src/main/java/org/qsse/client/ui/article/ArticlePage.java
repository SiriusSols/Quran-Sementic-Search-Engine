package org.qsse.client.ui.article;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.GetArticle;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.shared.dto.ArticleListResult;
import org.qsse.shared.dto.ArticlesListDTO;

public class ArticlePage extends Composite implements Page {

	private static ArticlePageUiBinder uiBinder = GWT
			.create(ArticlePageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private long articleId;
	interface ArticlePageUiBinder extends UiBinder<Widget, ArticlePage> {
	}

	public ArticlePage(PageContainer pageContainer, DispatchAsync dispatch, long articleId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.dispatch = dispatch;
		this.articleId = articleId;
		getArticle();
	}

	@UiField
	Label articleTitle;
	
	@UiField
	Label createdDate;
	
	@UiField
	DivElement articleTxt;
	
	@UiField
	Label back;
	
	public void getArticle(){
		
		GetArticle action = new GetArticle();
		action.setArticleId(articleId);
		
		dispatch.execute(action,
				new AsyncCallback<ArticleListResult>() {
					public void onFailure(Throwable throwable) {
						GWT.log("Authentication Failed at fetching article");
					}

					public void onSuccess(ArticleListResult result) {
						for(ArticlesListDTO a : result.getArticles()){
							articleTitle.setText(a.getTitle());
							createdDate.setText("Created Date: " + a.getSubmissionDate().toString());
							articleTxt.setInnerHTML(a.getArticleTxt());
						}
						
					}
				});
	}
	
	@UiHandler("back")
	void back(ClickEvent e){
		pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
	}
}


