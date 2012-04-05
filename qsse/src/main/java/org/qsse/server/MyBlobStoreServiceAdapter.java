package org.qsse.server;

import com.google.appengine.api.blobstore.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Muhammad Abid 
 * 
 * http://www.tech99.us
 */
/**
 * Adapter interface over the GAE blob store service that allows makes unit
 * testing a bit easier.
 * 
 */
public class MyBlobStoreServiceAdapter implements MyBlobStoreService {

	/**
	 * @uml.property  name="blobstoreService"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	/**
	 * @uml.property  name="blobInfoFactory"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private BlobInfoFactory blobInfoFactory = new BlobInfoFactory();

	public String getDataFileUploadUrl() {
		return blobstoreService.createUploadUrl("/upload/datafile");
	}

	public BlobInfo loadBlobInfo(BlobKey key) {
		return blobInfoFactory.loadBlobInfo(key);
	}

	public InputStream open(BlobKey key) throws IOException {
		return new BlobstoreInputStream(key);
	}

	public Map<String, BlobKey> getUploadedBlobs(HttpServletRequest req) {
		return blobstoreService.getUploadedBlobs(req);
	}

	public void delete(BlobKey blobKey) {
		blobstoreService.delete(blobKey);
	}

	public String createUploadUrl(String s) {
		return blobstoreService.createUploadUrl(s);
	}

	public void serve(BlobKey blobKey, HttpServletResponse httpServletResponse) throws IOException {
		blobstoreService.serve(blobKey, httpServletResponse);
	}

	public void serve(BlobKey blobKey, ByteRange byteRange, HttpServletResponse httpServletResponse) throws IOException {
		blobstoreService.serve(blobKey, byteRange, httpServletResponse);
	}

	public void serve(BlobKey blobKey, String s, HttpServletResponse httpServletResponse) throws IOException {
		blobstoreService.serve(blobKey, s, httpServletResponse);
	}

	public ByteRange getByteRange(HttpServletRequest httpServletRequest) {
		return blobstoreService.getByteRange(httpServletRequest);
	}

	public void delete(BlobKey... blobKeys) {
		blobstoreService.delete(blobKeys);
	}

	public byte[] fetchData(BlobKey blobKey, long offset, long length) {
		return blobstoreService.fetchData(blobKey, offset, length);
	}
}