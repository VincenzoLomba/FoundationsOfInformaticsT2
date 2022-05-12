package myfitnessdiary;

import java.io.FileReader;
import java.io.IOException;

import myfitnessdiary.controller.Controller;
import myfitnessdiary.controller.MyController;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.MyFitnessDiary;
import myfitnessdiary.persistence.ActivityRepository;
import myfitnessdiary.persistence.BadFileFormatException;
import myfitnessdiary.persistence.MyReportWriter;
import myfitnessdiary.persistence.TextFileActivityRepository;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;

public class MyFitnessDiaryApplication extends Application {

	public void start(Stage stage) {
		Controller controller = createController();
		if (controller == null)
			return;
		
		stage.setTitle("My Fitness Diary");
		MyFitnessPane root = new MyFitnessPane(controller);

		Scene scene = new Scene(root, 980, 480, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}
	
	protected Controller createController() {		
		ActivityRepository activityRepository;
		try (FileReader reader = new FileReader("Attivita.txt")) {
			
			activityRepository = new TextFileActivityRepository(reader);		
			
		} catch (IOException e) {
			alert("Errore di I/O", "Impossibile leggere i dati", "Errore nell'apertura del reader");
			return null;
		} catch (BadFileFormatException e) {
			alert("Errore di formato", "Impossibile decodificare i dati", "Bad file format");
			return null;
		}

		FitnessDiary fitnessDiary = new MyFitnessDiary();
		return new MyController(fitnessDiary, 
				activityRepository, 
				new MyReportWriter());
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
