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
import com.google.gwt.user.client.ui.Widget;

public class SementicSearch extends Composite implements Page {

	private static SementicSearchUiBinder uiBinder = GWT
			.create(SementicSearchUiBinder.class);

	interface SementicSearchUiBinder extends UiBinder<Widget, SementicSearch> {
	}

	public SementicSearch() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public SementicSearch(String pageName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

}
