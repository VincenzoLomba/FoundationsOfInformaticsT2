package agenda.gui.viewfx.impl;

import java.util.Optional;

import agenda.gui.view.DialogResult;
import agenda.gui.view.YesNoQuestionDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class FxYesNoQuestionDialog implements YesNoQuestionDialog {

	@Override
	public DialogResult showQuestion(String title, String message) {
		Alert dialog = new Alert(AlertType.CONFIRMATION, message, ButtonType.YES, ButtonType.NO);
		dialog.setTitle(title);
		dialog.setHeaderText(title);
		
		Optional<ButtonType> result = dialog.showAndWait();
		return result.isPresent() && result.get() == ButtonType.YES
				? DialogResult.Yes
				: DialogResult.No;
	}

}
