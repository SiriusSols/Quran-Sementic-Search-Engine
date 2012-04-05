package org.qsse.server.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Singleton;


/**
 * @author Muhammad Abid 
 * 
 * http://www.tech99.us
 */

@SuppressWarnings("serial")
@Singleton
public class SuccessfulUploadServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		String url = req.getParameter(UploadBlobServlet.IMAGE_URL_ATTRIBUTE);

		resp.setContentType("text/html");
		// resp.getWriter().println("Successfully uploaded. Blob key: ");
		// resp.getWriter().println("Starting MapReduce Job for this CSV file.");
		resp.getWriter().println(url);
	}
}