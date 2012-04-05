package org.qsse.client.ui.custom;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.text.shared.SafeHtmlRenderer;

/**
 * @author Muhammad Abid
 */

public class ClickableImageResourceRenderer implements SafeHtmlRenderer<String> {

	private static ClickableImageResourceRenderer instance;

	protected ClickableImageResourceRenderer() {
	}

	public static ClickableImageResourceRenderer getInstance() {
		if (instance == null) {
			instance = new ClickableImageResourceRenderer();
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