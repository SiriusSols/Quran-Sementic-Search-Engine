package org.qsse.client.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.qsse.client.actions.CreateArticle;
import org.qsse.client.actions.GetArticle;
import org.qsse.client.ui.article.ArticlePage;
import org.qsse.client.ui.article.ArticlesListPage;
import org.qsse.client.ui.article.EditArticle;
import org.qsse.client.ui.custom.ClickableImageResourceCell;
import org.qsse.client.ui.sidebar.UserSidebarMenu;
import org.qsse.shared.dto.ArticleListResult;
import org.qsse.shared.dto.ArticlesListDTO;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.type.UserType;

public class WelcomePage extends Composite implements Page {

	private static WelcomePageUiBinder uiBinder = GWT.create(WelcomePageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private final SideBarContainer sideBarContainer;

	private CellTable<ArticlesListDTO> cellTable;
	private ListDataProvider<ArticlesListDTO> dataProvider;
	private List<AbstractEditableCell<?, ?>> editableCells;
	
	interface WelcomePageUiBinder extends UiBinder<Widget, WelcomePage> {
	}
	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
	private static interface GetValue<C> {
		C getValue(ArticlesListDTO articles);
	}
	
	public WelcomePage(PageContainer pageContainer, SideBarContainer sideBarContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.sideBarContainer = sideBarContainer;
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		addSideBar();
		getArticles();
		createCellTable();
	}
	
	@UiField
	Hyperlink readQuran;
	
	@UiField
	Hyperlink searchQuran;
	
	@UiField
	FlowPanel latestArticles;
	
	public void addSideBar(){
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.add(new UserSidebarMenu(sideBarContainer, pageContainer, dispatch));
		if(Login.userType != null && !Login.userType.equals("")){
			sideBarContainer.add(verticalPanel);
		}
	}
	
	@UiHandler("readQuran")
	void readQuran(ClickEvent event){
		pageContainer.add(new ReadQuranPage(sideBarContainer, pageContainer, dispatch));
	}
	
	@UiHandler("searchQuran")
	void searchQuran(ClickEvent event){
		pageContainer.add(new QuranSearchPage(pageContainer, dispatch));
	}
	
	public void getArticles() {

		GetArticle action = new GetArticle();
		if (Login.userType != null && Login.userType.equals(UserType.ADMIN)) {
			action.setArticleId(Long.MAX_VALUE);
		} else {
			action.setArticleId(Long.MIN_VALUE);
		}

		dispatch.execute(action, new AsyncCallback<ArticleListResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at fetching articles");
			}

			public void onSuccess(ArticleListResult result) {
				dataProvider.getList().addAll(result.getArticles());
			}
		});
	}

	private void createCellTable() {
		editableCells = new ArrayList<AbstractEditableCell<?, ?>>();

		cellTable = new CellTable<ArticlesListDTO>();
		// cellTable.setEmptyTableWidget(new HTML("No User is available."));
		// Create a data provider.
		cellTable.setWidth("100%");
		cellTable.addStyleName("cellList");
		cellTable.setPageSize(5);
		dataProvider = new ListDataProvider<ArticlesListDTO>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(cellTable);

		createColumns();
		latestArticles.add(cellTable);

	}

	private void createColumns() {

		TextColumn<ArticlesListDTO> articleTitle = new TextColumn<ArticlesListDTO>() {
			@Override
			public String getValue(ArticlesListDTO dto) {
				return String.valueOf(dto.getTitle());
			}
		};

		TextColumn<ArticlesListDTO> createdDate = new TextColumn<ArticlesListDTO>() {
			@Override
			public String getValue(ArticlesListDTO dto) {
				return String.valueOf(dto.getSubmissionDate());
			}
		};

		cellTable.addColumn(articleTitle, "Article Title");
		cellTable.addColumn(createdDate, "Created Date");

		addColumn(new ButtonCell(), "View", new GetValue<String>() {
			public String getValue(ArticlesListDTO dto) {
				return "View";
			}
		}, new FieldUpdater<ArticlesListDTO, String>() {
			public void update(int index, ArticlesListDTO dto, String value) {
				pageContainer.add(new ArticlePage(pageContainer, dispatch, dto.getArticleId()));
			}
		});
		
	}

	/**
	 * Add a column with a header.
	 * 
	 * @param <C>
	 *            the cell type
	 * @param cell
	 *            the cell used to render the column
	 * @param headerText
	 *            the header string
	 * @param getter
	 *            the value getter for the cell
	 */
	private <C> Column<ArticlesListDTO, C> addColumn(Cell<C> cell, String headerText, final GetValue<C> getter,
			FieldUpdater<ArticlesListDTO, C> fieldUpdater) {
		Column<ArticlesListDTO, C> column = new Column<ArticlesListDTO, C>(cell) {
			@Override
			public C getValue(ArticlesListDTO object) {
				return getter.getValue(object);
			}
		};
		column.setFieldUpdater(fieldUpdater);
		if (cell instanceof AbstractEditableCell<?, ?>) {
			editableCells.add((AbstractEditableCell<?, ?>) cell);
		}
		cellTable.addColumn(column, headerText);
		return column;
	}

}
