package org.qsse.client.ui.discussion;

import java.util.ArrayList;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.axeiya.gwtckeditor.client.CKConfig;
import com.axeiya.gwtckeditor.client.CKEditor;
import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.qsse.client.actions.CreateAnswer;
import org.qsse.client.actions.EditQuestion;
import org.qsse.client.actions.GetAnswers;
import org.qsse.client.ui.Login;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.custom.ClickableImageResourceCell;
import org.qsse.client.ui.custom.HtmlCell;
import org.qsse.client.ui.user.ProfileDetailPage;
import org.qsse.shared.dto.AnswerDTO;
import org.qsse.shared.dto.AnswerListResult;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.type.UserType;

public class AnswerPage extends Composite implements Page {

	private static AnswerPageUiBinder uiBinder = GWT.create(AnswerPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;
	private final long questionId;
	private boolean questionStatus;
	CKEditor answerEditor = new CKEditor(CKConfig.full);

	private CellTable<AnswerDTO> cellTable;
	private ListDataProvider<AnswerDTO> dataProvider;
	private List<AbstractEditableCell<?, ?>> editableCells;

	interface AnswerPageUiBinder extends UiBinder<Widget, AnswerPage> {
	}

	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
	private static interface GetValue<C> {
		C getValue(AnswerDTO answers);
	}

	public AnswerPage(PageContainer pageContainer, DispatchAsync dispatch, long questionId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		this.questionId = questionId;
		
		postAnswerPanel.clear();
		
		createCellTable();
		loadAnswers(questionId);

	}

	@UiField
	Button postAnswer;

	@UiField
	DivElement question;

	@UiField
	Label validationMessage;

	@UiField
	VerticalPanel postAnswerPanel;

	@UiField
	Label postAnserHeading;
	
	@UiField
	Image flagQuestion;

	@UiField
	FlowPanel answersListPanel;

	private void loadAnswers(long questionId) {
		dispatch.execute(new GetAnswers(questionId), new AsyncCallback<AnswerListResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at fetching Answer");
			}

			public void onSuccess(AnswerListResult result) {
				// Window.alert("Answer fetched successfully.");
				question.setInnerHTML(result.getQuestionDTO().getQuestion());
				questionStatus = result.getQuestionDTO().getStatus();
				dataProvider.getList().addAll(result.getAnswers());

				postAnswer(questionStatus);
			}
		});
	}

	@SuppressWarnings("deprecation")
	public void postAnswer(boolean questionStatus) {
		if (questionStatus) {
			flagQuestion.setUrl("images/flag-off.png");

			if (Login.userType == null) {
				//postAnswerPanel.removeFromParent();
				validationMessage.setText("Please login to answer this question.");
			} else {
				answerEditor.setWidth("650px");
				postAnswerPanel.add(postAnserHeading);
				postAnswerPanel.add(answerEditor);
				postAnswerPanel.add(postAnswer);
			}
		} else {
			flagQuestion.setUrl("images/flag-on.png");
			//postAnswerPanel.removeFromParent();
			validationMessage.setText("This question is flagged and locked.");
		}

	}

	public CreateAnswer createAnswerAction() {

		CreateAnswer action = new CreateAnswer(questionId);

		action.setAnswser(answerEditor.getHTML());

		return action;
	}

	private void createAnswer() {
		dispatch.execute(createAnswerAction(), new AsyncCallback<VoidResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at creating Answer");
			}

			public void onSuccess(VoidResult result) {
				answerEditor.setData("");
				Window.alert("Answer saved successfully.");
				pageContainer.add(new AnswerPage(pageContainer, dispatch, questionId));
			}
		});
	}

	public boolean validateForm() {
		String data = answerEditor.getData();
		if (data.equals("")) {
			validationMessage.setText("All fields marked with * are required.");
			return false;
		}
		return true;
	}

	@UiHandler("postAnswer")
	public void onPostQuestion(ClickEvent e) {
		if (validateForm()) {
			createAnswer();
		}
	}

	private void createCellTable() {
		editableCells = new ArrayList<AbstractEditableCell<?, ?>>();

		cellTable = new CellTable<AnswerDTO>();
		cellTable.setEmptyTableWidget(new HTML("No Answer is available."));
		// Create a data provider.
		cellTable.setWidth("100%");
		cellTable.addStyleName("cellList");
		dataProvider = new ListDataProvider<AnswerDTO>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(cellTable);

		createColumns();
		answersListPanel.add(cellTable);

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

		answersListPanel.add(pager);
	}

	private void createColumns() {

		// Edit/delete Button.
		addColumn(new ClickableImageResourceCell(), "User", new GetValue<String>() {
			public String getValue(AnswerDTO dto) {
				if (!dto.getUserDp().equals("")) {
					Image image = new Image(dto.getUserDp());
					return image.toString();
				} else {
					Image image = new Image("images/noProfileImage.png");
					return image.toString();
				}
			}
		}, new FieldUpdater<AnswerDTO, String>() {
			public void update(int index, AnswerDTO dto, String value) {
				pageContainer.add(new ProfileDetailPage(pageContainer, dispatch, dto.getUserEmail()));
			}
		});

		// Edit/delete Button.
		addColumn(new HtmlCell(), "Answers", new GetValue<String>() {
			public String getValue(AnswerDTO dto) {
				return answerCell(dto).asString();
			}
		}, new FieldUpdater<AnswerDTO, String>() {
			public void update(int index, AnswerDTO dto, String value) {

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
	private <C> Column<AnswerDTO, C> addColumn(Cell<C> cell, String headerText, final GetValue<C> getter,
			FieldUpdater<AnswerDTO, C> fieldUpdater) {
		Column<AnswerDTO, C> column = new Column<AnswerDTO, C>(cell) {
			@Override
			public C getValue(AnswerDTO object) {
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

	public SafeHtml answerCell(AnswerDTO dto) {

		SafeHtmlBuilder html = new SafeHtmlBuilder();

		html.appendHtmlConstant("<div class=\"\">");
		html.appendHtmlConstant(dto.getAnswer());
		html.appendHtmlConstant("</div>");
		html.appendHtmlConstant("<div class=\"questionDetail\">");
		html.appendEscaped("Answered by: " + dto.getUserFirstName());
		html.appendHtmlConstant("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		html.appendEscaped("User Type: " + dto.getUserType());
		html.appendHtmlConstant("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		html.appendEscaped("Date: " + dto.getSubmissionDate());
		html.appendHtmlConstant("</div>");

		return html.toSafeHtml();
	}

	public EditQuestion questionFlagAction() {

		EditQuestion action = new EditQuestion();

		action.setQuestionId(questionId);
		if (questionStatus) {
			action.setStatus(false);
			flagQuestion.setUrl("images/flag-on.png");
		} else {
			action.setStatus(true);
			flagQuestion.setUrl("images/flag-off.png");
		}

		return action;

	}

	private void editQuestion() {
		dispatch.execute(questionFlagAction(), new AsyncCallback<VoidResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at edit question");
			}

			public void onSuccess(VoidResult result) {
				pageContainer.add(new AnswerPage(pageContainer, dispatch, questionId));

				if (questionStatus) {
					Window.alert("Question flagged successfully.");
				} else {
					Window.alert("Question unflagged successfully.");
				}

			}
		});
	}

	@UiHandler("flagQuestion")
	public void flagQuestion(ClickEvent event){
		if(Login.userType != null && Login.userType != UserType.USER){
			if (questionStatus && Window.confirm("Are you sure to flagge this question.")) {
				editQuestion();
			} 
			if(!questionStatus && Window.confirm("Are you sure to unflagge this question.")) {
				editQuestion();
			}
		}
	}
}
