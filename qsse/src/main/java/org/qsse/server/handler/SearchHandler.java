package org.qsse.server.handler;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jqurantree.analysis.AnalysisTable;
import org.jqurantree.arabic.encoding.EncodingType;
import org.jqurantree.orthography.Document;
import org.jqurantree.orthography.Token;
import org.jqurantree.search.SearchOptions;
import org.jqurantree.search.TokenSearch;
import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.ReadQuran;
import org.qsse.client.actions.Search;
import org.qsse.server.QsseUserService;
import org.qsse.shared.dto.ReadQuranResult;
import org.qsse.shared.dto.SearchResult;

public class SearchHandler implements ActionHandler<Search, SearchResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager;
	int start = 0;
	int end = 0;
	int totalVerses = 0;
	StringBuilder builder;

	@Inject
	public SearchHandler(QsseUserService userService, EntityManager entityManager) {
		this.userService = userService;
		this.entityManager = entityManager;
	}

	@Override
	public SearchResult execute(Search action, ExecutionContext context) throws DispatchException {

		SearchResult result = new SearchResult();

		builder = new StringBuilder();
		if (action.getSearchMode() == 1) {
			// Create a new search.
			TokenSearch searchLetter = new TokenSearch(EncodingType.Buckwalter, SearchOptions.RemoveDiacritics);

			// Search for "Ajr" (reward), "wAjr" (and reward) and "wlAjr" (and
			// the reward).
			searchLetter.findSubstring(action.getSearchText());
			searchLetter.findToken(action.getSearchText());

			// Display the results.
			AnalysisTable table = searchLetter.getResults();

			end = table.getRowCount();
			System.out.println(table);
			System.out.println("Matches: " + table.getRowCount());

			if (end == 0) {
				TokenSearch searchToken = new TokenSearch(EncodingType.Buckwalter);
				searchToken.findSubstring(action.getSearchText());
				searchToken.findToken(action.getSearchText());

				table = searchToken.getResults();
				end = table.getRowCount();

				System.out.println(table);
				System.out.println("Matches: " + table.getRowCount() + "\r\n");
			}

			builder.append("<ul class=\"searchList\">");

			for (int i = start; i < end; i++) {

				Integer chapterNumber = new Integer(table.getInteger(i, "ChapterNumber"));
				Integer verseNumber = new Integer(table.getInteger(i, "VerseNumber"));
				Integer tokenNumber = new Integer(table.getInteger(i, "TokenNumber"));
				Iterable<Token> tokens = Document.getChapter(chapterNumber).getVerse(verseNumber).getTokens();
				String surahName = Document.getChapter(chapterNumber).getName().toString();
				builder.append("<li><span class=\"aya-wrapper\"><span class=\"ayaText\">");

				for (Token token : tokens) {
					if (token.getTokenNumber() == tokenNumber) {
						builder.append("<span class=\"tokenFocus\">" + token.toString() + "</span>");
					} else {
						builder.append(token.toString());
						builder.append(" ");
					}
				}

				builder.append("</span>" + "<span class=\"ayaNumber\"> (" + surahName + " : " + verseNumber
						+ ") </span></span></li>");
			}

			builder.append("</ul>");

			result.setQuranText(builder.toString());
			result.setTotalResult(end);
			return result;

		} else if (action.getSearchMode() == 2) {

			totalVerses = Document.getChapter(action.getSurahNumber()).getVerseCount();

			result.setTotalVerses(totalVerses);
			return result;
		}else if(action.getSearchMode() == 3){
			
			String surahName = Document.getChapter(action.getSurahNumber()).getName().toString();
			
			builder.append("<span class=\"aya-wrapper\"><span class=\"ayaText\">");


			builder.append("<span class=\"tokenFocus\">" + Document.getVerse(action.getSurahNumber(), action.getAyahNumber()).toUnicode() + "</span>");
		

			builder.append("</span>" + "<span class=\"ayaNumber\"> (" + surahName + " : " + action.getAyahNumber()
					+ ") </span></span>");
			
			result.setQuranText(builder.toString());
			result.setTotalResult(1);
			return result;
		}
		else{
			return result;
		}
	}

	@Override
	public Class<Search> getActionType() {
		return Search.class;
	}

	@Override
	public void rollback(Search action, SearchResult result, ExecutionContext conext) throws DispatchException {

	}

}
