package org.qsse.client.ui.user;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.Widget;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.SideBarContainer;

public class MainReadQuranPage extends Composite implements Page {

	private static MainReadQuranPageUiBinder uiBinder = GWT
			.create(MainReadQuranPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final SideBarContainer sideBarContainer;
	private final PageContainer pageContainer;
	
	
	@UiField
	TabPanel quranTab;
	

	interface MainReadQuranPageUiBinder extends
			UiBinder<Widget, MainReadQuranPage> {
	}

	public MainReadQuranPage(SideBarContainer sideBarContainer, PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.sideBarContainer = sideBarContainer;
		this.pageContainer = pageContainer;
		
		quranTab.add(new ReadQuranPage(sideBarContainer, pageContainer, dispatch), "Quran");
		//quranTab.add(new QuranTranslationPage(dispatch), "Translation");

		quranTab.selectTab(0);
		

	}



}
