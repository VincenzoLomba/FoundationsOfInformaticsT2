package dentinia.governor.ui.controller;

import java.io.IOException;

import dentinia.governor.model.Elezioni;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public abstract class Controller {

	public abstract Elezioni getElezioni();
	
	public abstract Elezioni ricalcola(double sbarramento);

	public abstract void salvaSuFile(String msg) throws IOException;

	public void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

}
