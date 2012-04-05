package org.qsse.client.ui.custom;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

public class HtmlCellRenderer implements SafeHtmlRenderer<String> {

	private static HtmlCellRenderer instance;

	protected HtmlCellRenderer() {
	}

	public static HtmlCellRenderer getInstance() {
		if (instance == null) {
			instance = new HtmlCellRenderer();
		}
		return instance;
	}

	@Override
	public SafeHtml render(String value) {
		return SafeHtmlUtils.fromTrustedString(value);
	}

	@Override
	public void render(String yourObject, SafeHtmlBuilder builder) {
		builder.append(render(yourObject));
	}

}
