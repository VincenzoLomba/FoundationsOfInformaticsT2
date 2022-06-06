package battleship.ui;


import java.io.FileReader;
import java.io.IOException;

import battleship.controller.Controller;
import battleship.controller.MyController;
import battleship.model.ShipBoard;
import battleship.persistence.BadFileFormatException;
import battleship.persistence.MyBattleshipReader;
import battleship.persistence.BattleshipReader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BattleshipApp extends Application {

	private Controller controller;

	public void start(Stage stage) {
		try {
			BattleshipReader myReader = new MyBattleshipReader();
			ShipBoard solutionBoard = myReader.getSolutionBoard(new FileReader("battlefield.txt"));
			System.out.println("Soluzione acquisita");
			ShipBoard initialBoard = myReader.getPlayerBoard(new FileReader("initialfield.txt"));
			System.out.println("Configurazione iniziale acquisita");
			controller = new MyController(solutionBoard,initialBoard);
		} catch ( BadFileFormatException  | IOException e) {
			Controller.alert("File non trovato", "Errore nella creazione del controller", e.getMessage());
			System.exit(1);
		} 
	
		stage.setTitle("Battaglia navale");
		MainPane root = new MainPane(controller, stage);
		Scene scene = new Scene(root, 820, 550, Color.ALICEBLUE);
		stage.setScene(scene);
		stage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
	
}
