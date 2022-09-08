package agenda.gui.viewfx.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Set;

import agenda.controller.AgendaController;
import agenda.gui.view.MainView;
import agenda.model.Contact;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class FxMainView implements MainView {
	private AgendaController controller;
	private Stage stage;
	private TableView<Contact> contactTable;
	private TextField searchField;

	public FxMainView(Stage stage) {
		this.stage = stage;

		BorderPane mainPane = new BorderPane();
		{
			GridPane buttonPane = new GridPane();
			setupButtonPane(buttonPane);
			mainPane.setLeft(buttonPane);
			
			BorderPane searchPane = new BorderPane();
			setupSearchPane(searchPane);
			mainPane.setTop(searchPane);

			contactTable = new TableView<Contact>();
			setupContactTable();
			mainPane.setCenter(contactTable);
		}

		stage.setScene(new Scene(mainPane));
		stage.setMinWidth(640);
		stage.setMinHeight(480);
		stage.setResizable(true);
	}

	private void setupSearchPane(BorderPane searchPane) {
		HBox hbox = new HBox();
		
		Button searchButton = new Button("Search");
		searchButton.setOnAction(e -> search(searchField.getText()));
		hbox.getChildren().add(searchButton);
		
		searchField = new TextField();
		hbox.getChildren().add(searchField);
		
		searchPane.setRight(hbox);
	}

	private void search(String query) {
		Set<Contact> contacts = controller.search(query);
		bindData(contacts);
	}

	private void setupContactTable() {
		TableColumn<Contact, String> firstNameCol = new TableColumn<Contact, String>("Name");
		firstNameCol.prefWidthProperty().bind(contactTable.widthProperty().multiply(0.5));
		firstNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("name"));
		contactTable.getColumns().add(firstNameCol);

		TableColumn<Contact, String> lastNameCol = new TableColumn<Contact, String>("Surname");
		lastNameCol.prefWidthProperty().bind(contactTable.widthProperty().multiply(0.5));
		lastNameCol.setCellValueFactory(new PropertyValueFactory<Contact, String>("surname"));
		contactTable.getColumns().add(lastNameCol);
	}

	private void setupButtonPane(GridPane buttonPane) {
		ColumnConstraints c = new ColumnConstraints();
		c.setPercentWidth(100);
		buttonPane.getColumnConstraints().add(c);

		Image openImage = new Image(getClass().getResourceAsStream("/resources/Open.png"));
		Button open = new Button("Open", new ImageView(openImage));
		open.setOnAction(e -> open());
		open.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(open, 0, 0);

		Image saveImage = new Image(getClass().getResourceAsStream("/resources/Save.png"));
		Button save = new Button("Save", new ImageView(saveImage));
		save.setOnAction(e -> controller.save());
		save.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(save, 0, 1);

		Image insertImage = new Image(getClass().getResourceAsStream("/resources/New.png"));
		Button insert = new Button("Insert", new ImageView(insertImage));
		insert.setOnAction(e -> insert());
		insert.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(insert, 0, 2);

		Image editImage = new Image(getClass().getResourceAsStream("/resources/Edit.png"));
		Button edit = new Button("Edit", new ImageView(editImage));
		edit.setOnAction(e -> edit());
		edit.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(edit, 0, 3);

		Image deleteImage = new Image(getClass().getResourceAsStream("/resources/Delete.png"));
		Button delete = new Button("Delete", new ImageView(deleteImage));
		delete.setOnAction(e -> delete());
		delete.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(delete, 0, 4);

		Button details = new Button("Details");
		details.setOnAction(e -> details());
		details.setMaxWidth(Double.MAX_VALUE);
		buttonPane.add(details, 0, 5);
	}

	private void details() {
		Contact selected = contactTable.getSelectionModel().getSelectedItem();
		if (selected != null) {
			controller.showDetails(selected);
		}
	}

	private void delete() {
		Contact selected = contactTable.getSelectionModel().getSelectedItem();
		if (selected != null) {
			controller.deleteContact(selected);
			bindData();
		}
	}

	private void edit() {
		Contact selected = contactTable.getSelectionModel().getSelectedItem();
		if (selected != null) {
			controller.editContact(selected);
			bindData();
		}
	}

	private void insert() {
		controller.insertContact();
		bindData();
	}

	private void open() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("."));
		fileChooser.setTitle("File Agenda");
		fileChooser.setSelectedExtensionFilter(
				new ExtensionFilter("Agenda", Arrays.asList("*.txt", "*.*")));
		File file = fileChooser.showOpenDialog(stage);

		if (file != null) {
			controller.loadContacts(file.getAbsolutePath());
			bindData();
		}
	}

	private void bindData() {
		bindData(controller.getAll());
	}
	
	private void bindData(Set<Contact> contacts) {
		contactTable.getItems().clear();
		contactTable.getItems().addAll(contacts);
	}

	@Override
	public void setController(AgendaController controller) {
		this.controller = controller;
		bindData();
	}

	@Override
	public void showView() {
		stage.show();
	}

}
