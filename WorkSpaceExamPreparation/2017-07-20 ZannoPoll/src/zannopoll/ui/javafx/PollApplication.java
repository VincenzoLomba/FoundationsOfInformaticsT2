package zannopoll.ui.javafx;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import zannopoll.persistence.BadFileFormatException;
import zannopoll.persistence.MySondaggioReader;
import zannopoll.persistence.SondaggioReader;
import zannopoll.ui.controller.Controller;
import zannopoll.ui.controller.DialogManager;
import zannopoll.ui.controller.MyController;

public class PollApplication extends Application implements DialogManager {

	private Controller controller;

	public void start(Stage stage) {
		controller = createController();
		if (controller == null)
			return;
		stage.setTitle("ZannoPoll - Sondaggi di opinione");
		MyPollPane root = new MyPollPane(controller);

		Scene scene = new Scene(root, 650, 610, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}

	protected Controller createController() {
		try(Reader reader = new FileReader("RisultatoSondaggio.txt")) {
			SondaggioReader myReader = new MySondaggioReader();			
			return new MyController(myReader, reader, this);
		} catch (IOException e) {
			alert("Errore di I/O", "Impossibile aprire il file", "Errore nell'apertura del reader");
			return null;
		} catch (BadFileFormatException e) {
			alert("Errore di I/O", "Errore di formato nel file", "Impossibile effettuare la lettura");
			return null;
		}
	}

	public void alert(String title, String headerMessage, String contentMessage) {
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
