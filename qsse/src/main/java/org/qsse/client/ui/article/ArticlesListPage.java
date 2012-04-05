package org.qsse.client.ui.article;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
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
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.qsse.client.actions.CreateArticle;
import org.qsse.client.actions.DeleteArticle;
import org.qsse.client.actions.GetArticle;
import org.qsse.client.ui.Login;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.custom.ClickableImageResourceCell;
import org.qsse.shared.dto.ArticleListResult;
import org.qsse.shared.dto.ArticlesListDTO;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.type.UserType;

public class ArticlesListPage extends Composite implements Page {

	private static ArticlesListPageUiBinder uiBinder = GWT.create(ArticlesListPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;

	private CellTable<ArticlesListDTO> cellTable;
	private ListDataProvider<ArticlesListDTO> dataProvider;
	private List<AbstractEditableCell<?, ?>> editableCells;

	interface ArticlesListPageUiBinder extends UiBinder<Widget, ArticlesListPage> {
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

	public ArticlesListPage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.pageContainer = pageContainer;
		this.dispatch = dispatch;
		getArticles();
		createCellTable();
	}

	@UiField
	FlowPanel questionListPanel;

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
		dataProvider = new ListDataProvider<ArticlesListDTO>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(cellTable);

		createColumns();
		questionListPanel.add(cellTable);

		createPager();
	}

	private void createPager() {
		SimplePager pager;
		SimplePager.Resources pagerResources = GWT.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, true, 0, true);
		pager.setDisplay(cellTable);

		pager.setRangeLimited(true);
		pager.setPageSize(15);
		pager.addStyleName("pager");

		questionListPanel.add(pager);
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
		articleTitle.setSortable(true);
		createdDate.setSortable(true);

		if (Login.userType != null && Login.userType.equals(UserType.ADMIN)) {
			addColumn(new ClickableImageResourceCell(), "Publish", new GetValue<String>() {
				public String getValue(ArticlesListDTO dto) {
					if (dto.isPublish()) {
						Image image = new Image("images/unpublish.png");
						image.setStyleName("publishImage");
						image.setTitle("Unpublish this article");
						return image.toString();
					} else {
						Image image = new Image("images/publish.png");
						image.setStyleName("publishImage");
						image.setTitle("Publish this article");
						return image.toString();
					}
				}
			}, new FieldUpdater<ArticlesListDTO, String>() {
				public void update(int index, ArticlesListDTO dto, String value) {

					CreateArticle action = new CreateArticle();
					action.setArticleId(dto.getArticleId());
					action.setTitle("");
					action.setArticleTxt("");
					if (dto.isPublish()) {
						if (Window.confirm("Are you sure to unpunlish article: " + dto.getTitle())) {
							action.setPublish(false);

							dispatch.execute(action, new AsyncCallback<VoidResult>() {
								public void onFailure(Throwable throwable) {
									GWT.log("Authentication Failed at unpublishing articles");
								}

								public void onSuccess(VoidResult result) {
									Window.alert("Article successfully unpublished.");
									pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
								}
							});
						}

					} else {
						if (Window.confirm("Are you sure to publish article: " + dto.getTitle())) {
							action.setPublish(true);

							dispatch.execute(action, new AsyncCallback<VoidResult>() {
								public void onFailure(Throwable throwable) {
									GWT.log("Authentication Failed at publishing articles");
								}

								public void onSuccess(VoidResult result) {
									Window.alert("Article successfully published.");
									pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
								}
							});
						}
					}
				}
			});
			
			addColumn(new ButtonCell(), "Edit", new GetValue<String>() {
				public String getValue(ArticlesListDTO dto) {
					return "Edit";
				}
			}, new FieldUpdater<ArticlesListDTO, String>() {
				public void update(int index, ArticlesListDTO dto, String value) {
					pageContainer.add(new EditArticle(pageContainer, dispatch, dto.getArticleId()));
				}
			});
			
			addColumn(new ButtonCell(), "Delete", new GetValue<String>() {
				public String getValue(ArticlesListDTO dto) {
					return "Delete";
				}
			}, new FieldUpdater<ArticlesListDTO, String>() {
				public void update(int index, ArticlesListDTO dto, String value) {
					if(Window.confirm("Are you sure to delete this article?")){
						deleteArticle(dto.getArticleId());
					}
				}
			});

		}

		addColumn(new ButtonCell(), "View", new GetValue<String>() {
			public String getValue(ArticlesListDTO dto) {
				return "View";
			}
		}, new FieldUpdater<ArticlesListDTO, String>() {
			public void update(int index, ArticlesListDTO dto, String value) {
				pageContainer.add(new ArticlePage(pageContainer, dispatch, dto.getArticleId()));
			}
		});
		
		ListHandler<ArticlesListDTO> columnSortHandler = new ListHandler<ArticlesListDTO>(
				dataProvider.getList());
		columnSortHandler.setComparator(articleTitle,
				new Comparator<ArticlesListDTO>() {
					public int compare(ArticlesListDTO o1, ArticlesListDTO o2) {
						if (o1 == o2) {
							return 0;
						}

						// Compare the articleTitle columns.
						if (o1 != null) {
							return (o2 != null) ? o1.getTitle().compareTo(
									o2.getTitle()) : 1;
						}
						return -1;
					}
				});

		columnSortHandler.setComparator(createdDate,
				new Comparator<ArticlesListDTO>() {
					public int compare(ArticlesListDTO o1, ArticlesListDTO o2) {
						if (o1 == o2) {
							return 0;
						}

						// Compare the CreatedDate columns.
						if (o1 != null) {
							return (o2 != null) ? o1.getSubmissionDate().compareTo(
									o2.getSubmissionDate()) : 1;
						}
						return -1;
					}
				});

		cellTable.addColumnSortHandler(columnSortHandler);
		cellTable.getColumnSortList().push(articleTitle);
		cellTable.getColumnSortList().push(createdDate);

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

	public void deleteArticle(long articleId){
		
		DeleteArticle action = new DeleteArticle();
		
		action.setArticleId(articleId);
		
		dispatch.execute(action, new AsyncCallback<VoidResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at deleting article");
			}

			public void onSuccess(VoidResult result) {
				Window.alert("Article successfully deleted.");
				pageContainer.add(new ArticlesListPage(pageContainer, dispatch));
			}
		});
	}
	
}
