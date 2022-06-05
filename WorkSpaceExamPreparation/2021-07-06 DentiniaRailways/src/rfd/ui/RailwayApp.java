package rfd.ui;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import rfd.model.RailwayLine;
import rfd.persistence.MyRailwayLineReader;
import rfd.persistence.RailwayLineReader;


public class RailwayApp extends Application {

	public static void alert(String title, String headerMessage, String contentMessage) {
		javafx.scene.control.Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(headerMessage);
		alert.setContentText(contentMessage);
		alert.showAndWait();
	}
	
	@Override
	public void start(Stage stage) {
		
		stage.setTitle("Rupestri Ferrovie di Dentinia");
	
		Set<RailwayLine> rwylines = new HashSet<>();	
		try {
				RailwayLineReader railwayReader = new MyRailwayLineReader();
				List<String> lineNames = RailwayLineReader.getAllLineNames(Path.of(".")).get();
				for (String lineName : lineNames) {
					RailwayLine rwyline = railwayReader.getRailwayLine(new FileReader(lineName));
					rwylines.add(rwyline);
				}
				rfd.controller.Controller controller = new rfd.controller.MyController(rwylines);
				
				MainPane mainPanel = new MainPane(controller);
				Scene scene = new Scene(mainPanel, 600, 400, Color.AQUAMARINE);
				stage.setScene(scene);
				stage.show();
				
		} catch (IOException e) {
			alert(
					"ERRORE DI I/O",
					"Errore di lettura: impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		} catch (IllegalArgumentException e) {
			alert(
					"ERRORE DI I/O",
					"Formato dei file errato: impossibile leggere i dati",
					"Dettagli: " + e.getMessage());
		}
	}


	public static void main(String[] args) {
		launch(args);
	}
	

}
