package org.qsse.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Footer extends Composite {

	private static FooterUiBinder uiBinder = GWT.create(FooterUiBinder.class);

	interface FooterUiBinder extends UiBinder<Widget, Footer> {
	}

	public Footer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	
	public Footer(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
