package edlift.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import edlift.controller.Controller;
import edlift.controller.MyController;
import edlift.model.Lift;
import edlift.model.Installation;


public class EDLiftAppMock extends Application {

	Installation e1 = new Installation(
			"Hotel Miralago",  
			Lift.of("BASIC", -2, 4, 1.0));

	
	private Controller controller;

	public void start(Stage stage) {
		List<Installation> edifici = new ArrayList<>(); 
		edifici.add(e1);
		System.out.println("Elenco edifici acquisito");
		controller = new MyController(edifici); 
	
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
