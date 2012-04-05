package org.qsse.server;

import com.google.inject.servlet.ServletModule;

public class AdminServerModule extends ServletModule {

	@Override
	protected void configureServlets() {
		
		serve("/Admin/dispatch").with(DispatchServlet.class);
	}	
}
