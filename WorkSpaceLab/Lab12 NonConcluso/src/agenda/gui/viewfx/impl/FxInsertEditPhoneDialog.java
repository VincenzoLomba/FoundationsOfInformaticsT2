package agenda.gui.viewfx.impl;

import java.util.Optional;

import agenda.gui.view.DialogResult;
import agenda.gui.view.InsertEditDetailDialog;
import agenda.model.Detail;
import agenda.model.Phone;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FxInsertEditPhoneDialog extends Dialog<DialogResult> implements
		InsertEditDetailDialog {

	private TextField phoneTextbox;
	private TextField descriptionTextbox;

	public FxInsertEditPhoneDialog() {
		setTitle("Phone");
		setHeaderText("Inserisci/Modifica Telefono");
		
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
		Label phoneLabel = new Label();
		phoneLabel.setText("Telefono: ");
		grid.add(phoneLabel, 0, 0);
		phoneTextbox = new TextField();
		grid.add(phoneTextbox, 1, 0);

		Label descriptionLabel = new Label();
		descriptionLabel.setText("Descrizione: ");
		grid.add(descriptionLabel, 0, 1);
		descriptionTextbox = new TextField();
		grid.add(descriptionTextbox, 1, 1);
	}

	@Override
	public void setDetail(Detail detail) {
		Phone phone = (Phone) detail;
		phoneTextbox.setText(phone.getValue());
		descriptionTextbox.setText(phone.getDescription());
	}

	@Override
	public void updateDetail(Detail detail) {
		Phone phone = (Phone) detail;
		phone.setValue(adaptValue(phoneTextbox.getText()));
		phone.setDescription(adaptValue(descriptionTextbox.getText()));
	}

	@Override
	public DialogResult showDialog() {
		Optional<DialogResult> result = this.showAndWait();
		return result.isPresent() ? result.get() : DialogResult.Cancel;
	}

	private String adaptValue(String text) {
		return text == null ? " " : text;
	}
}
