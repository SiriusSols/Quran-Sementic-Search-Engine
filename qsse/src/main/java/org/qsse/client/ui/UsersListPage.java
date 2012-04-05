package org.qsse.client.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import net.customware.gwt.dispatch.client.DispatchAsync;

import com.google.gwt.cell.client.AbstractEditableCell;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.ImageCell;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import org.qsse.client.actions.DeleteUser;
import org.qsse.client.actions.GetUsers;
import org.qsse.client.ui.user.ProfileDetailPage;
import org.qsse.shared.dto.UsersListDTO;
import org.qsse.shared.dto.UsersListResult;
import org.qsse.shared.dto.VoidResult;
import org.qsse.shared.type.UserType;

public class UsersListPage extends Composite implements Page {

	private static UsersListPageUiBinder uiBinder = GWT
			.create(UsersListPageUiBinder.class);

	private final DispatchAsync dispatch;
	private final PageContainer pageContainer;

	private CellTable<UsersListDTO> cellTable;
	private ListDataProvider<UsersListDTO> dataProvider;
	private List<AbstractEditableCell<?, ?>> editableCells;

	@UiField
	FlowPanel usersListPanel;
	
	interface UsersListPageUiBinder extends UiBinder<Widget, UsersListPage> {
	}

	/**
	 * Get a cell value from a record.
	 * 
	 * @param <C>
	 *            the cell type
	 */
	private static interface GetValue<C> {
		C getValue(UsersListDTO portlet);
	}

	public UsersListPage(PageContainer pageContainer, DispatchAsync dispatch) {
		initWidget(uiBinder.createAndBindUi(this));
		this.dispatch = dispatch;
		this.pageContainer = pageContainer;

		createCellTable();

		getUsers();
	}

	private void createCellTable() {
		editableCells = new ArrayList<AbstractEditableCell<?, ?>>();

		cellTable = new CellTable<UsersListDTO>();
		// cellTable.setEmptyTableWidget(new HTML("No User is available."));
		// Create a data provider.
		cellTable.setWidth("100%");
		cellTable.addStyleName("cellList");
		dataProvider = new ListDataProvider<UsersListDTO>();

		// Connect the table to the data provider.
		dataProvider.addDataDisplay(cellTable);

		createColumns();
		usersListPanel.add(cellTable);

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
		
		usersListPanel.add(pager);
	}

	private void createColumns() {

		TextColumn<UsersListDTO> idColumns = new TextColumn<UsersListDTO>() {
			@Override
			public String getValue(UsersListDTO dto) {
				return String.valueOf(dto.getUserID());
			}
		};

		TextColumn<UsersListDTO> firstName = new TextColumn<UsersListDTO>() {
			@Override
			public String getValue(UsersListDTO dto) {
				return dto.getFirstName();
			}
		};

		// Create Title column.
		TextColumn<UsersListDTO> email = new TextColumn<UsersListDTO>() {
			@Override
			public String getValue(UsersListDTO dto) {
				return dto.getEmail();
			}
		};

		// Create Title column.
		TextColumn<UsersListDTO> userType = new TextColumn<UsersListDTO>() {
			@Override
			public String getValue(UsersListDTO dto) {
				return dto.getUserType().toString();
			}
		};

		// Make the name column sortable.
		firstName.setSortable(true);
		userType.setSortable(true);

		// Add the columns.
		cellTable.addColumn(idColumns, "ID");
		
		addColumn(new ImageCell(), "Profile Image", new GetValue<String>() {
			public String getValue(UsersListDTO dto) {
				if(!dto.getUserDp().equals("")){
					return dto.getUserDp();
				}else{
					return "images/noProfileImage.png";
				}
			}
		}, new FieldUpdater<UsersListDTO, String>() {
			public void update(int index, UsersListDTO dto, String value) {
				// TODO callMethod of edit
			}
		});
		
		cellTable.addColumn(firstName, "First Name");
		cellTable.addColumn(email, "Email");
		cellTable.addColumn(userType, "User Type");

		ListHandler<UsersListDTO> columnSortHandler = new ListHandler<UsersListDTO>(
				dataProvider.getList());
		columnSortHandler.setComparator(firstName,
				new Comparator<UsersListDTO>() {
					public int compare(UsersListDTO o1, UsersListDTO o2) {
						if (o1 == o2) {
							return 0;
						}

						// Compare the name columns.
						if (o1 != null) {
							return (o2 != null) ? o1.getFirstName().compareTo(
									o2.getFirstName()) : 1;
						}
						return -1;
					}
				});

		columnSortHandler.setComparator(userType,
				new Comparator<UsersListDTO>() {
					public int compare(UsersListDTO o1, UsersListDTO o2) {
						if (o1 == o2) {
							return 0;
						}

						// Compare the name columns.
						if (o1 != null) {
							return (o2 != null) ? o1.getUserType().compareTo(
									o2.getUserType()) : 1;
						}
						return -1;
					}
				});
		
		cellTable.addColumnSortHandler(columnSortHandler);
		cellTable.getColumnSortList().push(firstName);
		cellTable.getColumnSortList().push(userType);
		
		// Edit/delete Button.

		if (Login.userType != null && !Login.userType.equals(UserType.USER)) {
			addColumn(new ButtonCell(), "Delete", new GetValue<String>() {
				public String getValue(UsersListDTO contact) {
					return "Delete ";
				}
			}, new FieldUpdater<UsersListDTO, String>() {
				public void update(int index, UsersListDTO dto, String value) {
					if (Window.confirm("Are you sure to delete this user?")) {
						deleteUser(dto.getUserID());
						dataProvider.getList().remove(dto);
					}
				}
			});
		}
		addColumn(new ButtonCell(), "Detail", new GetValue<String>() {
			public String getValue(UsersListDTO contact) {
				return "Detail ";

			}
		}, new FieldUpdater<UsersListDTO, String>() {
			public void update(int index, UsersListDTO dto, String value) {

				pageContainer.add(new ProfileDetailPage(pageContainer,
						dispatch, dto.getEmail()));
				// TODO callMethod of edit
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
	private <C> Column<UsersListDTO, C> addColumn(Cell<C> cell,
			String headerText, final GetValue<C> getter,
			FieldUpdater<UsersListDTO, C> fieldUpdater) {
		Column<UsersListDTO, C> column = new Column<UsersListDTO, C>(cell) {
			@Override
			public C getValue(UsersListDTO object) {
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

	/*
	 * Function to get all users in DB
	 */

	public GetUsers getUsersAction() {

		GetUsers action = new GetUsers();

		return action;

	}

	public void getUsers() {

		dispatch.execute(getUsersAction(),
				new AsyncCallback<UsersListResult>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Authentication Failed...");
					}

					@Override
					public void onSuccess(UsersListResult result) {
						dataProvider.getList().addAll(result.getUsers());

					}
				});

	}

	/*
	 * Function to delete user
	 */

	public DeleteUser deleteUserAction(long userId) {

		DeleteUser action = new DeleteUser();

		action.setUserID(userId);

		return action;
	}

	public void deleteUser(long userId) {

		dispatch.execute(deleteUserAction(userId),
				new AsyncCallback<VoidResult>() {

					@Override
					public void onFailure(Throwable caught) {
						GWT.log("Authentication Failed at deleting users...");
					}

					@Override
					public void onSuccess(VoidResult result) {

					}
				});

	}

}
