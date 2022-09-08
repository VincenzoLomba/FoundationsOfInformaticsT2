package agenda.gui.viewfx.impl;

import java.util.Optional;

import agenda.gui.view.DialogResult;
import agenda.gui.view.InsertEditDetailDialog;
import agenda.model.Address;
import agenda.model.Detail;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FxInsertEditAddressDialog extends Dialog<DialogResult> implements InsertEditDetailDialog {

	private TextField descriptionTextBox;
	private TextField streetTextBox;
	private TextField numberTextBox;
	private TextField zipCodeTextBox;
	private TextField cityTextBox;
	private TextField stateTextBox;

	public FxInsertEditAddressDialog() {
		setTitle("Indirizzo");
		setHeaderText("Inserisci/Modifica Indirizzo");

		GridPane grid = new GridPane();
		setupLabelsAndFields(grid);
		getDialogPane().setContent(grid);

		getDialogPane().getButtonTypes().add(ButtonType.OK);
		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

		this.setResultConverter(bt -> {
			return bt == ButtonType.OK ? DialogResult.Ok : DialogResult.Cancel;
		});
	}

	private void setupLabelsAndFields(GridPane grid) {
		Label descriptionLabel = new Label("Descrizione");
		grid.add(descriptionLabel, 0, 0);
		descriptionTextBox = new TextField();
		grid.add(descriptionTextBox, 1, 0);

		Label streetLabel = new Label("Strada");
		grid.add(streetLabel, 0, 1);
		streetTextBox = new TextField();
		grid.add(streetTextBox, 1, 1);

		Label numberLabel = new Label("Numero");
		grid.add(numberLabel, 0, 2);
		numberTextBox = new TextField();
		grid.add(numberTextBox, 1, 2);

		Label zipCodeLabel = new Label("CAP");
		grid.add(zipCodeLabel, 0, 3);
		zipCodeTextBox = new TextField();
		grid.add(zipCodeTextBox, 1, 3);

		Label cityLabel = new Label("Città:");
		grid.add(cityLabel, 0, 4);
		cityTextBox = new TextField();
		grid.add(cityTextBox, 1, 4);

		Label stateLabel = new Label("Stato");
		grid.add(stateLabel, 0, 5);
		stateTextBox = new TextField();
		grid.add(stateTextBox, 1, 5);
	}

	@Override
	public void setDetail(Detail detail) {
		Address model = (Address) detail;
		descriptionTextBox.setText(model.getDescription());
		streetTextBox.setText(model.getStreet());
		numberTextBox.setText(model.getNumber());
		cityTextBox.setText(model.getCity());
		stateTextBox.setText(model.getState());
		zipCodeTextBox.setText(model.getZipCode());
	}

	@Override
	public void updateDetail(Detail detail) {
		Address model = (Address) detail;
		model.setStreet(adaptValue(streetTextBox.getText()));
		model.setNumber(adaptValue(numberTextBox.getText()));
		model.setCity(adaptValue(cityTextBox.getText()));
		model.setState(adaptValue(stateTextBox.getText()));
		model.setZipCode(adaptValue(zipCodeTextBox.getText()));
		model.setDescription(adaptValue(descriptionTextBox.getText()));
	}

	private String adaptValue(String text) {
		return text == null ? " " : text;
	}

	@Override
	public DialogResult showDialog() {
		Optional<DialogResult> result = this.showAndWait();
		return result.isPresent() ? result.get() : DialogResult.Cancel;
	}
}
