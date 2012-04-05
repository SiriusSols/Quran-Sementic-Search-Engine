package org.qsse.server.handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jqurantree.analysis.AnalysisTable;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.core.resource.ResourceReader;
import org.jqurantree.orthography.Chapter;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Verse;
import org.jqurantree.search.SearchOptions;
import org.jqurantree.search.TokenSearch;
import org.jqurantree.tanzil.TanzilReader;
import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.ReadQuran;
import org.qsse.client.actions.TranslateAhmedRaza;
import org.qsse.server.QsseUserService;
import org.qsse.shared.dto.ReadQuranResult;
import org.qsse.shared.dto.TranslateAhmedRazaResult;

public class TranslateAhmedRazaHandler implements ActionHandler<TranslateAhmedRaza, TranslateAhmedRazaResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager;
	private final String TANZIL_RESOURCE_PATH = "/tanzil/ur.maududi.xml";
	
	
	int page = 0;
	int start = 0;
	int end = 0;
	
	@Inject
	public TranslateAhmedRazaHandler(QsseUserService userService, EntityManager entityManager){
		this.userService = userService;
		this.entityManager = entityManager;
	}

	@Override
	public TranslateAhmedRazaResult execute(TranslateAhmedRaza action, ExecutionContext context) throws DispatchException {
		
		TranslateAhmedRazaResult result = new TranslateAhmedRazaResult();
		
		TanzilReader reader = new TanzilReader();
		
		//ResourceReader rr = new ResourceReader(TANZIL_RESOURCE_PATH);
	
		//rr.readLine();
		
		
		Chapter[] chapter = reader.readXml(TANZIL_RESOURCE_PATH);
	
		
		result.setSuraName(chapter[action.getChapterNumber()].getName().toString());
		result.setQuranText(chapter[action.getChapterNumber()].getVerse(1).toBuckwalter());
		result.setPage(1);
		result.setNumberOfPages(1);
		
		System.out.println(reader);
		// Display the results.
		//AnalysisTable table = search.getResults();
		//System.out.println(table);
		//System.out.println("Matches: " + table.getRowCount());
		
		return result;
	}

	@Override
	public Class<TranslateAhmedRaza> getActionType() {
		return TranslateAhmedRaza.class;
	}

	@Override
	public void rollback(TranslateAhmedRaza action, TranslateAhmedRazaResult result, ExecutionContext conext) throws DispatchException {

	}

}

