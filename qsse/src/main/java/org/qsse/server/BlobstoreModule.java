package org.qsse.server;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

/**
 * @author Muhammad Abid 
 * 
 * http://www.tech99.us
 */
public class BlobstoreModule extends AbstractModule {

  @Override
  protected void configure() {
  }

	@Provides
	public MyBlobStoreService provideBlobStoreService() {
		return new MyBlobStoreServiceAdapter();
	}
}
