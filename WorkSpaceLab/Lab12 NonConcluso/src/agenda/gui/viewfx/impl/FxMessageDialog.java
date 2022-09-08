package agenda.gui.viewfx.impl;

import agenda.gui.view.MessageDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class FxMessageDialog implements MessageDialog {

	@Override
	public void showMessage(String title, String message) {
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.CLOSE);
		alert.setHeaderText(title);
		alert.showAndWait();
	}

}
