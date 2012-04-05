package org.qsse.shared.dto;

import net.customware.gwt.dispatch.shared.Result;

/**
 * @author Muhammad Abid 
 * 
 * http://www.tech99.us
 */
/**
 * Result of the GetUploadUrl action which contains a URL which can be used to
 * post a blob
 */
public class UploadUrl implements Result {

	private String url;

	public UploadUrl() {
	}

	public UploadUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
