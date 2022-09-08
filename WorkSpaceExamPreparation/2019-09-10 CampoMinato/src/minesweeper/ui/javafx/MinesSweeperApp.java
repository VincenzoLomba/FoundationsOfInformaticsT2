package minesweeper.ui.javafx;

import java.io.FileNotFoundException;
import java.io.FileReader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper.persistence.BadFileFormatException;
import minesweeper.persistence.ConfigReader;
import minesweeper.persistence.MyConfigReader;
import minesweeper.ui.controller.Controller;
import minesweeper.ui.controller.MyController;

public class MinesSweeperApp extends Application {

	private Controller controller;

	public void start(Stage stage) {
		try {
			ConfigReader configReader = new MyConfigReader(new FileReader("config.txt"));
			controller = new MyController(configReader.getSize(), configReader.getMinesNumber(),"gameover.txt");
			System.out.println("Configuration read");
		} catch (FileNotFoundException | BadFileFormatException e) {
			controller = new MyController(5,3,"gameover.txt");
			System.out.println("Configuration not read, using defaults");
		}
		if (controller == null) {
			Controller.alert("Errore irrecuperabile", "Errore nella creazione del controller", "Addio");
			System.exit(1);
		}
		stage.setTitle("Mines Sweeper");
		MinesSweeperPane root = new MinesSweeperPane(controller, stage);
		
		Scene scene = new Scene(root, 450, 300, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}

}
