package agenda.gui.viewfx.impl;

import java.util.Optional;

import agenda.gui.view.DialogResult;
import agenda.gui.view.InsertEditDetailDialog;
import agenda.model.Detail;
import agenda.model.EMail;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FxInsertEditEmailDialog extends Dialog<DialogResult> implements
		InsertEditDetailDialog {

	private TextField emailTextbox;
	private TextField descriptionTextbox;

	public FxInsertEditEmailDialog() {
		setTitle("Email");
		setHeaderText("Inserisci/Modifica Email");
		
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
		Label emailLabel = new Label();
		emailLabel.setText("Indirizzo e-mail: ");
		grid.add(emailLabel, 0, 0);
		emailTextbox = new TextField();
		grid.add(emailTextbox, 1, 0);

		Label descriptionLabel = new Label();
		descriptionLabel.setText("Descrizione: ");
		grid.add(descriptionLabel, 0, 1);
		descriptionTextbox = new TextField();
		grid.add(descriptionTextbox, 1, 1);
	}

	@Override
	public void setDetail(Detail detail) {
		EMail email = (EMail) detail;
		emailTextbox.setText(email.getValue());
		descriptionTextbox.setText(adaptValue(email.getDescription()));
	}

	@Override
	public void updateDetail(Detail detail) {
		EMail email = (EMail) detail;
		email.setValue(adaptValue(emailTextbox.getText()));
		email.setDescription(adaptValue(descriptionTextbox.getText()));
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
