package agenda.gui.viewfx.impl;

import java.util.Optional;

import agenda.gui.view.DialogResult;
import agenda.gui.view.InsertEditContactDialog;
import agenda.model.Contact;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class FxInsertEditContactDialog extends Dialog<DialogResult> implements InsertEditContactDialog {

	private TextField text1;
	private TextField text2;

	public FxInsertEditContactDialog() {
		setTitle("Contact");
		setHeaderText("Insert your new contact");
		setResizable(true);

		Label label1 = new Label("Name: ");
		Label label2 = new Label("Surname: ");
		text1 = new TextField();
		text2 = new TextField();

		GridPane grid = new GridPane();
		grid.add(label1, 0, 0);
		grid.add(text1, 1, 0);
		grid.add(label2, 0, 1);
		grid.add(text2, 1, 1);
		getDialogPane().setContent(grid);

		getDialogPane().getButtonTypes().add(ButtonType.OK);
		getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
		
		this.setResultConverter(bt -> {
			return bt == ButtonType.OK ? DialogResult.Ok : DialogResult.Cancel;
		});
	}

	@Override
	public void setContact(Contact contact) {
		text1.setText(adaptValue(contact.getName()));
		text2.setText(adaptValue(contact.getSurname()));
		
	}

	@Override
	public void updateContact(Contact contact) {
		contact.setName(adaptValue(text1.getText()));
		contact.setSurname(adaptValue(text2.getText()));
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
