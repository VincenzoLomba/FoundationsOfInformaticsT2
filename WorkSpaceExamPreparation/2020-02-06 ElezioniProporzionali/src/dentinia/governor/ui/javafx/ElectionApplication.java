package dentinia.governor.ui.javafx;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import dentinia.governor.persistence.BadFileFormatException;
import dentinia.governor.persistence.MySeggiWriter;
import dentinia.governor.persistence.MyVotiReader;
import dentinia.governor.persistence.SeggiWriter;
import dentinia.governor.persistence.VotiReader;
import dentinia.governor.ui.controller.Controller;
import dentinia.governor.ui.controller.MyController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ElectionApplication extends Application {

	private Controller controller;

	public void start(Stage stage) {
		controller = createController();
		if (controller == null)
			return;
		stage.setTitle("Governatore di Dentinia");
		ElectionPane root = new ElectionPane(controller);

		Scene scene = new Scene(root, 750, 500, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	protected Controller createController() {
		try(Reader reader = new FileReader("Voti.txt")) {
			VotiReader votiReader = new MyVotiReader(reader);
			SeggiWriter seggiWriter = new MySeggiWriter("Report.txt");			
			return new MyController(votiReader.getElezioni(), seggiWriter);
		} catch (IOException e) {
			alert("Errore di I/O", "Impossibile aprire uno dei file", "Errore nell'apertura del reader o del writer");
			return null;
		} catch (BadFileFormatException e) {
			alert("Errore di I/O", "Errore di formato nel file", "Impossibile effettuare la lettura");
			return null;
		}
	}

	protected void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
