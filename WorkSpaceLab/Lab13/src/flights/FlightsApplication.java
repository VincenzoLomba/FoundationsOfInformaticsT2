package flights;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import flights.controller.MyController;
import flights.persistence.BadFileFormatException;
import flights.persistence.DataManager;
import flights.persistence.MyAircraftsReader;
import flights.persistence.MyCitiesReader;
import flights.persistence.MyFlightScheduleReader;
import flights.ui.MainPane;

public class FlightsApplication extends Application {
	
	@Override
	public void init(){
		// do nothing
	}

	@Override
	public void start(Stage stage) {
		DataManager dataManager = createDataManager();
		if (!readData(dataManager))
			return;

		MyController controller = new MyController(dataManager);
		stage.setTitle("Flights Scheduler");
		
		BorderPane root = new MainPane(controller);
		Scene scene = new Scene(root, 980, 480);
		stage.setScene(scene);
		stage.show();
	}

	private boolean readData(DataManager dataManager) {
		try {
			dataManager.readAll();
			return true;
		} catch (IOException e) {
			showAlert("Errore di I/O");
			e.printStackTrace();
		} catch (BadFileFormatException e) {
			showAlert("Formato del file errato");
			e.printStackTrace();
		}
		return false;
	}

	private void showAlert(String text) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Errore");
		alert.setHeaderText("Impossibile leggere i dati");
		alert.setContentText(text);
		alert.showAndWait();
	}

	protected DataManager createDataManager() {
		MyCitiesReader citiesReader = new MyCitiesReader();
		MyAircraftsReader aircraftsReader = new MyAircraftsReader();
		MyFlightScheduleReader flightScheduleReader = new MyFlightScheduleReader();

		DataManager dataManager = new DataManager(citiesReader, 
				aircraftsReader, flightScheduleReader);
		return dataManager;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
