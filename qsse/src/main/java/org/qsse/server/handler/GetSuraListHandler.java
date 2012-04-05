package org.qsse.server.handler;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.server.ActionHandler;
import net.customware.gwt.dispatch.server.ExecutionContext;
import net.customware.gwt.dispatch.shared.DispatchException;

import org.jqurantree.orthography.Document;
import org.simpleds.EntityManager;

import com.google.inject.Inject;
import org.qsse.client.actions.GetSuraList;
import org.qsse.client.actions.ReadQuran;
import org.qsse.server.QsseUserService;
import org.qsse.shared.dto.ReadQuranResult;
import org.qsse.shared.dto.SuraListResult;

public class GetSuraListHandler  implements ActionHandler<GetSuraList, SuraListResult> {

	private final QsseUserService userService;
	private final EntityManager entityManager;
	
	@Inject
	public GetSuraListHandler(QsseUserService userService, EntityManager entityManager){
		this.userService = userService;
		this.entityManager = entityManager;
	}

	@Override
	public SuraListResult execute(GetSuraList action, ExecutionContext context) throws DispatchException {
		
		SuraListResult result = new SuraListResult();
		
		List<String> chaptersName = new ArrayList<String>();
		
		for(int i = 1; i <= 114; i++){

			chaptersName.add(Document.getChapter(i).getName().toUnicode());
	
		}
		
		result.setChaptersName(chaptersName);
		
		return result;
	}

	@Override
	public Class<GetSuraList> getActionType() {
		return GetSuraList.class;
	}

	@Override
	public void rollback(GetSuraList action, SuraListResult result, ExecutionContext conext) throws DispatchException {

	}

}
