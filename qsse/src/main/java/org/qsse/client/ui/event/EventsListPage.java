package org.qsse.client.ui.event;

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
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.qsse.client.actions.CreateEvent;
import org.qsse.client.actions.DeleteEvent;
import org.qsse.client.actions.GetEvent;
import org.qsse.client.ui.Login;
import org.qsse.client.ui.Page;
import org.qsse.client.ui.PageContainer;
import org.qsse.client.ui.custom.ClickableImageResourceCell;
import org.qsse.client.ui.custom.HtmlCell;
import org.qsse.shared.dto.EventListResult;
import org.qsse.shared.dto.EventsListDTO;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.type.UserType;

public class EventsListPage extends Composite implements Page {

	private static EventsListPageUiBinder uiBinder = GWT.create(EventsListPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;

	private CellTable<EventsListDTO> cellTable;
	private ListDataProvider<EventsListDTO> dataProvider;
	private List<AbstractEditableCell<?, ?>> editableCells;

	interface EventsListPageUiBinder extends UiBinder<Widget, EventsListPage> {
	}

	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
	private static interface GetValue<C> {
		C getValue(EventsListDTO events);
	}

	public EventsListPage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;
		getEvents();
		createCellTable();
	}

	@UiField
	FlowPanel eventListPanel;

	public void getEvents() {

		GetEvent action = new GetEvent();
		if (Login.userType != null && Login.userType.equals(UserType.ADMIN)) {
			action.setEventId(Long.MAX_VALUE);
		} else {
			action.setEventId(Long.MIN_VALUE);
		}

		dispatch.execute(action, new AsyncCallback<EventListResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at fetching events");
			}

			public void onSuccess(EventListResult result) {
				dataProvider.getList().addAll(result.getEvents());
			}
		});
	}

	public SafeHtml eventCell(EventsListDTO dto) {

		SafeHtmlBuilder html = new SafeHtmlBuilder();

		html
				.appendHtmlConstant("<table class=\"eventTable\" border=\"0\" cellpadding=\"2\" cellspacing=\"2\" ><tbody><tr><td rowspan=\"2\" align=\"center\" bgcolor=\"#FFFFFF\">");
		html.appendHtmlConstant("<font class=\"cat_date\">");
		html.appendEscaped(String.valueOf(dto.getStartDate()));
		html.appendHtmlConstant("<br><br></font>");
		html.appendHtmlConstant("<font class=\"small_text\">");
		html.appendEscaped(dto.getTiming());
		html.appendHtmlConstant("</font></td><td><p class=\"labelBig\">");
		html.appendEscaped(dto.getTitle());

		html.appendHtmlConstant("</p></td></tr><tr><td>");
		html.appendHtmlConstant("<p>");
		html.appendEscaped("Location: " + dto.getLocation());
		html.appendHtmlConstant("<p></p>");
		html.appendEscaped("From: " + String.valueOf(dto.getStartDate()) + "To: " + String.valueOf(dto.getEndDate()));
		html.appendHtmlConstant("<p></p>");
		html.appendEscaped(dto.getDetail());
		html.appendHtmlConstant("</p>");
		html.appendHtmlConstant("</td></tr></tbody></table>");

		return html.toSafeHtml();
	}

	private void createCellTable() {
		editableCells = new ArrayList<AbstractEditableCell<?, ?>>();

		cellTable = new CellTable<EventsListDTO>();
		cellTable.setEmptyTableWidget(new HTML("No event is available."));
		// Create a data provider.
		cellTable.setWidth("100%");
		cellTable.addStyleName("cellList");
		dataProvider = new ListDataProvider<EventsListDTO>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(cellTable);

		createColumns();
		eventListPanel.add(cellTable);

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

		eventListPanel.add(pager);
	}

	private void createColumns() {

		addColumn(new HtmlCell(), "Events", new GetValue<String>() {
			public String getValue(EventsListDTO dto) {
				return eventCell(dto).asString();
			}
		}, new FieldUpdater<EventsListDTO, String>() {
			public void update(int index, EventsListDTO dto, String value) {

			}
		});

		if (Login.userType != null && Login.userType.equals(UserType.ADMIN)) {
			addColumn(new ClickableImageResourceCell(), "Publish", new GetValue<String>() {
				public String getValue(EventsListDTO dto) {
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
			}, new FieldUpdater<EventsListDTO, String>() {
				public void update(int index, EventsListDTO dto, String value) {

					CreateEvent action = new CreateEvent();
					action.setTask(Long.MIN_VALUE);
					action.setEvendId(dto.getEventId());
					if (dto.isPublish()) {
						if (Window.confirm("Are you sure to unpunlish event: " + dto.getTitle())) {
							action.setPublish(false);

							dispatch.execute(action, new AsyncCallback<VoidResult>() {
								public void onFailure(Throwable throwable) {
									GWT.log("Authentication Failed at unpublishing event");
								}

								public void onSuccess(VoidResult result) {
									Window.alert("Event successfully unpublished.");
									pageContainer.add(new EventsListPage(pageContainer, dispatch));
								}
							});
						}

					} else {
						if (Window.confirm("Are you sure to publish event: " + dto.getTitle())) {
							action.setPublish(true);

							dispatch.execute(action, new AsyncCallback<VoidResult>() {
								public void onFailure(Throwable throwable) {
									GWT.log("Authentication Failed at publishing event");
								}

								public void onSuccess(VoidResult result) {
									Window.alert("Event successfully published.");
									pageContainer.add(new EventsListPage(pageContainer, dispatch));
								}
							});
						}
					}
				}
			});

			addColumn(new ButtonCell(), "Delete", new GetValue<String>() {
				public String getValue(EventsListDTO dto) {
					return "Delete";
				}
			}, new FieldUpdater<EventsListDTO, String>() {
				public void update(int index, EventsListDTO dto, String value) {
					if(Window.confirm("Are you sure to delete this event?")){
						  deleteEvent(dto.getEventId()); 
						  }
				}
			});


		}

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
	private <C> Column<EventsListDTO, C> addColumn(Cell<C> cell, String headerText, final GetValue<C> getter,
			FieldUpdater<EventsListDTO, C> fieldUpdater) {
		Column<EventsListDTO, C> column = new Column<EventsListDTO, C>(cell) {
			@Override
			public C getValue(EventsListDTO object) {
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
	
	public void deleteEvent(long eventId){
		
		DeleteEvent action = new DeleteEvent();
		
		action.setEventId(eventId);
		
		dispatch.execute(action, new AsyncCallback<VoidResult>() {
			public void onFailure(Throwable throwable) {
				GWT.log("Authentication Failed at deleting event");
			}

			public void onSuccess(VoidResult result) {
				Window.alert("Event successfully deleted.");
				pageContainer.add(new EventsListPage(pageContainer, dispatch));
			}
		});
	}
}
