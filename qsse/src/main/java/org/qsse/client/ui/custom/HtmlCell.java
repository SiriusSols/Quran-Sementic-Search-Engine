package org.qsse.client.ui.custom;

import com.google.gwt.cell.client.AbstractSafeHtmlCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.cell.client.Cell.Context;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class HtmlCell extends AbstractSafeHtmlCell<String> {

	public HtmlCell() {
		super(HtmlCellRenderer.getInstance(), "click");
	}

	@Override
	public void onBrowserEvent(Context context, Element parent, String value,
			NativeEvent event, ValueUpdater<String> valueUpdater) {

		super.onBrowserEvent(context, parent, value, event, valueUpdater);

	}

	@Override
	protected void onEnterKeyDown(Context context, Element parent,
			String value, NativeEvent event, ValueUpdater<String> valueUpdater) {

		valueUpdater.update(value);
	}

	@Override
	protected void render(com.google.gwt.cell.client.Cell.Context context,
			SafeHtml data, SafeHtmlBuilder sb) {
		if (data != null) {
			sb.append(data);
		}
	}

}
