package org.qsse.shared.dto;

import net.customware.gwt.dispatch.shared.Result;

public class ReadQuranDTO implements Result{

	private String quranText;

	public ReadQuranDTO(){
		
	}
	
	public String getQuranText() {
		return quranText;
	}

	public void setQuranText(String quranText) {
		this.quranText = quranText;
	}
	
}
