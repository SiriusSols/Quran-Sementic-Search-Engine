package org.qsse.client.ui.discussion;

import java.util.ArrayList;
import java.util.List;


import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.qsse.client.actions.GetQuestions;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.custom.ClickableImageResourceCell;
import org.qsse.client.ui.custom.HtmlCell;
import org.qsse.client.ui.user.ProfileDetailPage;
import org.qsse.shared.dto.QuestionListDTO;
import org.qsse.shared.dto.QuestionListResult;


public class QuestionsListPage extends Composite implements Page {

	private static QuestionsListPageUiBinder uiBinder = GWT
			.create(QuestionsListPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;

	private CellTable<QuestionListDTO> cellTable;
	private ListDataProvider<QuestionListDTO> dataProvider;
	private List<AbstractEditableCell<?, ?>> editableCells;

	@UiField
	FlowPanel questionListPanel;

	interface QuestionsListPageUiBinder extends
			UiBinder<Widget, QuestionsListPage> {
	}

	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
	private static interface GetValue<C> {
		C getValue(QuestionListDTO questions);
	}

	public QuestionsListPage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		createCellTable();
		createQuestion();
	}

	public GetQuestions getQuestionsAction() {

		GetQuestions action = new GetQuestions();

		return action;

	}

	private void createQuestion() {
		dispatch.execute(getQuestionsAction(),
				new AsyncCallback<QuestionListResult>() {
					public void onFailure(Throwable throwable) {
						GWT.log("Authentication Failed at fetching question");
					}

					public void onSuccess(QuestionListResult result) {
						//Window.alert("Questions fetched successfully.");
						dataProvider.getList().addAll(result.getQuestions());
					}
				});
	}

	private void createCellTable() {
		editableCells = new ArrayList<AbstractEditableCell<?, ?>>();

		cellTable = new CellTable<QuestionListDTO>();
		// cellTable.setEmptyTableWidget(new HTML("No User is available."));
		// Create a data provider.
		cellTable.setWidth("100%");
		cellTable.addStyleName("cellList");
		dataProvider = new ListDataProvider<QuestionListDTO>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(cellTable);

		createColumns();
		questionListPanel.add(cellTable);

		createPager();
	}

	private void createPager() {
		SimplePager pager;
		SimplePager.Resources pagerResources = GWT
				.create(SimplePager.Resources.class);
		pager = new SimplePager(TextLocation.CENTER, pagerResources, true, 0,
				true);
		pager.setDisplay(cellTable);

		pager.setRangeLimited(true);
		pager.setPageSize(15);
		pager.addStyleName("pager");

		questionListPanel.add(pager);
	}

	private void createColumns() {

		TextColumn<QuestionListDTO> idColumns = new TextColumn<QuestionListDTO>() {
			@Override
			public String getValue(QuestionListDTO dto) {
				return String.valueOf(dto.getQuestionID());
			}
		};

		cellTable.addColumn(idColumns, "ID");

		// Edit/delete Button.
		addColumn(new ClickableImageResourceCell(), "User",
				new GetValue<String>() {
					public String getValue(QuestionListDTO dto) {
						if (!dto.getUserDp().equals("")) {
							Image image = new Image(dto.getUserDp());
							return image.toString();
						} else {
							Image image = new Image("images/noProfileImage.png");
							return image.toString();
						}
					}
				}, new FieldUpdater<QuestionListDTO, String>() {
					public void update(int index, QuestionListDTO dto,
							String value) {
						pageContainer.add(new ProfileDetailPage(pageContainer,
								dispatch, dto.getUserEmail()));
					}
				});


		addColumn(new HtmlCell(), "Questions", new GetValue<String>() {
			public String getValue(QuestionListDTO dto) {
				return questionCell(dto).asString();
			}
		}, new FieldUpdater<QuestionListDTO, String>() {
			public void update(int index, QuestionListDTO dto, String value) {

			}
		});

		addColumn(new ButtonCell(), "View", new GetValue<String>() {
			public String getValue(QuestionListDTO dto) {
				return "View";
			}
		}, new FieldUpdater<QuestionListDTO, String>() {
			public void update(int index, QuestionListDTO dto, String value) {
				pageContainer.add(new AnswerPage(pageContainer, dispatch, dto.getQuestionID()));
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
	private <C> Column<QuestionListDTO, C> addColumn(Cell<C> cell,
			String headerText, final GetValue<C> getter,
			FieldUpdater<QuestionListDTO, C> fieldUpdater) {
		Column<QuestionListDTO, C> column = new Column<QuestionListDTO, C>(cell) {
			@Override
			public C getValue(QuestionListDTO object) {
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

	public SafeHtml questionCell(QuestionListDTO dto) {

		SafeHtmlBuilder html = new SafeHtmlBuilder();

		html.appendHtmlConstant("<div class=\"questionBold\">");
		html.appendHtmlConstant(dto.getQuestion());
		html.appendHtmlConstant("</div>");
		html.appendHtmlConstant("<div class=\"questionDetail\">");
		html.appendEscaped("Asked by: " + dto.getUserFirstName());
		html.appendHtmlConstant("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		html.appendEscaped(dto.getTotalAnswers() + " Answers");
		html.appendHtmlConstant("</div>");

		return html.toSafeHtml();
	}

}
