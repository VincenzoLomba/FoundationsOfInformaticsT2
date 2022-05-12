package myfitnessdiary;

import java.awt.Color;
import java.io.Writer;
import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import myfitnessdiary.controller.Controller;
import myfitnessdiary.controller.MyController;
import myfitnessdiary.controller.test.ActivityRepositoryMock;
import myfitnessdiary.model.FitnessDiary;
import myfitnessdiary.model.MyFitnessDiary;
import myfitnessdiary.persistence.ActivityRepository;
import myfitnessdiary.persistence.ReportWriter;

public class MockFitnessDiaryApplication extends Application {
	
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
	
	protected void alert(String title, String headerMessage, String contentMessage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
 
	protected Controller createController() {
		ActivityRepository activityRepository = new ActivityRepositoryMock();

		FitnessDiary fitnessDiary = new MyFitnessDiary();
		ReportWriter reportWriter = new ReportWriter() {
			@Override
			public void printReport(Writer writer, LocalDate date, FitnessDiary diary) {
				// do nothing
			}			
		}	;
		return new MyController(fitnessDiary, 
				activityRepository, 
				reportWriter);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
