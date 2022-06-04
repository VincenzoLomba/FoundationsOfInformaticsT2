package edlift.ui;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import edlift.controller.Controller;
import edlift.controller.MyController;
import edlift.model.Installation;
import edlift.persistence.BadFileFormatException;
import edlift.persistence.InstallationReader;
import edlift.persistence.MyInstallationReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class EDLiftApp extends Application {

	private Controller controller;

	public void start(Stage stage) {
		try {
			InstallationReader myReader = new MyInstallationReader();
			List<Installation> installations = myReader.readAll(new FileReader("Installazioni.txt")); 
			System.out.println("Installation list read " + installations);
			controller = new MyController(installations);
		} catch ( BadFileFormatException  | IOException e) {
			Controller.alert("File not found", "Error when creating controller", e.getMessage());
			System.exit(1);
		} 
	
		stage.setTitle("EDLift simulator");
		MainPane root = new MainPane(controller, stage);
		Scene scene = new Scene(root, 820, 550, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
	
}
