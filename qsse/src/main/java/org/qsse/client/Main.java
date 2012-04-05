package org.qsse.client;

import net.customware.gwt.dispatch.client.DispatchAsync;
import net.customware.gwt.dispatch.client.standard.StandardDispatchAsync;
import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import org.qsse.client.actions.GetUsers;
import org.qsse.client.ui.HomePage;
import org.qsse.shared.dto.UsersListResult;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Main implements EntryPoint {

	private DispatchAsync dispatch;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		dispatch = new StandardDispatchAsync(null);
		
		RootPanel.get().add(new HomePage(dispatch));

		dispatch.execute(new GetUsers(), new AsyncCallback<UsersListResult>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Could not load data, Please Try Later.");
			}

			@Override
			public void onSuccess(UsersListResult result) {

			}
		});
		
	}
}
