package org.qsse.server;

import java.io.IOException;
import java.io.InputStream;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;

/**
 * @author Muhammad Abid 
 * 
 * http://www.tech99.us
 */
public interface MyBlobStoreService extends BlobstoreService {


	  /**
	   * See {@link com.google.appengine.api.blobstore.BlobInfo}
	   * @param key
	   * @return
	   */
	  BlobInfo loadBlobInfo(BlobKey key);

	  /**
	   * Opens a specific blob for reading as an InputStream.
	   * See {@link com.google.appengine.api.blobstore.BlobstoreInputStream}
	   * @param key
	   * @return
	   * @throws java.io.IOException
	   */
	  InputStream open(BlobKey key) throws IOException;
	}
