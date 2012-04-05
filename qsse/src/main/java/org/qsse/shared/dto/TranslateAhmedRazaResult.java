package org.qsse.shared.dto;

import net.customware.gwt.dispatch.shared.Result;

public class TranslateAhmedRazaResult implements Result{
	
	private String quranText;
	private String suraName;
	private int numberOfPages;
	private int page;
	
	public TranslateAhmedRazaResult(){
		
	}

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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

}