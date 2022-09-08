package agenda.gui.viewfx.impl;

import java.util.List;

import agenda.controller.DetailController;
import agenda.gui.view.DetailDialog;
import agenda.gui.view.DialogResult;
import agenda.model.Detail;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class FxDetailDialog extends Dialog<DialogResult> implements DetailDialog {

	private DetailController controller;
	private TableView<Detail> detailTable;
	
	public FxDetailDialog() {
		BorderPane mainPane = new BorderPane();
		{
			GridPane buttonPane = new GridPane();
			setupButtonPane(buttonPane);
			mainPane.setLeft(buttonPane);

			detailTable = new TableView<Detail>();
			setupDetailTable();
			mainPane.setCenter(detailTable);
		}
		getDialogPane().setContent(mainPane);		
		getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		getDialogPane().setMinSize(500, 350);
		setResizable(true);
	}

	private void setupDetailTable() {
		TableColumn<Detail, String> nameCol = new TableColumn<Detail, String>("Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Detail, String>("name"));
		detailTable.getColumns().add(nameCol);
		
		TableColumn<Detail, String> descriptionCol = new TableColumn<Detail, String>("Description");
		descriptionCol.setCellValueFactory(new PropertyValueFactory<Detail, String>("description"));
		detailTable.getColumns().add(descriptionCol);
		
		TableColumn<Detail, String> valueCol = new TableColumn<Detail, String>("Values");
		valueCol.setCellValueFactory(new PropertyValueFactory<Detail, String>("values"));
		detailTable.getColumns().add(valueCol);
	}

	private void setupButtonPane(GridPane buttonPane) {
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(100);
		buttonPane.getColumnConstraints().add(c);
		
		Button insert = new Button("Insert");
		insert.setOnAction(e -> insert());
		insert.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(insert, 0, 0);

		Button edit = new Button("Edit");
		edit.setOnAction(e -> edit());
		edit.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(edit, 0, 1);

		Button delete = new Button("Delete");
		delete.setOnAction(e -> delete());
		delete.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(delete, 0, 2);

	}

	private void delete() {
		Detail detail = detailTable.getSelectionModel().getSelectedItem();
		if (detail != null && controller.deleteDetail(detail)) {
			bindData();
		}
	}

	private void edit() {
		Detail detail = detailTable.getSelectionModel().getSelectedItem();
		if (detail != null && controller.editDetail(detail)) {
			bindData();
		}
	}

	private void insert() {
		if (controller.insertDetail()) {
			bindData();
		}
	}

	@Override
	public void setController(DetailController controller) {
		this.controller = controller;
		bindData();
	}

	private void bindData() {
		detailTable.getItems().clear();
		List<Detail> details = controller.getContact().getDetailList();
		detailTable.getItems().addAll(details);
	}

	@Override
	public DialogResult showDialog() {
		showAndWait();
		return DialogResult.None;
	}

}
