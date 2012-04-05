package org.qsse.client.ui;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.GetSuraList;
import org.qsse.client.actions.Search;
import org.qsse.shared.dto.SearchResult;
import org.qsse.shared.dto.SuraListResult;

public class QuranSearchPage extends Composite implements Page {

	private static SearchUiBinder uiBinder = GWT.create(SearchUiBinder.class);

	interface SearchUiBinder extends UiBinder<Widget, QuranSearchPage> {
	}
	
	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;

	public QuranSearchPage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		populateSuraList();
		quranText.setInnerHTML("No search result.");
		
	}
	
	@UiField
	TextBox searchTxt;
	
	@UiField
	Button search;
	
	@UiField
	DivElement quranText;
	
	@UiField
	Label totalSearches;
	
	@UiField
	ListBox selectSura;
	
	@UiField
	ListBox selectAyah;
	
	@UiField
	DivElement sura;
	
	public Search searchAction(){
		
		Search action = new Search();
		action.setSearchText(searchTxt.getValue());
		action.setSearchMode(1);
		return action;
	}
	
	public void performSearch(){
		
		dispatch.execute(searchAction(), new AsyncCallback<SearchResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed at Quran Search...");
			}

			@Override
			public void onSuccess(SearchResult result) {
				if(result.getTotalResult() != 0){
					quranText.setInnerHTML(result.getQuranText());
					totalSearches.setText("Total Search Results =  " + String.valueOf(result.getTotalResult()));
				}
				else{
					quranText.setInnerHTML("No search result.");	
					totalSearches.setText("Total Search Results =  " + String.valueOf(result.getTotalResult()));
				}
			
			}
		});
	}
	
	@UiHandler("search")
	void search(ClickEvent event){
		performSearch();
	
	}
	
	public GetSuraList getSuraListAction(){
		
		GetSuraList action = new GetSuraList();
		
		return action;
	}
	
	public void populateSuraList(){
		
		dispatch.execute(getSuraListAction(), new AsyncCallback<SuraListResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed...");
			}

			@Override
			public void onSuccess(SuraListResult result) {
				selectSura.addItem("Select Sura", "0");
				int i = 1;
				for(String ch : result.getChaptersName()){	
					selectSura.addItem(String.valueOf(i) + " " + sura.getInnerText() + " " + ch, String.valueOf(i));
					selectSura.getElement().addClassName("slct");
					i++;
				}
			}
		});
	}
	
	public void searchAyah(int surahNumber){
		
		Search action = new Search();
		action.setSearchMode(2);
		action.setSurahNumber(surahNumber);
		
		dispatch.execute(action, new AsyncCallback<SearchResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed at Search Ayah...");
			}

			@Override
			public void onSuccess(SearchResult result) {
				populatePageList(result.getTotalVerses());
			
			}
		});
	}
	
	public void getAyah(int ayahNumber){
		
		Search action = new Search();
		action.setSearchMode(3);
		action.setSurahNumber(selectSura.getSelectedIndex());
		action.setAyahNumber(ayahNumber);
		
		dispatch.execute(action, new AsyncCallback<SearchResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed at getting Ayah...");
			}

			@Override
			public void onSuccess(SearchResult result) {
				if(result.getTotalResult() != 0){
					quranText.setInnerHTML(result.getQuranText());
				}
				else{
					quranText.setInnerHTML("No search result.");
				}
			
			}
		});
	}
	
	@UiHandler("selectSura")
	void selectSurrah(ChangeEvent event){
		if(selectSura.getSelectedIndex() != 0){
			searchAyah(selectSura.getSelectedIndex());
		}
	}
	
	@UiHandler("selectAyah")
	void selectAyah(ChangeEvent event){
		if(selectAyah.getSelectedIndex() != 0){
			getAyah(selectAyah.getSelectedIndex());
		}
	}


	public void populatePageList(int totalVerses){
		selectAyah.clear();
		selectAyah.addItem("Select Ayah", "0");
		for(int i = 1; i <= totalVerses; i++){	
			selectAyah.addItem(String.valueOf(i), String.valueOf(i));
			selectAyah.getElement().addClassName("slct");
		}
	}
}
