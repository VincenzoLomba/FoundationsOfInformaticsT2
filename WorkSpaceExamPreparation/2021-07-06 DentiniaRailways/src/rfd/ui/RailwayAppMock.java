package rfd.ui;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rfd.model.MyPointOfInterest;
import rfd.model.RailwayLine;

public class RailwayAppMock extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		stage.setTitle("MOCK - Rupestri Ferrovie di Dentinia");
	
		RailwayLine lineaBoVr = new RailwayLine(Map.of(
				"Verona Porta Nuova", new MyPointOfInterest("Verona Porta Nuova", "114+951"),
				"Bologna Centrale", new MyPointOfInterest("Bologna Centrale", "0+000")
				),
				new TreeSet<>(Set.of("Bologna Centrale", "Verona Porta Nuova")
				));
		RailwayLine lineaMoVr = new RailwayLine(Map.of(
				"Verona Porta Nuova", new MyPointOfInterest("Verona Porta Nuova", "97+858"),
				"Modena", new MyPointOfInterest("Modena", "0+000")
				),
				new TreeSet<>(Set.of("Modena", "Verona Porta Nuova")
				));
		RailwayLine lineaBoMi = new RailwayLine(Map.of(
				"Modena", new MyPointOfInterest("Modena", "36+932"),
				"Reggio Emilia", new MyPointOfInterest("Reggio Emilia", "61+435"),
				"Parma", new MyPointOfInterest("Parma", "89+741"),
				"Bologna Centrale", new MyPointOfInterest("Bologna Centrale", "0+000"),
				"Piacenza", new MyPointOfInterest("Piacenza", "146+823")
				),
				new TreeSet<>(Set.of("Bologna Centrale", "Parma", "Modena")
				));
		
		Set<RailwayLine> rwylines = Set.of(lineaBoVr, lineaMoVr, lineaBoMi);	

		rfd.controller.Controller controller = new rfd.controller.MyController(rwylines);
				
		MainPane mainPanel = new MainPane(controller);
		Scene scene = new Scene(mainPanel, 600, 400, Color.AQUAMARINE);
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	

}
