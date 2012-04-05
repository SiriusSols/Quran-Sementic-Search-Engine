package org.qsse.client.actions;

import net.customware.gwt.dispatch.shared.Action;

import org.qsse.shared.dto.ReadQuranResult;
import org.qsse.shared.dto.SearchResult;

public class Search implements Action<SearchResult>{

	
	private int searchMode; // 1 = word search : 2 = search ayah : 3 = select ayah
	
	private String searchText;
	private int surahNumber;
	private int ayahNumber;
	
	public Search(){}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public int getSearchMode() {
		return searchMode;
	}

	public void setSearchMode(int searchMode) {
		this.searchMode = searchMode;
	}

	public int getSurahNumber() {
		return surahNumber;
	}

	public void setSurahNumber(int surahNumber) {
		this.surahNumber = surahNumber;
	}

	public int getAyahNumber() {
		return ayahNumber;
	}

	public void setAyahNumber(int ayahNumber) {
		this.ayahNumber = ayahNumber;
	}
	
	
}
