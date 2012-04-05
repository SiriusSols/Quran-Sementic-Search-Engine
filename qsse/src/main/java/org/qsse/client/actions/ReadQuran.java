package org.qsse.client.actions;

import org.qsse.shared.dto.ReadQuranResult;

import net.customware.gwt.dispatch.shared.Action;

public class ReadQuran implements Action<ReadQuranResult> {
	
	private int chapterNumber;
	private int start;
	private int end;
	
	public ReadQuran(){
		
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
