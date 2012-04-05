package org.qsse.server.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.qsse.server.MyBlobStoreService;
import org.qsse.server.MyBlobStoreServiceAdapter;

@SuppressWarnings("serial")
@Singleton
public class UploadBlobServlet extends HttpServlet {

	public static String IMAGE_URL_ATTRIBUTE = "image-url";

	private final MyBlobStoreService blobstoreService;
	
	public UploadBlobServlet() {
		blobstoreService = new MyBlobStoreServiceAdapter();
	}

	@Inject
	public UploadBlobServlet(MyBlobStoreService blobstoreService) {
		this.blobstoreService = blobstoreService;
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		BlobKey blobKey = null;

		try {
			blobKey = getDatafileBlobKey(req);
			BlobInfo blobInfo = blobstoreService.loadBlobInfo(blobKey);

			if (blobKey == null) {

				resp.getWriter().println("error: Image could not be uploaded, try later.");
				// resp.sendRedirect("/");
			} else {
				ImagesService imagesService = ImagesServiceFactory.getImagesService();
				String imageURL = imagesService.getServingUrl(blobKey);
				imageURL = imageURL.substring(imageURL.indexOf("_")-1, imageURL.length());
		
				
				
				//resp.getWriter().println(imageURL);

				 resp.sendRedirect("/upload-success?" + IMAGE_URL_ATTRIBUTE +
				 "=" + imageURL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private BlobKey getDatafileBlobKey(HttpServletRequest req) {

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		BlobKey blobKey = blobs.get("datafile");
		if (blobKey == null) {
			throw new IllegalStateException("No file has been uploaded");
		}
		return blobKey;
	}
}