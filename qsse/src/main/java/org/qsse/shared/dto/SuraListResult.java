package org.qsse.shared.dto;

import java.util.List;

import net.customware.gwt.dispatch.shared.Result;

public class SuraListResult implements Result {

	private List<String> chaptersName;
	
	public SuraListResult(){
		
	}

	public List<String> getChaptersName() {
		return chaptersName;
	}

	public void setChaptersName(List<String> chaptersName) {
		this.chaptersName = chaptersName;
	}
	
}
