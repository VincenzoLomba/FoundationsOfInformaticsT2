package oroscopo;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import oroscopo.controller.AbstractController;
import oroscopo.controller.MyController;
import oroscopo.controller.MyStrategiaSelezione;
import oroscopo.persistence.BadFileFormatException;
import oroscopo.persistence.OroscopoRepository;
import oroscopo.persistence.TextFileOroscopoRepository;
import oroscopo.ui.MainPane;

public class OroscopoApplication extends Application {

	/**
	 * 
	 * @throws BadFileFormatException
	 * @throws IOException
	 */
	public void init() throws IOException, BadFileFormatException {

		// do nothing
	}

	@Override
	public void start(Stage stage) {
		stage.setTitle("Vedo, prevedo e stravedo");

		OroscopoRepository repository = createRepo();
		if (repository == null)
			return;

		AbstractController controller = createController(repository);
		if (controller == null)
			return;

		MainPane mainPanel = new MainPane(controller, 6);

		Scene scene = new Scene(mainPanel, 980, 480);
		stage.setScene(scene);
		stage.show();

	}

	protected AbstractController createController(OroscopoRepository repository) {
		try {

			return new MyController(repository, new MyStrategiaSelezione());

		} catch (IOException e) {
			showAlert("Errore di I/O");
			e.printStackTrace();

		} catch (IllegalArgumentException e) {
			showAlert("Formato del file errato");
			e.printStackTrace();
		}
		return null;
	}

	protected OroscopoRepository createRepo() {
		try (Reader reader = new FileReader("FrasiOroscopo.txt")) {

			return new TextFileOroscopoRepository(reader);

		} catch (IOException e) {
			showAlert("Errore di I/O");
			e.printStackTrace();

		} catch (BadFileFormatException e) {
			showAlert("Formato del file errato");
			e.printStackTrace();
		}
		return null;
	}

	private void showAlert(String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Errore");
		alert.setHeaderText("Impossibile leggere i dati");
		alert.setContentText(text);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		System.setProperty("java.locale.providers", "JRE");
		launch(args);
	}
}
