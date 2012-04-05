package org.qsse.client.ui.user;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.TranslateAhmedRaza;
import org.qsse.client.ui.Page;
import org.qsse.shared.dto.TranslateAhmedRazaResult;

public class QuranTranslationPage extends Composite implements Page {

	private static QuranTranslationUiBinder uiBinder = GWT
			.create(QuranTranslationUiBinder.class);

	@UiField
	ListBox selectSura;
	
	@UiField
	ListBox selectPage;
	
//	@UiField
//	TextBox searchTxt;
//	
//	@UiField
//	Label search;
	
	@UiField
	DivElement quranText;  

	@UiField
	DivElement suraName;
	
	@UiField
	FlowPanel navigationPanel;
	
	@UiField
	DivElement sura;
	
	@UiField
	DivElement bismillah;
	
	@UiField
	DivElement bismillahTxt;
	
	@UiField
	Hyperlink next;
	
	@UiField
	Hyperlink back;
	
	@UiField
	Label page;
	
	int numberOfPages = 0;
	int pageNumber = 0;
	
	private final DispatchAsync dispatch;
	interface QuranTranslationUiBinder extends
			UiBinder<Widget, QuranTranslationPage> {
	}

	public QuranTranslationPage(DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		translate(0, 1, 10);
	}

	public TranslateAhmedRaza translateAhmedRazaAction(int chapterNumber, int start, int end){
	
		TranslateAhmedRaza action = new TranslateAhmedRaza();
		action.setChapterNumber(chapterNumber);
		action.setStart(start);
		action.setEnd(end);
		
		return action;
		
	}
	
	public void translate(final int chapterNumber, int start, int end){
		
		dispatch.execute(translateAhmedRazaAction(chapterNumber, start, end), new AsyncCallback<TranslateAhmedRazaResult>() {
	
			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed...");
			}
	
			@Override
			public void onSuccess(TranslateAhmedRazaResult result) {
				Window.alert("Translate Performed!");
				suraName.setInnerHTML(sura.getInnerText() + " " + result.getSuraName());
				quranText.setInnerHTML(result.getQuranText());
				page.setText(String.valueOf(result.getPage()));
				numberOfPages = result.getNumberOfPages();
				
				
				if(numberOfPages == 1){
					navigationPanel.add(page);
					next.removeFromParent();
					back.removeFromParent();
					bismillah.setInnerHTML("");
					if(chapterNumber != 1){
						bismillah.setInnerHTML(bismillahTxt.getInnerText());
					}
				}
				else if(Integer.valueOf(page.getText()) == 1){
					back.removeFromParent();
					navigationPanel.add(page);
					navigationPanel.add(next);
					bismillah.setInnerHTML("");
					if(chapterNumber != 1){
						bismillah.setInnerHTML(bismillahTxt.getInnerText());
					}
				}
				else if(Integer.valueOf(page.getText()) == numberOfPages){
					next.removeFromParent();
					navigationPanel.add(back);
					navigationPanel.add(page);
					bismillah.setInnerHTML("");
				}
				else{
					navigationPanel.add(back);
					navigationPanel.add(page);
					navigationPanel.add(next);
					bismillah.setInnerHTML("");
				}
			}
		});
	}
	
	
}
