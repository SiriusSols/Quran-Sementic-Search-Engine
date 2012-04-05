package org.qsse.client.ui;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class SideBarContainer extends SimplePanel {

	public void removeExisting() {
		if (this.getWidget() != null) {
			this.remove(this.getWidget());
		}

	}

	@Override
	public void add(Widget page) {

			removeExisting();
			super.add((Widget) page);

			return;
	}
	
}
