package org.qsse.shared.dto;

import net.customware.gwt.dispatch.shared.Result;

public class SearchResult implements Result {

	private String quranText;
	private String suraName;
	private int totalResult;
	private int totalVerses;
	
	public SearchResult(){}

	public String getQuranText() {
		return quranText;
	}

	public void setQuranText(String quranText) {
		this.quranText = quranText;
	}

	public String getSuraName() {
		return suraName;
	}

	public void setSuraName(String suraName) {
		this.suraName = suraName;
	}

	public int getTotalResult() {
		return totalResult;
	}

	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}

	public int getTotalVerses() {
		return totalVerses;
	}

	public void setTotalVerses(int totalVerses) {
		this.totalVerses = totalVerses;
	}
	
	
	
}
