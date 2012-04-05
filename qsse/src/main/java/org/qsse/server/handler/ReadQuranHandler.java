package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;


import org.jqurantree.orthography.Document;

import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.ReadQuran;
import org.qsse.server.QsseUserService;
import org.qsse.shared.dto.ReadQuranResult;

public class ReadQuranHandler implements ActionHandler<ReadQuran, ReadQuranResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager;
	int page = 0;
	int start = 0;
	int end = 0;
	
	@Inject
	public ReadQuranHandler(QsseUserService userService, EntityManager entityManager){
		this.userService = userService;
		this.entityManager = entityManager;
	}

	@Override
	public ReadQuranResult execute(ReadQuran action, ExecutionContext context) throws DispatchException {
		
		ReadQuranResult result = new ReadQuranResult();
		
		Integer chapterNumber = new Integer(action.getChapterNumber());
		
		Integer totalVerses = new Integer(Document.getChapter(action.getChapterNumber()).getVerseCount());
		
		StringBuilder builder = new StringBuilder();
		
		Integer numberOfPages = new Integer((totalVerses/10)+1);
		
		start = action.getStart();
		end = action.getEnd();
		
		if(end > totalVerses){
			end = totalVerses;
		}
		
		for(int i = start; i <= end; i++){
				
				builder.append("<span class=\"aya-wrapper\"><span class=\"ayaText\">"
						+ Document.getChapter(chapterNumber).getVerse(i).toUnicode()
						+ "</span>"
						+ "<span class=\"ayaNumber\"> ("
						+ Document.getChapter(chapterNumber).getVerse(i).getVerseNumber()
						+ ") </span></span>"
				);
		}
		
		page = (start/10)+1;
		result.setPage(page);
		
		result.setNumberOfPages(numberOfPages);
		
		result.setQuranText(builder.toString());
		
		result.setSuraName(Document.getChapter(chapterNumber).getName().toUnicode());
		
		return result;
	}

	@Override
	public Class<ReadQuran> getActionType() {
		return ReadQuran.class;
	}

	@Override
	public void rollback(ReadQuran action, ReadQuranResult result, ExecutionContext conext) throws DispatchException {

	}

}
