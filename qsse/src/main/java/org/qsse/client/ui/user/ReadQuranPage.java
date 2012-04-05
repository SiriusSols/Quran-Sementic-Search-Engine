package org.qsse.client.ui.user;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.actions.GetSuraList;
import org.qsse.client.actions.ReadQuran;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.SideBarContainer;
import org.qsse.client.ui.UserStatus;
import org.qsse.client.ui.sidebar.UserSidebarMenu;
import org.qsse.shared.dto.ReadQuranResult;
import org.qsse.shared.dto.SuraListResult;

public class ReadQuranPage extends Composite implements Page {

	private static ReadQuranUiBinder uiBinder = GWT.create(ReadQuranUiBinder.class);

	private final DispatchAsync dispatch;
	private final SideBarContainer sideBarContainer;
	private final PageContainer pageContainer;
	
	//UserStatus userStatus;
	
	List<String> chaptersName = new ArrayList<String>();
	
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
	
	interface ReadQuranUiBinder extends UiBinder<Widget, ReadQuranPage> {
	}

	public ReadQuranPage(SideBarContainer sideBarContainer, PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.sideBarContainer = sideBarContainer;
		this.pageContainer = pageContainer;
		next.removeFromParent();
		back.removeFromParent();
		populateSuraList();
		readQuran(1, 1, 10);
	
		addSideBar();
	}


	public void addSideBar(){
		UserStatus userStatus = new UserStatus(dispatch);
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new UserSidebarMenu(sideBarContainer, pageContainer, dispatch));
		//if(userStatus.isLoggedIn()){
			sideBarContainer.add(verticalPanel);
		//}
	}
	
	public ReadQuran readQuranAction(int chapterNumber, int start, int end){
		
		ReadQuran action = new ReadQuran();
		action.setChapterNumber(chapterNumber);
		action.setStart(start);
		action.setEnd(end);
		
		return action;
	}

	public void readQuran(final int chapterNumber, int start, int end){
		
		dispatch.execute(readQuranAction(chapterNumber, start, end), new AsyncCallback<ReadQuranResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed...");
			}

			@Override
			public void onSuccess(ReadQuranResult result) {
				suraName.setInnerHTML(sura.getInnerText() + " " + result.getSuraName());
				quranText.setInnerHTML(result.getQuranText());
				page.setText(String.valueOf(result.getPage()));
				numberOfPages = result.getNumberOfPages();
				
				populatePageList();
				selectPage.setItemSelected(Integer.valueOf(page.getText()), true);
				
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
	
	@UiHandler("selectSura")
	void selectSurrah(ChangeEvent event){
		pageNumber = 1;
		readQuran(Integer.valueOf(selectSura.getValue(selectSura.getSelectedIndex())), pageNumber, (pageNumber + 9));
	}
	
	@UiHandler("next")
	void next(ClickEvent event){
		readQuran(Integer.valueOf(selectSura.getValue(selectSura.getSelectedIndex())), ((Integer.valueOf(page.getText())*10)+1), ((Integer.valueOf(page.getText())*10)+10));
	}
	
	@UiHandler("back")
	void onback(ClickEvent event){
		readQuran(Integer.valueOf(selectSura.getValue(selectSura.getSelectedIndex())), ((Integer.valueOf(page.getText())*10)-19), ((Integer.valueOf(page.getText())*10)-10));
	}
	
	@UiHandler("selectPage")
	void selectPage(ChangeEvent event){
		readQuran(Integer.valueOf(selectSura.getValue(selectSura.getSelectedIndex())), ((Integer.valueOf(selectPage.getValue(selectPage.getSelectedIndex()))*10)-9), (Integer.valueOf(selectPage.getValue(selectPage.getSelectedIndex()))*10));
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
				selectSura.addItem("Select Sura", "1");
				int i = 1;
				for(String ch : result.getChaptersName()){	
					selectSura.addItem(String.valueOf(i) + " " + sura.getInnerText() + " " + ch, String.valueOf(i));
					selectSura.getElement().addClassName("slct");
					i++;
				}
			}
		});
	}
	
	public void populatePageList(){
		selectPage.clear();
		selectPage.addItem("Select Page", "");
		for(int i = 1; i <= numberOfPages; i++){
			selectPage.addItem(String.valueOf(i), String.valueOf(i));
		}
	}
	
	/*public Search searchAction(){
		
		Search action = new Search();
		action.setSearchText(searchTxt.getValue());
		
		return action;
	}
	
	public void performSearch(){
		
		dispatch.execute(searchAction(), new AsyncCallback<SearchResult>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("Authentication Failed...");
			}

			@Override
			public void onSuccess(SearchResult result) {
				Window.alert("Search Performed!");
			}
		});
	}
	
	@UiHandler("search")
	void search(ClickEvent event){
		performSearch();
	
	}*/
}
