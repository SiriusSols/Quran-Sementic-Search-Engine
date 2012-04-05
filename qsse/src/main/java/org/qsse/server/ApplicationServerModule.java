package org.qsse.server;

import com.google.appengine.api.users.UserService;
import com.google.inject.servlet.ServletModule;
import org.qsse.server.handler.ApplicationHandlers;
import org.qsse.server.servlet.FirstRunServlet;
import org.qsse.server.servlet.SuccessfulUploadServlet;
import org.qsse.server.servlet.UploadBlobServlet;
public class ApplicationServerModule extends ServletModule {

	@Override
	protected void configureServlets() {
		//super.configureServlets();

		install(new ApplicationHandlers());
		install(new SimpleDSModule());
		install(new BlobstoreModule());
		
		serve("*/dispatch").with(DispatchServlet.class);
		
		bind(UserService.class).to(QsseUserService.class);
		
		serve("/upload/datafile").with(UploadBlobServlet.class);
		serve("*/upload-success").with(SuccessfulUploadServlet.class);
		serve("/install-data").with(FirstRunServlet.class);
	}

}
