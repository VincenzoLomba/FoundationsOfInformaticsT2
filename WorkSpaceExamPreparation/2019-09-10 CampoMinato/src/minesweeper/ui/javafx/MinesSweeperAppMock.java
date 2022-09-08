package minesweeper.ui.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import minesweeper.ui.controller.Controller;
import minesweeper.ui.controller.MyController;

public class MinesSweeperAppMock extends Application {

	private Controller controller;

	public void start(Stage stage) {
		controller = new MyController(5,3,"gameover.txt");
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
