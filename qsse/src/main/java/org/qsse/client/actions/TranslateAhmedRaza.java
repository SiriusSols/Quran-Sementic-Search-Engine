package org.qsse.client.actions;

import net.customware.gwt.dispatch.shared.Action;

import org.qsse.shared.dto.TranslateAhmedRazaResult;

public class TranslateAhmedRaza implements Action<TranslateAhmedRazaResult> {
	
	private int chapterNumber;
	private int start;
	private int end;
	
	public TranslateAhmedRaza(){
		
	}

	public int getChapterNumber() {
		return chapterNumber;
	}

	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	
}
