package bussy.ui;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.Map;

import bussy.model.Fermata;
import bussy.model.Linea;
import bussy.model.LineaPaP;
import bussy.ui.controller.Controller;

public class BussyAppMock2 extends Application {
	
	public void start(Stage stage){
		Map<String,Linea> linee = Map.of(
				"72", new LineaPaP("72", Map.of(
						0,  new Fermata("40", "Porta San Mamolo"),
						3,  new Fermata("42", "Aldini"),
						5,  new Fermata("44", "Porta Saragozza"),
						17,  new Fermata("16", "Stazione Centrale")
						)),
				"73", new LineaPaP("73", Map.of(
						0,  new Fermata("43", "Porta San Mamolo"),
						21, new Fermata("16", "Stazione Centrale"),
						33,  new Fermata("33", "Porta Saragozza")
						))
				);
		// --- set up del controller con le linee caricate
		Controller controller = new Controller(linee);
		// --- retrieve available file trackings
		MainPane panel = new MainPane(controller);
		// --- set up scene
		stage.setTitle("Bussy MOCK - Avremmo viaggiato con trasporto, se avessimo letto i file..");
		Scene scene = new Scene(panel,Color.PINK);
		stage.setScene(scene);
		stage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}
