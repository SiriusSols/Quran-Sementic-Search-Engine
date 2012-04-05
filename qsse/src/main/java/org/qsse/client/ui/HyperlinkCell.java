package org.qsse.client.ui;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;

/**
 * {@link Cell} that wraps a {@link Hyperlink}
 * WARNING: make sure the contents of your Hyperlink really are safe from XSS!
 * 
 * @author turbomanage
 */

/**
 * @author Muhammad Abid
 */

public class HyperlinkCell extends AbstractCell<String> {

	@Override
	public void render(com.google.gwt.cell.client.Cell.Context context, String h, SafeHtmlBuilder sb) {
		sb.append(SafeHtmlUtils.fromTrustedString(h));
	}

}
