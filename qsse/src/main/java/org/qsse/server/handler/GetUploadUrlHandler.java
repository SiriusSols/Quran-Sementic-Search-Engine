package org.qsse.server.handler;

import com.google.inject.Inject;
import org.qsse.client.actions.GetUploadUrl;
import org.qsse.server.MyBlobStoreService;
import org.qsse.shared.dto.UploadUrl;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.ActionException;

/**
 * Wraps the blobstore api to provide an upload URL for the GWT client
 */
public class GetUploadUrlHandler implements
		ActionHandler<GetUploadUrl, UploadUrl> {

	private final MyBlobStoreService blobStoreService;

	@Inject
	public GetUploadUrlHandler(MyBlobStoreService blobStoreService) {
		this.blobStoreService = blobStoreService;
	}

	public UploadUrl execute(GetUploadUrl action, ExecutionContext context)
			throws ActionException {

		System.out.println("GET UPLOAD HANDLER");

		return new UploadUrl(blobStoreService
				.createUploadUrl("/upload/datafile"));
	}

	public Class<GetUploadUrl> getActionType() {
		return GetUploadUrl.class;
	}

	public void rollback(GetUploadUrl action, UploadUrl result,
			ExecutionContext context) throws ActionException {

	}
}
